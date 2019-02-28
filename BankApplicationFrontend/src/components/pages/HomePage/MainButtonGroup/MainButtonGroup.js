import React from 'react';
import { Link } from 'react-router-dom';
import { Row, Button, ButtonToolbar } from 'react-bootstrap';

class MainButtonGroup extends React.Component {

    render() {
        const btnGroupStyle = {
            marginTop: '2.5%'
        };

        const loginBtnStyle = {
            marginLeft: '5px',
        };

        return (
            <Row className="justify-content-md-center">
                <ButtonToolbar style={btnGroupStyle} aria-label="First group">
                    <Link to="/register"><Button variant="primary" size="lg">Register</Button></Link>
                    <Link to="/login"><Button style={loginBtnStyle} variant="secondary" size="lg">Login</Button></Link>
                </ButtonToolbar>
            </Row>
        )
    }
}

export default MainButtonGroup