import React from 'react';
import TeacherNavBar from '../components/Teacher/TeacherNavBar'
import TeacherHome from '../components/Teacher/home/TeacherHome'
import TeacherChart from '../components/Teacher/TeacherChart'
import TeacherExam from '../components/Teacher/exam/TeacherExam'
import TeacherStudent from '../components/Teacher/TeacherStudent'
import TeacherQuestionList from '../components/Teacher/questions/TeacherQuestionList'
import TeacherExamDetail from '../components/Teacher/exam/TeacherExamDetail'
import { Redirect, Switch } from 'react-router';
import { Route } from 'react-router-dom';
import { useSelector } from 'react-redux';

const BASE_PATH = '/teacher/'

const TeacherPage = () => {
  const { loginStatus, userInfo } = useSelector(state => state.users);

  return (
    <>
      {loginStatus && userInfo.auth === 1
        ? (<div className="teacher--wrap">
            <TeacherNavBar/>
            <div className="teacher-content">
              <Switch>
                <Route path={`${BASE_PATH}home`} render={()=> <TeacherHome/>} />
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