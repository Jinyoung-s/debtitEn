import axios from 'axios'
import parse from '@/util/error-parser'
import { i18n } from '@/util/i18n'

axios.defaults.headers.common['Content-Type'] = 'application/x-www-form-urlencoded'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*'
axios.defaults.baseURL = '/api'
axios.defaults.headers.common.Accept = 'application/json'
axios.interceptors.response.use(
  response => response,
  (error) => {
    return Promise.reject(error)
  }
)

export default function post (url: string, param: any) {
  return new Promise((resolve, reject) => {
    axios.post(url, param).then(({ data }) => {
      resolve(data)
    }).catch((error: any) => {
      if (error.response.data && error.response.data.type === 'NOTI') {
        alert(i18n.t(error.response.data.message))
      } else {
        reject(parse(error))
      }
    })
  })
}
