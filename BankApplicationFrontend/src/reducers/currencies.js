const currencies = (state = false, action) => {
    switch (action.type) {
        case 'CURRENCIES_OBSOLETE':
            return true;
        case 'CURRENCIES_UPTODATE':
            return false;
        default:
            return state;
    }
}

export default currencies