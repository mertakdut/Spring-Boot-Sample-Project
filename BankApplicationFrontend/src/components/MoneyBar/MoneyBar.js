import React from 'react';
import { Alert } from 'react-bootstrap'
import { connect } from 'react-redux'

import Request from '../../services/Request'
import { showDialog, currenciesUptodate } from '../../actions';

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
            ownedCurrencies: null
        };

        this.retrieveWealthAndUpdateState = this.retrieveWealthAndUpdateState.bind(this);
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
            request.post('wealth/retrieve', { username: this.props.loggedInUsername })
                .then((response) => {
                    this.setState({ ownedCurrencies: [] });
                    Object.keys(response.data.wealthMap).map((key) => {
                        if (response.data.wealthMap[key] == 0) delete response.data.wealthMap[key];
                    });
                    this.setState({ ownedCurrencies: response.data.wealthMap });
                    this.props.currenciesUptodate();
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

    render() {

        var barMessage = "";
        if (this.state.ownedCurrencies != null) {
            if (Object.keys(this.state.ownedCurrencies).length === 0) {
                barMessage = "Possessions make you rich? Your richness is life, forever."
            } else {
                barMessage += "You have ";
                Object.keys(this.state.ownedCurrencies).forEach((key, index, array) => {
                    barMessage += this.state.ownedCurrencies[key] + " " + key + ((array.length - 1) != index ? ", " : ".");
                });
            }
        }

        const popupDialog = barMessage !== "" ?
            <Alert className="text-center" variant="primary">
                {barMessage}
            </Alert> : null;

        return (
            <div>
                {popupDialog}
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(MoneyBar);