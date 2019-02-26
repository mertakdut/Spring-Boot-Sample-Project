import React from 'react';
import axios from 'axios';
import apiConfig from '../../config/client';
import { Table, Button, ButtonToolbar, Modal, InputGroup, FormControl } from 'react-bootstrap';
import NumberFormat from 'react-number-format';

import PopupDialog from '../../components/PopupDialog';

class CurrencyPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = { currencies: [] };
    }

    componentDidMount() {
        axios.get('https://api.exchangeratesapi.io/latest?base=TRY')
            .then((response) => {
                // console.log(response);
                this.setState({ currencies: response.data.rates });
            }).catch((error) => {
                console.log(error.response);
            });
    }

    render() {
        return (
            <div>
                <CurrencyList currencies={this.state.currencies} onOwnedCurrenciesUpdated={this.props.onOwnedCurrenciesUpdated} />
            </div>
        )
    }
}

class CurrencyList extends React.Component {
    render() {
        console.log(this.props.currencies);
        const currencies = Object.keys(this.props.currencies).map((keyName, index) => (
            <Currency key={keyName} currency={keyName} rate={this.props.currencies[keyName]} index={index} onOwnedCurrenciesUpdated={this.props.onOwnedCurrenciesUpdated} />
        ))

        return (
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Value</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {currencies}
                </tbody>
            </Table>
        )
    }
}

class Currency extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.currency}</td>
                <td>{this.props.rate}</td>
                <td>
                    <ButtonToolbar>
                        <BuySellButton isBuying={true} index={this.props.index} currency={this.props.currency} rate={this.props.rate} onOwnedCurrenciesUpdated={this.props.onOwnedCurrenciesUpdated} />
                        <BuySellButton isBuying={false} index={this.props.index} currency={this.props.currency} rate={this.props.rate} onOwnedCurrenciesUpdated={this.props.onOwnedCurrenciesUpdated} />
                    </ButtonToolbar>
                </td>
            </tr>
        )
    }
}

class BuySellButton extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isShowingBuyPopup: false,
            isShowingPopup: false,
            popupTitle: 0,
            popupMessage: ''
        };

        this.onBuyingPopupClosed = this.onBuyingPopupClosed.bind(this);
    }

    onBuyingPopupClosed(isProcessed, message, statusCode) {
        console.log(isProcessed + "::" + message);

        this.setState({ isShowingBuyPopup: false });
        if (isProcessed) {
            if (message != null) {
                this.setState({ isShowingPopup: true, popupTitle: statusCode, popupMessage: message });
            }

            if (statusCode != 0) {
                this.props.onOwnedCurrenciesUpdated();
            }
        }
    }

    render() {

        const popupDialog = this.state.isShowingPopup ?
            <PopupDialog callback={() => this.setState({ isShowingPopup: false })} title={this.state.popupTitle} message={this.state.popupMessage} isAnswerable={false} />
            : null;

        const buyDialog = this.state.isShowingBuyPopup ?
            <BuySellModal isBuying={this.props.isBuying} currency={this.props.currency} rate={this.props.rate} callback={this.onBuyingPopupClosed} />
            : null;

        return (
            <div>
                {popupDialog}
                {buyDialog}
                <Button className="mx-1" variant={this.props.isBuying ? 'success' : 'danger'} onClick={() => { this.setState({ isShowingBuyPopup: true }) }}>
                    {this.props.isBuying ? 'Buy' : 'Sell'}
                </Button>
            </div>
        )
    }
}

class BuySellModal extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            show: true,
            inputAmount: '',
            convertedAmount: '0.00',
            isProcessingTransaction: false
        };

        this.onInputAmountChange = this.onInputAmountChange.bind(this);
        this.handlePositive = this.handlePositive.bind(this);
    }

    onInputAmountChange(e) {
        this.setState({
            inputAmount: e.target.value,
            convertedAmount: (e.target.value * this.props.rate).toFixed(2)
        });
    }

    handlePositive() {

        this.setState({
            isProcessingTransaction: true
        });

        axios.post(apiConfig.apiBaseUrl + 'transaction/make', {
            username: "Mert",
            buying: this.props.isBuying,
            currency: this.props.currency,
            amount: this.state.inputAmount
        }).then((response) => {
            console.log(response);
            this.props.callback(true, (this.props.isBuying ? "Bought " : "Sold ") + response.data.amount + " " + response.data.currency + ".", 2);
        }).catch((error) => {
            var errorMessage = 'Network Error';
            if (error != null && error.response != null) {
                console.log(error.response);
                if (error.response.data != null && error.response.data.message != null) {
                    errorMessage = error.response.data.message;
                }
            }
            this.props.callback(true, errorMessage, 0);
        }).finally(() => {
            this.setState({
                show: false
            });
        });
    }

    render() {
        return (
            <Modal show={this.state.show} onHide={() => { this.props.callback(false); }}>
                <Modal.Header closeButton>
                    <Modal.Title>{this.props.isBuying ? 'Buying' : 'Selling'} {this.props.currency}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <InputGroup className="mb-3">
                        <FormControl placeholder="Enter amount" onChange={this.onInputAmountChange} maxLength={6} />
                        <InputGroup.Append>
                            <InputGroup.Text>
                                <NumberFormat value={this.state.convertedAmount} displayType={'text'} thousandSeparator={true} suffix={'TL'} />
                            </InputGroup.Text>
                        </InputGroup.Append>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => { this.props.callback(false); }}>Cancel</Button>
                    <Button variant="success" onClick={this.handlePositive} disabled={this.state.isProcessingTransaction || isNaN(this.state.inputAmount)}>{this.props.isBuying ? 'Buy' : 'Sell'}</Button>
                </Modal.Footer>
            </Modal>
        );
    }

}

export default CurrencyPage;