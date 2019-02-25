import React from 'react';

import { BrowserRouter as Router, Route } from 'react-router-dom'

import NavBarTop from '../components/NavigationBar';

import HomePage from './inner/HomePage';
import UsersPage from './inner/UsersPage';
import CurrencyPage from './inner/CurrencyPage';
import TransferPage from './inner/TransferPage';
import HistoryPage from './inner/HistoryPage';

import RegisterPage from './inner/RegisterPage';
import LoginPage from './inner/LoginPage';

import { Container } from 'react-bootstrap'

class MainPage extends React.Component {
    render() {
        return (
            <Router>
                <div>
                    <NavBarTop />
                    <Container>
                        <div>You have 1000 USD 5000 EUR</div>
                    	<Route exact path="/" component={HomePage} />
	                    <Route exact path="/users" component={UsersPage} />
	                    <Route exact path="/currency" component={CurrencyPage} />
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