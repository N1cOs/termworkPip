import Vue from "vue";
import Router from "vue-router";
import Login from "./views/Login.vue";
import Register from "./views/Register.vue";
import Main from "@/views/Main.vue";
import Colleges from "@/views/college/Colleges.vue";
import College from "@/views/college/College.vue";

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: "/l",
      name: "login",
      component: Login
    },
    {
      path: "/r",
      name: "reg",
      component: Register
    },
    {
      path: "/m",
      name: "main",
      component: Main
    },{
      path: "/colleges/:id",
      name: "colleges",
      component: College
    },
    {
      path: "/colleges",
      name: "colleges",
      component: Colleges,
      children:[

      ]
    }
  ]
});
