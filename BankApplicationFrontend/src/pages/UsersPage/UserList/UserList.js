import React from 'react';
import { Table } from 'react-bootstrap';
import User from './User/User'

class UserList extends React.Component {
    render() {
        const users = this.props.users.map((user, index) =>
            <User key={user.id} user={user} index={index} />
        );
        return (
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Username</th>
                        <th>TC No</th>
                    </tr>
                </thead>
                <tbody>
                    {users}
                </tbody>
            </Table>
        )
    }
}

export default UserList