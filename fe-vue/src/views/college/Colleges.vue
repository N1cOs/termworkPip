<template>
  <el-container>
    <el-header>Вузы России</el-header>
    <el-container>
      <el-main>
        <el-autocomplete
          placeholder="Введите название учебного заведения"
          style="margin-bottom: 30px; width: 100%"
          v-model="search"
          :fetch-suggestions="querySearch"
          @keydown.native.esc="restorePreviousColleges"
          @select="handleSelect"
        ></el-autocomplete>
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
                <img v-bind:src="college.logoUrl"/>
              </el-col>
              <div class="content">
                <el-col :offset="2" :span="4" :xs="6" class="city">
                {{college.city}}
                </el-col>
                <el-col :offset="2" :span="6" :xs="14" class="specs">
                  Бюджетных мест: {{college.places}}
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
        v-bind:pager-count="pagerCount"
        v-bind:page-size="pageSize"
        v-bind:total="pageSize * pageAmount"
        @current-change="nextPage"
        v-loading.fullscreen.lock="screenLock"
        v-bind:hidden="hiddenPagination"
        ></el-pagination>
      </el-main>
    </el-container>

  </el-container>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import College from "@/types/College";
import CollegesResponse from "@/types/CollegesResponse";
import Axios,{ AxiosResponse, AxiosError, AxiosPromise } from 'axios';

@Component
export default class Colleges extends Vue {

  search: string = "";
  
  colleges: College[] = [];

  previousColleges: College[] = [];

  fetchedColleges: Map<string, College[]> = new Map();

  pageSize: number = 20;

  pageAmount: number = 1;

  pagerCount: number = 5;

  screenLock: boolean = false;

  hiddenPagination: boolean = false;

  created() {
    this.getColleges(this.pageSize, 0)
      .then((response: AxiosResponse) => {
        let data = response.data as CollegesResponse;
        this.colleges = data.colleges;
        this.pageAmount = data.amount / this.pageSize - 1;
      })
      .catch((e: AxiosError) => {
        this.$message.error('Упс, что-то пошло не так');
      })
  }

  nextPage(pageNumber: number){
    this.screenLock = true;
    this.getColleges(this.pageSize, this.pageSize * (pageNumber - 1))
      .then((response: AxiosResponse) => {
        let data = response.data as CollegesResponse;
        this.colleges = data.colleges;
        window.scrollTo(0, 0);
        this.screenLock = false;
      })
      .catch((e: AxiosError) => {
        this.screenLock = false;
        this.$message.error('Упс, что-то пошло не так');
      })
  }

  querySearch(query: string, callback: any){
    const url = `api/public/colleges?query=${query}`;
    if(query.trim().length > 0){
      let colleges = this.fetchedColleges.get(query);
      if(colleges !== undefined){
        callback(this.getNamesFromColleges(colleges));
      }
      else{
        Axios.get(url)
        .then((response: AxiosResponse) => {
          let data = response.data as College[];
          this.fetchedColleges.set(query, data);
          callback(this.getNamesFromColleges(data));
        })
        .catch((e: AxiosError) => {

        })
      }
    }
    else{
      callback([]);
    }
  }

  handleSelect(query: any){
    this.screenLock = true;
    for(let colleges of this.fetchedColleges.values()){
      colleges.forEach(c =>{
        if(c.name === query.value){
          this.previousColleges = this.colleges;
          this.colleges = [c];
        }
      });
    }
    this.hiddenPagination = true;
    this.screenLock = false;
  }

  restorePreviousColleges(){
    if(this.previousColleges.length > 0 && 
      this.colleges != this.previousColleges &&
      this.colleges.length == 1){
      this.colleges = this.previousColleges;
      this.search = '';
      this.hiddenPagination = false;
    }
  }

  getNamesFromColleges(colleges: College[]): any[]{
    let names: any[] = [];
    colleges.forEach(c => {
      names.push({value: c.name});
    });
    return names;
  }

  getColleges(limit: number, offset: number): AxiosPromise{
    let url = `api/public/colleges?limit=${limit}&offset=${offset}`;
    return Axios.get(url);
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
