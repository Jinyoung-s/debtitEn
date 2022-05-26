<template>
  <footer class="main-footer">
    <strong>Copyright &copy; 2022 Debtit</strong>
     All rights reserved.

    <div class="float-right d-none d-sm-inline-block" style="font-size: 12px">
        <a href="" @click="goPrivate">개인정보 처리방침</a>
      | <a href="" @click="goUseTerm" > 이용약관 </a>
    </div>
  </footer>
</template>
<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'

@Component
export default class DebateFooter extends Vue {
  goPrivate () {
    this.$router.push('/privateInfo')
  }

  goUseTerm () {
    this.$router.push('/debateUseTerm')
  }
}
</script>
