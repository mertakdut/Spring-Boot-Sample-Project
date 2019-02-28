import React from 'react';
import { ButtonToolbar, Button } from 'react-bootstrap';
import { connect } from 'react-redux'
import { showDialog, currenciesObsolete } from '../../../../actions';

import BuySellModal from './BuySellModal/BuySellModal'

const mapStateToProps = state => ({
    loggedInUsername: state.login
})

const mapDispatchToProps = dispatch => ({
    currenciesNeedUpdate: () => dispatch(currenciesObsolete),
    showPopup: (title, message) => dispatch(showDialog(title, message))
})

class Currency extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isShowingBuyPopup: false,
            isBuying: false
        };

        this.onBuyingPopupClosed = this.onBuyingPopupClosed.bind(this);
    }

    onBuyingPopupClosed(isProcessed, message, statusCode) {
        this.setState({ isShowingBuyPopup: false });
        if (isProcessed) {
            if (message != null) {
                this.props.showPopup(statusCode, message);
            }

            if (statusCode != 0) {
                this.props.currenciesNeedUpdate();
            }
        }
    }

    render() {

        const popupDialog = this.state.isShowingBuyPopup ?
            <BuySellModal isBuying={this.state.isBuying} currency={this.props.currency} rate={this.props.rate} callback={this.onBuyingPopupClosed} username={this.props.loggedInUsername} />
            : null;

        return (

            <tr>
                <td>{this.props.currency}</td>
                <td>{this.props.rate}</td>
                <td>
                    <ButtonToolbar>
                        <Button className="mx-1" variant='success' onClick={() => { this.setState({ isShowingBuyPopup: true, isBuying: true }) }} disabled={this.props.currency === "TRY"}>
                            Buy
                        </Button>
                        <Button className="mx-1" variant='danger' onClick={() => { this.setState({ isShowingBuyPopup: true, isBuying: false }) }} disabled={this.props.currency === "TRY"}>
                            Sell
                        </Button>
                    </ButtonToolbar>
                </td>
                {popupDialog}
            </tr>

        )
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Currency)