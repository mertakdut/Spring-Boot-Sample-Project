import React from 'react';
import { Table } from 'react-bootstrap';
import Request from '../../services/Request';
import PopupDialog from '../../components/PopupDialog';
import Moment from 'react-moment';

class HistoryPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            histories: [],
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
            });
    }

    render() {

        const popupDialog = this.state.isShowingPopup ?
            <PopupDialog callback={() => this.setState({ isShowingPopup: false })} title={this.state.popupTitle} message={this.state.popupMessage} isAnswerable={false} />
            : null;

        return (
            <div>
                {popupDialog}
                <HistoryList histories={this.state.histories} />
            </div>
        )
    }
}

class HistoryList extends React.Component {
    render() {
        const histories = this.props.histories.map((history, index) =>
            <History key={history.id} history={history} index={index} /> // key={user.links.self.href}
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