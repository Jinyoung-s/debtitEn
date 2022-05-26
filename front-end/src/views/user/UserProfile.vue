<template>
  <div>
    <img
      v-if="appUserInfo"
      class="direct-chat-img" :src="userImgPath" alt="message user image" @click="clickImage" data-target="#modal-default" style="cursor: pointer">
    <img
      v-if="!appUserInfo"
      class="direct-chat-img" :src="userImgPath" alt="message user image" style="cursor:pointer" @click="alertLogin">
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'
import { bus } from '@/main'
import post from '@/util/util'

@Component
export default class UserProfile extends Vue {
  @Prop() user: any
  userInfo = this.$store.getters.getUserInfo

  appUserInfo = {}

  created () {
    this.appUserInfo = this.userInfo
  }

  clickImage (evt: any) {
    this.$store.commit('changeModalMemberInfo', this.user)
    bus.$emit('chagneMember', this.user)
    evt.target.setAttribute('data-toggle', 'modal')
    evt.target.click()
  }

  get userImgPath () {
    if (this.user) {
      return this.user.userImgPath
    }
    return ''
  }

  alertLogin () {
    alert(this.$i18n.t('alert.loginAlert'))
  }
}
</script>
