import { ACTIONTYPE_SHOWDIALOG, ACTIONTYPE_HIDEDIALOG } from '../config/constants'

const initialState = {
    isOpen: false,
    titleEnum: '',
    message: ''
}

const dialog = (state = initialState, action) => {
    switch (action.type) {
        case ACTIONTYPE_SHOWDIALOG:
            return Object.assign({}, state, {
                isOpen: true,
                titleEnum: action.titleEnum,
                message: action.message,
                callback: action.callback
            })
        case ACTIONTYPE_HIDEDIALOG:
            return Object.assign({}, state, {
                isOpen: false,
                titleEnum: '',
                message: '',
                callback: null
            })
        default:
            return state;
    }
}

export default dialog