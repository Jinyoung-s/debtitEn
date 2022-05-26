<template>
  <div class="content-wrapper">
    <div class="content-header">
    </div>
    <section class="content">
      <div class="container">
        <div class="row">
          <div class="col-md-3"></div>
          <div class="card-body col-md-6" style="background: #fff; padding:20px">
            <p class="login-box-msg">{{ $i18n.t("label.login") }}</p>
            <form @submit.prevent="submitForm">
              <div class="input-group mb-3">
                <input type="email" class="form-control" :placeholder="$i18n.t('label.email')" v-model="form.userId">
                <div class="input-group-append">
                  <div class="input-group-text">
                    <span class="fas fa-envelope"></span>
                  </div>
                </div>
              </div>
              <div class="input-group mb-3">
                <input type="password" class="form-control" :placeholder="$i18n.t('label.PassInput')" v-model="form.userPass">
                <div class="input-group-append">
                  <div class="input-group-text">
                    <span class="fas fa-lock"></span>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-8">
                  <div class="icheck-primary" style="display: none">
                    <input type="checkbox" id="remember">
                    <label for="remember">
                      Remember Me
                    </label>
                  </div>
                </div>
                <!-- /.col -->
                <div class="col-4">
                  <button type="submit" class="btn btn-primary btn-block">{{ $i18n.t("label.login") }}</button>
                </div>
                <!-- /.col -->
              </div>
            </form>
            <div class="social-auth-links text-center mb-3">
              <p>- OR -</p>
              <a class="btn btn-block btn-primary" @click="logInWithFacebook">
                <i class="fab fa-facebook mr-2"></i> {{ $i18n.t("label.signFacebook") }}
              </a>
              <a class="btn btn-block btn-danger" @click="signupWithGoogle">
                <i class="fab fa-google-plus mr-2"></i> {{ $i18n.t("label.singGoogle") }}
              </a>
            </div>
            <!-- /.social-auth-links -->

            <p class="mb-1" style="display:none;">
              <a href="forgot-password.html">I forgot my password</a>
            </p>
            <p class="mb-0">
              <a href="/register" class="text-center">{{ $i18n.t("label.join") }}</a>
            </p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import post from '@/util/util.ts'

export default {
  name: 'LoginPage',
  data: function () {
    return {
      form: {
        userId: '',
        userPass: ''
      }
    }
  },
  props: ['test'],
  created: function () {
    this.loadFacebookSDK(document, 'script', 'facebook-jssdk')
    this.initFacebook()
  },
  methods: {
    submitForm: function () {
      post('/authentication', this.form).then(() => {
        this.$emit('userChange')
        this.$router.push({ name: 'HomePage' })
      })
    },
    signupWithGoogle () {
      post('/getGoogleAuthUrl', {}).then((res) => { location.href = res })
    },
    fnFbCustomLogin () {
      console.log(3)
    },
    async logInWithFacebook () {
      const vm = this
      window.FB.login(function (response) {
        if (response.authResponse) {
          console.log(response)
          vm.getFBUserInfo(response.authResponse.userID)
        } else {
          alert('User cancelled login or did not fully authorize.')
        }
      }, { scope: 'public_profile, email' })
      return false
    },
    async initFacebook () {
      window.fbAsyncInit = function () {
        window.FB.init({
          appId: '1342433849567832',
          cookie: true,
          version: 'v13.0'
        })
      }
    },
    async loadFacebookSDK (d, s, id) {
      const fjs = d.getElementsByTagName(s)[0]
      if (d.getElementById(id)) {
        return
      }
      const js = d.createElement(s)
      js.id = id
      js.src = 'https://connect.facebook.net/en_US/sdk.js'
      fjs.parentNode.insertBefore(js, fjs)
    },
    getFBUserInfo (userId) {
      const vm = this
      window.FB.api('/' + userId + '/?fields=id,name,email', 'GET', {}, function (res) {
        const userInfo = {}
        userInfo.userId = res.email
        userInfo.userPass = res.id
        vm.$store.commit('changeUserInfo', userInfo)
        vm.$router.push({ name: 'SignWithFacebook' })
      })
    }
  }
}
</script>
