// TODO: Soft-coded action types.
// export const ADD_TODO = 'ADD_TODO'

export const showDialog = (titleEnum, message, callback) => ({
    type: 'SHOW_DIALOG',
    titleEnum,
    message,
    callback
})

export const closeDialog = {
    type: 'HIDE_DIALOG'
}

export const login = (username) => ({
    type: 'LOGIN',
    username
})

export const logout = {
    type: 'LOGOUT'
}

export const currenciesObsolete = {
    type: 'CURRENCIES_OBSOLETE'
}

export const currenciesUptodate = {
    type: 'CURRENCIES_UPTODATE'
}