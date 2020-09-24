// 추후 TOKEN 값은 redux에 저장된 토큰 값 가져와서 매칭시킬 예정
export function setInterceptors(instance) {
  instance.interceptors.request.use(
    config => {
      let TOKEN = process.env.REACT_APP_TEMP_TOKEN
      if (TOKEN) {
        config.headers['access-token'] = TOKEN
      }
      return config
    },
    error => Promise.reject(error.response)
  )

  instance.interceptors.response.use(
    config => config,
    error => Promise.reject(error.response)
  )
  return instance
}
