import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    contentData: {
      currentBoard: {},
      history: '',
      prevScrollHeight: '',
      boadDvsn: '',
      page: 0,
      boardCount: 0
    },
    userInfo: {},
    modalMemberInfo: {},
    boardList: []
  },
  getters: {
    getContentData (state) {
      return state.contentData
    },
    getUserInfo (state) {
      return state.userInfo
    },
    getModalMemberInfo (state) {
      return state.modalMemberInfo
    },
    getBoardList (state) {
      return state.boardList
    }
  },
  mutations: {
    changeContentData (state, contentData) {
      state.contentData = contentData
    },
    changeUserInfo (state, userInfo) {
      state.userInfo = userInfo
    },
    changeModalMemberInfo (state, modalMemberInfo) {
      state.modalMemberInfo = modalMemberInfo
    },
    changeBoardList (state, boardList) {
      state.boardList = boardList
    }
  },
  actions: {
    callMutation ({ state, commit }, contentData) {
      commit('changeContentData', contentData)
    },
    callUserInfo ({ state, commit }, userInfo) {
      commit('changeUserInfo', userInfo)
    },
    callModalMemberInfo ({ state, commit }, modalMemberInfo) {
      commit('changeModalMemberInfo', modalMemberInfo)
    },
    callBoardList ({ state, commit }, boardList) {
      commit('changeBoardList', boardList)
    }

  },
  modules: {
  }
})
