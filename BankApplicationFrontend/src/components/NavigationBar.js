import React from 'react';
import { Navbar, Nav } from 'react-bootstrap'

class NavBarTop extends React.Component {
    
    render() {
        return (
            <Navbar bg="light" expand="lg">
                <Navbar.Brand href="/">Demo Bank</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        <Nav.Link href="/users">Users</Nav.Link>
                        <Nav.Link href="/currency">Currency</Nav.Link>
                        <Nav.Link href="/transfer">Transfer</Nav.Link>
                        <Nav.Link href="/history">Transaction History</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default NavBarTop;