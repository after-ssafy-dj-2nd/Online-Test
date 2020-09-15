import React, {useState} from 'react'
import TeacherQuestion from './TeacherQuestion'
import {$} from '../../util/DOM'

const defaultQuestion = [
  {content:'', examples : [{content : '', correct:false},{content : '', correct:false},{content : '', correct:false}], description: '' ,score : 5,correct : [0,1]},
]

const totalScore = (questions) => {
  return questions.reduce((score,question) => 
    score + (parseInt(question.score) || 0), 0
  )
}
  
const TeacherQuestionList = () => {
  let [showIndex , setShowIndex] = useState(0)

  const [questions,setQuestions] = useState([
    {content:'이 중 먹을 수 없는 것은', examples : [{content : '마라탕', correct:false},{content : '설렁탕', correct:false},{content : '곰탕', correct:false},{content : '목욕탕', correct:true}], description : '',score : 50},
    {content:'2번 문제', examples : [{content : '1', correct:false},{content : '2', correct:false},{content : '3', correct:true}], description :'2번 문제는 ' ,score : 5},
  ]);

  const addQuestions = () => {
    setShowIndex(questions.length);
    setQuestions(questions.concat(defaultQuestion))
  }
  const setQuestion = (newQuestion)=> {
    setQuestions(questions.map((question,i) => {
      if (i === showIndex){
        return newQuestion
      }
      return question
    })
    )
  }

  const checkQuestion = (question) => {
    if (!question.score) {
      return false
    }
    if (!question.content) {
      return false
    }

    if (question.examples.some(answer => !answer.content)){
      return false
    }
    return true
  }
  const onEnd = () =>{
    const $wrong = $('button.wrong')
    if ($wrong){
      alert(`${$wrong.innerText } 문항을 확인해주세요`)
    }
    console.log(questions)
  }

  const deleteQuestion = (e,index) => {
    e.preventDefault();
    if (window.confirm(`${index+1}번 문제를 지우겠습니까`)){
      setQuestions(
        questions.filter((_,questionIndex)=> questionIndex !==index)
      )
    }
    setShowIndex(index === questions.length - 1 ? index -1 : index)
  }

  return (
    <div>
      <span>{'총점 = '+ totalScore(questions) + '점'}</span>
      <div className="questions-box">
        {questions.map((question,index) => (
          <button className={checkQuestion(question) ? '' : 'red'}
            key={index}
            onClick={()=>setShowIndex(index)}>
            <span>{index+1 + '번'}</span>
          </button>
        ))}
      </div>
      <div className="question-list">
        {questions.map((question,index) => (
          <div key={index}  className={"question-wrap" + (index===showIndex ? ' show' : '')}>
            <div className="question-title">{index+1 + '번 문제'}
              <button onClick={(e)=>deleteQuestion(e,index)}>X</button>
            </div>
            <TeacherQuestion question={question} setQuestion={setQuestion}/>
          </div>
        ))}
      </div>
      <div className="text-center">
        <div>{showIndex+1 + ' / ' + questions.length}</div>
        <button onClick={addQuestions}>문제추가</button>
      </div>
      <div onClick={onEnd}>완료</div>
    </div>
  )
}

export default TeacherQuestionList
