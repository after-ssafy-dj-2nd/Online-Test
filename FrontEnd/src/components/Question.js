import React, {useState} from 'react'
const unicode = {
  // 1~9
  '0' : '\u2460',
  '1' : '\u2461',
  '2' : '\u2462',
  '3' : '\u2463',
  '4' : '\u2464',
  '5' : '\u2465',
  '6' : '\u2466',
  '7' : '\u2467',
  '8' : '\u2468',
  // 1 ~ 9 correct
  '10' : '\u2776',
  '11' : '\u2777',
  '12' : '\u2778',
  '13' : '\u2779',
  '14' : '\u277A',
  '15' : '\u277B',
  '16' : '\u277C',
  '17' : '\u277D',
  '18' : '\u277E',
}

const Question = (props) => {
  const {question} = props
  const [shorthen, setShorthen] = useState(true)
  const [showDescription,setShowDescription] = useState(false)
  const showQuestion = () => {
    setShorthen(!shorthen)
  }
  return (
    <div className="question-wrap">
      <div className="question-content">
        <span>{question.content}</span>
        <i onClick={showQuestion} className={"fas " + (shorthen ? "fa-chevron-down" : "fa-chevron-up")}></i>
      </div>
      <div className={shorthen ? 'hide' : ''}>
        <div className="question-commentary">
          {question.commentary}
        </div>
        <div className="question-examples">
          {question.examples.map((example,index)=>
            <div key={example.id} className="question-example">
              {unicode[example.correct ? index+10 : index] } {example.content}
            </div>
          )}
        </div>
        <div className="toggle-description" onClick={()=> setShowDescription(!showDescription)}>
            {showDescription ? '해설 닫기' : '해설 펼쳐보기'}
        </div>
        <div className={(showDescription ? '' : 'hide ') + "question-description"}>
          {question.description}
        </div>
      </div>
    </div>
  )
}

export default Question
