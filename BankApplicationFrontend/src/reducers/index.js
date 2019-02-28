import { combineReducers } from 'redux'
import dialog from './dialog'
import login from './login'
import currencies from './currencies'

export default combineReducers({
    dialog,
    login,
    currencies

})