import instance from '../index';

// question fetch API
export function fetchQuestions() {
  return instance.get('questions');
}

export function createQuestions(data) {
  return instance.post('questions', {question:data})
}

export function deleteQuestion(id) {
  return instance.delete('question', {params : {id: id}})
}