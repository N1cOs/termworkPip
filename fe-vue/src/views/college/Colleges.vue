<template>
  <el-container>
    <el-header> Colleges</el-header>
    <el-container>
      <el-header> Type name of college you are looking for</el-header>
      <el-main>
        <el-input
          placeholder="type college name"
          style="margin-bottom: 30px"
        ></el-input>
        <el-card>
          <div slot="header" class="clearfix">
            <span>Card header</span>
          </div>
          <div v-for="college in colleges" :key="college.id">
            <router-link
              :to="'/colleges/'+college.id"
              >{{ college.name }}</router-link
            >
          </div>
        </el-card>
      </el-main>
    </el-container>

  </el-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import College from "@/types/College";
import {mockCollege} from "@/mock/Colleges";

@Component
export default class Colleges extends Vue {
  search: string = "";

  colleges: College[] =mockCollege;
}

Vue.component("Colleges", Colleges);
</script>
