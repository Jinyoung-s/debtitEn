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
                  <div class="form-group row">
                    <label class="col-sm-2 col-form-label">{{ $i18n.t("label.title") }}</label>
                    <div class="col-sm-10">
                      <input class="form-control" type="text" placeholder="Title" v-model="contentData.boadTitl">
                    </div>
                  </div>
                  <div class="form-group row">
                    <label class="col-sm-2 col-form-label">{{ $i18n.t("label.winOption") }}</label>
                    <div class="col-sm-2 form-group">
                      <input type="number" class="form-control col-sm-12" v-model="contentData.winaLike" min="10">
                    </div>
                    <label class="col-sm-2 col-form-label">{{ $i18n.t("label.addPoint") }} : {{ winAddPoint }}</label>
                  </div>
                  <div class="form-group">
                    <vue-editor v-model="contentData.boadCont"></vue-editor>
                  </div>
                  <div class="form-group">
                    <button type="submit" class="btn btn-primary">{{ $i18n.t("label.save") }}</button>
                  </div>
                </form>
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
  name: 'WriteDebate',
  components: { VueEditor },
  data: () => ({
    contentData: {
      boadTitl: '',
      boadCont: '',
      boadDvsn: '',
      winaLike: 10
    }
  }),
  created: function () {
    this.contentData.boadDvsn = this.$route.params.param
    this.setOption()
  },
  computed: {
    winAddPoint () {
      return this.contentData.winaLike * 2
    }
  },
  methods: {
    submitForm () {
      post('/writeBoard', this.contentData).then(() => this.$router.push({ name: 'HomePage' }))
    },
    setOption () {
      let optionData = '<option value="">' + this.$i18n.t('label.none') + '</option>'
      for (let i = 1; i < 25; i++) {
        optionData += '<option value=' + i + ' >' + i + ' Hour </option>'
      }
      this.timeLimitOption = optionData
    }
  }
}
</script>
