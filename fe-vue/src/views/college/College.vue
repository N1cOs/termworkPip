<template>
  <el-container v-infinite-scroll="loadSpecialities" :infinite-scroll-disabled="loading">
    <el-main>
      <el-card class="header">
        {{college.name}}
      </el-card>
      <el-card v-if="college.description" class="info">
        <div slot="header" class="header">
          <span>Описание</span>
        </div>
        <el-row class="description">
          <el-col>
            {{college.description}}
          </el-col>
        </el-row>
      </el-card>
      <el-card>
        <div slot="header" class="header">
          <span>Дополнительные баллы к ЕГЭ</span>
        </div>
        <el-row :gutter="15">
          <el-col v-for="(achScore) in college.achievementsScore" :key="achScore.achievement.id" :md="8" :sm="12" :xs="24">
             <div class="achievement">
               <div class="ach-name">{{achScore.achievement.name}}</div>
               <el-tooltip v-bind:content="`${achScore.score} баллов`" placement="top">
                <el-progress type="circle" color="#67C23A" :percentage="achScore.score * 10" :show-text="false"></el-progress>
               </el-tooltip>
             </div>
          </el-col>
        </el-row>
      </el-card>
      <el-card>
        <div slot="header" class="header">
          <span>Направления подготовки</span>
        </div>
        <div>
          <div v-for="spec in specialities" :key="spec.id + '1'" class="spec hidden-xs-only">
            <el-row class="spec-name-holder">
              <router-link :to="{name: 'speciality', params: {id: spec.id}}" class="spec-name">
                {{spec.name}}
              </router-link>
            </el-row>
            <el-row>
              <el-col v-for="req in spec.requirements" :key="req.subject.id + '11'" :span="24 / spec.requirements.length" class="subject-name">
                {{req.subject.name}}
              </el-col>
            </el-row>
            <el-row class="score-title">
              Минимальные баллы:
            </el-row>
            <el-row>
              <el-col v-for="req in spec.requirements" :key="req.subject.id + '12'" :span="24 / spec.requirements.length" class="subject-name">
                {{req.minScore}}
              </el-col>
            </el-row>
            <el-row class="score-title">
              Минимальный уровень олимпиады:
            </el-row>
            <el-row>
              <el-col v-for="req in spec.requirements" :key="req.subject.id + '13'" :span="24 / spec.requirements.length" class="subject-name">
                {{req.minLevelOfOlympiad}}
              </el-col>
            </el-row>
          </div>
          <div v-for="spec in specialities" :key="spec.id + '2'" class="spec hidden-sm-and-up">
            <el-row class="spec-name-holder">
              <router-link :to="{name: 'speciality', params: {id: spec.id}}" class="spec-name">
                {{spec.name}}
              </router-link>
            </el-row>
            <el-row class="score-title-mobile">
              Минимальные баллы:
            </el-row>
            <el-row v-for="req in spec.requirements" :key="req.subject.id + '21'">
              <el-col :span="12" class="subject-name-mobile">
                {{req.subject.name}}
              </el-col>
              <el-col :span="12" class="subject-name">
                {{req.minScore}}
              </el-col>
            </el-row>
            <el-row class="score-title-mobile">
              Минимальный уровень олимпиады:
            </el-row>
            <el-row v-for="req in spec.requirements" :key="req.subject.id + '22'">
              <el-col :span="12" class="subject-name-mobile">
                {{req.subject.name}}
              </el-col>
              <el-col :span="12" class="subject-name">
                {{req.minLevelOfOlympiad}}
              </el-col>
            </el-row>
          </div>
        </div>
      </el-card>
    </el-main>
  </el-container>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator";
  import College from "@/types/College";
  import SpecialitiesResponse from "@/types/SpecialitiesResponse";
  import Speciality from "@/types/Speciality";
  import Axios, { AxiosResponse, AxiosError, AxiosPromise } from 'axios';

  @Component
  export default class Colleges extends Vue {
    
    college: College = {} as College;

    specialities: Speciality[] = [];

    loading: boolean = false;

    offset: number = 0;

    specAmount: number = -1;

    created(){
      let collegeUrl = "/api/public" + this.$router.currentRoute.fullPath;
      Axios.get(collegeUrl)
        .then((response: AxiosResponse) => {
          this.college = response.data;
        })
        .catch((e: AxiosError) => {

        })
    }

    getSpecs(limit: number = 20, offset: number = 0): AxiosPromise{
      let id = this.$router.currentRoute.params.id;
      let url = `/api/public/specialities?collegeId=${id}&limit=${limit}&offset=${offset}`; 
      return Axios.get(url);
    }

    loadSpecialities(){
      if(this.specialities.length != this.specAmount){
        this.loading = true;
        this.getSpecs(20, this.offset)
        .then((response: AxiosResponse) => {
            let data = response.data as SpecialitiesResponse;
            data.specialities.forEach( s => this.specialities.push(s));
            this.specAmount = data.amount;
            this.offset += 20;
            this.loading = false;
          })
          .catch((e: AxiosError) => {
            
          })
      }
    }
  }
</script>

<style scoped>
.el-card{
  margin-bottom: 20px;
}

.header{
  color: #409EFF;
  font-size: 1.1em;
  font-weight: bold;
}

.description{
  margin-top: 10px;
  text-align: justify;
  color: #909399;
  font-style: italic;
  font-size: 0.9em;
}

.achievement{
  margin-top: 15px;
  margin-bottom: 15px;
  border: solid 2px #DCDFE6;
}

.ach-name{
  height: 50px;
  font-size: 0.7em;
  color: #909399; 
  font-weight: bold;
  margin-top: 5px;
  margin-bottom: 25px;
  border-bottom: solid 2px #DCDFE6;
}

.spec{
  border: solid 2px #DCDFE6;
  padding: 10px;
  margin-bottom: 10px;
}

.spec-name{
  font-size: 1.05em;
  color: #409EFF;
}

.spec-name-holder{
  text-align: start;
}

a{
  text-decoration: none;
}

.subject-name{
  margin-top: 10px;
  color: #303133;
  font-weight: bold;
  font-size: 0.9em;
}

.score-title{
  margin-top: 5px; 
  text-align: start;
  color: #303133;
  font-style: italic;
  font-size: 0.9em;
}

.subject-name-mobile{
  text-align: start;
  margin-top: 10px;
  color: #303133;
  font-weight: bold;
  font-size: 0.9em;
}

.score-title-mobile{
  margin-top: 10px; 
  text-align: start;
  color: #303133;
  font-style: italic;
  font-size: 0.9em;
  border-bottom: solid 2px black;
}
</style>
