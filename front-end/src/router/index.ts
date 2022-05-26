import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import LoginPage from '../views/LoginPage.vue'
import HomePage from '../views/HomePage.vue'
import RegisterPage from '../views/reg/RegisterPage.vue'
import SignWithGoogle from '../views/reg/SignGoogle.vue'
import SignWithFacebook from '../views/reg/SignFacebook.vue'
import EmailSendNotice from '../views/reg/EmailSendNotice.vue'
import WriteDebate from '../views/board/WriteDebate.vue'
import ViewDebate from '../views/board/ViewDebate.vue'
import UserInfo from '../views/user/UserInfo.vue'
import FreeboardList from '../views/board/freeboard/FreeboardList.vue'
import FreeboardView from '../views/board/freeboard/FreeboardView.vue'
import MemoBoardList from '@/views/board/memo/MemoBoardList.vue'
import BoardList from '../views/board/BoardList.vue'
import WriteBoard from '../views/board/freeboard/WriteBoard.vue'
import ModifyBoard from '../views/board/freeboard/ModifyBoard.vue'
import BoardListCover from '../views/board/BoardListCover.vue'
import PrivateInfo from '../views/frame/DebatePrivateInfo.vue'
import DebateUserTerm from '../views/frame/DebateUseTerm.vue'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'HomePage',
    component: HomePage
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: LoginPage
  },
  {
    path: '/login/oauth_google',
    name: 'SignWithGoogle',
    props: true,
    component: SignWithGoogle
  },
  {
    path: '/login/oauth_facebook',
    name: 'SignWithFacebook',
    props: true,
    component: SignWithFacebook
  },
  {
    path: '/register',
    name: 'RegisterPage',
    component: RegisterPage
  },
  {
    path: '/emailSendNotice',
    name: 'EmailSendNotice',
    component: EmailSendNotice
  },
  {
    path: '/write/:param',
    name: 'WriteDebate',
    props: true,
    component: WriteDebate
  },
  {
    path: '/view/:id',
    name: 'ViewDebate',
    props: true,
    component: ViewDebate
  },
  {
    path: '/user',
    name: 'UserInfo',
    component: UserInfo
  },
  {
    path: '/freeboardList/:id',
    name: 'FreeboardList',
    props: true,
    component: FreeboardList
  },
  {
    path: '/freeboardView/:id',
    name: 'FreeboardView',
    component: FreeboardView
  },
  {
    path: '/memoBoardList',
    name: 'MemoBoardList',
    component: MemoBoardList
  },
  {
    path: '/boardList/:id',
    name: 'BoardList',
    props: true,
    component: BoardList
  },
  {
    path: '/writeBoard/:param',
    name: 'WriteBoard',
    props: true,
    component: WriteBoard
  },
  {
    path: '/modifyBoard/:param/:id',
    name: 'ModifyBoard',
    props: true,
    component: ModifyBoard
  },
  {
    path: '/boardListCover/:id',
    name: 'BoardListCover',
    props: true,
    component: BoardListCover
  },
  {
    path: '/privateInfo',
    name: 'PrivateInfo',
    component: PrivateInfo
  },
  {
    path: '/debateUseTerm',
    name: 'DebateUserTerm',
    component: DebateUserTerm
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
  }
})

export default router
