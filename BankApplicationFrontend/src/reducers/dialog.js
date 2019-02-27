const initialState = {
    isOpen: false,
    title: '',
    message: ''
}

const dialog = (state = initialState, action) => {
    switch (action.type) {
        case 'SHOW_DIALOG':
            return Object.assign({}, state, {
                isOpen: true,
                title: action.title,
                message: action.message
            })
        case 'HIDE_DIALOG':
            return Object.assign({}, state, {
                isOpen: false,
                title: '',
                message: ''
            })
        default:
            return state;
    }
}

export default dialog