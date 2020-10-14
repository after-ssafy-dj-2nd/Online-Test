import React, {useReducer} from 'react';
import { reducer } from '../../../util/reducer';

const TeacherExam = () => {
  const [state, dispatch] = useReducer(reducer, {
    title: '시험 제목',
    startTime: '2020-07-01T13:00',
    endTime:'2020-07-01T15:00',
    isOpen: true,
    participants : '13',
    password : ''
  });
  
  const {title,startTime, endTime, isOpen,participants, password} = state
  
  const onChange = e => {
    dispatch(e.target)
  }
  
  const onCheckChange = (e) => {
    dispatch({id: 'isOpen',value: !isOpen})
  }

  const onSubmit = e => {
    e.preventDefault();
    if (!title){
      alert('시험 제목을 입력하세요');
      return
    }
    if (!startTime) {
      alert('시작 시간을 입력해주세요')
      return
    } 
    if (!endTime) {
      alert('시작 시간을 입력해주세요')
      return
    }
    if (!participants){
      alert('정원수를 입력해주세요')
      return
    }
    if (!isOpen && !password){
      alert('비공개일시 비밀번호는 필수값입니다.')
      return
    }
    if (startTime > endTime) {
      alert('시험 종료시간이 시작시간보다 늦어야합니다')
      return
    }

    // api 저장 로직
    console.log(state)
    
    // 문제생성부분으로 전달
    window.location.href="/teacher/home"
  }

  return (
    <div>
      <form className="exam-form" onSubmit={onSubmit}>
        <article>
          <label htmlFor="title">시험이름 </label>
          <input id="title" value={title} onChange={onChange} placeholder="시험이름을 입력해주세요"/>
        </article>
        <article>
          <label htmlFor="startTime">시작시간</label>
          <input type="datetime-local" id="startTime" value={startTime} onChange={onChange}/>
        </article>
        <article>
        <label htmlFor="endTime">종료시간</label>
          <input type="datetime-local" id="endTime" value={endTime} onChange={onChange}/>
        </article>
        <article>
        <label htmlFor="participants">정원</label>
          <input type="number" min='0' id="participants" value={participants} onChange={onChange}/>
        </article>       
        <article>
          <label htmlFor="isOpen">공개</label>
          <input type="checkbox" id="isOpen" onChange={onCheckChange} checked={isOpen} />
        </article>
        <article className={"password-wrap" + (isOpen ? '' : ' show')}>
          <label htmlFor="password">비밀번호 : </label>
          <input type="password" id="password" value={password} onChange={onChange}/> 
        </article>
        <button type="submit">문제집 완료</button>
      </form>
    </div>
  )
}

export default TeacherExam
