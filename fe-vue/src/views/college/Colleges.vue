<template>
  <el-container>
    <el-header>Вузы России</el-header>
    <el-container>
      <el-main>
        <el-input
          placeholder="Введите название учебного заведения"
          style="margin-bottom: 30px"
        ></el-input>
        <div v-for="college in colleges" :key="college.id">
          <el-card>
            <el-row>
              <el-col class="name-row">
                <router-link :to="{name: 'college', params: {id: college.id}}" class="name">
                  {{college.name}}
                </router-link>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2" :xs="24">
                <img src="images/logo/msu.png"/>
              </el-col>
              <div class="content">
                <el-col :offset="2" :span="4" :xs="6" class="city">
                {{college.city}}
                </el-col>
                <el-col :offset="2" :span="6" :xs="14" class="specs">
                  Образовательных программ: 20 
                </el-col>
              </div>
            </el-row>
            <el-row>
              <el-col class="question">
                <el-button type="text">Задать вопрос</el-button>
              </el-col>
            </el-row>
          </el-card>
        </div>
        <el-pagination
        background
        layout="prev, pager, next"
        pager-count="5"
        v-bind:page-size="pageSize"
        v-bind:total="pageSize * pageAmount"
        @current-change="nextPage"
        v-loading.fullscreen.lock="screenLock"
        ></el-pagination>
      </el-main>
    </el-container>

  </el-container>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import College from "@/types/College";
import Axios,{ AxiosResponse, AxiosError } from 'axios';

@Component
export default class Colleges extends Vue {
  
  search: string = "";

  colleges: College[] = [];

  pageSize: number = 20;

  pageAmount: number = 20;

  screenLock: boolean = false;

  created() {
    this.getColleges(this.pageSize, 0);
  }

  nextPage(pageNumber: number){
    this.screenLock = true;
    this.getColleges(this.pageSize, this.pageSize * pageNumber);
  }

  getColleges(limit: number, offset: number){
    let url = `api/public/colleges?limit=${limit}&offset=${offset}`;
    Axios.get(url)
    .then((response: AxiosResponse) => {
      this.colleges = response.data
      window.scrollTo(0, 0);
      this.screenLock = false;
    })
    .catch((e: AxiosError) => {  
      this.screenLock = false;
      this.$message.error('Упс, что-то пошло не так');
    })
  }
}
</script>

<style scoped>
.el-card{
  margin-bottom: 20px;
}

.el-col{
  margin-bottom: 10px;
}

.name-row{
  text-align: start;
}

.name{
  font-size: 0.9em;
  color: #409EFF;
}

a{
  text-decoration: none;
}

.city{
  font-size: 0.75em;
  text-align: start;
  font-style: italic;
  color: #909399;
}

.specs{
  font-size: 0.75em;
  text-align: start;
  color: #909399;
}

.content{
  margin-top: 20px;
}

.question{  
  text-align: end;
  margin-bottom: 0;
}
</style>
