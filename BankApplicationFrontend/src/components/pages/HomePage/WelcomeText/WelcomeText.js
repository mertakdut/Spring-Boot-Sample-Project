import React from 'react';
import {Jumbotron} from 'react-bootstrap'

class WelcomeText extends React.Component {
    render() {
        return (
            <Jumbotron className="text-center">
                <h1>Welcome back {this.props.username}!</h1>
            </Jumbotron>
        )
    }
}

export default WelcomeText