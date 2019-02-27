import React from 'react'
import { Modal, Button } from 'react-bootstrap'
import { connect } from 'react-redux'
import { closeDialog } from '../actions';

const mapStateToProps = state => ({
    show: state.dialog.isOpen,
    title: state.dialog.title,
    message: state.dialog.message
})

const mapDispatchToProps = dispatch => ({
    onClose: () => dispatch(closeDialog)
})

class PopupDialog extends React.Component {
    constructor(props, context) {
        super(props, context);

        // this.handlePositive = this.handlePositive.bind(this);
        // this.handleNegative = this.handleNegative.bind(this);
    }

    componentDidMount() {
        console.log(this.props.store);
    }

    // handlePositive() {
    //     this.setState({ show: false });
    //     this.props.callback(true);
    // }

    // handleNegative() {
    //     this.setState({ show: false });
    //     this.props.callback(false);
    // }

    render() {

        console.log(this.props.show);

        return (
            <div>
                <Modal show={this.props.show} onHide={this.props.onClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.props.title}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>{this.props.message}</Modal.Body>
                    <Modal.Footer>
                        {this.props.isAnswerable ? <Button variant="secondary" onClick={this.props.onClose}>No</Button> : null}
                        <Button variant="primary" onClick={this.props.onClose}>{this.props.isAnswerable ? 'Yes' : 'Okay'}</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(PopupDialog)