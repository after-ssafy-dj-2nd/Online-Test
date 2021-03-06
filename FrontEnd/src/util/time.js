export const timeDiff = (sec) => {
  const s = parseInt(sec%60)
  const m = parseInt((sec/60)%60)
  const h = parseInt((sec/60/60)%24)
  const d = parseInt((sec/60/60/24))
  let diff = ''
  if (d) {diff += `${d}`.padStart(2, '0') + '일 '}
  diff += `${h}`.padStart(2, '0') + '시간 '
  diff += `${m}`.padStart(2, '0') + '분'
  if (!d) {diff += ' ' + `${s}`.padStart(2, '0') + '초'}
  return diff
}
export const timeToString = (time) => {
  return time.replace('T',' ')
/*  const [Y,M,D,h,m,s] = time.split(/[^0-9]/g)
  let date = ''
  if (Y) {date += `${Y.slice(2,4)}년`}
  if (M) {date += `${M}월 `}
  if (D) {date += `${D}일 `}
  if (h) {date += `${h}시간 `}
  if (m) {date += `${m}분 `} 
  if (s) {date += `${s}초`}
  return date */
}

export const diffSecond = (startTime,endTime) => {
  return (Date.parse(endTime) - Date.parse(startTime))/1000
}