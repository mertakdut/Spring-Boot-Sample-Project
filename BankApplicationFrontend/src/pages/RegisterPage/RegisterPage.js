import React from 'react';
import { Redirect } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';
import { connect } from 'react-redux'
import FormElement from '../../components/FormElement/FormElement';
import Request from '../../services/Request';
import { login, showDialog } from '../../actions';

const ERROR_ALLFIELDSMANDATORY = 'All fields are mandatory.';
const ERROR_DIFFPASS = 'Password fields should be same.';
const ERROR_TCNOLENGTH = 'Length of TC No must be 11. Currently it is: ';

const mapStateToProps = state => ({
    isLoggedIn: state.login != null
})

const mapDispatchToProps = dispatch => ({
    login: (username) => dispatch(login(username)),
    showPopup: (title, message) => dispatch(showDialog(title, message))
})

class RegisterPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            username: '',
            tcno: '',
            firstPass: '',
            secondPass: '',
            isProcessingRegister: false
        };

        this.handleClick = this.handleClick.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handleFirstPasswordChange = this.handleFirstPasswordChange.bind(this);
        this.handleSecondPasswordChange = this.handleSecondPasswordChange.bind(this);
        this.handleTcNoChange = this.handleTcNoChange.bind(this);
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

    handleClick() {

        if (this.state.firstPass != this.state.secondPass) {
            this.props.showPopup(1, ERROR_DIFFPASS);
        } else if (this.state.username == '' || this.tcno == '' || this.state.firstPass == '') {
            this.props.showPopup(1, ERROR_ALLFIELDSMANDATORY);
        } else if (this.state.tcno.length != 11) {
            this.props.showPopup(1, ERROR_TCNOLENGTH + this.state.tcno.length);
        } else {
            
            this.setState({
                isProcessingRegister: true
            });

            const request = new Request().getRequestInstance();
            request.post('user/new', {
                username: this.state.username,
                password: this.state.firstPass,
                tcno: this.state.tcno
            }).then((response) => {
                console.log(response);
                localStorage.setItem('username', response.data.username);
                this.props.login(localStorage.getItem('username'));
            }).catch((error) => {
                console.log(error);
                var errorMessage = 'Network error';
                if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                    errorMessage = error.response.data.message;
                }
                this.props.showPopup(0, errorMessage);
            }).finally(() => {
                this.setState({
                    isProcessingRegister: false
                });
            });
        }
    }

    render() {
        const formStyle = {
            marginTop: '7%'
        };

        if (!this.state.isProcessingRegister && this.props.isLoggedIn) {
            return <Redirect to='/' />;
        }

        return (
            <div>
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

export default connect(mapStateToProps, mapDispatchToProps)(RegisterPage)