<template>
  <div class="content-wrapper">
    <div class="container public">
      <div class="content-header">
      </div>
      <section class="content">
        <div class="row" style="margin-top: 40px">
          <div class="col-md-3"></div>
          <div class="col-md-6 justify-content-center">
            <div class="card">
              <div class="row mb-4 justify-content-center">
                <div class="form">
                  <div class="logo-wrapper">
                  </div>
                  <form @submit.prevent="submitForm">
                    <div class="form-group">
                      <label>{{ $i18n.t("alert.inputNick") }}</label>
                      <input type="text" class="form-control" id="userNick" v-model="form.userNick" :placeholder="NickNameLabel" maxlength="10">
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">{{ $i18n.t("label.register") }}</button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import post from '@/util/util.ts'
import axios from 'axios'

export default {
  name: 'LoginPage',
  data: function () {
    return {
      form: {
        userNick: ''
      },
      access_token: '',
      user: {},
      NickNameLabel: this.$i18n.t('label.nickName')
    }
  },
  created: function () {
    const param = {}
    const vm = this
    this.user = this.$store.getters.getUserInfo
    console.log(this.user)
    post('/exsitSocialUserInfo', vm.user).then((data) => {
      if (data && data.userId) {
        post('/socialLogin', data).then((dataS) => {
          vm.$emit('userChange')
          vm.$router.push({ name: 'HomePage' })
        })
      }
    })
  },
  props: ['param'],
  methods: {
    submitForm: function () {
      const vm = this
      if (!this.form.userNick) {
        alert(this.$i18n.t('alert.inputNick'))
        return
      }
      const userInfo = this.$store.getters.getUserInfo
      vm.user.userNick = this.form.userNick
      console.log(userInfo)
      post('/socialRegister', vm.user).then((data) => {
        vm.$emit('userChange')
        vm.$router.push({ name: 'HomePage' })
      })
    }
  }
}
</script>
