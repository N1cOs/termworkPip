<template>
  <div>
    <el-container>
      <el-header> {{ college.name }}</el-header>
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

    @Component
    export default class Colleges extends Vue {
        colleges: College[] = mockCollege;
        college: College = {
            id: 1,
            name: "0",
            description: ""
        };

        specialities: Speciality[] = mockSpecialities

        mounted() {
            // this.getCollege(this.$route.params.id);
            this.college = this.colleges.find(
                college => college.id === parseInt(this.$route.params.id)
            );


            // console.log(this.$route.params.id);
        }

        // getCollege(id: string) {
        //   this.college = this.colleges[parseInt(id) - 1];
        // }

        // findCollege(id: string){
        //
        // }
    }
</script>

<style scoped></style>
