<template>
  <div class="navbar-wrapper">
  
    <div>
      <router-link :to="{name: 'colleges'}">
        <img src="@/assets/vuz.png" alt="">
      </router-link>
    </div>
    <div  v-if="this.$store.state.token !== ''">
      <router-link :to="{name: 'user'}">
        <img src="@/assets/person.png" alt="">
      </router-link>
    </div>
    <div v-if="this.$store.state.token === ''">
      <router-link :to="{name: 'signIn'}">
        <img src="@/assets/sign-in.svg" alt="">
      </router-link>
    </div>
    <div v-if="this.$store.state.token !== ''" @click="signOut">
      <a><img src="@/assets/sign-out-option.svg" alt=""></a>
    </div>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';

  @Component
    export default class NavBar extends Vue {

      created(){
        console.log(this.$router.currentRoute);
      }

      signOut(){
        this.$store.state.token = '';
        this.$router.push('/sign-in');
      }
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

    & img {
      width: 50px;
      height: 50px;
    }

  }

</style>