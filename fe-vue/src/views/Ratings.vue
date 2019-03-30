<template>

  <el-container>
    <el-header>
      Speciality id{{this.$route.params.specId}}
    </el-header>
    <el-row>
      <el-col :span="6">
        Rating place:
        {{ "som e code" }}
      </el-col>
      <el-col :span="12" :offset="6">
        <button>Calculate chances</button>
        <button>Send documents</button>
      </el-col>
    </el-row>
    <el-container>
      Subjects needed for applying to this speciality:
      <!--<div v-if="speciality!={}">-->
      <br>
      <div v-for="subject in speciality.subjects" :key="subject.id">
        {{subject.subjectName }} ( {{subject.minimalPoints}} )
      </div>
      <!--</div>-->
    </el-container>
  </el-container>

</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator";
  import RatingRecord from "@/types/RatingRecord";
  import Speciality from "@/types/Speciality";
  import {spec} from "@/mock/subjectspecialitymock";

  @Component
  export default class Ratings extends Vue {
    ratings?: RatingRecord[]
    speciality?: Speciality = spec

  }
</script>

<style scoped></style>
