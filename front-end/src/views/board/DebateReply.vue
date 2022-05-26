<template>
  <div class="row mb-4" style="background: #f8f9fa !important; padding:0px !important;">
    <!-- Box Comment -->
    <div class="card card-widget">
      Comments
      <!-- /.card-body -->
      <div class="card-footer card-comments" style="display: block;">
        <template v-for="(item, key) in commentList">
          <div class="card-comment" :key="key">
            <!-- User image -->
            <div class="comment-text">
                  <span class="username">
                    {{ item.userNick }}
                    <span class="text-muted float-right">{{ item.cretDate | datetime }}</span>
                  </span><!-- /.username -->
             {{ item.cmntMesg }}
            </div>
            <!-- /.comment-text -->
          </div>
          <!-- /.card-comment -->
        </template>

        <!-- /.card-comment -->
      </div>
      <!-- /.card-footer -->
      <div class="card-footer" style="display: block;">
        <form action="#" method="post">
          <div class="img-push">
            <textarea class="form-control" style="height: 50px" v-model="cmntMesg" :disabled="disableInput" ></textarea>
            <button type="button" class="btn btn-block btn-outline-primary" style="height: 50px; margin-top: 15px" @click="sendComment">Send</button>
          </div>
        </form>
      </div>
      <!-- /.card-footer -->
    </div>
    <!-- /.card -->
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'
import post from '@/util/util'
import store from '@/store'

@Component
export default class DebateReply extends Vue {
  appBoard: any = {}
  replyList: any = []
  cmntMesg = ''

  @Prop() disableInput:any
  @Prop() board:any
  @Prop() commentList:any

  sendComment () {
    if (this.disableInput) {
      const loginMsg = this.$i18n.t('confirm.goLogin').toString()
      if (confirm(loginMsg)) {
        this.$router.push({ name: 'LoginPage' })
      }
      alert(44)
      return
    }

    if (!this.cmntMesg) {
      alert(this.$i18n.t('alert.inputContent'))
      return
    }

    this.$emit('OnClick', this.cmntMesg)
  }
}
</script>
