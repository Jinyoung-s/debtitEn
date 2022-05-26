<template>
  <div>
    <div v-for="(item, key) in commentList" :key="key">
      <div class="card-comment" :style="replyBorder">
        <!-- User image -->
        <img
          v-if="item.userNick"
          class="img-circle" :src="item.user.userImgPath" alt="User Image" style="width:40px !important;margin-right: 5px">
        <div class="comment-text">
                    <span class="username">
                      {{ item.userNick }}
                      <span class="text-muted float-right">{{ item.cretDate | datetime }}</span>
                    </span><!-- /.username -->
          <div style="clear:both"></div>
          <div
            style="width: 100%; display:block; word-break:break-all;word-wrap:break-word;"
            v-if="!item.openModify"
            v-sanitize="item.cmntMesg"></div>
          <textarea
            v-if="item.openModify"
            class="form-control" style="height: 50px; margin-bottom: 10px" v-model="item.cmntMesg"></textarea>
        </div>
        <div style="float:right">
          <button
            v-if="getBtnAuth(item) && !item.openModify"
            type="button" class="btn btn-default btn-xs" style="margin-right: 10px" @click="deleteReplay(item)"><i class="fas fa-trash"></i></button>
          <button
            v-if="getBtnAuth(item)"
            type="button" class="btn btn-default btn-xs" style="margin-right: 10px" @click="openModifyArea(key)"><i class="fas fa-toolbox"></i> Modify</button>
          <button
            v-if="replyView && !item.openModify"
            type="button" class="btn btn-default btn-xs" @click="setReply(key)"><i class="fas fa-reply"></i> Reply</button>
          <button
            v-if="item.openModify"
            type="button" class="btn btn-default btn-xs" @click="updateSave(item)"><i class="fas fa-save"></i> Save</button>

        </div>
        <div style="clear:both"></div>
        <div
          v-if="item.openReply"
          style="clear:both;margin-top:10px">
          <textarea class="form-control" style="height: 50px" v-model="item.reply" :disabled="false" ></textarea>
          <div style="float:right; margin-top:10px">
            <button type="button" class="btn btn-default btn-sm" @click="saveReply(item)"><i class="fas fa-save"></i> Save</button>
          </div>
        </div>
        <div style="clear:both"></div>
        <div v-if="getItemChildListExist(item.childList)" :style="replyMargin">
          <DebateComment
            :comment-list="item.childList"
            :level="level+1"
            :user-info="userInfo"></DebateComment>
        </div>
        <!-- /.comment-text -->
      </div>
      <!-- /.card-comment -->
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'
import post from '@/util/util'

@Component({
  name: 'DebateComment'
})
export default class DebateComment extends Vue {
  @Prop() commentList: any
  @Prop({ default: 0 }) level: any
  @Prop() userInfo: any

  get replyMargin () {
    return this.level < 4 ? 'margin-left: 10px' : ''
  }

  get replyView () {
    return this.userInfo.userNmbr !== null
  }

  getBtnAuth (item: any) {
    if (this.userInfo && item.user.userNmbr === this.userInfo.userNmbr) {
      return true
    }

    return false
  }

  get replyBorder () {
    let border = ''
    if (this.level === 0) {
      border += 'border-bottom: 1px solid #e9ecef;padding:8px;'
    }

    if (this.level > 0) {
      border += 'border-left:1px solid #e9ecef'
    }
    return border
  }

  getItemChildListExist (item: any) {
    return item && item.length > 0
  }

  setReply (item: any) {
    this.commentList[item].openReply = !this.commentList[item].openReply
  }

  saveReply (item: any) {
    if (!item.reply) {
      alert(this.$i18n.t('alert.inputContent'))
      return
    }
    const newComment:any = {}
    newComment.parent = {}
    newComment.parent.cmntNmbr = item.cmntNmbr
    newComment.cmntNmbr = null
    newComment.cmntMesg = item.reply
    newComment.boadNmbr = this.$route.params.id
    newComment.userNick = item.userNick
    newComment.user = item.user

    post('/saveComment', newComment).then(() => {
      location.reload()
    })
  }

  deleteReplay (item: any) {
    if (!confirm(this.$i18n.t('confirm.delete').toString())) {
      return
    }

    post('/deleteComment', item).then(() => {
      location.reload()
    })
  }

  openModifyArea (key: any) {
    this.commentList[key].openModify = !this.commentList[key].openModify
  }

  updateSave (item: any) {
    post('/saveComment', item).then(() => {
      location.reload()
    })
  }
}
</script>
