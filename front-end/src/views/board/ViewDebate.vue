<template>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
    </div>
    <section class="content">
      <div class="container">
        <div class="row mb-4">
          <!-- DIRECT CHAT -->
          <div class="card direct-chat direct-chat-primary">
            <div class="card-header">
              <h3 class="card-title">{{ board.boadTitl }}</h3>

              <div class="card-tools">
                <button type="button" class="btn btn-block btn-primary btn-xs" @click="joinDebate" :disabled="isDisableJoinBtn">{{ partBtnLabel }}</button>
                {{ secondUserNick }}
                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                  <i class="fas fa-minus"></i>
                </button>
                <button type="button" class="btn btn-tool" title="Contacts" data-widget="chat-pane-toggle">
                  <i class="fas fa-comments"></i>
                </button>
                <span style="margin-right: 10px;margin-left: -10px"><span class="badge badge-danger">{{ commentListLength }}</span></span>
                <button
                  v-if="showDeleteBtn"
                  type="button" class="btn btn-tool"   @click="deleteBoard">
                  <i class="fas fa-trash"></i>
                </button>
              </div>
            </div>
            <!-- /.card-header -->
            <div class="card-body" style="height: auto;">
              <div style="text-align: center; text-align: center;">
                <div class="direct-chat-msg" style="margin-top: 20px; float:left">
                  <a class="btn btn-app bg-info" style="background: #d2d6de !important; min-width:36px !important;" @click="addLike('OWNER')">
                    <span class="badge bg-danger">{{ board.agreCunt ? board.agreCunt : 0 }}</span>
                    <i class="fas fa-thumbs-up"></i> Likes
                  </a>
                </div>
                <div style="display: inline-block; margin-top: 20px">
                  <div
                    v-if="!isClose"
                    style="font-width: bold;"> {{ $i18n.t('label.winOption') }} :
                    <span style="margin-right: 10px;"><span class="badge badge-danger">{{ board.winaLike }}</span></span>
                  </div>
                  <div v-if="!isClose">VS</div>
                  <div
                    v-if="isClose">
                    <img class="direct-chat-img" :src="board.winUser.userImgPath" alt="message user image">
                      {{ board.winUser.userNick }}
                    <div>
                      <span style="margin-right: 10px;"><span class="badge badge-danger">{{ board.winaLike }} win</span></span>
                    </div>
                  </div>
                </div>
                <div class="direct-chat-msg" style="margin-top:20px; margin-right:10px; float:right" @click="addLike('PART')">
                  <a class="btn btn-app bg-info" style="background: #007bff !important; min-width:36px !important;">
                    <span class="badge bg-danger">{{ board.oppoCunt ? board.oppoCunt : 0 }}</span>
                    <i class="fas fa-thumbs-up"></i> Likes
                  </a>
                </div>
              </div>
              <div style="clear: both"></div>
              <div class="direct-chat-msg">
                <div class="direct-chat-infos clearfix">
                  <span class="direct-chat-name float-left">{{ firstUserNick }}</span>
                  <span class="direct-chat-timestamp float-right">{{ board.cretDate | datetime }}</span>
                </div>
                <!-- /.direct-chat-infos -->
                <user-profile
                  :user="board.firstUser"
                ></user-profile>
                <!-- /.direct-chat-img -->
                <div class="direct-chat-text" style="font-size: 14px">
                  <div v-html="board.boadCont"></div>
                </div>
                <!-- /.direct-chat-text -->
              </div>
              <!-- Conversations are loaded here -->
              <div class="direct-chat-messages" style="height: auto;" v-for="(item, key) in messageList" v-bind:key="key">
                <!-- Message. Default to the left -->
                <debate-left
                  v-if="item.type === 'OWNER'"
                  :message="item"
                  :board="board"
                  ></debate-left>
                <!-- /.direct-chat-msg -->
                <!-- Message to the right -->
                <debate-right
                  v-if="item.type ==='PART'"
                  :message="item"
                  :board="board"
                  ></debate-right>
                <!-- /.direct-chat-msg -->
              </div>
              <!--/.direct-chat-messages-->
            </div>
            <!-- /.card-body -->
            <div class="" style="margin-bottom: 10px">
              <form action="#" method="post">
                <div class="input-group">
                  <!-- input type="text" name="message" placeholder="Type Message ..." class="form-control" -->
                  <textarea class="form-control" style="height: 70px" v-model="boardMessage" :disabled="disableChat" :placeholder="textAreaPlaceHolder"></textarea>
                  <span style="margin-left: 10px">
                      <button type="button" class="btn btn-block btn-outline-primary" style="height: 70px" @click="sendMessage" :disabled="disableChat">Send</button>
                  </span>
                </div>
              </form>
            </div>
            <!-- /.card-footer-->
          </div>
          <!--/.direct-chat -->
        </div>
        <div class="row mb-4" style="background: #f8f9fa !important; padding:0px !important;">
          <!-- Box Comment -->
          <div class="card-header">
            <div class="card-tools">
              <button type="button" class="btn btn-block btn-primary btn-xs" @click="openComment" >{{ commentBtnLabel }}</button>
            </div>
          </div>
          <div class="card card-widget .card-comments">
            Comments
            <!-- /.card-body -->
            <div v-if="openCommentFlag" class="card-body card-comments">
              <Debate-comment
                :comment-list="commentList"
                :user-info="userInfo"></Debate-comment>
            </div>
            <!-- /.card-footer -->
            <div v-if="openCommentFlag" class="card-footer" style="display: block;">
              <form action="#" method="post">
                <div class="img-push">
                  <textarea class="form-control" style="height: 50px" v-model="cmntMesg" :disabled="!isLogin" ></textarea>
                  <button type="button" class="btn btn-block btn-outline-primary" style="height: 50px; margin-top: 15px" @click="addReply">Send</button>
                </div>
              </form>
            </div>
            <!-- /.card-footer -->
          </div>
          <!-- /.card -->
        </div>
        <!-- /.row -->
      </div>
      <!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
</template>

<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'
import post from '@/util/util'
import DebateLeft from '@/views/board/DebateLeft.vue'
import DebateRight from '@/views/board/DebateRight.vue'
import DebateReply from '@/views/board/DebateReply.vue'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import DebateComment from '@/views/board/DebateComment.vue'
import UserProfile from '@/views/user/UserProfile.vue'
import { Route } from 'vue-router'

@Component({
  components: {
    UserProfile,
    DebateComment,
    DebateLeft,
    DebateRight,
    DebateReply
  }
})
export default class ViewDebate extends Vue {
  board: any = {}
  userInfo: any = {}
  boardList: any = []
  boardMessage = ''
  messageList: any = []
  commentList: any = []

  connected = false
  receivedMessage: any = []
  socket: any = null
  stompClient: any = null
  disableAddLike = true
  cmntMesg = ''
  commentBtnLabelName = this.$i18n.t('label.removeComment')
  openCommentFlag = true
  initScreen = true
  replyMessage = ''
  replyIdx = 0

  get textAreaPlaceHolder () {
    if (this.isLogin) {
      if (this.board.firstUser.userNmbr === this.userInfo.userNmbr) {
        return ''
      }
      if (!this.board.secondUser || !this.board.secondUser.userId) {
        return this.$i18n.t('label.joinMessage')
      }
    } else {
      return this.$i18n.t('label.userAfterLogin')
    }
    return ''
  }

  get boardCont () {
    return this.$sanitize(this.board.boadCont)
  }

  get isLogin () {
    return this.userInfo && this.userInfo.userNmbr
  }

  get currentDate () {
    return new Date()
  }

  get ownerImg () {
    return this.board.firstUser ? this.board.firstUser.userImgPath : ''
  }

  get firstUserNick () {
    if (this.board && this.board.firstUser) {
      return this.board.firstUser.userNick
    }
    return ''
  }

  get secondUserNick () {
    if (this.board && this.board.secondUser) {
      return this.board.secondUser.userNick
    }
    return ''
  }

  get disableChat () {
    if (!this.userInfo) {
      return true
    }

    if (this.board.firstUser && this.userInfo && this.userInfo.userNmbr) {
      if (this.board.firstUser.userNmbr === this.userInfo.userNmbr || (this.board.secondUser && this.board.secondUser.userNmbr === this.userInfo.userNmbr)) {
        return false
      }
    }

    return true
  }

  get commentListLength () {
    if (this.board && this.board.commentList) {
      return this.board.commentList.length
    }
  }

  get commentBtnLabel () {
    return this.openCommentFlag ? this.$i18n.t('label.removeComment') : this.$i18n.t('label.openComment')
  }

  get showDeleteBtn () {
    if (this.userInfo && this.userInfo.userNmbr) {
      return this.board.firstUser.userNmbr === this.userInfo.userNmbr
    }
    return false
  }

  get openReply () {
    console.log(this.replyIdx)
    return this.commentList[this.replyIdx].openReply
  }

  get isDisableJoinBtn () {
    return !((this.userInfo && this.userInfo.userId) && !this.board.chatUser && (!this.board.secondUser || !this.board.secondUser.userNmbr))
  }

  get isClose () {
    return this.board.winUser && this.board.winUser.userNick
  }

  get partBtnLabel () {
    if (this.isClose) {
      return this.$i18n.t('label.closeDebate')
    }

    if (this.board.secondUser && this.board.secondUser.userNick) {
      return this.$i18n.t('label.progress')
    }

    return this.$i18n.t('label.partBtn')
  }

  beforeMount () {
    this.board.boadNmbr = this.$route.params.id
    post('/getBoard', this.board).then((data: any) => {
      console.log(data)
      this.board = data.board
      this.userInfo = data.user

      if (this.userInfo && this.userInfo.userId) {
        this.disableAddLike = false
      }

      const chatList = data.chatList
      if (chatList && chatList.length > 0) {
        for (let i = 0; i < chatList.length; i++) {
          const source = chatList[i]
          const chat: any = {}
          chat.cretDate = source.cretDate
          chat.type = source.chatUser
          chat.content = source.chatMesg

          if (chat.type === 'PART') {
            chat.imgPath = this.board.secondUser.userImgPath
          } else {
            chat.imgPath = this.board.firstUser.userImgPath
          }

          this.messageList.push(chat)
        }
      }

      this.commentList = data.commentList

      console.log(this.board)
    })

    this.socket = new SockJS(this.$i18n.t('url.socketUrl').toString())
    this.stompClient = Stomp.over(this.socket)
    this.stompClient.connect({}, (frames: any) => {
      this.connected = true
      console.log(frames)
      this.stompClient.subscribe('/views/' + this.board.boadNmbr, (tick: { body: string }) => {
        this.receivedMessage.push(JSON.parse(tick.body).content)
        console.log(tick.body)

        const chat = JSON.parse(tick.body)

        chat.imgPath = this.userInfo.userImgPath
        this.messageList.push(chat)
        console.log(document.body.scrollHeight)
      })
    }, (error: any) => {
      console.log(JSON.stringify(error))
      this.connected = false
    })
  }

  @Watch('receivedMessage')
  protected onEeceivedMessageChanged () {
    if (!this.openCommentFlag) {
      setTimeout(() => {
        window.scrollTo(0, document.body.scrollHeight)
      }, 500)
    }
  }

  sendMessage () {
    if (this.stompClient && this.stompClient.connected) {
      const message: any = {}
      message.boadMssg = this.boardMessage
      message.cretUser = this.board.firstUser.userNmbr
      this.stompClient.send('/app/boadMessage/' + this.board.boadNmbr, JSON.stringify(message), {})
      this.boardMessage = ''
    }
  }

  joinDebate () {
    if (this.board.secondUser && this.board.secondUser.userNmbr) {
      alert(this.$i18n.t('alert.joinDisabled'))
      return
    }

    post('/joinDebate', this.board).then((data: any) => {
      if (!data.successStatus) {
        alert(this.$i18n.t(data.errMsg))
        return
      }
      this.board = data.board
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

      /* TODO think about the process later
      if (data.board.winUser && data.board.winUser.userNick) {
        const message: any = {}
        message.boadMssg = 'CLOSE'
        message.cretUser = 0
        this.stompClient.send('/app/boadMessage/' + this.board.boadNmbr, JSON.stringify(message), {})
        this.boardMessage = ''
      }
      */
    })
  }

  addReply () {
    if (!this.isLogin) {
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

  openComment () {
    this.openCommentFlag = !this.openCommentFlag
  }

  deleteBoard () {
    if (!confirm(this.$i18n.t('confirm.delete').toString())) {
      return
    }
    console.log(this.board)
    post('/deleteBoard', this.board).then(() => {
      this.$router.push('/')
    })
  }
}
</script>

<style>
.container img {
  max-width: 100%;
  height: auto;
  display: block;
}

.direct-chat-img {
  width:45px !important
}
</style>
