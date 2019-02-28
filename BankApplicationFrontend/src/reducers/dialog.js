const initialState = {
    isOpen: false,
    titleEnum: '',
    message: ''
}

const dialog = (state = initialState, action) => {
    switch (action.type) {
        case 'SHOW_DIALOG':
            return Object.assign({}, state, {
                isOpen: true,
                titleEnum: action.titleEnum,
                message: action.message,
                callback: action.callback
            })
        case 'HIDE_DIALOG':
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