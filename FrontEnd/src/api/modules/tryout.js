import instance from '../index';

// fetcth specific exam API
export function fetchTryout(examId) {
  return instance.get(`starttest?exam_id=${examId}`);
}