import React from 'react';
import { Alert } from 'react-bootstrap'
import { connect } from 'react-redux'
import Request from '../../services/Request'
import { showDialog, currenciesUptodate } from '../../actions';
import { URL_RETRIEVEWEALTH } from '../../config/constants'

const mapStateToProps = state => ({
    loggedInUsername: state.login,
    isCurrenciesObsolete: state.currencies
})

const mapDispatchToProps = dispatch => ({
    showPopup: (title, message) => dispatch(showDialog(title, message)),
    currenciesUptodate: () => dispatch(currenciesUptodate),
})

class MoneyBar extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isProcessing: false,
            barMessage: ''
        }

        this.retrieveWealthAndUpdateState = this.retrieveWealthAndUpdateState.bind(this);
        this.setBarMessage = this.setBarMessage.bind(this);
    }

    componentDidMount() {
        if (this.props.loggedInUsername != null) {
            this.retrieveWealthAndUpdateState();
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.loggedInUsername != null && (this.props.loggedInUsername != prevProps.loggedInUsername || this.props.isCurrenciesObsolete)) {
            this.retrieveWealthAndUpdateState();
        }
    }

    retrieveWealthAndUpdateState() {
        if (!this.state.isProcessing) {
            this.setState({ isProcessing: true });

            const request = new Request().getRequestInstance();
            request.post(URL_RETRIEVEWEALTH, { username: this.props.loggedInUsername })
                .then((response) => {
                    Object.keys(response.data.wealthMap).map((key) => {
                        if (response.data.wealthMap[key] == 0) delete response.data.wealthMap[key];
                    });
                    this.props.currenciesUptodate();
                    this.setBarMessage(response.data.wealthMap);
                }).catch((error) => {
                    console.log(error);
                    var errorMessage = 'Network error.';
                    if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                        errorMessage = error.response.data.message;
                    }
                    this.props.showPopup(0, errorMessage);
                }).finally(() => {
                    this.setState({ isProcessing: false });
                });
        }
    }

    setBarMessage(ownedCurrencies) {
        let barMessage = "";
        if (ownedCurrencies != null) {
            if (Object.keys(ownedCurrencies).length === 0) {
                barMessage = "Possessions make you rich? Your richness is life, forever."
            } else {
                barMessage += "You have ";
                Object.keys(ownedCurrencies).forEach((key, index, array) => {
                    barMessage += ownedCurrencies[key] + " " + key + ((array.length - 1) != index ? ", " : ".");
                });
            }
        }
        this.setState({ barMessage: barMessage });
    }

    render() {
        if (this.state.barMessage != "") {
            return <Alert className="text-center" variant="primary">{this.state.barMessage}</Alert>
        }

        return null;
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(MoneyBar);