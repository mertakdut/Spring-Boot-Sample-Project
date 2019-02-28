import {
    ACTIONTYPE_SHOWDIALOG,
    ACTIONTYPE_HIDEDIALOG,
    ACTIONTYPE_LOGIN,
    ACTIONTYPE_LOGOUT,
    ACTIONTYPE_CURRENCIESOBSOLETE,
    ACTIONTYPE_CURRENCIESUPTODATE
} from '../config/constants'

export const showDialog = (titleEnum, message, callback) => ({
    type: ACTIONTYPE_SHOWDIALOG,
    titleEnum,
    message,
    callback
})

export const closeDialog = {
    type: ACTIONTYPE_HIDEDIALOG
}

export const login = (username) => ({
    type: ACTIONTYPE_LOGIN,
    username
})

export const logout = {
    type: ACTIONTYPE_LOGOUT
}

export const currenciesObsolete = {
    type: ACTIONTYPE_CURRENCIESOBSOLETE
}

export const currenciesUptodate = {
    type: ACTIONTYPE_CURRENCIESUPTODATE
}