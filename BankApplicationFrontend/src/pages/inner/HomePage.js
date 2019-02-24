import React from 'react';
import { Row, Button, ButtonToolbar } from 'react-bootstrap';

import { Link } from 'react-router-dom';

class HomePage extends React.Component {
    render() {
        return (
            <MainButtonGroup />
        )
    }
}

class MainButtonGroup extends React.Component {

    render() {
        let btnGroupStyle = {
            marginTop: '5%'
        };

        let loginBtnStyle = {
            marginLeft: '5px',
        };

        return (
            <div>
                <Row className="justify-content-md-center mr-auto">
                    <ButtonToolbar style={btnGroupStyle} aria-label="First group">
                        <Link to="/register"><Button variant="primary" size="lg">Register</Button></Link>
                        <Link to="/login"><Button style={loginBtnStyle} variant="secondary" size="lg">Login</Button></Link>
                    </ButtonToolbar>
                </Row>
                <Row className="justify-content-md-center mr-auto">
                    {/* <LoginForm /> */}
                </Row>
            </div>
        )
    }
}

export default HomePage;