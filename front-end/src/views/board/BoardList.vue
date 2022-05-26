<template>
  <div class="content-wrapper">
    <div class="content" style="padding-top: 20px">
      <div class="container public"  >
        <div class="">
          <button class="btn btn-block btn-outline-primary" style="width: 100px; margin-bottom: 10px" @click="createBoard">Create</button>
          <div class="row">
            <div class="col-md-6">
              <template
                v-for="(item, index) in boardList">
                <div
                  v-if="getShowData(index)"
                  class="card card-primary card-outline" :key="index">
                  <div class="card-body" @click="goFreeboard(item)" style="cursor:pointer">
                    <div><h5 class="card-title"><a><strong>{{ item.boadTitl }}</strong></a></h5></div><br>
                    <div v-html="item.boadCont"></div>
                    <div style="float:right;">
                      <span style="margin-right: 10px;">{{ item.readCunt ? item.readCunt : 0 }} </span> / <span style="margin-right: 10px;"><i class="fas fa-thumbs-up" style="color:#d2d6de"></i><span class="badge badge-danger">{{ item.agreCunt ? item.agreCunt : 0 }}</span> </span> / <i class="fas fa-thumbs-up" style="color:#007bff"></i><span class="badge badge-danger">{{ item.oppoCunt ? item.oppoCunt : 0 }}</span>
                    </div>
                  </div>
                </div><!-- /.card -->
              </template>
            </div>
            <div
              v-if="showRight"
              class="col-md-6">
              <template
                v-for="(item, index) in boardList">
                <div
                  v-if="index % 2 == 1"
                  class="card card-primary card-outline" :key="index">
                  <div class="card-body" @click="goFreeboard(item)" style="cursor:pointer">
                    <div><h5 class="card-title"><a><strong>{{ item.boadTitl }}</strong></a></h5></div><br>
                    <div v-html="item.boadCont"></div>
                    <div style="float:right;">
                      <span style="margin-right: 10px;">{{ item.readCunt ? item.readCunt : 0 }} </span> / <span style="margin-right: 10px;"><i class="fas fa-thumbs-up" style="color:#d2d6de"></i><span class="badge badge-danger">{{ item.agreCunt ? item.agreCunt : 0 }}</span> </span> / <i class="fas fa-thumbs-up" style="color:#007bff"></i><span class="badge badge-danger">{{ item.oppoCunt ? item.oppoCunt : 0 }}</span>
                    </div>
                  </div>
                </div><!-- /.card -->
              </template>
            </div>
          </div>
        </div>
    </div>
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'
import post from '@/util/util'
import { Route } from 'vue-router'
import DebateCard from '@/views/board/DebateCard.vue'
import jQuery from 'jquery'
import Router from '@/router'

@Component({
  components: {
    DebateCard
  }
})
export default class freeboardList extends Vue {
  boardList: any = []
  page = 0
  boadDvsn = ''
  progress = false
  width = window.innerWidth

  created () {
    window.addEventListener('scroll', this.handleScroll)
    window.addEventListener('gesturechange', this.handleScroll)
    document.body.addEventListener('touchmove', this.handleScroll)
    window.addEventListener('resize', this.handleResize)
    this.handleResize()

    this.boadDvsn = this.$route.params.id
    const storeBoardList = this.$store.getters.getBoardList
    if (storeBoardList.length > 0) {
      console.log(storeBoardList)
      if (storeBoardList[0].boadDvsn === this.$route.params.id) {
        this.boardList = storeBoardList
        return
      }
    }

    post('/getFreeBoardList', { pageNo: this.page, boadDvsn: this.boadDvsn }).then((data: any) => {
      this.boardList = data.boardList
      this.$store.commit('changeBoardList', data.boardList)
    })
  }

  @Watch('$route', { immediate: true, deep: true })
  onChangeBoardDvsn (newVal: Route) {
    console.log(newVal.params.id)
  }

  get showRight () {
    return this.width > 770
  }

  createBoard () {
    this.$router.push('/write/' + this.$route.params.id)
  }

  beforeDestroy () {
    window.removeEventListener('scroll', this.handleScroll)
    window.removeEventListener('gesturechange', this.handleScroll)
    document.body.removeEventListener('touchmove', this.handleScroll)
    window.removeEventListener('resize', this.handleResize)
  }

  searchPost () {
    if (this.progress || this.boardList.length === 0) {
      return
    }
    this.progress = true
    this.page = this.page + 1
    post('/getFreeBoardList', { pageNo: this.page, boadDvsn: this.$route.params.id }).then((data: any) => {
      // this.boardList = data
      const boardList1 = data.boardList
      for (var i = 0; i < boardList1.length; i++) {
        this.boardList.push(boardList1[i])
      }

      const addBoardData = this.boardList
      this.$store.commit('changeBoardList', addBoardData)
      if (data.boardList.length > 0) {
        this.progress = false
      }
    })
  }

  goFreeboard (item: any) {
    // TO-DO think about the process later
    this.$router.push('/view/' + item.boadNmbr)
    // if (this.$route.params.id === 'debate') {
    // }
    // this.$router.push('/freeboardView/' + item.boadNmbr)
  }

  handleScroll (event: any) {
    const scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop)
    const scrollHeight = this.getDocHeight()
    const clientHeight = Math.max(document.documentElement.clientHeight, window.innerHeight)
    const scrolledToBottom = Math.ceil(scrollTop + clientHeight) >= scrollHeight - 200
    if (scrolledToBottom) {
      this.searchPost()
    }
  }

  getDocHeight () {
    const D = document
    return Math.max(D.body.scrollHeight, D.documentElement.scrollHeight, D.body.offsetHeight, D.documentElement.offsetHeight, D.body.clientHeight, D.documentElement.clientHeight)
  }

  handleResize () {
    this.width = window.innerWidth
  }

  getShowData (index: number) {
    if (this.width > 770) {
      return index % 2 === 0
    }
    return true
  }
}
</script>
