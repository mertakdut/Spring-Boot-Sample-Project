import React from 'react';
import { Form } from 'react-bootstrap'

class FormElement extends React.Component {
    render() {
        return (
            <Form.Group controlId={this.props.controlId}>
                <Form.Label>{this.props.label}</Form.Label>
                <Form.Control type={this.props.type} placeholder={this.props.label} onChange={this.props.onChange} value={this.props.value} maxLength={this.props.maxLength}/>
            </Form.Group>
        )
    }
}

export default FormElement;