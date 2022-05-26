<template>
  <div class="content-wrapper">
    <div class="content-header">
    </div>
    <section class="content">
      <div class="container">
        <div class="row">
          <div class="col-md-3"></div>
          <div class="card-body col-md-6" style="background: #fff; padding:20px">
            <p class="login-box-msg">{{ $i18n.t("label.register") }}</p>
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
              <div class="input-group mb-3">
                <input type="password" class="form-control" :placeholder="$i18n.t('label.confirmPass')" v-model="userPassConfirm">
                <div class="input-group-append">
                  <div class="input-group-text">
                    <span class="fas fa-lock"></span>
                  </div>
                </div>
              </div>
              <div class="input-group mb-3">
                <input type="text" class="form-control" :placeholder="$i18n.t('label.nickName')" v-model="form.userNick"  maxlength="10">
                <div class="input-group-append">
                  <div class="input-group-text">
                    <span class="fas fa-user"></span>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-8">
                  <div class="icheck-primary">
                    <input type="checkbox" id="agreeTerms" name="terms" value="agree" v-model="agreeTerm">
                    <label for="agreeTerms" style="font-size: 12px; margin-left: 5px">
                      {{ $i18n.t("label.agree") }} <a href="" @click="goUseTerm">{{ $i18n.t("label.usingTerm") }}</a>, <a href="" @click="goPrivate">{{ $i18n.t("label.privateInfo") }}</a>
                    </label>
                  </div>
                </div>
                <!-- /.col -->
                <div class="col-4">
                  <button type="submit" class="btn btn-primary btn-block" >{{ $i18n.t("label.register1") }}</button>
                </div>
                <!-- /.col -->
              </div>
            </form>
            <div class="social-auth-links text-center">
              <a href="#" class="btn btn-block btn-primary" @click="logInWithFacebook">
                <i class="fab fa-facebook mr-2"></i>
                {{ $i18n.t("label.signupFacebook") }}
              </a>
              <a href="#" class="btn btn-block btn-danger" @click="signupWithGoogle">
                <i class="fab fa-google-plus mr-2"></i>
                {{ $i18n.t("label.singupGoogle") }}
              </a>
            </div>
            <a href="login.html" class="text-center">I already have a membership</a>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import post from '@/util/util.ts'

export default {
  name: 'RegisterPage',
  data: function () {
    return {
      form: {
        userId: '',
        userPass: '',
        userNick: ''
      },
      userPassConfirm: '',
      agreeTerm: ''
    }
  },
  created: function () {
    this.loadFacebookSDK(document, 'script', 'facebook-jssdk')
    this.initFacebook()
  },
  methods: {
    submitForm: function () {
      if (this.userPassConfirm !== this.form.userPass) {
        alert(this.$i18n.t('alert.confirmPass'))
        return
      }

      if (!this.form.userNick) {
        alert(this.$i18n.t('alert.inputNick'))
        return
      }

      console.log(this.agreeTerm)
      if (!this.agreeTerm) {
        alert(this.$i18n.t('alert.agreeTerm'))
        return
      }

      post('/register', this.form).then(() => this.$router.push({ name: 'EmailSendNotice' }))
    },
    signupWithGoogle () {
      post('/getGoogleAuthUrl', {}).then((res) => { location.href = res })
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
        console.log('return')
        return
      }
      console.log('face')
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
    },
    goPrivate () {
      this.$router.push('/privateInfo')
    },
    goUseTerm () {
      this.$router.push('/debateUseTerm')
    }
  }
}
</script>
