import React from 'react';
import IntroContent from '../components/Intro/IntroContent'
import Welcome from '../components/Intro/Welcome'
const IntroPage = () => {
  return (
    <div className="intro-wrapper">
      <Welcome></Welcome>
      <IntroContent></IntroContent>
    </div>
  );
};

export default IntroPage;