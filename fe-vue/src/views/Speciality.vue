<template>
  <div style="margin-top: 20px;">
    <h1>{{specialityInfo.okso}}</h1>
    <h2>{{specialityInfo.name}}</h2>
    <div style="margin-top: 30px; font-weight: 800; font-size: 22px; text-align: start; padding: 5px;">
      Требуемые экзамены <br><span style="font-weight: 600; font-style: italic">(минимальное количество баллов, минимальный уровень олимпиады):</span>
    </div>

    <div class="spec-subjects-info">
      <div v-for="req in specialityInfo.requirements" :key="req.id">
       <span style="font-weight: bold">
                 {{req.subject.name}}
       </span>
        <br>
        <span style="font-style: italic">
                  ({{req.minScore+", "+req.minLevelOfOlympiad}})
        </span>
      </div>
    </div>
    <Ratings :specId="this.$route.params.id"></Ratings>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import Ratings from '@/views/Ratings.vue';
  import Axios from "axios";

  @Component({
    components: {
      Ratings
    }
  })
  export default class Speciality extends Vue {
    specialityInfo: Object = {};

    created() {
      this.getSpecialityInfo();
    }

    getSpecialityInfo() {
      Axios.get("/api/public/specialities/" + this.$route.params.id)
        .then(res => this.specialityInfo = res.data);
    }
  }
</script>

<style lang="scss" scoped>
  .spec-subjects-info {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin: 20px 200px;

    & div {
      width: auto;
    }
  }
</style>