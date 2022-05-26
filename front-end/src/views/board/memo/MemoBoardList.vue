<template>
  <div class="container public">
    <div class="content-header">
    </div>
    <div class="col-md-12">
        <div class="card card-primary card-outline">
          <div class="card-header">
            <h3 class="card-title"></h3>
            <div class="card-tools">
            </div>
            <!-- /.card-tools -->
          </div>
          <!-- /.card-header -->
          <div class="card-body p-0">
            <div class="mailbox-controls">
            </div>
            <div class="table-responsive mailbox-messages">
              <table class="table table-hover table-striped">
                <tbody>
                <tr v-for="(item, key) in memoList" :key="key">
                  <td>
                    <div class="icheck-primary">
                      <input type="checkbox" value="" id="check1">
                      <label for="check1"></label>
                    </div>
                  </td>
                  <td class="" style="margin: 0px; padding-right:0px; padding-left:0px">{{ item.sendUser.userNick | substring(4) }}</td>
                  <td class="mailbox-subject" @click="clickMemo($event, item)" data-target="#modal-memo" style="cursor: pointer">{{ item.memoCont | substring(40) }}
                  </td>
                  <td class="mailbox-date">{{ item.cretDate | dateMin }} ago</td>
                </tr>
                </tbody>
              </table>
              <!-- /.table -->
            </div>
            <!-- /.mail-box-messages -->
          </div>
          <!-- /.card-body -->
          <div class="card-footer p-0">
            <div class="mailbox-controls">
              <div class="btn-group">
                <button type="button" class="btn btn-default btn-sm">
                  <i class="far fa-trash-alt"></i>
                </button>
              </div>
              <!-- /.btn-group -->
              <button type="button" class="btn btn-default btn-sm">
                <i class="fas fa-sync-alt"></i>
              </button>
              <div class="float-right">
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
      <!-- /.card -->
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator'
import post from '@/util/util'
import { bus } from '@/main'

@Component
export default class MemoBoardList extends Vue {
  memoList: any = []
  page = 0

  beforeCreate () {
    post('/getUserMemoList', { pageNo: this.page }).then((data: any) => {
      this.memoList = data
    })
  }

  clickMemo (evt: any, item: any) {
    post('/userMemoCheck', item)
    item.sendUser.memoCont = item.memoCont
    bus.$emit('chagneMember', item.sendUser)
    evt.target.setAttribute('data-toggle', 'modal')
    evt.target.click()
  }
}
</script>
