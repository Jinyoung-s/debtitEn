<template>
  <div id="app" class="wrapper">
    <debate-header
      :change-user-info-flag="changeUserInfoFlag"
      @UserSet="userSet"></debate-header>
    <router-view
      :user-info="userInfo"
      @userChange="changeUserInfo"/>
    <debate-footer></debate-footer>
    <user-modal></user-modal>
  </div>
</template>

<script>
import header from '@/views/frame/DebateHeader.vue'
import UserModal from '@/views/user/UserModal.vue'
import jQuery from 'jquery'
import DebateFooter from '@/views/frame/DebateFooter'

window.jQuery = jQuery

export default {
  components: {
    DebateFooter,
    UserModal,
    'debate-header': header
  },
  beforeCreate () {
    document.body.className = 'hold-transition sidebar-collapse '
  },
  data: function () {
    return {
      userInfo: {},
      changeUserInfoFlag: false
    }
  },
  methods: {
    userSet: function (userInfo) {
      this.userInfo = userInfo
    },
    changeUserInfo: function () {
      this.changeUserInfoFlag = true
    }
  }

}

</script>
