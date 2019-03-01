import { ACTIONTYPE_LOGIN, ACTIONTYPE_LOGOUT } from '../config/constants'

const login = (state = null, action) => {
    switch (action.type) {
        case ACTIONTYPE_LOGIN:
            return Object.assign({}, state, {
                username: action.username,
                token: action.token
            })
        case ACTIONTYPE_LOGOUT:
            return null;
        default:
            return state;
    }
}

export default login