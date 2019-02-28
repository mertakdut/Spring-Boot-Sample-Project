// Action Types
export const ACTIONTYPE_SHOWDIALOG = 'SHOW_DIALOG';
export const ACTIONTYPE_HIDEDIALOG = 'HIDE_DIALOG';
export const ACTIONTYPE_LOGIN = 'LOGIN';
export const ACTIONTYPE_LOGOUT= 'LOGOUT';
export const ACTIONTYPE_CURRENCIESOBSOLETE = 'CURRENCIES_OBSOLETE';
export const ACTIONTYPE_CURRENCIESUPTODATE = 'CURRENCIES_UPTODATE';

// Error Messages
export const ERROR_ALLFIELDSMANDATORY = 'All fields are mandatory.';
export const ERROR_DIFFPASS = 'Password fields should be same.';
export const ERROR_TCNOLENGTH = 'Length of TC No must be 11. Currently it is: ';

// URLs
export const BASEURL_EXCHANGERATES = 'https://api.exchangeratesapi.io';
export const URL_EXCHANGERATES = '/latest?base=TRY';
export const URL_RETRIEVEHISTORY = 'transaction/findAllByUsername';
export const URL_LOGIN = 'user/login';
export const URL_REGISTER = 'user/new';
export const URL_MAKETRANSFER = 'transfer/make';
export const URL_RETRIEVEALLUSERS =  'user/findAll';
export const URL_RETRIEVEWEALTH = 'wealth/retrieve';
export const URL_MAKETRANSACTION = 'transaction/make';

// Local storage keys
export const LSKEY_USERNAME = 'username';