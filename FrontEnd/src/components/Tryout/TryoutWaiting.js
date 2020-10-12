import React, { useReducer, useEffect } from 'react';
import './waiting/ExamWaiting.css'
import ExamWaitingSection from './waiting/ExamWaitingSection'
import ExamInfo from './waiting/ExamInfo'
import ExamNotice from './waiting/ExamNotice'
import StartExamButton from './waiting/StartExamButton'
import axios from 'axios'

const getExamInfo = () => {
  const baseUrl = `http://221.158.91.249:5000/api/readytest?exam_id=${1}`
  const headers = {
    'access-token' : process.env.REACT_APP_TEMP_TOKEN,
  }
  return axios.get(baseUrl, {headers:headers})
  .then(result => result)
  .catch(error => console.log('error', error));
}

const reducer = (state, action) => {
  const result = action.payload
  return {
    ...state,
    diffTime: 40000,
    id : result.data.id,
    name : result.data.name,
    endTime : result.data.endtime,
    teacherName : result.data.teacher_name,
    startTime : result.data.starttime,
  }
}

const ExamWaitingPage = () => {
  const [state, dispatch] = useReducer(reducer, {
    diffTime: 9999999,
    id : null,
    name : null,
    endTime : null,
    teacherName : null,
    startTime : null,
  })

  useEffect(() => {
    async function aws() {
      const result = await getExamInfo()
      dispatch({payload: result})
    }
    aws()
  },[])

  const examContentList = [
    {title : '시험시작 전 확인해주세요!', component: <ExamInfo/>},
    {title: '테스트 주의사항', component: <ExamNotice/>},
    {title: 'content3', component: <ExamInfo/>},
  ]
  return (
    <div className="examwaiting-wrap">
      <StartExamButton diffTime={state.diffTime}/>
      <div className="examwaiting-content">
        {examContentList.map((content, i) => {
          return (<ExamWaitingSection key={i} title={content.title}>
            {content.component}
          </ExamWaitingSection>)
        })}
      </div>
    </div>
  );
};

export default ExamWaitingPage;