import React from 'react';
import { Navbar, Nav, NavItem } from 'react-bootstrap'
import { Link } from 'react-router-dom';

class NavBarTop extends React.Component {

    render() {

        let navbarStyling = {
            padding: '15px',
            display: 'inlineBlock',
            lineHeight: '20px'
        };

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
                        <NavItem style={navbarStyling} componentclass="span"><Link to="/login">Login</Link></NavItem>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default NavBarTop;