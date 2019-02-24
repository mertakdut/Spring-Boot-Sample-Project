'use strict';

import React from 'react';
import ReactDOM from 'react-dom';

import MainPage from './pages/MainPage';

import 'bootstrap/dist/css/bootstrap.min.css';

// tag::render[]
ReactDOM.render(
	<MainPage />,
	document.getElementById('react')
)
// end::render[]