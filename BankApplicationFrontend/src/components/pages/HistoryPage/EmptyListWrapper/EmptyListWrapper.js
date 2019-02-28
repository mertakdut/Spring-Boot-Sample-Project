import React from 'react';
import { Jumbotron } from 'react-bootstrap';

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

export default EmptyListWrapper