import React from 'react';
import CurrencyList from './CurrencyList/CurrencyList'
import Request from '../../services/Request'

import { connect } from 'react-redux'
import { showDialog } from '../../actions';

const mapDispatchToProps = dispatch => ({
    showPopup: (title, message) => dispatch(showDialog(title, message))
})

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
            this.props.showPopup(0, error);
        });
    }

    render() {
        return (
            <div>
                <CurrencyList currencies={this.state.currencies} />
            </div>
        )
    }
}

export default connect(null, mapDispatchToProps)(CurrencyPage)