import instance from '../index';

// exam fetch API
export function fetchExams() {
  return instance.get('exams')
}
export function fetchExam(id) {
  return instance.get('exam', {params : {id:id}})
}

export function createExam(data) {
  return instance.post('exam', data)
}
export function deleteExam(id){
  return instance.delete('exam', {params : {id: id}})
}