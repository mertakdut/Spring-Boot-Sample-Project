import React from 'react';
import { Alert } from 'react-bootstrap'

class MoneyBar extends React.Component {

    render() {

        var barMessage = "You have ";
        Object.keys(this.props.ownedCurrencies).forEach((key, index, array) => {
            barMessage += this.props.ownedCurrencies[key] + " " + key + ((array.length - 1) != index ? ", " : ".");
        });

        return (
            <Alert className="text-center" variant="primary">
                {barMessage}
            </Alert>
        );
    }
}

export default MoneyBar;