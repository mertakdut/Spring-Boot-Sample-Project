import React from 'react';

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

export default User