import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui'
import '@/styles/index.scss'
import Bootstrap from 'bootstrap-vue'


Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(Bootstrap);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
