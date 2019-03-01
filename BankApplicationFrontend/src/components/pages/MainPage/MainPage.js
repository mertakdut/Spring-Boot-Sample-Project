import React from 'react';
import { Container } from 'react-bootstrap'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import { connect } from 'react-redux'

import NavBarTop from '../../NavigationBar/NavigationBar'
import PopupDialog from '../../PopupDialog/PopupDialog'
import MoneyBar from '../../MoneyBar/MoneyBar'

import HomePage from '../HomePage/HomePage'
import UsersPage from '../UsersPage/UsersPage'
import CurrencyPage from '../CurrencyPage/CurrencyPage'
import TransferPage from '../TransferPage/TransferPage'
import HistoryPage from '../HistoryPage/HistoryPage'

import RegisterPage from '../RegisterPage/RegisterPage'
import LoginPage from '../LoginPage/LoginPage'

import { login } from '../../../actions'
import { LSKEY_USERNAME, LSKEY_TOKEN } from '../../../config/constants'

const mapDispatchToProps = dispatch => ({
    login: (username, token) => dispatch(login(username, token))
})

class MainPage extends React.Component {

    constructor(props) {
        super(props);

        const storedUsername = localStorage.getItem(LSKEY_USERNAME);
        if (storedUsername != null) {
            this.props.login(storedUsername, localStorage.getItem(LSKEY_TOKEN));
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

export default connect(null, mapDispatchToProps)(MainPage);