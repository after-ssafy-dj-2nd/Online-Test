import React , {useState,useEffect} from 'react'
import Exam from './exam'
import {fetchExams} from '../../api/modules/exams'

const statuses = ['완료', '진행중','종료']
let EXAM_SAMPLE = [
    {
      title: '1번 시험',
      startTime: '2020-09-15T13:00',
      endTime:'2020-09-15T15:00',
      isOpen: true,
      participants : 10,
      password : ''
    },
    {
      title: '2번 시험',
      startTime: '2020-09-16T00:00',
      endTime:'2020-09-17T15:00',
      isOpen: false,
      participants : 20,
      password : 'asdf'
    },
    {
      title: '3번 시험',
      startTime: '2020-10-07T14:00',
      endTime:'2020-10-07T17:30',
      isOpen: false,
      participants : 12,
      password : 'asdf'
    },
  ]
let sortedTime = false;
const TeacherHome = () => {
  const [exams , setExams] = useState(EXAM_SAMPLE)
  const [showStatusList, setShowStatusList] = useState(false)
  useEffect(()=>{
    const fetchData = async ()=> {
      try {
        const { data } = await fetchExams();
        setExams(data);
        EXAM_SAMPLE = data
      } catch {
        setExams(EXAM_SAMPLE)
        return
      }
    };
    fetchData()
  },[])
  const sortedExam = () => {
    if(sortedTime){
      setExams([...exams].sort(exam=> exam.startTime))
      sortedTime = false
    } else {
      setExams([...exams].sort(exam=> exam.startTime).reverse())
      sortedTime = true
    }
  }
  const filterExam = (e) => {
    setExams(EXAM_SAMPLE.filter(exam=> exam.title.includes(e.target.value)))
  }
  const showMenu = () =>{
    setShowStatusList(!showStatusList)
    console.log(showStatusList)
  }
  return (
    <div>
      <input className="exam-search" placeholder="제목으로 검색할 수 있습니다" onChange={e=>filterExam(e)}/>
      <div className="exam--table text-center">
        <div className="exam--row">
          <div>공개</div>
          <div>제목</div>
          <div>인원수</div>
          <div onClick={sortedExam}>시험시간<i className={"fas " + (sortedTime ? 'fa-sort-numeric-up' : 'fa-sort-numeric-down')}></i></div>
          <div>진행시간</div>
          <div onClick={showMenu}>상태
            <ul className={"status-list" + (showStatusList ? ' show' : '')}>
              {statuses.map((status,key)=>
                <li key={key}>
                  <button className="status-filter">
                    {status}
                  </button>
                </li>)}
            </ul>
          </div>
        </div>
        {exams.map((exam, key) => <Exam key={key} id={key+1} exam={exam}></Exam>)}
      </div>
    </div>
  )
}

export default TeacherHome
