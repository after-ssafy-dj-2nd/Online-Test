import React from 'react';
import TeacherNavBar from '../components/Teacher/TeacherNavBar'
import TeacherHome from '../components/Teacher/TeacherHome'
import TeacherChart from '../components/Teacher/TeacherChart'
import TeacherExam from '../components/Teacher/TeacherExam'
import TeacherStudent from '../components/Teacher/TeacherStudent'
import { Switch } from 'react-router';
import { Route } from 'react-router-dom';
const BASE_PATH = '/teacher/'
const TeacherPage = () => {
  return (
    <div id="teacher--wrap">
      <TeacherNavBar/>
      <div id="teacher-content">
        <Switch>
          <Route path={`${BASE_PATH}`} render={()=> <TeacherHome/>} exact/>
          <Route path={`${BASE_PATH}exam`} render={() => <TeacherExam />} />
          <Route path={`${BASE_PATH}student`} render={() => <TeacherStudent />} />
          <Route path={`${BASE_PATH}chart`} render={() => <TeacherChart />} />
        </Switch>
      </div>
    </div>
  );
};

export default TeacherPage;