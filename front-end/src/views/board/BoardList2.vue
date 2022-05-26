<template>
  <div class="container public" style="margin-top: 20px">
    <div class="col-md-12">
      <DebateCard
        :board-list="boardList"/>
      <div class="card card-primary card-outline">
        <div class="card-header">
          <h3 class="card-title">{{ $i18n.t("label.debateboard") }}</h3>

          <div class="card-tools">
            <div class="input-group input-group-sm">
              <input type="text" class="form-control" placeholder="Search">
              <div class="input-group-append">
                <div class="btn btn-primary">
                  <i class="fas fa-search"></i>
                </div>
              </div>
            </div>
          </div>
          <!-- /.card-tools -->
        </div>
        <!-- /.card-header -->
        <div class="card-body p-0">
          <div class="mailbox-controls">
            <button type="button" class="btn btn-default btn-sm" @click="searchPost">
              <i class="fas fa-sync-alt"></i>
            </button>
            <div class="float-right">
              1-50/200
              <div class="btn-group">
                <button type="button" class="btn btn-default btn-sm">
                  <i class="fas fa-chevron-left"></i>
                </button>
                <button type="button" class="btn btn-default btn-sm">
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
                  <img class="img-circle img-bordered-sm" :src="item.firstUser.userImgPath" alt="user image">
                  <span class="username">
                          {{ item.firstUser.userNick }}
                  </span>
                  <span class="description">{{ item.cretDate | dateMin }} ago</span>
                </div>
                <!-- /.user-block -->
                <p style="cursor: pointer" @click="goFreeboard(item)">{{item.boadTitl}}</p>
                <p>
                  <a href="#" class="link-black text-sm"><i class="far fa-thumbs-up mr-1"></i> Like</a>
                  <span class="float-right">
                          <a href="#" class="link-black text-sm">
                            <i class="far fa-comments mr-1"></i> Comments (5)
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
              1-50/200
              <div class="btn-group">
                <button type="button" class="btn btn-default btn-sm">
                  <i class="fas fa-chevron-left"></i>
                </button>
                <button type="button" class="btn btn-default btn-sm">
                  <i class="fas fa-chevron-right"></i>
                </button>
              </div>
              <!-- /.btn-group -->
            </div>
            <!-- /.float-right -->
          </div>
        </div>
      </div>
      <button class="btn btn-block btn-outline-primary" style="width: 100px" @click="createBoard">Create</button>
      <!-- /.card -->
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'
import post from '@/util/util'
import DebateCard from '@/views/board/DebateCard.vue'

@Component({
  components: {
    DebateCard
  }
})
export default class freeboardList extends Vue {
  boardList: any = []
  page = 0
  boadDvsn = ''

  beforeCreate () {
    this.boadDvsn = this.$route.params.id
    post('/getFreeBoardList', { pageNo: this.page, boadDvsn: this.boadDvsn }).then((data: any) => {
      this.boardList = data
    })
  }

  createBoard () {
    this.$router.push('/write/' + this.$route.params.id)
  }

  searchPost () {
    post('/getFreeBoardList', { pageNo: this.page, boadDvsn: this.$route.params.id }).then((data: any) => {
      this.boardList = data
    })
  }

  goFreeboard (item: any) {
    if (this.$route.params.id === 'debate') {
      this.$router.push('/view/' + item.boadNmbr)
      return
    }
    this.$router.push('/freeboardView/' + item.boadNmbr)
  }
}
</script>
