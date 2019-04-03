<template>
  <div>
    <Ratings></Ratings>
  </div>
</template>

<script>
    import {Component, Vue} from 'vue-property-decorator';
    import Ratings from '@/views/Ratings.vue';

    @Component({
        components:{
            Ratings
        }
    })
    export default class Speciality extends Vue {

    }
    Vue.component("Speciality", Speciality);
</script>

<style scoped>

</style>