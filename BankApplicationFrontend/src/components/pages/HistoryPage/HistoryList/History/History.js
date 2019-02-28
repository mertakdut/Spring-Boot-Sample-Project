import React from 'react';
import Moment from 'react-moment';

class History extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.history.bought ? "Bought" : "Sold"}</td>
                <td>{this.props.history.amount}</td>
                <td>{this.props.history.currency}</td>
                <td>
                    <Moment format="hh:mm:ss DD/MM/YYYY">
                        {this.props.history.transactionTime}
                    </Moment>
                </td>
            </tr>
        )
    }
}

export default History