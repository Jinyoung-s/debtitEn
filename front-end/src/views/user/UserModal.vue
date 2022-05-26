<template>
  <div>
    <div class="modal fade" id="modal-default" style="display: none;" aria-hidden="true">
      <div class="modal-dialog modal-sm">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">{{ $i18n.t('label.userInfo') }}</h4>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="text-align:center">
              <p style="float:left">
                <img id="modalInfoImg" :src="memberImgPath" alt="User Avatar" class="img-size-64 img-circle btn-block" >
              </p>
              <p style="float:right">{{ memberInfo.userPoin }} p</p>
            </div>
            <p id="modalMemberNick" style="">{{ memberInfo.userNick }}</p>
            <textarea class="form-control" style="height: 70px; margin-bottom:10px" v-model="memoCont" ></textarea>
            <button class="btn btn-block btn-outline-primary" @click="sendMemo">{{ $i18n.t('label.sendMail') }}</button>
          </div>
          <div class="modal-footer justify-content-between">
            <button type="button" id="modalCloseBtn" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>

    <div class="modal fade" id="modal-memo" style="display: none;" aria-hidden="true">
      <div class="modal-dialog modal-sm">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">{{ $i18n.t('label.memo') }}</h4>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="closeReply">
              <span aria-hidden="true">×</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="text-align:center">
              <p style="float:left">
                <img id="modalMemoImg" :src="memberImgPath" alt="User Avatar" class="img-size-64 img-circle btn-block" >
              </p>
              <p id="modalMemoNick" style="float:right">{{ memberInfo.userNick }}</p>
            </div>
            <div>point:{{ memberInfo.userPoin }}</div>
            <div style="clear: both"></div>
            <div v-html="memberInfo.memoCont"></div>
            <textarea
              v-show="showReplyArea"
              class="form-control" style="height: 70px; margin-bottom:10px" v-model="memoCont" ></textarea>
            <button
              v-show="!showReplyArea"
              class="btn btn-block btn-outline-primary" @click="showReply">{{ $i18n.t('label.reply') }}</button>
            <button
              v-show="showReplyArea"
              class="btn btn-block btn-outline-primary" @click="sendMemo">{{ $i18n.t('label.sendMail') }}</button>
          </div>
          <div class="modal-footer justify-content-between">
            <button type="button" id="modalMemoCloseBtn" class="btn btn-default" data-dismiss="modal" @click="closeReply">Close</button>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'
import { bus } from '@/main'
import post from '@/util/util'

@Component
export default class UserModal extends Vue {
  memoCont = ''
  sendUser = ''
  memberInfo: any = {}
  showReplyArea = false

  get memberImgPath () {
    return this.memberInfo.userImgPath
  }

  beforeMount () {
    bus.$on('chagneMember', (data: any) => {
      this.memberInfo = data
    })
  }

  sendMemo () {
    if (!this.memoCont) {
      alert(this.$i18n.t('alert.inputContent'))
      return
    }

    const memo: any = {}
    memo.sendUser = this.$store.state.userInfo.userNmbr
    memo.recvUser = this.memberInfo.userNmbr
    memo.memoCont = this.memoCont

    post('/sendMemo', memo).then(() => {
      document.getElementById('modalCloseBtn')!.click()
      document.getElementById('modalMemoCloseBtn')!.click()
      alert(this.$i18n.t('alert.sendMemo'))
      this.memoCont = ''
      this.showReplyArea = false
    })
  }

  showReply () {
    this.showReplyArea = !this.showReplyArea
  }

  closeReply () {
    this.showReplyArea = false
  }
}
</script>
