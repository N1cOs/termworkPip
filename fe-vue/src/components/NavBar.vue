<template>
  <div class="navbar-wrapper">
    <div>
      <router-link :to="{name: 'colleges'}"><img src="@/assets/vuz.png" alt=""></router-link>
    </div>
    <div>
      <router-link :to="{name: 'user'}"><img src="@/assets/person.png" alt=""></router-link>
    </div>
    <div>
      <router-link to="ratings"><img src="@/assets/rating.png" alt=""></router-link>
    </div>
  </div>
</template>

<script>
    import {Component, Vue} from 'vue-property-decorator';

    export default class NavBar extends Vue {

    }
</script>

<style lang="scss" scoped>
  .navbar-wrapper {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    height: 70px;
    background: #EEEEEE;

    & div {
      margin-right: 20px;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }
    & img{
      width: 50px;
      height: 50px;
    }

  }

</style>