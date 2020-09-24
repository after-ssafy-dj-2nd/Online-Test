let emailReg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
let alphabetReg = /[a-zA-Z]/g;
let alphabetLowerCaseReg = /[a-z]/g;
let numberReg = /[0-9]/g;
let koreanReg = /[가-힣]/g;
let koreanConsonantVowelReg = /[ㄱ-ㅎ|ㅏ-ㅣ]/g;
let specialMarkReg = /[~!@#$%^&*()_+|<>?:{}]/g;

const validation = {
  idValidation: id => {
    return id.length >= 6 && id.match(alphabetLowerCaseReg).length >= 4 && id.match(numberReg).length >= 2;
  },
  passwordValidation: password => {
    return password.length >= 10 && password.match(alphabetLowerCaseReg).length >= 6 && password.match(numberReg).length >= 4;
  },
  usernameValidation: username => {
    return username.length >= 2 && username.length <= 5 && koreanReg.test(username) && !numberReg.test(username) && !alphabetReg.test(username) && !specialMarkReg.test(username) && !koreanConsonantVowelReg.test(username);
  },
  emailValidation: email => {
    return emailReg.test(String(email).toLowerCase());
  }
}

export default validation;