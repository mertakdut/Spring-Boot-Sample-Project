import React from 'react';
import { Link, Redirect } from 'react-router-dom';
import { Navbar, Nav, NavItem } from 'react-bootstrap'
import PopupDialog from './PopupDialog';

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
            username: localStorage.getItem('username')
        };
        console.log(this.state.username);

        this.onLoggedOut = this.onLoggedOut.bind(this);
    }

    onLoggedOut() {
        localStorage.removeItem('username');
        this.setState({ username: localStorage.getItem('username') });
    }

    render() {

        const loginLogout = this.state.username == null ?
            <NavItem style={navbarStyling} componentclass="span"><Link to="/login">Login</Link></NavItem> :
            <LogoutDialog username={this.state.username} callback={this.onLoggedOut} />

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
                        <NavItem style={navbarStyling} componentclass="span"><Link to="/register">Register</Link></NavItem>
                        {loginLogout}
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

class LogoutDialog extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isShowingPopup: false
        }

        this.onPopupClosed = this.onPopupClosed.bind(this);
    }

    onPopupClosed(result) {
        if (result) {
            this.props.callback();
        }
    }

    render() {

        const popupDialog = this.state.isShowingPopup ?
            <PopupDialog callback={this.onPopupClosed} title='Logout' message={'Are you sure you want to logout ' + this.props.username + '?'} isAnswerable={true} /> : null;

        return (
            <div>
                {popupDialog}
                <NavItem style={navbarStyling} onClick={() => this.setState({ isShowingPopup: true })}>Logout</NavItem>
            </div >
        )
    }

}

export default NavBarTop;