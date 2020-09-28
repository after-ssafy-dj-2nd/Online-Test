import React from 'react'

const TeacherQuestion = (props)=>{
  const {question ,setQuestion} = props
  const {content, description, examples} = question

  const reducer = (key,value) => {
    return {
      ...question,
      [key] : value
    }
  }

  const setExamples = (examples) => {
    setQuestion(reducer('examples', examples))
  }

  const setContent = (e) => {
    setQuestion(reducer('content', e.target.value))
  }

  const setDescription = (e) => {
    setQuestion(reducer('description', e.target.value))
  }

  const deleteExample = (e,index) => {
    e.preventDefault();
    if (window.confirm(`${index+1} 번 보기를 지우겠습니까`)){
      setExamples(
        examples.filter((_,exampleIndex)=> exampleIndex !==index)
      )
    }
  }
 
  const onExampleChange = (index,e)=> {
    setExamples(
      examples.map((example, exampleIndex) => {
        return exampleIndex === index ? {...example , content : e.target.value} : example
      })
    )
  }

  const addExample = ()=>{
    setExamples(examples.concat({content:'',correct:false}))
  }

  const onCorrectChange = (index,e) => {
    setExamples(
      examples.map((example, exampleIndex)=> {
        return exampleIndex === index ? {...example , correct : e.target.checked} : example
    })
    )
  }
  
  return (
    <form className="question-form">
      <article className="question-content-input">
        <label htmlFor="content"> 문제 </label>
        <textarea className="resize-none" id="content" value={content} onChange={e=>setContent(e)}/>
      </article>
      <article className="question-description-input">
        <textarea className="resize-none" id="description" value={description} onChange={e=>setDescription(e)}/>
      </article>
      <article className="question-examples-input">
        {examples.map((example,index)=> (
          <div className='question-example-input' key={index}>
            <span>{index+1} . </span>
            <input type="checkbox" checked={example.correct} onChange={e => onCorrectChange(index,e)}/>
            <input value={example.content} onChange={e=>onExampleChange(index, e)}/>
            <button onClick={(e)=>deleteExample(e,index)}>X</button>
          </div>
        ))}
        <div onClick={addExample}>항목 추가 +</div>
      </article>
    </form>
  )
}

export default TeacherQuestion
