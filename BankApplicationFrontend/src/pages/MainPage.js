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
import MoneyBar from '../components/MoneyBar';
import PopupDialog from '../components/PopupDialog';

import Request from '../services/Request'

class MainPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            loggedInUsername: localStorage.getItem('username'),
            ownedCurrencies: null,
            isShowingPopup: false,
            popupTitle: 0,
            popupMessage: '',
        };

        this.retrieveWealthAndUpdateState = this.retrieveWealthAndUpdateState.bind(this);
        this.onLoginStatusChanged = this.onLoginStatusChanged.bind(this);
    }

    onLoginStatusChanged() {

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
                var errorMessage = 'Network error';
                if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                    errorMessage = error.response.data.message;
                }
                this.setState({ isShowingPopup: true, popupTitle: 0, popupMessage: errorMessage });
            });
    }

    render() {

        const popupDialog = this.state.isShowingPopup ?
            <PopupDialog callback={() => this.setState({ isShowingPopup: false })} title={this.state.popupTitle} message={this.state.popupMessage} isAnswerable={false} />
            : null;

        const moneyBar = this.state.loggedInUsername != null ?
            <MoneyBar username={this.state.loggedInUsername} ownedCurrencies={this.state.ownedCurrencies} />
            : null;

        return (
            <Router>
                <div>
                    {popupDialog}
                    <NavBarTop loggedInUsername={this.state.loggedInUsername} />
                    <Container>
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

export default MainPage;