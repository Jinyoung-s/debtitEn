<template>
  <div class="col-lg-6">
    <div>
      <h3 class="m-0"><small>{{ $i18n.t("label.popular") }}</small></h3>
    </div><!-- /.col -->
    <div>
      <div style="float:right"><a @click="goBoardList" style="color: rgba(0, 0, 0, 0.5); cursor: pointer" >더보기 + </a></div>
    </div>
    <div style="clear:both"></div>
    <template
      v-for="(item, index) in boardList">
      <div class="card card-primary card-outline" :key="index">
        <div class="card-body" @click="clickCard(item)" style="cursor:pointer">
          <h5 class="card-title"><a>{{ item.boadTitl }}</a></h5><br>
          <div v-html="item.boadCont"></div>
          <div style="float:right;">
           <span style="margin-right: 10px;">{{ item.readCunt ? item.readCunt : 0 }} </span> / <span style="margin-right: 10px;"><i class="fas fa-thumbs-up" style="color:#d2d6de"></i><span class="badge badge-danger">{{ item.agreCunt ? item.agreCunt : 0 }}</span> </span> / <i class="fas fa-thumbs-up" style="color:#007bff"></i><span class="badge badge-danger">{{ item.oppoCunt ? item.oppoCunt : 0 }}</span>
          </div>
        </div>
      </div><!-- /.card -->
    </template>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'
import post from '@/util/util'
import store from '@/store'

@Component
export default class DebateCard extends Vue {
  @Prop({ default: [] }) boardList: any

  board: any = {}
  contentData: any = {
    currentBoard: {}
  }

  clickCard (boardInfo: any) {
    this.$router.push('/view/' + boardInfo.boadNmbr)
  }

  goBoardList () {
    this.$router.push('/boardList/debate')
  }
}
</script>
