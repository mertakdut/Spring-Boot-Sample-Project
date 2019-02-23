'use strict';

import React from 'react';
import ReactDOM from 'react-dom';

import MainPage from './pages/MainPage';

//import 'bootstrap';
// import 'bootstrap/js/dist/navigation';
import 'bootstrap/dist/css/bootstrap.min.css';

// tag::render[]
ReactDOM.render(
	<MainPage />,
	document.getElementById('react')
)
// end::render[]