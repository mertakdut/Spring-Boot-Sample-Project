import React from 'react';
import axios from 'axios';
import { Table } from 'react-bootstrap'

import apiConfig from '../../config/client';

class UsersPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = { users: [] };
    }

    componentDidMount() {
        axios.get(apiConfig.apiBaseUrl + 'user/findAll')
            .then((response) => {
                console.log(response);
                this.setState({ users: response.data });
            }).catch((error) => {
                console.log(error);
            }).finally(() => {
            });
    }

    // Want to use async/await? Add the `async` keyword to your outer function/method.
    // async getUser() {
    //     try {
    //         const response = await axios.get('/user?ID=12345');
    //         console.log(response);
    //     } catch (error) {
    //         console.error(error);
    //     }
    // }

    render() {
        return (
            <div>
                <UserList users={this.state.users} />
            </div>
        )
    }
}

class UserList extends React.Component {
    render() {
        const users = this.props.users.map((user, index) =>
            <User key={user.id} user={user} index={index} /> // key={user.links.self.href}
        );
        return (
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>TcNo</th>
                    </tr>
                </thead>
                <tbody>
                    {users}
                </tbody>
            </Table>
        )
    }
}

class User extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.index + 1}</td>
                <td>{this.props.user.username}</td>
                <td>{this.props.user.tcno}</td>
            </tr>
        )
    }
}

export default UsersPage;