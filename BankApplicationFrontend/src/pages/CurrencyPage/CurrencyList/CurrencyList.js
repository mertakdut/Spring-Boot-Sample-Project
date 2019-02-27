import React from 'react';
import Currency from './Currency/Currency';
import { Table } from 'react-bootstrap';

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

export default CurrencyList;