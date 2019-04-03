import Vue from "vue";
import Router from "vue-router";
import SignIn from "./views/SignIn.vue";
import SignUp from "./views/SignUp.vue";
import Main from "@/views/Main.vue";
import Colleges from "@/views/college/Colleges.vue";
import College from "@/views/college/College.vue";
import User from "@/views/User.vue";
import Speciality from "@/views/Speciality.vue";

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
      path: "/spec/:specId",
      name: "specialities",
      component: Speciality
    },
    {
      path: "/sign-in",
      name: "signIn",
      component: SignIn
    },
    {
      path: "/sign-up",
      name: "signUp",
      component: SignUp
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
    },
    {
      path: "/colleges/:id",
      name: "college",
      component: College
    }
  ]
});
