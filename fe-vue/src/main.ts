import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import '@/styles/index.scss';
import Bootstrap from 'bootstrap-vue';
import locale from 'element-ui/lib/locale/lang/ru-RU';
import infiniteScroll from 'vue-infinite-scroll';

Vue.config.productionTip = false;

Vue.use(ElementUI, {locale});
Vue.use(Bootstrap);
Vue.use(infiniteScroll);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
