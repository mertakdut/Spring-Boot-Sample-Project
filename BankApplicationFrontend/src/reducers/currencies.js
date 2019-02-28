import { ACTIONTYPE_CURRENCIESOBSOLETE, ACTIONTYPE_CURRENCIESUPTODATE } from '../config/constants'

const currencies = (state = false, action) => {
    switch (action.type) {
        case ACTIONTYPE_CURRENCIESOBSOLETE:
            return true;
        case ACTIONTYPE_CURRENCIESUPTODATE:
            return false;
        default:
            return state;
    }
}

export default currencies