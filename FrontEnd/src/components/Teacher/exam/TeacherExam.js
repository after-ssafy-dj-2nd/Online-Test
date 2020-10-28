import React, {useReducer, useEffect } from 'react';
import { withRouter } from 'react-router-dom';

import { reducer } from '../../../util/reducer';
import { useSelector } from 'react-redux';
import { createExam } from '../../../api/modules/exams'
const TeacherExam = ({history}) => {
  const { userInfo } = useSelector(state => state.users);
  const [state, dispatch] = useReducer(reducer, {
    name: '시험 제목',
    starttime: '2020-10-19T16:00',
    endtime:'2020-10-19T20:00', 
  });
  
  const {name,starttime, endtime} = state
  useEffect(()=>{
    const data = state
    data['teacher_id'] = userInfo.id
    data['teacher_name'] = userInfo.name
    data['questions'] = []
    dispatch(data)
  },[userInfo])
  const onChange = e => {
    dispatch(e.target)
  }
  const checkForm = () => {
    if (!name){
      alert('시험 제목을 입력하세요');
      return
    }
    if (!starttime) {
      alert('시작 시간을 입력해주세요')
      return
    } 
    if (!endtime) {
      alert('시작 시간을 입력해주세요')
      return
    }
    if (starttime > endtime) {
      alert('시험 종료시간이 시작시간보다 늦어야합니다')
      return
    }
    return true
  }
  const onSubmit = async e => {
    e.preventDefault();
    if (checkForm) {
      const data = state
      data['starttime'] = data['starttime'].replace('T',' ') + ':00'
      data['endtime'] = data['endtime'].replace('T',' ') + ':00'
      console.log(data)
      await createExam(data)
      history.push('/teacher/home');
    }
  }

  return (
    <div>
      <form className="exam-form" onSubmit={onSubmit}>
        <article>
          <label htmlFor="title">시험이름 </label>
          <input id="name" value={name} onChange={onChange} placeholder="시험이름을 입력해주세요"/>
        </article>
        <article>
          <label htmlFor="starttime">시작시간</label>
          <input type="datetime-local" id="starttime" value={starttime} onChange={onChange}/>
        </article>
        <article>
        <label htmlFor="endtime">종료시간</label>
          <input type="datetime-local" id="endtime" value={endtime} onChange={onChange}/>
        </article>
        <button type="submit">문제집 완료</button>
      </form>
    </div>
  )
}

export default withRouter(TeacherExam);
