
// TODO: Soft-coded action types.
// export const ADD_TODO = 'ADD_TODO'

export const showDialog = (title, message) => ({
    type: 'SHOW_DIALOG',
    title,
    message
})

export const closeDialog = {
    type: 'HIDE_DIALOG'
}