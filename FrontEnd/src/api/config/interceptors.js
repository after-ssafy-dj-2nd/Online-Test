import {storage} from '../../util/storage'
export function setInterceptors(instance) {
  instance.interceptors.request.use(
    config => {
      const userInfo = storage(sessionStorage).getItem('userInfo');
      if (userInfo) {
        config.headers['access-token'] = userInfo.token
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
