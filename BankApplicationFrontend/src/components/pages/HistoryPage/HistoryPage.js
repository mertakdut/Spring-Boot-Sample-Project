import React from 'react'
import { connect } from 'react-redux'
import { showDialog } from '../../../actions'
import Request from '../../../services/Request';
import { URL_RETRIEVEHISTORY } from '../../../config/constants'
import EmptyListWrapper from './EmptyListWrapper/EmptyListWrapper'
import HistoryList from './HistoryList/HistoryList'

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
            request.post(URL_RETRIEVEHISTORY, {
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

export default connect(mapStateToProps, mapDispatchToProps)(HistoryPage);