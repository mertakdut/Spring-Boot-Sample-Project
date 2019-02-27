import React from 'react';
import { Table, Jumbotron } from 'react-bootstrap';
import Request from '../../services/Request';
import PopupDialog from '../../components/PopupDialog';
import Moment from 'react-moment';

class HistoryPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            histories: [],
            isDoneLoadingHistories: false,
            isShowingPopup: false
        };
    }

    componentDidMount() {
        const request = new Request().getRequestInstance();
        request.get('transaction/findAll')
            .then((response) => {
                console.log(response);
                this.setState({ histories: response.data });
            }).catch((error) => {
                console.log(error);
                var errorMessage = 'Network error';
                if (error != null && error.response != null && error.response.data != null && error.response.data.message != null) {
                    errorMessage = error.response.data.message;
                }
                this.setState({ isShowingPopup: true, popupTitle: 0, popupMessage: errorMessage });
            }).finally(() => {
                this.setState({ isDoneLoadingHistories: true });
            });
    }

    render() {

        const popupDialog = this.state.isShowingPopup ?
            <PopupDialog callback={() => this.setState({ isShowingPopup: false })} title={this.state.popupTitle} message={this.state.popupMessage} isAnswerable={false} />
            : null;

        const bestory = this.state.isDoneLoadingHistories ? this.state.histories != null && Object.keys(this.state.histories).length !== 0 ?
            <HistoryList histories={this.state.histories} /> : <EmptyListWrapper /> : null;

        return (
            <div>
                {popupDialog}
                {bestory}
            </div>
        )
    }
}

class EmptyListWrapper extends React.Component {

    render() {
        return (
            <Jumbotron>
                <h1>No Transaction Records Found!</h1>
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

export default HistoryPage;