import Vue from "vue";
import Router from "vue-router";
import Login from "./views/Login.vue";
import Register from "./views/Register.vue";
import Main from "@/views/Main.vue";
import Colleges from "@/views/college/Colleges.vue";
import College from "@/views/college/College.vue";
import Ratings from "@/views/Ratings.vue";
import User from "@/views/User.vue";

Vue.use(Router);

export default new Router({
  // mode: 'history',
  routes: [
    {
      path: '/user/:id',
      name: "user",
      component: User,
    },
    {
      path: "/ratings/:specId",
      name: "ratings",
      component: Ratings
    },
    {
      path: "/login",
      name: "login",
      component: Login
    },
    {
      path: "/signup",
      name: "signup",
      component: Register
    },
    {
      path: "/",
      name: "main",
      component: Main
    },
    {
      path: "/colleges",
      name: "colleges",
      component: Colleges,
      children:[
        {
          path: "/college/:id",
          name: "colleges",
          component: College
        },
      ]
    },
  ]
});
