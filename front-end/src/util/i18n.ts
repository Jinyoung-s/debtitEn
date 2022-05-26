import Vue from 'vue'
import VueI18n from 'vue-i18n'
import { koKR } from '@/locale/messages/kr'
import { enUS } from '@/locale/messages/en'

Vue.use(VueI18n)

// Create VueI18n instance with options
export const i18n = new VueI18n({
  locale: 'ko_KR',
  messages: {
    en_US: enUS,
    ko_KR: koKR
  }
})
