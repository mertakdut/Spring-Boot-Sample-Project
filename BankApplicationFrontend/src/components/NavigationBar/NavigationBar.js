import React from 'react';
import { Link, Redirect } from 'react-router-dom';
import { connect } from 'react-redux'
import { Navbar, Nav, NavItem } from 'react-bootstrap';

import { showDialog, logout } from '../../actions';

const mapStateToProps = state => ({
    loggedInUsername: state.login
})

const mapDispatchToProps = dispatch => ({
    showPopup: (title, message, callback) => dispatch(showDialog(title, message, callback)),
    logout: () => dispatch(logout)
})

const navbarStyling = {
    padding: '15px',
    display: 'inlineBlock',
    lineHeight: '20px',
    cursor: 'pointer'
};

class NavBarTop extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            hasJustLoggedOut: false
        }

        this.onLoggedOut = this.onLoggedOut.bind(this);
    }

    onLoggedOut() {
        localStorage.removeItem('username');
        this.props.logout();
        this.setState({ hasJustLoggedOut: true });
    }

    render() {

        if (this.state.hasJustLoggedOut) {
            this.setState({ hasJustLoggedOut: false })
            return <Redirect to="/" />
        }


        const loginLogout = this.props.loggedInUsername == null ?
            <NavItem style={navbarStyling} componentclass="span"><Link to="/login">Login</Link></NavItem> :
            <NavItem style={navbarStyling} onClick={() =>
                this.props.showPopup(1, 'Are sure you want to logout ' + this.props.loggedInUsername + '?', this.onLoggedOut)}>Logout</NavItem>

        return (
            <Navbar bg="light" expand="lg" >
                <Navbar.Brand><Link to="/">Demo Bank</Link></Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <NavItem style={navbarStyling} componentclass="span"><Link to="/users">Users</Link></NavItem>
                        <NavItem style={navbarStyling} componentclass="span"><Link to="/currency">Currency</Link></NavItem>
                        <NavItem style={navbarStyling} componentclass="span"><Link to="/transfer">Transfer</Link></NavItem>
                        <NavItem style={navbarStyling} componentclass="span"><Link to="/history">Transaction History</Link></NavItem>
                    </Nav>
                    <Nav className="ml-auto">
                        {this.props.loggedInUsername == null ? <NavItem style={navbarStyling} componentclass="span"><Link to="/register">Register</Link></NavItem> : null}
                        {loginLogout}
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(NavBarTop);