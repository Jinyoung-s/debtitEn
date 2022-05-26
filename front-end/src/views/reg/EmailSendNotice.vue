<template>
  <div class="content-wrapper">
    <div class="content">
      <div class="container public">
        <div class="row">
          <div class="col-md-3"></div>
          <div class="col-md-6 justify-content-center" style="margin-top: 100px">
            <div class="info-box">
              <span class="info-box-icon bg-info"><i class="far fa-envelope"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">{{ $i18n.t("label.emailSendAlert1") }}</span>
                <span class="info-box-text">{{ $i18n.t("label.emailSendAlert2") }}</span>
                <span class="info-box-number">{{ $i18n.t("label.emailSendAlert3") }}</span>
                <span class="info-box-number">{{ $i18n.t("label.emailSendAlert4") }}</span>
              </div>
              <!-- /.info-box-content -->
            </div>
          </div>
        </div>
      </div>
    </div>
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
        userPass: ''
      }
    }
  }
}
</script>
