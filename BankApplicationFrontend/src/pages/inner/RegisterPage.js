import React from 'react';
import { Redirect } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';
import FormElement from '../../components/FormElement';
import PopupDialog from '../../components/PopupDialog';
import Request from '../../services/Request';

const ERROR_ALLFIELDSMANDATORY = 'All fields are mandatory.';
const ERROR_DIFFPASS = 'Password fields should be same.';
const ERROR_TCNOLENGTH = 'Length of TC No must be 11. Currently it is: ';

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
            isProcessingRegister: false,
            isRegistered: false
        };

        this.handleClick = this.handleClick.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handleFirstPasswordChange = this.handleFirstPasswordChange.bind(this);
        this.handleSecondPasswordChange = this.handleSecondPasswordChange.bind(this);
        this.handleTcNoChange = this.handleTcNoChange.bind(this);
        this.resetFields = this.resetFields.bind(this);
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
            const request = new Request().getRequestInstance();
            request.post('user/new', {
                username: this.state.username,
                password: this.state.firstPass,
                tcno: this.state.tcno
            }).then((response) => {
                console.log(response);
                localStorage.setItem('username', response.data.username);
                this.setState({ isRegistered: true });
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
                    isProcessingRegister: false
                });
            });
        }
    }

    render() {
        const popupDialog = this.state.isShowingPopup ?
            <PopupDialog callback={() => this.setState({ isShowingPopup: false })} title={this.state.popupTitle} message={this.state.popupMessage} isAnswerable={false} />
            : null;

        const formStyle = {
            marginTop: '7%'
        };

        if (!this.state.isProcessingRegister && this.state.isRegistered) {
            return <Redirect to='/' />;
        }

        return (
            <div>
                {popupDialog}
                <Form style={formStyle}>
                    <FormElement controlId={"formUsername"} label={"Username"} type={"text"} onChange={this.handleUsernameChange} value={this.state.username} />
                    <FormElement controlId={"formTcNo"} label={"TC No"} type={"text"} onChange={this.handleTcNoChange} value={this.state.tcno} maxLength='11' />
                    <FormElement controlId={"formPassword"} label={"Password"} type={"password"} onChange={this.handleFirstPasswordChange} value={this.state.firstPass} />
                    <FormElement controlId={"formSecondPassword"} label={"Retype Password"} type={"password"} onChange={this.handleSecondPasswordChange} value={this.state.secondPass} />
                    <Button variant="primary" onClick={this.handleClick} disabled={this.state.isProcessingRegister}>Register</Button>
                </Form>
            </div>
        )

    }
}

export default RegisterPage;