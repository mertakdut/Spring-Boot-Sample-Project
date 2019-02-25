import React from 'react';

import { BrowserRouter as Router, Route } from 'react-router-dom'

import NavBarTop from '../components/NavigationBar';

import HomePage from './inner/HomePage';
import UsersPage from './inner/UsersPage';
import CurrencyPage from './inner/CurrencyPage';
import TransferPage from './inner/TransferPage';
import HistoryPage from './inner/HistoryPage';

import axios from 'axios';
import apiConfig from '../config/client';

import RegisterPage from './inner/RegisterPage';
import LoginPage from './inner/LoginPage';

import { Container } from 'react-bootstrap'
import MoneyBar from '../components/MoneyBar';

class MainPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            loggedInUsername: 'Mert',  // localStorage.getItem('liUsername'),
            ownedCurrencies: []
        };

        this.retrieveWealthAndUpdateState = this.retrieveWealthAndUpdateState.bind(this);
    }

    componentDidMount() {

        if (this.state.loggedInUsername != null) {
            retrieveWealthAndUpdateState();
        }
    }

    retrieveWealthAndUpdateState() {

        axios.post(apiConfig.apiBaseUrl + 'wealth/retrieve', {
            username: this.state.loggedInUsername
        }).then((response) => {
            console.log(response);
            Object.keys(response.data.wealthMap).map((key) => {
                if (response.data.wealthMap[key] == 0) delete response.data.wealthMap[key];
            });
            this.setState({ ownedCurrencies: response.data.wealthMap });
            console.log(this.state.ownedCurrencies);
        }).catch((error) => {
            console.log(error);
        }).finally(() => {

        });

    }

    render() {
        return (
            <Router>
                <div>
                    <NavBarTop isUserLoggedIn={this.state.loggedInUsername} />
                    <Container>
                        <MoneyBar username={this.state.loggedInUsername} ownedCurrencies={this.state.ownedCurrencies} />
                        <Route exact path="/" component={HomePage} />
                        <Route exact path="/users" component={UsersPage} />
                        <Route path="/abc" render={() => <CurrencyPage onOwnedCurrenciesUpdated={this.retrieveWealthAndUpdateState} />} />
                        <Route exact path="/currency" component={} />
                        <Route exact path="/transfer" component={TransferPage} />
                        <Route exact path="/history" component={HistoryPage} />
                        <Route exact path="/login" component={LoginPage} />
                        <Route exact path="/register" component={RegisterPage} />
                    </Container>
                </div>
            </Router>
        );
    }
}

export default MainPage;