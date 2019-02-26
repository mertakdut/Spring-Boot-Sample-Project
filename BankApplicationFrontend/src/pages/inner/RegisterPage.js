import React from 'react';
import { Form, Button } from 'react-bootstrap'
import FormElement from '../../components/FormElement'
import axios from 'axios';
import apiConfig from '../../config/client';

import PopupDialog from '../../components/PopupDialog';

const ERROR_ALLFIELDSMANDATORY = 'All fields are mandatory.';
const ERROR_DIFFPASS = 'Password fields should be same.';
const ERROR_TCNOLENGTH = 'Length of TC No must be 11. Currently it is: ';
const SUCCESS_REGISTER = 'User is registered successfully.';

class RegisterPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            username: '',
            tcno: '',
            firstPass: '',
            secondPass: '',
            isShowingPopup: false,
            popupMessage: '',
            isProcessingRegister: false
        };

        this.handleClick = this.handleClick.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handleFirstPasswordChange = this.handleFirstPasswordChange.bind(this);
        this.handleSecondPasswordChange = this.handleSecondPasswordChange.bind(this);
        this.handleTcNoChange = this.handleTcNoChange.bind(this);
        this.resetFields = this.resetFields.bind(this);
        this.onPopupClosed = this.onPopupClosed.bind(this);
    }

    handleUsernameChange(e) {
        this.setState({ username: e.target.value });
    }

    handleTcNoChange(e) {
        this.setState({ tcno: e.target.value });
    }

    handleFirstPasswordChange(e) {
        this.setState({ firstPass: e.target.value });
    }

    handleSecondPasswordChange(e) {
        this.setState({ secondPass: e.target.value });
    }

    resetFields() {
        this.setState({
            username: '',
            tcno: '',
            firstPass: '',
            secondPass: ''
        });
    }

    onPopupClosed(result) {
        console.log("dialog result: " + result);
        this.setState({
            isShowingPopup: false
        });
    }

    handleClick() {

        this.setState({
            isProcessingRegister: true
        });

        if (this.state.firstPass != this.state.secondPass) {
            this.setState({ isShowingPopup: true, popupTitle: 1, popupMessage: ERROR_DIFFPASS });
        } else if (this.state.username == '' || this.tcno == '' || this.state.firstPass == '') {
            this.setState({ isShowingPopup: true, popupTitle: 1, popupMessage: ERROR_ALLFIELDSMANDATORY });
        } else if (this.state.tcno.length != 11) {
            this.setState({ isShowingPopup: true, popupTitle: 1, popupMessage: ERROR_TCNOLENGTH + this.state.tcno.length });
        } else {
            axios.post(apiConfig.apiBaseUrl + 'user/new', {
                username: this.state.username,
                password: this.state.firstPass,
                tcno: this.state.tcno
            }).then((response) => {
                console.log(response);
                this.setState({ isShowingPopup: true, popupTitle: 2, popupMessage: SUCCESS_REGISTER });
            }).catch((error) => {
                console.log(error.response);
                this.setState({ isShowingPopup: true, popupTitle: 0, popupMessage: error.response.data.message });
            }).finally(() => {
                this.resetFields();
                this.setState({
                    isProcessingRegister: false
                })
            });
        }
    }

    render() {

        if (this.state.isShowingPopup) {
            return <PopupDialog callback={this.onPopupClosed} title={this.state.popupTitle} message={this.state.popupMessage} isAnswerable={false} />
        }

        let formStyle = {
            marginTop: '7%'
        };

        return (
            <Form style={formStyle}>
                <FormElement controlId={"formUsername"} label={"Username"} type={"text"} onChange={this.handleUsernameChange} value={this.state.username} />
                <FormElement controlId={"formTcNo"} label={"TC No"} type={"text"} onChange={this.handleTcNoChange} value={this.state.tcno} />
                <FormElement controlId={"formPassword"} label={"Password"} type={"password"} onChange={this.handleFirstPasswordChange} value={this.state.firstPass} />
                <FormElement controlId={"formSecondPassword"} label={"Retype Password"} type={"password"} onChange={this.handleSecondPasswordChange} value={this.state.secondPass} />
                <Button variant="primary" onClick={this.handleClick} disabled={this.state.isProcessingRegister}>Register</Button>
            </Form>
        )

    }
}

export default RegisterPage;