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
              :to="'/college/'+college.id"
            >{{ college.name }}
            </router-link>
          </div>
        </el-card>
        <el-card>
          <router-view/>
        </el-card>
      </el-main>
    </el-container>

  </el-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import College from "@/types/College";
import Axios,{ AxiosResponse, AxiosError } from 'axios';

@Component
export default class Colleges extends Vue {

  readonly url: string = "/api/public/colleges";

  search: string = "";

  colleges?: College[]

  created() {
    Axios.get(this.url)
    .then((response: AxiosResponse) => {
      this.colleges = response.data
    })
    .catch((e: AxiosError) => {

    })
  }
}

Vue.component("Colleges", Colleges);
</script>
