import React from 'react';

class CurrencyDropdownItem extends React.Component {
    render() {
        return (
            <option>{this.props.currency}</option>
        )
    }
}

export default CurrencyDropdownItem