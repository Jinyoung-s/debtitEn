<template>
  <div class="wrapper">
    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand-md navbar-light navbar-white">
      <div class="container">
        <a href="/" class="navbar-brand">
          <span class="brand-text font-weight-light">DEBTIT</span>
        </a>

        <button class="navbar-toggler order-1" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse order-3" id="navbarCollapse">
          <!-- Left navbar links -->
          <ul class="navbar-nav">
            <li class="nav-item" id='loginLeftMenu'>
              <a class="nav-link"  data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
            </li>
            <li class="nav-item">
              <a href="" @click="goDebate('debate')" class="nav-link">{{ $i18n.t("label.debateBoard") }}</a>
            </li>
            <li class="nav-item">
              <a href="" class="nav-link" @click="gofreeboardList('free')">{{ $i18n.t("label.freeBoard") }}</a>
            </li>
            <li class="nav-item dropdown">
              <a id="dropdownSubMenu1" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle" >{{ $i18n.t("label.menu") }}</a>
              <ul aria-labelledby="dropdownSubMenu1" class="dropdown-menu border-0">
                <li><a href="" class="dropdown-item" @click="gofreeboardList('hotdeal')">{{ $i18n.t("boardName.hotdeal")}}</a></li>
                <li><a href="" class="dropdown-item" @click="gofreeboardList('humor')">{{ $i18n.t("boardName.humor")}}</a></li>
                <li><a href="" class="dropdown-item" @click="gofreeboardList('propose')">{{ $i18n.t("label.propose") }} </a></li>
                <li class="dropdown-divider"></li>

                <!-- Level two dropdown-->
                <li class="dropdown-submenu " style="display:none;">
                  <a id="dropdownSubMenu2" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="dropdown-item dropdown-toggle">Hover for action</a>
                  <ul aria-labelledby="dropdownSubMenu2" class="dropdown-menu border-0 shadow">
                    <li>
                      <a tabindex="-1" href="#" class="dropdown-item">level 2</a>
                    </li>
                    <!-- Level three dropdown-->
                    <li class="dropdown-submenu">
                      <a id="dropdownSubMenu3" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="dropdown-item dropdown-toggle">level 2</a>
                      <ul aria-labelledby="dropdownSubMenu3" class="dropdown-menu border-0 shadow">
                        <li><a href="#" class="dropdown-item">3rd level</a></li>
                        <li><a href="#" class="dropdown-item">3rd level</a></li>
                      </ul>
                    </li>
                    <!-- End Level three -->

                    <li><a href="#" class="dropdown-item">level 2</a></li>
                    <li><a href="#" class="dropdown-item">level 2</a></li>
                  </ul>
                </li>
                <!-- End Level two -->
              </ul>
            </li>
          </ul>
        </div>

        <!-- Right navbar links -->
        <ul class="order-1 order-md-4 navbar-nav navbar-no-expand ml-auto">
          <!-- Messages Dropdown Menu -->
          <li class="nav-item dropdown" style="padding-top: 6px">
            <button
              v-if="loginBtnShow"
              @click="goUserInfo"
              class="btn btn-block btn-primary btn-xs"
              style="margin-right:5px"> {{ $i18n.t("label.userInfo")}}</button>
          </li>
          <li class="nav-item dropdown" style="padding-top: 6px">
              <button
                v-if="loginBtnShow"
                @click="goLogout"
                class="btn btn-block btn-primary btn-xs"
                style="margin-left:5px">{{ $i18n.t("label.logout")}}</button>
          </li>
          <li class="nav-item dropdown" style="padding-top: 6px">
            <button
              v-if="!loginBtnShow"
              @click="goLogin"
              class="btn btn-block btn-primary btn-xs"
              style="margin-right:5px">{{ $i18n.t("label.login")}}</button>
          </li>
          <li class="nav-item dropdown" style="padding-top: 6px;margin-left: 8px">
            <button
              v-if="!loginBtnShow"
              @click="goSignUp"
              class="btn btn-block btn-primary btn-xs"
              style="margin-left:5px">{{ $i18n.t("label.join")}}</button>
          </li>
          <li
            v-if="loginBtnShow"
            class="nav-item dropdown">
            <a class="nav-link" data-toggle="dropdown" href="#">
              <i class="fas fa-comments"></i>
              <span class="badge badge-danger navbar-badge">{{ memoList.length }}</span>
            </a>
            <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
              <div v-for="(item, key) in memoList" :key="key">
                <a href="" class="dropdown-item" @click="goMemoList">
                  <!-- Message Start -->
                  <div class="media">
                    <img :src="item.sendUser.userImgPath" alt="User Avatar" class="img-size-50 mr-3 img-circle">
                    <div class="media-body">
                      <h3 class="dropdown-item-title">
                        {{ item.sendUser.userNick }}
                        <span class="float-right text-sm text-danger"><i class="fas fa-star"></i></span>
                      </h3>
                      <p class="text-sm">{{ item.memoCont | substring(17) }}</p>
                      <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i>{{ item.cretDate | dateMin }} ago</p>
                    </div>
                  </div>
                  <!-- Message End -->
                </a>
                <div class="dropdown-divider"></div>
              </div>
              <a @click="goMemoList" style="cursor: pointer" class="dropdown-item dropdown-footer">See All Messages</a>
            </div>
          </li>
        </ul>
      </div>
    </nav>
    <!-- /.navbar -->

    <!-- Main Sidebar Container -->
    <aside class="main-sidebar sidebar-dark-primary elevation-4">
      <!-- Brand Logo -->
      <a href="../../index3.html" class="brand-link">
        <span class="brand-text font-weight-light">DEBTIT</span>
      </a>

      <!-- Sidebar -->
      <div class="sidebar">
        <!-- Sidebar user (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
          <div
            v-if="userImgPath"
            class="image">
            <img :src="userImgPath" class="img-circle elevation-2" alt="User Image">
          </div>
          <div
            v-if="!userImgPath">
            <nav class="mt-2">
              <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <li class="nav-item">
                  <a href="#" class="nav-link" @click="goLogin()">
                    <i class="nav-icon fas fa-door-open"></i>
                    <p>
                      {{ $i18n.t("label.login") }}
                    </p>
                  </a>
                </li>
              </ul>
            </nav>
          </div>
          <div class="info" @click="goUserInfo">
            <a href="#" class="d-block">{{ userNick }}</a>
          </div>
        </div>

        <!-- SidebarSearch Form -->
        <div class="form-inline" style="display: none">
          <div class="input-group" data-widget="sidebar-search">
            <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
            <div class="input-group-append">
              <button class="btn btn-sidebar">
                <i class="fas fa-search fa-fw"></i>
              </button>
            </div>
          </div>
        </div>
        <nav class="mt-2">
          <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
            <li class="nav-item">
              <a href="#" class="nav-link" @click="goUserInfo">
                <i class="nav-icon fas fa-coins"></i>
                <p>
                  Point {{ userPoint }}
                </p>
              </a>
            </li>
          </ul>
        </nav>
        <nav class="mt-2">
          <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
            <li class="nav-item">
              <a href="#" class="nav-link" @click="goMemoList">
                <i class="nav-icon fas fa-mail-bulk"></i>
                <p>
                  {{ memoListCount }}
                </p>
              </a>
            </li>
          </ul>
        </nav>
        <!-- /.sidebar-menu -->
      </div>
      <!-- /.sidebar -->
    </aside>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop, Watch } from 'vue-property-decorator'
import post from '@/util/util'

@Component
export default class DebateHeader extends Vue {
  userInfo: any = {}
  memoList = []
  @Prop({ default: false }) changeUserInfoFlag: any

  beforeMount () {
    this.getUserInfo()
  }

  get loginBtnShow () {
    return this.userInfo
  }

  get userNick () {
    if (this.userInfo && this.userInfo.userNick) {
      return this.userInfo.userNick
    }
    return ''
  }

  get memoListCount () {
    if (!this.memoList) {
      return 0
    }
    return this.memoList.length
  }

  get userPoint () {
    if (this.userInfo) {
      return this.userInfo.userPoin
    }
    return ''
  }

  get userImgPath () {
    if (this.userInfo && this.userInfo.userImgPath) {
      return this.userInfo.userImgPath
    }
    return ''
  }

  @Watch('changeUserInfoFlag')
  changeUserInfo () {
    this.getUserInfo()
  }

  getUserInfo () {
    post('/getUserInfo', null).then((data: any) => {
      console.log(data)
      this.userInfo = data.user
      this.memoList = data.userMemo

      this.$store.commit('changeUserInfo', this.userInfo)
      this.$emit('UserSet', this.userInfo)
    })
  }

  goLogin () {
    this.$router.push({ name: 'LoginPage' })
  }

  goUserInfo () {
    this.$router.push({ name: 'UserInfo' })
  }

  goLogout () {
    post('/logout', null).then((data: any) => {
      location.reload()
    })
  }

  goMemoList () {
    this.$router.push({ name: 'MemoBoardList' })
  }

  goSignUp () {
    this.$router.push({ name: 'RegisterPage' })
  }

  goDebate (dvsn: string) {
    this.$router.push('/boardList/' + dvsn)
  }

  gofreeboardList (dvsn: string) {
    this.$router.push('/FreeboardList/' + dvsn)
  }
}
</script>
