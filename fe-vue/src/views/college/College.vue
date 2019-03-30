<template>
  <div>
    <el-container>
      <el-header v-model="college"> {{ college.name }}</el-header>
      <el-container>
        <el-header>
          Common information
        </el-header>
        {{ college.description }}
      </el-container>
    </el-container>
    <el-container>
      <el-header>Specialities</el-header>
      <el-input></el-input>
      <div v-for="speciality in specialities" :key="speciality.id">
        {{speciality.name}}
      </div>
    </el-container>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator";
  import College from "@/types/College";
  import {mockCollege} from "@/mock/Colleges";
  import Speciality from "@/types/Speciality";
  import {mockSpecialities} from "@/mock/Specialities";
  import { Watch} from "vue-property-decorator";


  @Component
  export default class Colleges extends Vue {
    //   colleges: College[] = mockCollege;
    //   college?: College = {
    //       id: 1,
    //       name: "0",
    //       description: ""
    //   };

    // specialities: Speciality[] = mockSpecialities

    // @Watch('$route.params.id')
    // setCollege(){
    //   this.college = this.colleges.find(
    //     college => college.id === parseInt(this.$route.params.id)
    //   );
    // }

    // mounted() {
    //   this.setCollege()
    // }

  }
</script>

<style scoped></style>
