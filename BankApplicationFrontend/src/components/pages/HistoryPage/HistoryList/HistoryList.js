import React from 'react';
import { Table } from 'react-bootstrap';
import History from './History/History'

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

export default HistoryList