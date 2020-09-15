import React from 'react';
import './App.css';
import Header from './components/Common/Header';
import Footer from './components/Common/Footer';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import MainPage from './pages/MainPage';
import ExamPage from './pages/ExamPage';
import TeacherPage from './pages/TeacherPage';
import NotFoundPage from './pages/NotFoundPage';
import { Switch } from 'react-router';
import { Route } from 'react-router-dom';

function App() {
  return (
    <>
      <Header />
      <main>
        <Switch>
          <Route path="/main" render={() => <MainPage />} />
          <Route path="/login" render={() => <LoginPage />} />
          <Route path="/signup" render={() => <SignupPage />} />
          <Route path="/exam" render={() => <ExamPage />} />
          <Route path="/teacher" render={() => <TeacherPage />} />
          <Route component={NotFoundPage} />
        </Switch>
      </main>
      <Footer />
    </>
  );
}

export default App;
