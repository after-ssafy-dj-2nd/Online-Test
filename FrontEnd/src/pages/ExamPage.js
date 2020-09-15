import React from 'react';
import { Switch } from 'react-router';
import { Route } from 'react-router-dom';
import ExamList from '../components/Exam/ExamList';
import ExamForm from '../components/Exam/ExamForm';

const ExamPage = () => {
  return (
    <>
      <Switch>
        <Route path="/" render={() => <ExamList /> } exact />
        <Route path="/exam/:examId" render={(props) => <ExamForm {...props} /> } />
      </Switch>
    </>
  );
};

export default ExamPage;