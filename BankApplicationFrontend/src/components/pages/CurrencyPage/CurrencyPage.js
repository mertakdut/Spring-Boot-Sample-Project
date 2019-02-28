import React from 'react';
import { connect } from 'react-redux'
import CurrencyList from './CurrencyList/CurrencyList'
import Request from '../../../services/Request';
import { BASEURL_EXCHANGERATES, URL_EXCHANGERATES } from '../../../config/constants'
import { showDialog } from '../../../actions'

const mapDispatchToProps = dispatch => ({
    showPopup: (title, message) => dispatch(showDialog(title, message))
})

class CurrencyPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = { currencies: [] };
    }

    componentDidMount() {
        const request = new Request().getRequestInstance(BASEURL_EXCHANGERATES);
        request.get(URL_EXCHANGERATES).then((response) => {
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