import React from 'react';
import ReactDOM from 'react-dom';
import { createStore } from 'redux';
import { Provider } from 'react-redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import App from './App';
import * as serviceWorker from './util/serviceWorker';
import './assets/css/default.css';
import './assets/css/normalize.css';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import IntroPage from './pages/IntroPage';
import rootReducer from './store';
import TryoutPage from './pages/TryoutPage';

const store = createStore(rootReducer, composeWithDevTools());

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <Switch>
        <Route path="/" render={() => <IntroPage />} exact />
        <Route path={["/tryout/:examId/", "/tryout/:examId/:status"]} render={() => <TryoutPage />} />
        <Route path="/" render={() => <App/>} />
      </Switch>
    </BrowserRouter>
  </Provider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
