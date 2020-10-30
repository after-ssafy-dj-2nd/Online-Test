import instance from '../index';

// fetcth specific exam API
export function fetchTryout(examId) {
  return instance.get(`starttest?exam_id=${examId}`);
}

export function fetchExamInfo(examId) {
  return instance.get(`readytest?exam_id=${examId}`)
}