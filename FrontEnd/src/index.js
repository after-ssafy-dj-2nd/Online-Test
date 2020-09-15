import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import * as serviceWorker from './util/serviceWorker';
import './assets/css/default.css';
import './assets/css/normalize.css';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import IntroPage from './pages/IntroPage';

ReactDOM.render(
  <BrowserRouter>
    <Switch>
      <Route path="/" render={() => <IntroPage />} exact />
      <Route path="/" render={() => <App/>} />
    </Switch>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
