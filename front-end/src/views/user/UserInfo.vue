<template>
  <div class="content-wrapper">
    <section class="content-header">
      <div class="container">
        <div class="row mb-2">
          <div class="col-sm-6">
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Text Editors</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>
    <section class="content">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="card card-info">
              <div class="card-header">
                <h3 class="card-title">{{ $i18n.t("label.userInfo") }}</h3>
              </div>
              <!-- /.card-header -->
              <!-- form start -->
              <form class="form-horizontal">
                <div class="card-body">
                  <div class="form-group row">
                    <label for="inputEmail11" class="col-sm-2 col-form-label">Email</label>
                    <div class="col-sm-10">
                      <input type="email" id="inputEmail11" class="form-control" placeholder="Email" disabled :value="userInfo.userId">
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
                    <div
                      v-show="!showChagnePassArea"
                      class="col-sm-10">
                      <button type="button" @click="showChangePass" class="btn btn-info">{{ $i18n.t("label.changePass") }}</button>
                    </div>
                    <div
                      v-show="showChagnePassArea"
                      class="col-sm-10">
                      <div class="form-group row">
                        <input type="password" class="form-control" :placeholder="passLabel1" v-model="userInfo.userCurrentPass">
                      </div>
                      <div class="form-group row">
                        <input type="password" class="form-control" :placeholder="passLabel2" v-model="userInfo.changeUserPass" >
                      </div>
                      <div class="form-group row">
                        <input type="password" class="form-control" :placeholder="passLabel3" v-model="confirmPass">
                      </div>
                        <button type="button" @click="showChangePass" class="btn btn-info" style="margin-right: 20px">{{ $i18n.t("label.cancel") }}</button>
                        <button type="button" @click="changePass" class="btn btn-info">{{ $i18n.t("label.changePass") }}</button>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">{{ $i18n.t("label.profile") }}</label>
                    <div class="col-sm-10">
                      <img :src="previewImage" class="uploading-image" style="width: 120px; height: 120px" />
                      <div class="btn btn-default btn-file" style="margin-top: 5px">
                        <i class="fas fa-paperclip"></i> Attachment
                        <input type="file" name="attachment" accept="image/jpeg" @change=uploadImage>
                      </div>
                      <span style="display: inline-block; margin-left: 20px; font-size: 14px"> {{ $i18n.t("label.notice2M") }} </span>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">{{ $i18n.t("label.nickName") }}</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" placeholder="Nickname" v-model="userInfo.userNick" maxlength="10">
                    </div>
                  </div>
                </div>
                <!-- /.card-body -->
                <div class="card-footer">
                  <button type="button" @click="userModify" class="btn btn-info">{{ $i18n.t("label.modify") }}</button>
                  <button type="button" @click="goWithdrawal" class="btn btn-default float-right">{{ $i18n.t("label.withdrawal") }}</button>
                </div>
                <!-- /.card-footer -->
              </form>
            </div>
          </div>
          <!-- /.col-->
        </div>
      </div>
    </section>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'
import post from '@/util/util'

@Component
export default class UserInfo extends Vue {
  previewImage: any = null
  showChagnePassArea = false
  confirmPass = ''

  @Prop() userInfo: any

  created () {
    this.previewImage = this.$store.state.userInfo.userImgPath
  }

  get passLabel1 () {
    return this.$i18n.t('label.currentPass')
  }

  get passLabel2 () {
    return this.$i18n.t('label.newPass')
  }

  get passLabel3 () {
    return this.$i18n.t('label.newPassConfirm')
  }

  @Watch('userInfo')
  userInfoChanged () {
    console.log(this.userInfo)
    if (this.userInfo.userImag) {
      this.previewImage = this.$i18n.t('url.img') + this.userInfo.userImag
    }
  }

  showChangePass () {
    this.showChagnePassArea = !this.showChagnePassArea
  }

  changePass () {
    if (!this.userInfo.userCurrentPass) {
      alert(this.$i18n.t('alert.confirmPass'))
      return
    }

    if (this.confirmPass !== this.userInfo.changeUserPass) {
      alert(this.$i18n.t('alert.confirmPass'))
      return
    }

    if (this.userInfo.changeUserPass.length < 6) {
      alert(this.$i18n.t('alert.overPass'))
      return
    }

    const param: any = {}
    param.user = this.userInfo
    param.user.userPass = this.userInfo.userCurrentPass
    param.userNewPass = this.userInfo.changeUserPass
    post('/chagnePassword', param).then((data: any) => {
      alert(this.$i18n.t('alert.save'))
      location.reload()
    })
  }

  uploadImage (evt: any) {
    const image = evt.target.files[0]
    if (image.size > 5 * 1024 * 1024) {
      alert(this.$i18n.t('alert.alertImageSize'))
      return
    }
    const reader = new FileReader()
    reader.readAsDataURL(image)
    reader.onload = ev => {
      const image: any = new Image()
      image.src = ev.target!.result

      image.onload = (e: any) => {
        const $canvas = document.createElement('canvas')
        const ctx: any = $canvas.getContext('2d')

        $canvas.width = e.target.width
        $canvas.height = e.target.height

        ctx.drawImage(e.target, 0, 0)
        this.previewImage = $canvas.toDataURL('image/jpeg', 0.2)
        console.log($canvas.toDataURL('image/jpeg', 0.2))
      }
      // this.previewImage = ev.target!.result
      // console.log(this.previewImage)
    }
  }

  userModify () {
    this.userInfo.userImag = this.previewImage
    console.log(this.userInfo)
    post('/modifyUserInfo', this.userInfo).then((data: any) => {
      alert(this.$i18n.t('alert.save'))
    })
  }

  goWithdrawal () {
    const confirmMsg = this.$i18n.t('confirm.withdrawal')
    if (!confirm(confirmMsg.toString())) {
      return
    }

    post('/withdrawal', this.userInfo).then((data: any) => {
      post('/logout', null).then((data: any) => {
        this.$router.push({ name: 'HomePage' })
        location.reload()
      })
    })
  }
}
</script>
