import React from 'react';
import { connect } from 'react-redux'
import MainButtonGroup from './MainButtonGroup/MainButtonGroup'
import WelcomeText from './WelcomeText/WelcomeText'

const mapStateToProps = state => ({
    loggedInUsername: state.login
})

class HomePage extends React.Component {

    render() {
        if (this.props.loggedInUsername != null) {
            return <WelcomeText username={this.props.loggedInUsername} />
        } else {
            return <MainButtonGroup />
        }
    }
}

export default connect(mapStateToProps, null)(HomePage);