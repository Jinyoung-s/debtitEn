<template>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <div class="content-header">
    </div>
    <section class="content">
      <div class="container">
        <div class="row mb-4">
          <div class="card direct-chat direct-chat-primary">
            <div class="card-header">
              <h3 class="card-title">{{ board.boadTitl }}</h3>

              <div class="card-tools">
                <button type="button" class="btn btn-tool" title="Contacts" data-widget="chat-pane-toggle">
                  <i class="fas fa-comments"></i>
                </button>
                <span title="comment" class="badge badge-warning" style="margin-left: -10px"> {{ commentListLength }} </span>
                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                  <i class="fas fa-minus"></i>
                </button>
                <a href="#" class="link-black text-sm" @click="addLike('OWNER')"><i class="far fa-thumbs-up mr-1"></i>
                  <span title="3 New Messages" class="badge badge-primary" style="margin-left: -2px;margin-right: 10px"> {{ board.agreCunt == null ? 0 : board.agreCunt }} </span>
                </a>
                <a href="#" class="link-black text-sm" @click="addLike('PART')"><i class="far fa-thumbs-down mr-1"></i>
                  <span title="3 New Messages" class="badge badge-primary" style="margin-left: -2px"> {{ board.oppoCunt == null ? 0 : board.oppoCunt }} </span>
                </a>
                <button
                  v-if="showDeleteBtn"
                  type="button" class="btn btn-tool"   @click="deleteBoard">
                  <i class="fas fa-trash"></i>
                </button>
                <a
                  v-if="showDeleteBtn"
                  href="" class="link-black text-sm" @click="goModifyBoard">{{ $i18n.t("label.modify") }}</a>
              </div>
            </div>
            <div class="card-body" style="height: auto;">
              <pre v-html="board.boadCont"></pre>
            </div>
          </div>
          <div class="card card-widget .card-comments">
            Comments
            <div v-if="openCommentFlag" class="card-body card-comments">
              <Debate-comment
                :comment-list="commentList"
                :user-info="userInfo"></Debate-comment>
            </div>
          </div>
          <!-- /.card-footer -->
          <div v-if="openCommentFlag" class="card-footer" style="display: block;">
            <form action="#" method="post">
              <div class="img-push">
                <textarea class="form-control" style="height: 70px" v-model="cmntMesg" :disabled="false" ></textarea>
                <button type="button" class="btn btn-block btn-outline-primary" style="height: 50px; margin-top: 15px" @click="addReply">Send</button>
              </div>
            </form>
          </div>
          <!-- /.card-footer -->
        </div>
      </div>
    </section>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'
import post from '@/util/util'
import DebateComment from '@/views/board/DebateComment.vue'

@Component({
  components: {
    DebateComment
  }
})
export default class FreeboardView extends Vue {
  board: any = {}
  contentData: any = {
    currentBoard: {}
  }

  openCommentFlag = true
  disableInput = true
  cmntMesg = ''
  userInfo: any = {}
  commentList: any = []

  beforeMount () {
    this.board.boadNmbr = this.$route.params.id
    post('/getBoard', this.board).then((data: any) => {
      this.board = data.board
      this.userInfo = data.user
      this.commentList = data.commentList
    })
  }

  get showDeleteBtn () {
    if (this.userInfo && this.userInfo.userNmbr) {
      return this.board.firstUser.userNmbr === this.userInfo.userNmbr
    }
    return false
  }

  get commentListLength () {
    if (this.board && this.board.commentList) {
      return this.board.commentList.length
    }
  }

  addReply () {
    if (!this.userInfo || !this.userInfo.userId) {
      const loginMsg = this.$i18n.t('confirm.goLogin').toString()
      if (confirm(loginMsg)) {
        this.$router.push({ name: 'LoginPage' })
      }
      return
    }

    if (!this.cmntMesg) {
      alert(this.$i18n.t('alert.inputContent'))
      return
    }

    if (!this.userInfo || !this.userInfo.userId) {
      alert(this.$i18n.t('alert.loginAlert'))
      return
    }

    const comment: any = {}
    comment.boadNmbr = this.board.boadNmbr
    comment.cmntMesg = this.cmntMesg
    comment.userNick = this.userInfo.userNick

    post('/saveComment', comment).then(() => {
      location.reload()
    })
  }

  deleteBoard () {
    if (!confirm(this.$i18n.t('confirm.delete').toString())) {
      return
    }
    post('/deleteBoard', this.board).then(() => {
      this.$router.push('/')
    })
  }

  addLike (type: string) {
    if (!this.userInfo || !this.userInfo.userId) {
      alert(this.$i18n.t('alert.loginAlert'))
      return
    }

    const param = { boadNmbr: this.board.boadNmbr, type: type }
    post('/addLike', param).then((data: any) => {
      this.board = data.board
      this.board.oppoCunt = data.board.oppoCunt
      this.board.agreCunt = data.board.agreCunt
    })
  }

  goModifyBoard () {
    this.$router.push('/modifyBoard/free/' + this.board.boadNmbr)
  }
}
</script>
