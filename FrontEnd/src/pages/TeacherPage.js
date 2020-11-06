import React from 'react';
import TeacherNavBar from '../components/Teacher/TeacherNavBar'
import TeacherHome from '../components/Teacher/home/TeacherHome'
import TeacherChart from '../components/Teacher/TeacherChart'
import TeacherExam from '../components/Teacher/exam/TeacherExam'
import TeacherStudent from '../components/Teacher/TeacherStudent'
import TeacherQuestionListAdd from '../components/Teacher/questions/TeacherQuestionListAdd'
import TeacherQuestionList from '../components/Teacher/questions/TeacherQuestionList'
import TeacherExamDetail from '../components/Teacher/exam/TeacherExamDetail'
import { Redirect, Switch } from 'react-router';
import { Route } from 'react-router-dom';
import { storage } from '../util/storage'

const BASE_PATH = '/teacher/'

const TeacherPage = () => {
  const loginStatus = storage(sessionStorage).getItem('loginStatus');
  const userInfo = storage(sessionStorage).getItem('userInfo');

  return (
    <>
      {loginStatus && userInfo.auth === 1
        ? (<div className="teacher--wrap">
            <TeacherNavBar/>
            <div className="teacher-content">
              <Switch>
                <Route path={`${BASE_PATH}home`} render={()=> <TeacherHome/>} />
                <Route path={`${BASE_PATH}questions/add`} render={() => <TeacherQuestionListAdd />} />
                <Route path={`${BASE_PATH}questions`} render={() => <TeacherQuestionList />} />
                <Route path={`${BASE_PATH}exam/:id`} render={(props) => <TeacherExamDetail {...props}/>} />
                <Route path={`${BASE_PATH}exam`} render={() => <TeacherExam />} exact/>
                <Route path={`${BASE_PATH}student`} render={() => <TeacherStudent />} />
                <Route path={`${BASE_PATH}chart`} render={() => <TeacherChart />} />
              </Switch>
            </div>
          </div>)
        : <Redirect to="/main" />
      }
    </>
  );
};

export default TeacherPage;