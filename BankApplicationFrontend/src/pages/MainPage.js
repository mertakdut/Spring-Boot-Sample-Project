import React from 'react';

import { BrowserRouter as Router, Route } from 'react-router-dom'

import NavBarTop from '../components/NavigationBar';

import HomePage from './inner/HomePage';
import UsersPage from './inner/UsersPage';
import CurrencyPage from './inner/CurrencyPage';
import TransferPage from './inner/TransferPage';
import HistoryPage from './inner/HistoryPage';

import { Container } from 'react-bootstrap'

class MainPage extends React.Component {
    render() {
        return (
            <Router>
                <div>
                    <NavBarTop />
                    <Container>
                    	<Route path="/" component={HomePage} />
	                    <Route path="/users" component={UsersPage} />
	                    <Route path="/currency" component={CurrencyPage} />
	                    <Route path="/transfer" component={TransferPage} />
	                    <Route path="/history" component={HistoryPage} />
                    </Container>
                </div>
            </Router>
        );
    }
}

export default MainPage;