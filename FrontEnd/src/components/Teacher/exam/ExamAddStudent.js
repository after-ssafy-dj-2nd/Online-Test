import React, { useState } from 'react'
import validation from '../../../util/validation'
import XLSX from 'xlsx';
const REGISTED = ['leesy1403@naver.com' , 'wally-wally@google.com']

const ExamAddStudent = () => {
  const [registed , setRegisted] = useState(REGISTED)
  const [email, setEmail] = useState('')
  const [checkList, setCheckList] = useState([])
  const onChange = id => {
    if (checkList.includes(id)) {
      setCheckList(checkList.filter(item => item !== id));
    } else {
      setCheckList(checkList.concat(id));
    }
  };
  const emailDuplCheck = (email) => {
    if (registed.includes(email)){
      return false
    } else {
      return true
    }
  }
  const checkItem = (id) => {
    return checkList.includes(id)
  } 
  const allSelect = () => {
    setCheckList(registed.map((val,i)=>i))
  }
  const allUnSelect = () => {
    setCheckList([])
  }
  const selectDelete = () => {
    const confirm = window.confirm(`선택된 ${checkList.length}개의 메일을 지우시겠습니까?`)
    if (confirm) {
      setRegisted(registed.filter((email,id)=> {
        if (!(checkList.includes(id))){
          return true
        }
      })
      )
      setCheckList([])
    }
  }
  const excelExport = (event) => {
    var input = event.target;
    var reader = new FileReader();
    reader.onload = function(){
      var fileData = reader.result;
      var wb = XLSX.read(fileData, {type : 'binary'});
      const sheet = wb.Sheets[wb.SheetNames[0]]
      const mailList = Object.entries(sheet).map(data => data[1].v).slice(0,-1)
      const validMailList = mailList.filter(mail=> emailDuplCheck(mail) && validation.emailValidation(mail))
      const uniqValidList = [...new Set(validMailList)];
      const failMailList = mailList.filter(mail=> !validation.emailValidation(mail))
      const confirm = window.confirm(`${failMailList.length ? failMailList.join('\n') + '\n제외' : ''} ${uniqValidList.length} 개의 메일을 추가합니다`)
      if (confirm) {
        setRegisted(registed.concat(validMailList))
      }
    };
    reader.readAsBinaryString(input.files[0]);
  }
  const emailSubmit = (e) => {
    e.preventDefault()
    if (email){
      if (validation.emailValidation(email)) {
        if (emailDuplCheck(email)){
          setRegisted(registed.concat(email))
          setEmail('')
        } else {
          alert('중복된 이메일이 존재합니다.')
        }
      } else {
        alert('올바른 이메일을 입력해주세요')
      }
    }
  }
  const inputChange = (e) => {
    setEmail(e.target.value)
  }
  return (
    <>
      <hr/>
      <form onSubmit={emailSubmit}>
        <input type="text" onChange={e=>inputChange(e)} value={email}/>
        <button type="submit" className="btn btn--small">
          등록
        </button>
      </form>
      <input type="file" accept=".xlsx,.xls" id="excelFile" onChange={excelExport}/>
      <p><small>! xlsx 파일 첫 번째 sheet의 A열을 가져옵니다</small></p>
      <div><span>등록된 학생 수 : {registed.length} 개</span></div>
      <div onClick={allSelect} className="btn btn--xsmall">전체 선택</div>
      <div onClick={allUnSelect} className="btn btn--xsmall">선택 취소</div>
      <div onClick={selectDelete} className="btn btn--xsmall">선택 삭제</div>
      <ul className="email-list">
        <li>
          <div className="email-checked" />
          <div className="student-email">email</div>
        </li>
        {registed.map((student, i) =>
          <li key={i}>
            <div className="email-checked">
              <i onClick={()=> onChange(i)}
                className={"far " + (checkItem(i) ? "fa-check-square" : "fa-square")}
              >
              </i>
            </div>
            <div className="student-email">
              <span>{student}</span>
            </div>
          </li>
        )}
      </ul>
    </>
  )
}

export default ExamAddStudent
