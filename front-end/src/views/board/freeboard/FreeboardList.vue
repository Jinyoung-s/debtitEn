<template>
  <div class="content-wrapper">
    <div class="content" style="padding-top: 20px">
      <div class="container public" >
        <div class="col-md-12">
          <div class="card card-primary card-outline">
            <div class="card-header">
              <h3 class="card-title">{{ $i18n.t("boardName." + $route.params.id) }}</h3>

              <div class="card-tools">
              </div>
              <!-- /.card-tools -->
            </div>
            <!-- /.card-header -->
            <div class="card-body p-0">
              <div class="mailbox-controls">
                <button type="button" class="btn btn-default btn-sm" @click="loadData(page)">
                  <i class="fas fa-sync-alt"></i>
                </button>
                <div class="float-right">
                  <!--input type="number" class="" style="width: 40px;" v-model="page" -->  {{ page }} / {{ boardCount }}
                  <div class="btn-group">
                    <button type="button" class="btn btn-default btn-sm" @click="searchPost(-1)">
                      <i class="fas fa-chevron-left"></i>
                    </button>
                    <button type="button" class="btn btn-default btn-sm" @click="searchPost(1)">
                      <i class="fas fa-chevron-right"></i>
                    </button>
                  </div>
                  <!-- /.btn-group -->
                </div>
                <!-- /.float-right -->
              </div>
            </div>
            <!--card start -->
            <div class="card-body">
              <div class="tab-content">
                <div class="tab-pane active" id="activity">
                  <!-- Post -->
                  <div class="post" v-for="(item, key) in boardList" :key="key">
                    <div class="user-block">
                      <img class="img-circle img-bordered-sm" :src="getUserImgPath(item)" alt="user image">
                      <span class="username">
                              {{ getUserNick(item) }}
                      </span>
                      <span class="description">{{ item.cretDate | dateMin }} ago</span>
                    </div>
                    <!-- /.user-block -->
                    <div style="margin-left: 20px;: "><p style="cursor: pointer" @click="goFreeboard(item)">{{item.boadTitl}}</p></div>
                    <p>
                      <a href="#" class="link-black text-sm"><i class="far fa-thumbs-up mr-1"></i> Like {{ item.agreCunt == null ? 0 : item.agreCunt }}</a>
                      <span class="float-right">
                              <a href="#" class="link-black text-sm">
                                <i class="far fa-comments mr-1"></i> Comments ({{ getCommentLength(item) }})
                              </a>
                            </span>
                    </p>
                  </div>
                  <!-- /.post -->
                </div>
                  <!-- /.tab-pane -->
              </div>
              <!-- /.tab-content -->
            </div>
    <!------------------------>
            <!------------------------>
            <!------------------------>
            <!-- /.card-body -->
            <div class="card-footer p-0">
              <div class="mailbox-controls">
                <div class="float-right">
                  <!--input type="number" class="" style="width: 40px;" v-model="page" --> {{ page }} / {{ boardCount }}
                  <div class="btn-group">
                    <button type="button" class="btn btn-default btn-sm" @click="searchPost(-1)">
                      <i class="fas fa-chevron-left"></i>
                    </button>
                    <button type="button" class="btn btn-default btn-sm" @click="searchPost(1)">
                      <i class="fas fa-chevron-right"></i>
                    </button>
                  </div>
                  <!-- /.btn-group -->
                </div>
                <!-- /.float-right -->
              </div>
            </div>
          </div>
          <div class="card" style="border: 0px; box-shadow :none">
            <div style="text-align: center">
              <div style="float:left">
                <button class="btn btn-block btn-outline-primary" style="width: 100px;" @click="createBoard">Create</button>
              </div>
              <div style="float:right">
                <button class="btn btn-block btn-outline-primary" style="width: 100px;" @click="searchPost(1)">Next</button>
              </div>
            </div>
          </div>
          <!-- /.card -->
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'
import post from '@/util/util'

@Component
export default class freeboardList extends Vue {
  boardList: any = []
  page = 1
  boardCount = 0
  contentData: any = {}

  created () {
    const storeContentData = this.$store.getters.getContentData
    if (storeContentData.boadDvsn === this.$route.params.id) {
      this.page = storeContentData.page
      this.boardCount = storeContentData.boardCount
      this.boardList = this.$store.getters.getBoardList
      return
    } else {
      this.$store.commit('changeContentData', {})
    }

    post('/getFreeBoardList', { pageNo: this.page - 1, boadDvsn: this.$route.params.id }).then((data: any) => {
      console.log(data)
      this.boardList = data.boardList
      this.boardCount = data.boardCount
      this.contentData.page = this.page
      this.contentData.boadDvsn = this.$route.params.id
      this.contentData.boardCount = this.boardCount
      this.$store.commit('changeContentData', this.contentData)
      this.$store.commit('changeBoardList', data.boardList)
    })
  }

  createBoard () {
    this.$store.commit('changeContentData', {})
    this.$router.push('/writeBoard/' + this.$route.params.id)
  }

  searchPost (num: number) {
    if (this.page >= this.boardCount) {
      return
    }

    this.page = this.page + num
    this.loadData(this.page)
  }

  loadData (page: number) {
    post('/getFreeBoardList', { pageNo: this.page - 1, boadDvsn: this.$route.params.id }).then((data: any) => {
      console.log(data)
      this.boardList = data.boardList
      this.contentData.page = this.page
      this.contentData.boadDvsn = this.$route.params.id
      this.contentData.boardCount = this.boardCount
      this.$store.commit('changeContentData', this.contentData)
      this.$store.commit('changeBoardList', data.boardList)
      window.scrollTo(0, 0)
    })
  }

  getUserImgPath (item: any) {
    if (item.firstUser && item.firstUser.userImgPath) {
      return item.firstUser.userImgPath
    }
    return ''
  }

  getUserNick (item: any) {
    if (item.firstUser && item.firstUser.userNick) {
      return item.firstUser.userNick
    }
    return ''
  }

  getCommentLength (item: any) {
    if (item.commentList) {
      return item.commentList.length
    }
    return 0
  }

  goFreeboard (item: any) {
    this.$router.push('/freeboardView/' + item.boadNmbr)
  }
}
</script>
