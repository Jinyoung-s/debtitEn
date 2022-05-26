<template>
  <div>
    <keep-alive>
      <component :is="component" />
    </keep-alive>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'
import BoardLList from '@/views/board/BoardList.vue'

@Component({
  components: {
    BoardLList
  }
})
export default class boardListCover extends Vue {
  get component () {
    return 'BoardLList'
  }
}
</script>
