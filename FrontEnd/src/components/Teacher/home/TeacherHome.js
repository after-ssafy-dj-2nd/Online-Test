import React , {useState,useEffect} from 'react'
import Exam from './exam'
import {fetchExams, deleteExam} from '../../../api/modules/exams'

const statuses = ['완료', '진행중','종료']
let EXAM_SAMPLE = []
let sortedTime = false;
const TeacherHome = () => {
  const [exams , setExams] = useState(EXAM_SAMPLE)
  const [showStatusList, setShowStatusList] = useState(false)
  useEffect(()=>{
    const fetchData = async ()=> {
      try {
        const { data } = await fetchExams();
        EXAM_SAMPLE = data.exams
        setExams(EXAM_SAMPLE);
      } catch {
        setExams(EXAM_SAMPLE)
        return
      }
    };
    fetchData()
  },[])
  const sortedExam = () => {
    if(sortedTime){
      setExams(exams.sort(exam=> exam.starttime))
      sortedTime = false
    } else {
      setExams(exams.sort(exam=> exam.starttime).reverse())
      sortedTime = true
    }
  }
  const filterExam = (e) => {
    setExams(EXAM_SAMPLE.filter(exam=> exam.name.includes(e.target.value)))
  }

  const deleteExamItem = async (exam) => {
    const confirm = window.confirm(`정말 '${exam.name}' 시험을 삭제하시겠습니까`)
    if (confirm){
      EXAM_SAMPLE = EXAM_SAMPLE.filter(e => e.id !== exam.id)
      setExams(EXAM_SAMPLE)
      await deleteExam(exam.id)
    }
  }
  const showMenu = () =>{
    setShowStatusList(!showStatusList)
  }
  return (
    <div>
      <input className="exam-search" placeholder="제목으로 검색할 수 있습니다" onChange={e=>filterExam(e)}/>
      <div className="exam--table text-center">
        <div className="exam--row">
          <div>공개</div>
          <div>제목</div>
          <div>등록된 문제 수</div>
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
          <div>
            삭제
          </div>
        </div>
        {exams.map((exam, key) => <Exam key={key} deleteExam={()=>deleteExamItem(exam)} exam={exam}></Exam>)}
      </div>
    </div>
  )
}

export default TeacherHome