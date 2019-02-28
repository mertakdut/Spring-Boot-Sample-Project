'use strict';

import React from 'react';
import ReactDOM from 'react-dom';

import MainPage from './components/pages/MainPage/MainPage';

import 'bootstrap/dist/css/bootstrap.min.css';

import { createStore } from 'redux'
import { Provider } from 'react-redux'
import rootReducer from './reducers'

const store = createStore(rootReducer)

// tag::render[]
ReactDOM.render(
    <Provider store={store}>
        <MainPage />
    </Provider>,
    document.getElementById('react')
)
// end::render[]