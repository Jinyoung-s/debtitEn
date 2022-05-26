<template>
  <!-- Message to the left -->
  <div class="direct-chat-msg">
    <div class="direct-chat-infos clearfix">
      <span class="direct-chat-name float-left">{{ board.firstUser.userNick }}</span>
      <span class="direct-chat-timestamp float-right">{{ message.cretDate | datetime }}</span>
    </div>
    <!-- /.direct-chat-infos -->
    <user-profile
      :user="board.firstUser"
      ></user-profile>
    <!-- /.direct-chat-img -->
    <div class="direct-chat-text" style="font-size: 14px">
      {{ message.content }}
    </div>
    <!-- /.direct-chat-text -->
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'
import post from '@/util/util'
import UserProfile from '@/views/user/UserProfile.vue'

@Component({
  components: { UserProfile }
})
export default class DebateLeft extends Vue {
  boardList: any = []

  @Prop() private message: any
  @Prop() private board: any
}
</script>
