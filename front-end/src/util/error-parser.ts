import { i18n } from '@/util/i18n'

export default function parse (error: any): Error {
  if (error.response) {
    const status = error.response.status
    const data = error.response.data
    if (status === 400) {
      if (i18n.t(data.message)) {
        alert(i18n.t(data.message))
        return new Error(data.message)
      }
      if (data && data.message) {
        alert(data.message)
        return new Error(data.message)
      } else {
        return new Error(i18n.t('error.request.bad').toString())
      }
    } else if (status === 401) {
      return new Error(i18n.t('error.request.notAuthorized').toString())
    } else if (status === 403) {
      return new Error(i18n.t('error.request.forbidden').toString())
    } else if (status === 404) {
      return new Error(i18n.t('error.request.notFound').toString())
    } else if (status === 500) {
      if (data && data.message) {
        return new Error(data.message)
      } else {
        return new Error(i18n.t('error.request.unknownServerError').toString())
      }
    } else {
      return new Error(i18n.t('error.request.failed').toString())
    }
  } else if (error.request) {
    // Request was made and no response
    return new Error(i18n.t('error.request.noResponse').toString())
  } else {
    return new Error(error)
  }
}
