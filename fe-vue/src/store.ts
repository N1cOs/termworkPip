import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {},
  state: {
    token: '',
    colleges: [],
    userId: ''
  },
  mutations: {
    initialiseStore(state) {
      console.log(localStorage.getItem('token'), 'fafer');

      if (localStorage.getItem('token')!="") {
        state.token = JSON.parse(localStorage.getItem('token') || '{}');
        console.log(state.token, 'aaaa');
      }
    },
    saveToken(state, newToken){
      state.token = newToken;
    }
  },
  actions: {

  },
  plugins: [],
});

store.subscribe((mutation, state) => {
  console.log('hi from subscribe');
  localStorage.setItem('token', JSON.stringify(state.token));
});

export default store;