import React from 'react';
import { Form, Col, Button } from 'react-bootstrap';
import Request from '../../services/Request';
import PopupDialog from '../../components/PopupDialog';

class TransferPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            tcno: '',
            amount: '',
            isProcessingTransfer: false,
            isShowingPopup: false,
            popupTitle: 0,
            popupMessage: ''
        };

        this.handleTcNoChange = this.handleTcNoChange.bind(this);
        this.handleAmountChange = this.handleAmountChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
    }

    handleTcNoChange(e) {
        this.setState({ tcno: e.target.value });
    }

    handleAmountChange(e) {
        this.setState({ amount: e.target.value });
    }

    handleClick() {
        this.setState({
            isProcessingTransfer: true
        });

        const request = new Request().getRequestInstance();
        request.post('transfer/make',
            {
                senderUsername: "Mert",
                receiverTcno: this.state.tcno,
                currency: 'TRY',
                amount: this.state.amount
            }).then((response) => {
                console.log(response);
                this.setState({
                    isShowingPopup: true, popupTitle: 2, popupMessage:
                        'Successfully transfered ' + response.data.amount + " " + response.data.currency + " to " + this.state.tcno + "."
                });
                this.props.onOwnedCurrenciesUpdated();
            }).catch((error) => {
                console.log(error);
                var errorMessage = 'Network error';
                if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                    errorMessage = error.response.data.message;
                }
                this.setState({ isShowingPopup: true, popupTitle: 0, popupMessage: errorMessage });
            }).finally(() => {
                this.setState({
                    isProcessingTransfer: false
                });
            });
    }

    render() {

        const formStyle = {
            marginTop: '5%'
        };

        const popupDialog = this.state.isShowingPopup ?
            <PopupDialog callback={() => this.setState({ isShowingPopup: false })} title={this.state.popupTitle} message={this.state.popupMessage} isAnswerable={false} />
            : null;

        return (
            <div>
                {popupDialog}
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
                            <CurrencyDropdown />
                        </Form.Group>
                        <Form.Group as={Col} md="10">
                            <Form.Label>Amount</Form.Label>
                            <Form.Control type="text" placeholder="Amount" onChange={this.handleAmountChange} value={this.state.amount} maxLength="6" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row className="justify-content-end">
                        <Button variant="primary" size="lg" onClick={this.handleClick} disabled={this.state.isProcessingTransfer}>Transfer</Button>
                    </Form.Row>
                </Form>
            </div >
        )
    }
}

class CurrencyDropdown extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            currencies: []
        }
    }

    componentDidMount() {
        const request = new Request().getRequestInstance('https://api.exchangeratesapi.io');
        request.get('/latest?base=TRY')
            .then((response) => {
                this.setState({ currencies: response.data.rates });
                console.log(this.state.currencies);
            }).catch((error) => {
                console.log(error);
            });
    }


    render() {

        const currencies = Object.keys(this.state.currencies).map((keyName, index) =>
            <CurrencyDropdownItem key={index} currency={keyName} />
        );

        return (
            <Form.Control as="select">
                {currencies}
            </Form.Control>
        )
    }

}

class CurrencyDropdownItem extends React.Component {

    render() {
        return (
            <option>{this.props.currency}</option>
        )
    }

}

export default TransferPage;