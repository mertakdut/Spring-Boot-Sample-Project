import React from 'react';
import { Table, Jumbotron } from 'react-bootstrap';
import { connect } from 'react-redux'
import Moment from 'react-moment';

import Request from '../../services/Request'
import { showDialog } from '../../actions';

const mapStateToProps = state => ({
    loggedInUsername: state.login
})

const mapDispatchToProps = dispatch => ({
    showPopup: (title, message) => dispatch(showDialog(title, message))
})

class HistoryPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            histories: [],
            isRetrievingRecords: false
        };

        this.retrieveHistoryRecords = this.retrieveHistoryRecords.bind(this);
    }

    retrieveHistoryRecords() {
        if (!this.state.isRetrievingRecords) {
            this.setState({ isRetrievingRecords: true });

            const request = new Request().getRequestInstance();
            request.post('transaction/findAllByUsername', {
                username: this.props.loggedInUsername
            }).then((response) => {
                console.log(response);
                this.setState({ histories: response.data });
            }).catch((error) => {
                console.log(error.response);
                var errorMessage = 'Network error';
                if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                    errorMessage = error.response.data.message;
                }
                this.props.showPopup(0, errorMessage);
            }).finally(() => {
                this.setState({ isRetrievingRecords: false });
            });
        }
    }

    componentDidMount() {
        if (this.props.loggedInUsername != null) {
            this.retrieveHistoryRecords();
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.loggedInUsername != null && this.props.loggedInUsername != prevProps.loggedInUsername) {
            this.retrieveHistoryRecords();
        }
    }

    render() {

        if (!this.state.isRetrievingRecords) {
            if (this.state.histories != null && Object.keys(this.state.histories).length !== 0) {
                return <HistoryList histories={this.state.histories} />
            } else {
                return <EmptyListWrapper />
            }
        }

        return null;
    }
}

class EmptyListWrapper extends React.Component {

    render() {
        return (
            <Jumbotron>
                <h1 className="text-center">No Transaction Records Found!</h1>
                <p>
                    I wish I could show you some data over here... But apparently none found either in database
                    (*cough*mocked*cough*) -please excuse my 'phlegm'-
                    a there's a real problem about the connection.
                </p>
            </Jumbotron>
        )
    }

}

class HistoryList extends React.Component {
    render() {
        const histories = this.props.histories.map((history, index) =>
            <History key={history.id} history={history} index={index} />
        );
        return (
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Bought/Sold</th>
                        <th>Amount</th>
                        <th>Currency</th>
                        <th>Transaction Time</th>
                    </tr>
                </thead>
                <tbody>
                    {histories}
                </tbody>
            </Table>
        )
    }
}

class History extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.history.bought ? "Bought" : "Sold"}</td>
                <td>{this.props.history.amount}</td>
                <td>{this.props.history.currency}</td>
                <td>
                    <Moment format="hh:mm:ss DD/MM/YYYY">
                        {this.props.history.transactionTime}
                    </Moment>
                </td>
            </tr>
        )
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(HistoryPage);