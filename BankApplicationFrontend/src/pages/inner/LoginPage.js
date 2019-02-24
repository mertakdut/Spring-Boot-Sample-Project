import React from 'react';
import { Form, Button } from 'react-bootstrap'
import FormElement from '../../components/FormElement'

class LoginPage extends React.Component {

    render() {

        let formStyle = {
            marginTop: '7%'
        };

        return (
            <Form style={formStyle}>
                <FormElement controlId={"formUsername"} label={"Username"} type={"text"} />
                <FormElement controlId={"formPassword"} label={"Password"} type={"password"} />
                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        )
    }
}

export default LoginPage;