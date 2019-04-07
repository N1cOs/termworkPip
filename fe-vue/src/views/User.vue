<template>
  <el-container v-loading.fullscreen.lock="screenLock">
    <div class="info">
      <div class="name">
        {{user.surname}} {{user.name}} {{user.patronymic}}
      </div>
      <div class="birth-date">
        {{user.birthDate}}
      </div>
      <el-main>
        <el-row class="scores">
          <el-col :md="12" :sm="24" :xs="24" class="exams">
            <el-card>
              <div class="exams-title">
                Мои экзамены
              </div>
              <div v-for="(exam, i) in user.exams" :key="exam.id" class="exam-row">
                <span style="float: left;">{{exam.name}} : {{exam.score}}</span>
                <el-button class="delete-button" icon="el-icon-delete" @click="deleteExistingExam(i)" circle></el-button>
              </div>
              <el-form :model="examForm" ref="examForm">
                <el-form-item v-for="(exam, index) in examForm.exams" :key="exam.subjectId" style="text-align: start;">
                  <span class="subject-name">
                    <el-select v-model="exam.subjectId" :disabled="disabledSelects[index]" @change="selectedNewExam">
                    <el-option
                      v-for="(subj, i) in subjects"
                      :key="subj.id"
                      :label="subj.name"
                      :value="subj.id"
                      :disabled="disabledSubjects[i]"
                    ></el-option>
                  </el-select>
                  </span>
                  <span class="score-input">
                    <el-input v-model="exam.score" placeholder="Введите количество баллов"></el-input>
                  </span>
                  <el-button class="delete-button" icon="el-icon-delete" @click="deleteNewExam(index)" circle></el-button>
                </el-form-item>
              </el-form>
              <div>
                <el-button class="addButton" @click="addExam">Добавить экзамен</el-button>
                <el-button class="saveButton" @click="submitExams('examForm')">Сохранить</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </div>
  </el-container>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import Subject from "@/types/Subject";
  import Axios, {AxiosResponse, AxiosError, AxiosPromise} from "axios";
  import Olympiad from "@/types/Olympiad";
  import UserInfo from "@/types/User";
  import Exam from '../types/Exam';
  import Error from '../types/InputError';

  @Component

  export default class User extends Vue {
    user: UserInfo = {} as UserInfo;

    subjects: Subject[] = [];

    //subjectId, position
    subjectPosition: Map<number, number> = new Map();

    disabledSubjects: boolean[] = [];

    disabledSelects: boolean[] = [];

    examForm: any = {
      exams: []
    }

    deletedExams: Set<number> = new Set();

    screenLock: boolean = false;

    async created(){
      await this.getMe()
        .then((response: AxiosResponse) => {
          this.user = response.data;
        })
        .catch((e: AxiosError) => {

        });

      let subjectUrl = "/api/public/subjects";
      Axios.get(subjectUrl)
        .then((response: AxiosResponse) => {
          this.subjects = response.data;
          
          let exams: Exam[] = [];
          if(this.user.exams != undefined){
            exams = this.user.exams;
          }

          this.subjects.forEach((s, i) => {
            this.subjectPosition.set(s.id, i);

            let exist = false;
            for(let i = 0; i < exams.length; i++){
              if(exams[i].id == s.id){
                exist = true;
                break;
              }
            }

            if(!exist){
              this.disabledSubjects.push(false);
            }
            else{
              this.disabledSubjects.push(true)
            }
          });
         
        })
        .catch((e: AxiosError) => {

        })
    }

    getMe(): AxiosPromise{
      let meUrl = "/api/me";
      return this.getSecuredResource(meUrl);
    }

  
    getSecuredResource(url: string): AxiosPromise{
      let config = {
        headers: {
          Authorization: this.$store.state.token
        }
      };

      return Axios.get(url, config);
    }

    addExam(){
      this.examForm.exams.push({
        score: 0
      })
      this.disabledSelects.push(false);
    }

    deleteNewExam(examIndex: number){
      let position = this.subjectPosition.get(this.examForm.exams[examIndex].subjectId);
      if(position != undefined){
        this.disabledSubjects[position] = false;
      }
      this.examForm.exams.splice(examIndex, 1);
      this.disabledSelects.splice(examIndex, 1);
    }

    deleteExistingExam(examIndex: number){
      if(this.user.exams != undefined){
        let subject = {
          id: this.user.exams[examIndex].id, 
          name: this.user.exams[examIndex].name
        };

        let position = this.subjectPosition.get(subject.id);
        if(position != undefined){
          this.disabledSubjects[position] = false;
        }
        
        this.deletedExams.add(this.user.exams[examIndex].id); 
        this.user.exams.splice(examIndex, 1);
      }
    }

    selectedNewExam(subjectId: number){
      let position = this.subjectPosition.get(subjectId);
      if(position != undefined){
        this.disabledSubjects[position] = true;
      }
    }

    submitExams(formName: string){
      (this.$refs[formName] as any).validate((valid : any) => {
        if(valid){
          this.screenLock = true;
          let url = "/api/student/exams";
          

          this.examForm.exams.forEach((e: any) => {
            this.deletedExams.forEach(de => {
              if(e.subjectId == de){
                this.deletedExams.delete(de);
              }
            });
          })
          
          if(this.deletedExams.size > 0){
            let config = {
              headers: {
                'Authorization': this.$store.state.token
              },
              data: Array.from(this.deletedExams)
            };

            Axios.delete(url, config)
              .then((response: AxiosResponse) => {
                this.saveExams();
              })
              .catch((error: AxiosError) => {
                this.getMe()
                  .then((response: AxiosResponse) => {
                    this.user = response.data;
                  })
                  .catch((error: AxiosError) => {

                  });
                if(error.response != undefined){
                  let e = error.response.data as Error;
                  this.$message({
                    message: e.info,
                    type: 'error'
                  })
                }
                this.screenLock = false;  
              });
          }
          else{
            this.saveExams();
          }
        }
        else{
          return false;
        }
      })
    }

    saveExams(){
      let url = "/api/student/exams";
        let config = {
          headers: {
            'Authorization': this.$store.state.token
        }
      };
      if(this.examForm.exams.length > 0){
        Axios.post(url, this.examForm.exams, config)
        .then((response: AxiosResponse) => {
          this.getMe()
            .then((response: AxiosResponse) => {
              this.user = response.data;
              this.$message({
                message: 'Информация об экзаменах успешно обновлена',
                type: 'success'
              });
              this.screenLock = false;
              this.examForm.exams = [];
          })
          .catch((e: AxiosError) => {

          });
        })
        .catch((error: AxiosError) => {
          this.$message({
            message: 'При выполнении запроса произошла ошибка, информация не обновлена',
            type: 'error'
          });
          this.screenLock = false;
        });
      }
      else{
        this.screenLock = false;
        this.$message({
          message: 'Информация об экзаменах успешно обновлена',
          type: 'success'
        });
      }
    }

  }  
</script>

<style scoped>
a{
  text-decoration: none;
}

.el-card{
  padding: 10px;
}

.info{
  width: 100%;
  text-align: end;
  color: #303133; 
}

.name{
  font-size: 1.1em;
  font-weight: bold;
}

.birth-date{
  margin-top: 5px;
  font-style: italic; 
}

.exams-title{
  font-size: 1.1em;
  color: #409EFF;
  font-weight: bold;
  text-align: center;
  margin-bottom: 5px;
}

.exam-row{
  margin-bottom: 5px;
  text-align: start;
  font-style: italic;
  color: #909399;
  height: 40px;
}

.delete-button{
  float: right;
}

.subject-name{
  float: left;   
}

.score-input{
  float: left;
  width: 20%;
}

.addButton{
  float: left;
}

.saveButton{
  float: right;
}
</style>