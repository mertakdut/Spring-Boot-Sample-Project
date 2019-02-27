import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom'

import NavBarTop from '../components/NavigationBar';

import HomePage from './inner/HomePage';
import UsersPage from './inner/UsersPage';
import CurrencyPage from './CurrencyPage/CurrencyPage';
import TransferPage from './inner/TransferPage';
import HistoryPage from './inner/HistoryPage';

import RegisterPage from './inner/RegisterPage';
import LoginPage from './inner/LoginPage';

import { Container } from 'react-bootstrap'
import MoneyBar from '../components/MoneyBar';

import { showDialog } from '../actions';

import Request from '../services/Request'
import { connect } from 'react-redux'
import PopupDialog from '../components/PopupDialog';

const mapDispatchToProps = dispatch => ({
    showPopup: (title, message) => dispatch(showDialog(title, message))
})

class MainPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            loggedInUsername: localStorage.getItem('username'),
            ownedCurrencies: null
        };

        this.retrieveWealthAndUpdateState = this.retrieveWealthAndUpdateState.bind(this);
    }

    componentDidMount() {
        if (this.state.loggedInUsername != null) {
            this.retrieveWealthAndUpdateState();
        }
    }

    retrieveWealthAndUpdateState() {
        const request = new Request().getRequestInstance();
        request.post('wealth/retrieve', { username: this.state.loggedInUsername })
            .then((response) => {
                console.log(response);
                this.state.ownedCurrencies = [];
                Object.keys(response.data.wealthMap).map((key) => {
                    if (response.data.wealthMap[key] == 0) delete response.data.wealthMap[key];
                });
                this.setState({ ownedCurrencies: response.data.wealthMap });
                console.log(this.state.ownedCurrencies);
            }).catch((error) => {
                console.log(error);
                var errorMessage = 'Network error.';
                if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                    errorMessage = error.response.data.message;
                }
                this.props.showPopup("Error", errorMessage);
            });

        // console.log(this.props.getSomething("Mert", "It works!"));
    }

    render() {

        const moneyBar = this.state.loggedInUsername != null ?
            <MoneyBar username={this.state.loggedInUsername} ownedCurrencies={this.state.ownedCurrencies} />
            : null;

        return (
            <Router>
                <div>
                    <NavBarTop loggedInUsername={this.state.loggedInUsername} />
                    <Container>
                        <PopupDialog />
                        {moneyBar}
                        <Route exact path="/" component={HomePage} />
                        <Route exact path="/users" component={UsersPage} />
                        <Route exact path="/currency" render={() => <CurrencyPage onOwnedCurrenciesUpdated={this.retrieveWealthAndUpdateState} />} />
                        <Route exact path="/transfer" render={() => <TransferPage onOwnedCurrenciesUpdated={this.retrieveWealthAndUpdateState} />} />
                        <Route exact path="/history" component={HistoryPage} />
                        <Route exact path="/login" component={LoginPage} />
                        <Route exact path="/register" component={RegisterPage} />
                    </Container>
                </div>
            </Router>
        );
    }
}

export default connect(null, mapDispatchToProps)(MainPage);