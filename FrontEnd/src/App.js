import React from 'react';
import './App.css';
import Header from './components/Common/Header';
import Footer from './components/Common/Footer';
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
        <Route path="/main" render={() => <MainPage />} />
        <Route path="/login" render={() => <LoginPage />} />
        <Route path="/signup" render={() => <SignupPage />} />
        <Route path="/test" render={() => <TestPage />} />
        <Route path="/teacher" render={() => <TeacherPage />} />
        <Route component={NotFoundPage} />
      </Switch>
      <Footer />
    </>
  );
}

export default App;
