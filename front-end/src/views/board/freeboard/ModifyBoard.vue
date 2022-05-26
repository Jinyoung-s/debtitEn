<template>
  <div class="content-wrapper">
    <section class="content-header">
      <div class="container">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>{{ $i18n.t("boardName." + $route.params.param) }}</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>
    <section class="content">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="card card-outline card-info">
              <div class="card-header">
                <h3 class="card-title">
                  {{ $i18n.t("label.write") }}
                </h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                <form @submit.prevent="submitForm">
                  <div class="form-group">
                    <input class="form-control" type="text" placeholder="Title" v-model="contentData.boadTitl">
                  </div>
                  <div class="form-group">
                    <vue-editor v-model="contentData.boadCont"></vue-editor>
                  </div>
                  <div class="form-group">
                    <button type="submit" class="btn btn-primary">{{ $i18n.t("label.save") }}</button>
                  </div>
                </form>
              </div>
              <div class="card-footer">
                {{ contentData.title }}
                {{ contentData.contents }}
              </div>
            </div>
          </div>
          <!-- /.col-->
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { VueEditor } from 'vue2-editor'
import post from '@/util/util.ts'

/* create Debate */
export default {
  name: 'ModifyBoard',
  components: { VueEditor },
  data: () => ({
    contentData: {
      boadNmbr: 0,
      boadTitl: '',
      boadCont: '',
      boadDvsn: '',
      winaLike: 0
    }
  }),
  created: function () {
    this.contentData.boadDvsn = this.$route.params.param
    this.contentData.boadNmbr = this.$route.params.id
    post('/getBoard', this.contentData).then((data) => {
      this.contentData.boadNmbr = data.board.boadNmbr
      this.contentData.boadTitl = data.board.boadTitl
      this.contentData.boadCont = data.board.boadCont
      this.contentData.boadDvsn = data.board.boadDvsn
      this.contentData.winaLike = data.board.winaLike
    })
  },
  methods: {
    submitForm () {
      post('/writeBoard', this.contentData).then(() => this.$router.push('/FreeboardView/' + this.$route.params.id))
    }
  }
}
</script>
