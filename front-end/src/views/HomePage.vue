<template>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container">
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <div class="content">
      <div class="container">
        <div class="row">
          <DebateCard
            :board-list="debateBoardList"/>
          <FreeboardCard
            :board-list="freeBoardList"/>
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'
import DebateCard from '@/views/board/DebateCard.vue'
import post from '@/util/util'
import FreeboardCard from '@/views/board/freeboard/FreeboardCard.vue'

@Component({
  components: {
    FreeboardCard,
    DebateCard
  }
})
export default class HomePage extends Vue {
  boardList: any = []
  debateBoardList: any = []
  freeBoardList: any = []

  beforeMount () {
    post('/getPopularBoard', {}).then((data: any) => {
      this.boardList = data
      for (let i = 0; i < this.boardList.length; i++) {
        if (this.boardList[i].boadDvsn === 'debate') {
          this.debateBoardList.push(this.boardList[i])
        }
        if (this.boardList[i].boadDvsn === 'free') {
          this.freeBoardList.push(this.boardList[i])
        }
      }
    })
  }
}
</script>
