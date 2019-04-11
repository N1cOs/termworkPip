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

const router = new Router({
  // mode: 'history',
  routes: [
    {
      path: '/me',
      name: "user",
      component: User,
    },
    {
      path: "/speciality/:id",
      name: "speciality",
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

router.beforeEach((to, from, next) => {
  if (localStorage.getItem('token') !== '') {
    next();
  } else {
    if (to.name === 'signIn' || to.name === 'colleges' || to.name === 'signUp') {
      next();
    } else {
      next({
        name: 'signIn',
      })
    }
  }
});

export default router;