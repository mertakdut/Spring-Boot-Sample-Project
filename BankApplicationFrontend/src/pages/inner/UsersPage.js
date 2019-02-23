import React from 'react';
import axios from 'axios';
import { Table, Button } from 'react-bootstrap'

class UsersPage extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
		axios.get('http://localhost:8080/api/users').then(response => {
            console.log(response);
            this.setState({users: response.data._embedded.users});
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
				<td>{this.props.index + 1}</td>
				<td>{this.props.user.username}</td>
				<td>{this.props.user.tcno}</td>
			</tr>
		)
	}
}

export default UsersPage;