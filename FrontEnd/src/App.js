import React from 'react';
import './App.css';
import Header from './components/Common/Header';
import Footer from './components/Common/Footer';
import IntroPage from './pages/IntroPage';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import MainPage from './pages/MainPage';
import TestPage from './pages/TestPage';
import TeacherPage from './pages/TeacherPage';
import NotFoundPage from './pages/NotFoundPage';
import { Switch } from 'react-router';
import { Route } from 'react-router-dom';

function App() {
  return (
    <>
      <Header />
      <Switch>
        <Route path="/" render={() => <IntroPage />} exact />
        <Route path="/main" render={() => <MainPage />} exact />
        <Route path="/login" render={() => <LoginPage />} exact />
        <Route path="/signup" render={() => <SignupPage />} exact />
        <Route path="/test" render={() => <TestPage />} exact />
        <Route path="/teacher" render={() => <TeacherPage />} exact />
        <Route component={NotFoundPage} />
      </Switch>
      <Footer />
    </>
  );
}

export default App;
