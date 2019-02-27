import React from 'react';
import { Form, Button } from 'react-bootstrap';
import FormElement from '../../components/FormElement';
import PopupDialog from '../../components/PopupDialog';
import Request from '../../services/Request';
import { Redirect } from 'react-router-dom';

const ERROR_ALLFIELDSMANDATORY = 'All fields are mandatory.';

class LoginPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: '',
            isShowingPopup: false,
            popupMessage: '',
            popupTitle: '',
            isProcessingLogin: false,
            isLoggedIn: false
        }

        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
        this.onPopupClosed = this.onPopupClosed.bind(this);
        this.resetFields = this.resetFields.bind(this);
    }

    handleUsernameChange(e) {
        this.setState({ username: e.target.value });
    }

    handlePasswordChange(e) {
        this.setState({ password: e.target.value });
    }

    resetFields() {
        this.setState({
            username: '',
            tcno: '',
            firstPass: '',
            secondPass: ''
        });
    }

    handleClick() {

        this.setState({
            isProcessingLogin: true
        });

        // TODO: Same logic exists in RegisterPage.
        if (this.state.username == "" || this.state.password == "") {
            this.setState({ isShowingPopup: true, popupTitle: 1, popupMessage: ERROR_ALLFIELDSMANDATORY });
        } else {
            const request = new Request().getRequestInstance();
            request.post('user/login', {
                username: this.state.username,
                password: this.state.password
            }).then((response) => {
                console.log(response);
                localStorage.setItem('username', response.data.username);
                this.setState({ isLoggedIn: true });
            }).catch((error) => {
                console.log(error);
                var errorMessage = 'Network error';
                if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                    errorMessage = error.response.data.message;
                }
                this.setState({ isShowingPopup: true, popupTitle: 0, popupMessage: errorMessage });
            }).finally(() => {
                this.resetFields();
                this.setState({
                    isProcessingLogin: false
                });
            });
        }
    }

    onPopupClosed(result) {
        console.log(result);
        this.setState({
            isShowingPopup: false
        });
    }

    render() {

        if (this.state.isShowingPopup) {
            return <PopupDialog callback={this.onPopupClosed} title={this.state.popupTitle} message={this.state.popupMessage} isAnswerable={false} />
        }

        if (!this.state.isProcessingLogin && this.state.isLoggedIn) {
            return <Redirect to='/' />;
        }

        let formStyle = {
            marginTop: '7%'
        };

        return (
            <Form style={formStyle}>
                <FormElement controlId={"formUsername"} label={"Username"} type={"text"} onChange={this.handleUsernameChange} value={this.state.username} />
                <FormElement controlId={"formPassword"} label={"Password"} type={"password"} onChange={this.handlePasswordChange} value={this.state.password} />
                <Button variant="primary" onClick={this.handleClick} disabled={this.state.isProcessingLogin}>Login</Button>
            </Form>
        )
    }
}

export default LoginPage;