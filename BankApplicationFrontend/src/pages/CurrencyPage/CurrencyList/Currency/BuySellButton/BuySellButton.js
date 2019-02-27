import React from 'react';
import BuySellModal from './BuySellModal/BuySellModal';
import { Button } from 'react-bootstrap';
import PopupDialog from '../../../../../components/PopupDialog';

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
                <Button className="mx-1" variant={this.props.isBuying ? 'success' : 'danger'} onClick={() => { this.setState({ isShowingBuyPopup: true }) }} disabled={this.props.currency === "TRY"}>
                    {this.props.isBuying ? 'Buy' : 'Sell'}
                </Button>
            </div>
        )
    }
}

export default BuySellButton;