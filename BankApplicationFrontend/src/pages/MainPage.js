import React from 'react';
import { Container } from 'react-bootstrap'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import { connect } from 'react-redux'

import NavBarTop from '../components/NavigationBar/NavigationBar';
import PopupDialog from '../components/PopupDialog';

import HomePage from './inner/HomePage';
import UsersPage from './inner/UsersPage';
import CurrencyPage from './CurrencyPage/CurrencyPage';
import TransferPage from './inner/TransferPage';
import HistoryPage from './inner/HistoryPage';

import RegisterPage from './inner/RegisterPage';
import LoginPage from './inner/LoginPage';

import MoneyBar from '../components/MoneyBar';

import { login } from '../actions';

const mapDispatchToProps = dispatch => ({
    login: (username) => dispatch(login(username))
})

class MainPage extends React.Component {

    constructor(props) {
        super(props);

        const storedUsername = localStorage.getItem('username');
        if (storedUsername != null) {
            this.props.login(storedUsername);
        }
    }

    render() {

        return (
            <Router>
                <div>
                    <NavBarTop />
                    <Container>
                        <PopupDialog />
                        <MoneyBar />
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