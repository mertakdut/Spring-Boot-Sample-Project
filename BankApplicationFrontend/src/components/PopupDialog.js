import React from 'react';
import { Modal, Button } from 'react-bootstrap';

class PopupDialog extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.handleShow = this.handleShow.bind(this);
        this.handlePositive = this.handlePositive.bind(this);
        this.handleNegative = this.handleNegative.bind(this);

        this.state = {
            show: true
        };
    }

    handlePositive() {
        this.setState({ show: false });
        this.props.callback(true);
    }

    handleNegative() {
        this.setState({ show: false });
        this.props.callback(false);
    }

    handleShow() {
        this.setState({ show: true });
    }

    render() {
        return (
            <div>
                <Modal show={this.state.show} onHide={this.handleNegative}>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.props.title == 0 ? "Error" : this.props.title == 1 ? "Warning" : "Success" }</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>{this.props.message}</Modal.Body>
                    <Modal.Footer>
                        {this.props.isAnswerable ? <Button variant="secondary" onClick={this.handleNegative}>No</Button> : null}
                        <Button variant="primary" onClick={this.handlePositive}>{this.props.isAnswerable ? 'Yes' : 'Okay'}</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

export default PopupDialog;