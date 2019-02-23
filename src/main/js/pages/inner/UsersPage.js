import React from 'react';
const client = require('../../services/client');
import { Table, Button } from 'react-bootstrap'

class UsersPage extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/users'}).done(response => {
			this.setState({users: response.entity._embedded.users});
		});
	}

	render() {
		return (
			<div>
				<UserList users={this.state.users}/>
				<Button>aa</Button>
			</div>
		)
	}
}

class UserList extends React.Component {
	render() {
		const users = this.props.users.map((user, index) =>
			<User key={user._links.self.href} user={user} index={index}/>
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

class User extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.index}</td>
				<td>{this.props.user.username}</td>
				<td>{this.props.user.tcno}</td>
			</tr>
		)
	}
}

export default UsersPage;