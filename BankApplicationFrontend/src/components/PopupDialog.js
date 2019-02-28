import React from 'react'
import { Modal, Button } from 'react-bootstrap'
import { connect } from 'react-redux'
import { closeDialog } from '../actions';

const mapStateToProps = state => ({
    show: state.dialog.isOpen,
    titleEnum: state.dialog.titleEnum,
    message: state.dialog.message,
    callback: state.dialog.callback
})

const mapDispatchToProps = dispatch => ({
    onClose: () => dispatch(closeDialog)
})

class PopupDialog extends React.Component {

    constructor(props) {
        super(props);
        this.onCloseDialog = this.onCloseDialog.bind(this);
    }

    onCloseDialog() {
        if (this.props.callback) {
            this.props.callback();
        }
        this.props.onClose();
    }

    render() {

        return (
            <div>
                <Modal show={this.props.usingAsWidget ? true : this.props.show} onHide={this.props.onClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.props.titleEnum == 0 ? "Error" : this.props.titleEnum == 1 ? "Warning" : "Success"}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>{this.props.message}</Modal.Body>
                    <Modal.Footer>
                        {this.props.callback ? <Button variant="secondary" onClick={this.props.onClose}>No</Button> : null}
                        <Button variant="primary" onClick={this.onCloseDialog}>{this.props.callback ? 'Yes' : 'Okay'}</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(PopupDialog)