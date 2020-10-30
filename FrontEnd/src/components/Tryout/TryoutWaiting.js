import React, { useReducer, useEffect } from 'react';
import { withRouter } from 'react-router'
import './waiting/ExamWaiting.css'
import ExamWaitingSection from './waiting/ExamWaitingSection'
import ExamInfo from './waiting/ExamInfo'
import ExamNotice from './waiting/ExamNotice'
import StartExamButton from './waiting/StartExamButton'
import { fetchExamInfo } from '../../api/modules/tryout'

const reducer = (state, action) => {
  const result = action.payload
  // 여기에서 시간 계산이 남은시간 계산이 필요함
  return {
    ...state,
    diffTime: 5, // 시간 계산한 초를 저장하자
    id : result.data.id,
    name : result.data.name,
    endTime : result.data.endtime,
    teacherName : result.data.teacher_name,
    startTime : result.data.starttime,
  }
}

const ExamWaitingPage = ({location, history}) => {
  const [state, dispatch] = useReducer(reducer, {
    diffTime: 8639999, // 99 일 23시간 59분 59초
    id : null,
    name : null,
    endTime : null,
    teacherName : null,
    startTime : null,
  })

  useEffect(() => {
    async function SaveExamInfo() {
      try {
        // 현재 시험정보의 id가 필요
        const examId = location.pathname.split('/')[2]
        const result = await fetchExamInfo(examId)
        // 시험이 없다면 에러가 나지 않음!
        // 이부분 수정
        console.log(result)
        dispatch({payload: result})
      }
      catch(error) {
        console.log(error)
      }
    }
    SaveExamInfo()
  },[])

  const examContentList = [
    {title : '시험시작 전 확인해주세요!', component: <ExamInfo/>},
    {title: '테스트 주의사항', component: <ExamNotice/>},
    {title: 'content3', component: <ExamInfo/>},
  ]
  return (
    <div className="examwaiting-wrap">
      <StartExamButton diffTime={state.diffTime} examId={state.id}/>
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

export default withRouter(ExamWaitingPage);