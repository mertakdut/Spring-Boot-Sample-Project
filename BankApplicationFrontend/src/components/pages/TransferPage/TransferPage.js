import React from 'react';
import { connect } from 'react-redux'
import { Form, Col, Button } from 'react-bootstrap';
import Request from '../../../services/Request';
import { ERROR_ALLFIELDSMANDATORY, ERROR_TCNOLENGTH, URL_MAKETRANSFER } from '../../../config/constants'
import { showDialog, currenciesObsolete } from '../../../actions';
import CurrencyDropdown from './CurrencyDropdown/CurrencyDropdown'

const mapStateToProps = state => ({
    loggedInUsername: state.login
})

const mapDispatchToProps = dispatch => ({
    currenciesNeedUpdate: () => dispatch(currenciesObsolete),
    showPopup: (title, message) => dispatch(showDialog(title, message))
})

class TransferPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            tcno: '',
            currency: '',
            amount: '',
            isProcessingTransfer: false
        };

        this.handleTcNoChange = this.handleTcNoChange.bind(this);
        this.handleAmountChange = this.handleAmountChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
        this.handleCurrencyChange = this.handleCurrencyChange.bind(this);
    }

    handleTcNoChange(e) {
        this.setState({ tcno: e.target.value });
    }

    handleCurrencyChange(value) {
        this.setState({ currency: value });
    }

    handleAmountChange(e) {
        this.setState({ amount: e.target.value });
    }

    handleClick() {
        if (this.state.currency == '' || this.state.tcno == '' || this.state.firstPass == '') {
            this.props.showPopup(1, ERROR_ALLFIELDSMANDATORY);
        } else if (this.state.tcno.length != 11) {
            this.props.showPopup(1, ERROR_TCNOLENGTH + this.state.tcno.length);
        } else {
            this.setState({
                isProcessingTransfer: true
            });

            const request = new Request().getRequestInstance();
            request.post(URL_MAKETRANSFER,
                {
                    senderUsername: this.props.loggedInUsername,
                    receiverTcno: this.state.tcno,
                    currency: this.state.currency,
                    amount: this.state.amount
                }).then((response) => {
                    console.log(response);
                    this.props.showPopup(2, 'Successfully transfered ' + response.data.amount + " " + response.data.currency + " to " + this.state.tcno + ".");
                    this.props.currenciesNeedUpdate();
                }).catch((error) => {
                    console.log(error.response != null ? error.response : error);
                    var errorMessage = 'Network error';
                    if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                        errorMessage = error.response.data.message;
                    }
                    this.props.showPopup(0, errorMessage);
                }).finally(() => {
                    this.setState({
                        isProcessingTransfer: false
                    });
                });
        }
    }

    render() {

        const formStyle = {
            marginTop: '5%'
        };

        return (
            <Form style={formStyle}>
                <Form.Row>
                    <Form.Group as={Col} md="12">
                        <Form.Label>Receiver TC</Form.Label>
                        <Form.Control type="text" placeholder="Receiver TC" onChange={this.handleTcNoChange} value={this.state.tcno} maxLength="11" />
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} md="2">
                        <Form.Label>Currency</Form.Label>
                        <CurrencyDropdown onDropdownChanged={this.handleCurrencyChange} />
                    </Form.Group>
                    <Form.Group as={Col} md="10">
                        <Form.Label>Amount</Form.Label>
                        <Form.Control type="text" placeholder="Amount" onChange={this.handleAmountChange} value={this.state.amount} maxLength="8" />
                    </Form.Group>
                </Form.Row>
                <Form.Row className="justify-content-end">
                    <Button variant="primary" size="lg" onClick={this.handleClick} disabled={this.state.isProcessingTransfer}>Transfer</Button>
                </Form.Row>
            </Form>
        )
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(TransferPage)