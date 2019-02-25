import React from 'react';
import { Navbar, Nav, NavItem } from 'react-bootstrap'

class MoneyBar extends React.Component {

    render() {

        var barMessage = "You have ";
        Object.keys(this.props.ownedCurrencies).forEach((key) => {
            barMessage += this.props.ownedCurrencies[key] + " " + key + " ";
        });

        return (
            <div>
                {barMessage}
            </div>
        );
    }
}

export default MoneyBar;