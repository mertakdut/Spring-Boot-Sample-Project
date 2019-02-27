import React from 'react';
import { ButtonToolbar } from 'react-bootstrap';
import BuySellButton from './BuySellButton/BuySellButton';

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

export default Currency;