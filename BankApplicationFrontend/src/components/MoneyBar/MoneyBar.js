import React from 'react';
import { Alert } from 'react-bootstrap'
import { connect } from 'react-redux'
import Request from '../../services/Request'
import { showDialog, currenciesUptodate } from '../../actions';
import { URL_RETRIEVEWEALTH } from '../../config/constants'
import NumberFormat from 'react-number-format'

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
            ownedCurrencies: null
        }

        this.retrieveWealthAndUpdateState = this.retrieveWealthAndUpdateState.bind(this);
    }

    componentDidMount() {
        if (this.props.loggedInUsername != null) {
            this.retrieveWealthAndUpdateState();
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.loggedInUsername != prevProps.loggedInUsername || this.props.isCurrenciesObsolete) {
            if (this.props.loggedInUsername != null) {
                this.retrieveWealthAndUpdateState();
            } else {
                this.setState({ ownedCurrencies: null });
            }
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
                    this.setState({ ownedCurrencies: response.data.wealthMap });
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
        if (this.state.ownedCurrencies == null) {
            return null;
        } else if (Object.keys(this.state.ownedCurrencies).length === 0) {
            return <Alert className="text-center" variant="primary">Possessions make you rich? Your richness is life, forever.</Alert>
        } else {
            const barMessage = Object.keys(this.state.ownedCurrencies).map((keyName, index, array) => (
                <NumberFormat key={keyName} value={this.state.ownedCurrencies[keyName]} displayType={'text'} thousandSeparator={true}
                    suffix={" " + keyName + ((array.length - 1) != index ? ", " : ".")} />))

            return <Alert className="text-center" variant="primary">
                {barMessage}
            </Alert>
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(MoneyBar);