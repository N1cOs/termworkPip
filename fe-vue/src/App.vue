<template>
  <div id="app">
    <nav-bar></nav-bar>
    <router-view/>
  </div>
</template>


<script lang="ts">
  import {Component, Vue} from "vue-property-decorator";
  import NavBar from '@/components/NavBar.vue';
  @Component({
    components: {NavBar}
  })
  export default class App extends Vue {
  }
</script>

<style>

  html {
    box-sizing: border-box;
    height: 100%;
  }
  body {
    margin: 0;
    height: inherit;
  }
  *, *:before, *:after {
    box-sizing: inherit;
  }

  #app {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }
</style>
