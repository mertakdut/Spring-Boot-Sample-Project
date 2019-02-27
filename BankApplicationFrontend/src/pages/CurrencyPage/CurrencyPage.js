import React from 'react';
import CurrencyList from './CurrencyList/CurrencyList'

import Request from '../../services/Request'

class CurrencyPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = { currencies: [] };
    }

    componentDidMount() {
        const request = new Request().getRequestInstance('https://api.exchangeratesapi.io');
        request.get('/latest?base=TRY').then((response) => {
            this.setState({ currencies: response.data.rates });
        }).catch((error) => {
            console.log(error);
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

export default CurrencyPage;