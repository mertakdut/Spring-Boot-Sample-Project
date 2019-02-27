import React from 'react';
import { Link } from 'react-router-dom';
import { Row, Button, ButtonToolbar, Jumbotron } from 'react-bootstrap';

class HomePage extends React.Component {

    render() {

        const username = localStorage.getItem('username');
        if (username != null) {
            return <WelcomeText username={username} />
        } else {
            return <MainButtonGroup />
        }
    }
}

class MainButtonGroup extends React.Component {

    render() {
        const btnGroupStyle = {
            marginTop: '2.5%'
        };

        const loginBtnStyle = {
            marginLeft: '5px',
        };

        return (
            <div>
                <Row className="justify-content-md-center">
                    <ButtonToolbar style={btnGroupStyle} aria-label="First group">
                        <Link to="/register"><Button variant="primary" size="lg">Register</Button></Link>
                        <Link to="/login"><Button style={loginBtnStyle} variant="secondary" size="lg">Login</Button></Link>
                    </ButtonToolbar>
                </Row>
            </div>
        )
    }
}

class WelcomeText extends React.Component {
    render() {
        return (
            <Jumbotron className="text-center">
                <h1>Welcome back {this.props.username}!</h1>
            </Jumbotron>
        )
    }

}

export default HomePage;