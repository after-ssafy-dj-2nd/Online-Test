import React, { useEffect, useState } from 'react';
// import { fetchTryout } from '../../api/modules/tryout';
import { withRouter } from 'react-router';
import tempTryout from '../../assets/json/tempExam.json';
import './Tryout.css';
import TryoutQuestion from './TryoutQuestion';
import TryoutTimer from './TryoutTimer';
import TryoutRemainQuestion from './TryoutRemainQuestion';
import TryoutQuestionButtons from './TryoutQuestionButtons';

// 임시 데이터로 작업(추후 삭제 예정)
const tempData = tempTryout.question;

const TryoutHeader = ({ location, history }) => {
  const [tryout, setTryout] = useState(null);
  const [nowQuestionNumber, setNowQuestionNumber] = useState(0);
  const [myAnswer, setMyAnswer] = useState(null); // 내가 작성한 정답
  const [error, setError] = useState(true);
  const query = new URLSearchParams(location.search);
  const token = query.get('token');

  // const fetchSpecificTryout = async () => {
  //   return await fetchTryout(9);
  // }

  useEffect(() => {
    if (!token) {
      history.push('/');
      return
    }

    // 임시 데이터로 작업(추후 삭제 예정)
    setError(false);
    setTryout(tempData);
    const initMyAnswer = Array(tempData.length).fill(null);
    setMyAnswer(initMyAnswer);

    // 실제 시험 정보 가져오는 로직
    // (나중에 주석 풀어서 할 것, 지금은 assets/json 안에 있는 임시 데이터로 작업)
    // const fetchTryoutData = async () => {
    //   try {
    //     const { data } = await fetchSpecificTryout();
    //     setTryout(data.question);
    //     setMyAnswer(Array(data.question.length).fill(null));
    //     setError(false);
    //   } catch (error) {
    //     setTryout(error);
    //   }
    // }

    // fetchTryoutData();
  }, []);

  const onChangeNowQuestionNumber = e => {
    const selectedNumber = e.target.innerText;
    setNowQuestionNumber(Number(selectedNumber - 1));
  }

  if (error) {
    return <p>예기치 못한 에러가 발생했습니다. 다시 접속해주세요.</p>
  }

  return (
    <div className="tryout">
      <section className="tryout-question">
        <TryoutQuestion nowQuestion={tryout[nowQuestionNumber]} nowQuestionNumber={nowQuestionNumber} />
      </section>
      <section className="tryout-sidebar">
        <TryoutTimer />
        <TryoutRemainQuestion myAnswer={myAnswer} />
        <TryoutQuestionButtons myAnswer={myAnswer} onChangeNowQuestionNumber={onChangeNowQuestionNumber} />
        <button className="btn">최종 답안 제출</button>
      </section>
    </div>
  );
};

export default withRouter(TryoutHeader);