import React from 'react'

const TeacherQuestion = (props)=>{
  const {question ,setQuestion} = props
  const {score, content, description, answers} = question

  const reducer = (key,value) => {
    return {
      ...question,
      [key] : value
    }
  }

  const setAnswers = (answers) => {
    setQuestion(reducer('answers', answers))
  }

  const setContent = (e) => {
    setQuestion(reducer('content', e.target.value))
  }

  const setDescription = (e) => {
    setQuestion(reducer('description', e.target.value))
  }

  const setScore = (e)=> {
    setQuestion(reducer('score',e.target.value))
  }

  const deleteAnswer = (e,index) => {
    e.preventDefault();
    if (window.confirm(`${index+1} 번 보기를 지우겠습니까`)){
      setAnswers(
        answers.filter((_,answerIndex)=> answerIndex !==index)
      )
    }
  }
 
  const onAnswerChange = (index,e)=> {
    setAnswers(
      answers.map((answer, answersIndex) => {
        return answersIndex === index ? {...answer , content : e.target.value} : answer
      })
    )
  }

  const addAnswer = ()=>{
    setAnswers(answers.concat({content:'',correct:false}))
  }

  const onCorrectChange = (index,e) => {
    setAnswers(
      answers.map((answer, answersIndex)=> {
        return answersIndex === index ? {...answer , correct : e.target.checked} : answer
    })
    )
  }
  
  return (
    <form className="question-form">
      <article className="question-score">
        <label htmlFor="score"> 점수 </label>
        <input id="score" type="number" value={score} onChange={e=>setScore(e)}/> 점
      </article>
      <article className="question-content">
        <label htmlFor="content"> 문제 </label>
        <textarea id="content" value={content} onChange={e=>setContent(e)}/>
      </article>
      <article className="question-description">
        <textarea id="description" value={description} onChange={e=>setDescription(e)}/>
      </article>
      <article className="question-answers">
        {answers.map((answer,index)=> (
          <div className='question-answer' key={index}>
            <span>{index+1} . </span>
            <input type="checkbox" checked={answer.correct} onChange={e => onCorrectChange(index,e)}/>
            <input value={answer.content} onChange={e=>onAnswerChange(index, e)}/>
            <button onClick={(e)=>deleteAnswer(e,index)}>X</button>
          </div>
        ))}
        <div onClick={addAnswer}>항목 추가 +</div>
      </article>
    </form>
  )
}

export default TeacherQuestion
