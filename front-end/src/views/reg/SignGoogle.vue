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
              <div class="card-body">

                <div class="row mb-4 justify-content-center">
                  <div class="form">
                    <div class="logo-wrapper">
                    </div>
                    <form @submit.prevent="submitForm">
                      <div class="form-group">
                        <label>{{ $i18n.t("alert.inputNick") }}</label>
                        <input type="text" class="form-control" id="userNick" v-model="form.userNick" placeholder="Nickname" maxlength="10" >
                      </div>
                      <button type="submit" class="btn btn-primary btn-block">{{ $i18n.t("label.register") }}</button>
                    </form>
                  </div>
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
      user: {}
    }
  },
  created: function () {
    const vm = this
    const userParam = {}
    userParam.userSocialCode = this.$route.query.code
    post('/socialUserInfo', userParam).then((data) => {
      if (data && data.userId) {
        vm.user.userId = data.userId
        vm.user.userPass = data.userPass
        post('/exsitSocialUserInfo', vm.user).then((data) => {
          if (data && data.userId) {
            post('/socialLogin', data).then((dataStr) => {
              vm.$emit('userChange')
              vm.$router.push({ name: 'HomePage' })
            })
          }
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
      vm.user.userNick = this.form.userNick
      post('/socialRegister', vm.user).then((data) => {
        vm.$emit('userChange')
        vm.$router.push({ name: 'HomePage' })
      })
    }
  }
}
</script>
