import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { i18n } from '@/util/i18n'
import VSanitize from 'v-sanitize'

const defaultOptions = {
  allowedTags: ['a', 'img', 'p', 'br', 'h*'],
  allowedAttributes: {
    a: ['href'],
    img: ['src', 'width', 'height']
  }
}

Vue.config.productionTip = false
Vue.use(VSanitize, defaultOptions)

Vue.filter('datetime', (value: Date) => {
  if (!value) {
    return ''
  }
  const jsDate = new Date(value)
  const year = jsDate.getFullYear()
  const month = jsDate.getMonth() + 1
  const day = jsDate.getDate()
  const hour = jsDate.getHours()
  const min = jsDate.getMinutes()

  return year + '-' + month + '-' + day + ' ' + hour + ':' + min
})

Vue.filter('substring', (value: string, max: number) => {
  if (!value) {
    return ''
  }
  const maxLength = max
  if (value.length < maxLength) {
    return value
  }
  return value.substring(0, maxLength) + '...'
})

Vue.filter('dateMin', (value: Date) => {
  if (!value) {
    return ''
  }
  const date = new Date(value)

  const now = new Date()
  const noOfMS = now.getTime() - date.getTime()
  const numberOfSeconds = noOfMS / 1000

  if (numberOfSeconds > 60 * 60 * 24) {
    return (Math.floor(numberOfSeconds / (60 * 60 * 24))) + 'd'
  }

  if (numberOfSeconds > 60 * 60) {
    return Math.floor((numberOfSeconds / (60 * 60))) + 'h'
  }

  if (numberOfSeconds > 60) {
    return Math.ceil((numberOfSeconds / (60 * 60))) + 'm'
  }
  return '1 M'
})

export const bus = new Vue()

new Vue({
  router,
  store,
  render: h => h(App),
  i18n
}).$mount('#app')
