import React from 'react'
import { connect } from 'react-redux'
import NumberFormat from 'react-number-format'
import { Modal, InputGroup, FormControl, Button } from 'react-bootstrap'
import Request from '../../../../../../services/Request'
import {URL_MAKETRANSACTION} from '../../../../../../config/constants'

const mapStateToProps = state => ({
    loggedInUsername: state.login != null ? state.login.username : null,
    token: state.login != null ? state.login.token : null
})

class BuySellModal extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
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
            convertedAmount: (e.target.value / this.props.rate).toFixed(2)
        });
    }

    handlePositive() {

        this.setState({
            isProcessingTransaction: true
        });

        const request = new Request().getRequestInstance(null, this.props.token);
        request.post(URL_MAKETRANSACTION,
            {
                username: this.props.loggedInUsername,
                buying: this.props.isBuying,
                currency: this.props.currency,
                amount: this.state.inputAmount
            }).then((response) => {
                this.props.callback(true, (this.props.isBuying ? "Bought " : "Sold ") + response.data.transaction.amount + " " + response.data.transaction.currency + ".", 2);
            }).catch((error) => {
                console.log(error.response != null ? error.response : error);
                var errorMessage = 'Network Error';
                if (error != null && error.response != null) {
                    console.log(error.response);
                    if (error.response.data != null && error.response.data.message != null) {
                        errorMessage = error.response.data.message;
                    }
                }
                this.props.callback(true, errorMessage, 0);
            });
    }

    render() {
        return (
            <Modal show={true} onHide={() => { this.props.callback(false); }}>
                <Modal.Header closeButton>
                    <Modal.Title>{this.props.isBuying ? 'Buying' : 'Selling'} {this.props.currency}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <InputGroup className="mb-3">
                        <FormControl placeholder="Enter amount" onChange={this.onInputAmountChange} maxLength={8} />
                        <InputGroup.Append>
                            <InputGroup.Text>
                                <NumberFormat value={this.state.convertedAmount} displayType={'text'} thousandSeparator={true} prefix={this.props.isBuying ? '-' : '+'} suffix={'TL'} />
                            </InputGroup.Text>
                        </InputGroup.Append>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => { this.props.callback(false); }}>Cancel</Button>
                    <Button variant="success" onClick={this.handlePositive} disabled={this.state.isProcessingTransaction || isNaN(this.state.inputAmount) || this.props.currency === "TRY"}>
                        {this.props.isBuying ? 'Buy' : 'Sell'}
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

}

export default connect(mapStateToProps, null)(BuySellModal)