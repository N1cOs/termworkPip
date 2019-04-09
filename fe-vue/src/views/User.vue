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
        <el-row :gutter="20" class="scores">
          <el-col :md="12" :sm="24" :xs="24" class="exams">
            <el-card>
              <div slot="header" class="card-title">
                <span> Мои экзамены</span>
              </div>
              <div v-for="(exam, i) in user.exams" :key="exam.id" class="exam-row">
                <span style="float: left; width: 80%">{{exam.name}} : {{exam.score}}</span>
                <el-button class="delete-button" icon="el-icon-delete" @click="deleteExistingExam(i)"
                           circle></el-button>
              </div>
              <el-form :model="examForm" ref="examForm">
                <div v-for="(exam, index) in examForm.exams" :key="exam.subjectId">
                  <el-form-item
                    :rules="{
                    required: true, message: 'Выберите экзамен', trigger: 'blur'
                  }"
                    class="subject-name"
                    :prop="'exams.' + index + '.subjectId'"
                  >
                    <el-select v-model="exam.subjectId" @change="selectedNewExam">
                      <el-option
                        v-for="(subj, i) in subjects"
                        :key="subj.id"
                        :label="subj.name"
                        :value="subj.id"
                        :disabled="disabledSubjects[i]"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item
                    :rules="[
                    {required: true, message: 'Введите количество баллов', trigger: 'blur'},
                    {
                      type: 'number', min: 1, max: 100, 
                      message: 'Количество баллов должно быть больше 0, но не больше 100', 
                      trigger: 'blur'
                    }
                  ]"
                    class="score-input"
                    :prop="'exams.' + index + '.score'"
                  >
                    <el-input v-model.number="exam.score" type="number"
                              placeholder="Введите количество баллов"></el-input>
                  </el-form-item>
                  <el-button class="delete-button" icon="el-icon-delete" @click="deleteNewExam(index)"
                             circle></el-button>
                </div>
                <div>
                  <el-button class="addButton" @click="addExam">Добавить экзамен</el-button>
                  <el-button class="saveButton" @click="submitExams('examForm')">Сохранить</el-button>
                </div>
              </el-form>
            </el-card>
          </el-col>
          <el-col :md="12" :sm="24" :xs="24" class="exams">
            <el-card>
              <div slot="header" class="card-title">
                <span>Мои индивидуальные достижения</span>
              </div>
              <div v-for="(ach, i) in user.achievements" :key="ach.id" class="ach-row">
                <span style="float: left; width: 80%">{{ach.name}}</span>
                <el-button class="delete-button" icon="el-icon-delete" @click="deleteExistingAch(i)" circle></el-button>
              </div>
              <el-form :model="achievementForm" ref="achievementForm">
                <el-form-item v-for="(ach, index) in achievementForm.achievements" :key="ach.key"
                              :rules="{
                  required: true, message: 'Выберите достижение', trigger: 'blur'
                }"
                              :prop="'achievements.' + index + '.id'"
                >
                  <span class="subject-name">
                    <el-select v-model="ach.id" @change="selectedNewAch">
                    <el-option
                      v-for="(a, i) in achievements"
                      :key="a.id"
                      :label="a.name"
                      :value="a.id"
                      :disabled="disabledAchievements[i]"
                    ></el-option>
                  </el-select>
                  </span>
                  <el-button class="delete-button" icon="el-icon-delete" @click="deleteNewAchievement(index)"
                             circle></el-button>
                </el-form-item>
              </el-form>
              <div>
                <el-button class="addButton" @click="addAchievement">Добавить достижение</el-button>
                <el-button class="saveButton" @click="submitAchievements('achievementForm')">Сохранить</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <div class="note">
          <div>
            *Обведенная строка означает, что на данную специальность поданы оригиналы документов
          </div>
          <div>
            **Подсвеченная зелёным строка означает, что вы проходите на эту специальность по оригиналам на данный момент
          </div>
        </div>
        <el-card class="ratings">
          <div slot="header" class="card-title">
            <span>Мои рейтинги</span>
          </div>
          <el-row class="rating-header hidden-sm-and-down">
            <el-col :span="7">
              Специальность
            </el-col>
            <el-col :span="4">
              Университет
            </el-col>
            <el-col :span="5">
              Количество баллов
            </el-col>
            <el-col :span="4">
              Место по копиям
            </el-col>
            <el-col :span="4">
              Место по оригиналам
            </el-col>
          </el-row>
          <el-row v-for="rating in user.ratings" :key="rating.speciality.id + '1'"
                  :class="{'rating-row' : true, 'originals': rating.originals, 
                  'success-speciality': rating.success, 'hidden-sm-and-down' : true}">
            <el-col :span="7">
              <router-link :to="{name: 'speciality', params: {id: rating.speciality.id}}">
                {{rating.speciality.okso}}
              </router-link>
            </el-col>
            <el-col :span="4">
              <router-link :to="{name: 'college', params: {id: rating.speciality.college.id}}">
                {{rating.speciality.college.abbreviation}}
              </router-link>
            </el-col>
            <el-col :span="5">
              {{rating.totalScore}}
            </el-col>
            <el-col :span="4">
              {{rating.place}}
            </el-col>
            <el-col :span="4">
              {{rating.placeOriginal}}
            </el-col>
          </el-row>
          <el-row v-for="rating in user.ratings" :key="rating.speciality.id + '2'"
                  :class="{'rating-row' : true, 'originals': rating.originals, 
                  'success-speciality': rating.success, 'hidden-md-and-up' : true}">
            <el-row class="row-mobile">
              <el-col :span="12" class="col-name">
                Специальность
              </el-col>
              <el-col :span="12">
                <router-link :to="{name: 'speciality', params: {id: rating.speciality.id}}">
                  {{rating.speciality.okso}}
                </router-link>
              </el-col>
            </el-row>
            <el-row class="row-mobile">
              <el-col :span="12" class="col-name">
                Университет
              </el-col>
              <el-col :span="12">
                <router-link :to="{name: 'college', params: {id: rating.speciality.college.id}}">
                  {{rating.speciality.college.abbreviation}}
                </router-link>
              </el-col>
            </el-row>
            <el-row class="row-mobile">
              <el-col :span="12" class="col-name">
                Количество баллов
              </el-col>
              <el-col :span="12">
                {{rating.totalScore}}
              </el-col>
            </el-row>
            <el-row class="row-mobile">
              <el-col :span="12" class="col-name">
                Место по копиям
              </el-col>
              <el-col :span="12">
                {{rating.place}}
              </el-col>
            </el-row>
            <el-row class="row-mobile">
              <el-col :span="12" class="col-name">
                Место по оригиналам
              </el-col>
              <el-col :span="12">
                {{rating.placeOriginal}}
              </el-col>
            </el-row>
          </el-row>
        </el-card>
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
  import Achievement from '../types/Achievement';

  @Component

  export default class User extends Vue {

    user: UserInfo = {} as UserInfo;

    subjects: Subject[] = [];

    achievements: Achievement[] = [];

    //subjectId, position
    subjectPosition: Map<number, number> = new Map();

    disabledSubjects: boolean[] = [];

    examForm: any = {
      exams: []
    };

    deletedExams: Set<number> = new Set();

    //achievementId, position
    achievementPosition: Map<number, number> = new Map();

    disabledAchievements: boolean[] = [];

    achievementForm: any = {
      achievements: []
    };

    deletedAchievements: Set<number> = new Set();

    screenLock: boolean = false;

    created() {
      let subjectUrl = "/api/public/subjects";
      let achievementUrl = "/api/public/achievements";

      this.getMe()
        .then((response: AxiosResponse) => {
          this.user = response.data;

          Axios.get(subjectUrl)
            .then((response: AxiosResponse) => {
              this.subjects = response.data;
              this.resetSubjects();
            })
            .catch((e: AxiosError) => {

            });

          Axios.get(achievementUrl)
            .then((response: AxiosResponse) => {
              this.achievements = response.data;
              this.resetAchievements();
            })
            .catch((e: AxiosError) => {

            });
        })
        .catch((e: AxiosError) => {

        });
    }

    resetSubjects() {
      this.disabledSubjects = [];
      this.deletedExams.clear();
      let exams: Exam[] = [];
      if (this.user.exams != undefined) {
        exams = this.user.exams;
      }

      this.subjects.forEach((s, i) => {
        this.subjectPosition.set(s.id, i);

        let exist = false;
        for (let i = 0; i < exams.length; i++) {
          if (exams[i].id == s.id) {
            exist = true;
            break;
          }
        }

        this.disabledSubjects.push(exist);
      });
    }

    resetAchievements() {
      this.disabledAchievements = [];
      let achievements: Achievement[] = [];
      if (this.user.achievements != undefined) {
        achievements = this.user.achievements;
      }

      this.achievements.forEach((ach, i) => {
        this.achievementPosition.set(ach.id, i);

        let exist = false;
        for (let i = 0; i < achievements.length; i++) {
          if (achievements[i].id == ach.id) {
            exist = true;
            break;
          }
        }
        this.disabledAchievements.push(exist);
      });
    }

    getMe(): AxiosPromise {
      let meUrl = "/api/me";
      return this.getSecuredResource(meUrl);
    }


    getSecuredResource(url: string): AxiosPromise {
      let config = {
        headers: {
          Authorization: this.$store.state.token
        }
      };

      return Axios.get(url, config);
    }

    addExam() {
      this.examForm.exams.push({});
    }

    deleteNewExam(examIndex: number) {
      let position = this.subjectPosition.get(this.examForm.exams[examIndex].subjectId);
      if (position != undefined) {
        this.disabledSubjects[position] = false;
      }
      this.examForm.exams.splice(examIndex, 1);
    }

    deleteExistingExam(examIndex: number) {
      if (this.user.exams != undefined) {
        let subject = {
          id: this.user.exams[examIndex].id,
          name: this.user.exams[examIndex].name
        };

        let position = this.subjectPosition.get(subject.id);
        if (position != undefined) {
          this.disabledSubjects[position] = false;
        }

        this.deletedExams.add(this.user.exams[examIndex].id);
        this.user.exams.splice(examIndex, 1);
      }
    }

    selectedNewExam(subjectId: number) {
      let position = this.subjectPosition.get(subjectId);
      if (position != undefined) {
        this.disabledSubjects[position] = true;
      }
    }

    submitExams(formName: string) {
      (this.$refs[formName] as any).validate((valid: any) => {
        if (valid) {
          this.screenLock = true;
          let url = "/api/student/exams";


          this.examForm.exams.forEach((e: any) => {
            this.deletedExams.forEach(de => {
              if (e.subjectId == de) {
                this.deletedExams.delete(de);
              }
            });
          });

          if (this.deletedExams.size > 0) {
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
                    this.resetSubjects();
                  })
                  .catch((error: AxiosError) => {

                  });
                if (error.response != undefined) {
                  let e = error.response.data as Error;
                  this.$message({
                    message: e.info,
                    type: 'error'
                  });
                }
                this.screenLock = false;
              });
          } else {
            this.saveExams();
          }
        } else {
          return false;
        }
      });
    }

    saveExams() {
      let url = "/api/student/exams";
      let config = {
        headers: {
          'Authorization': this.$store.state.token
        }
      };
      if (this.examForm.exams.length > 0) {
        Axios.post(url, this.examForm.exams, config)
          .then((response: AxiosResponse) => {
            this.getMe()
              .then((response: AxiosResponse) => {
                this.user = response.data;
                this.$message({
                  message: 'Информация об экзаменах успешно обновлена',
                  type: 'success'
                });
                this.examForm.exams = [];
                this.screenLock = false;
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
      } else {
        this.screenLock = false;
        this.$message({
          message: 'Информация об экзаменах успешно обновлена',
          type: 'success'
        });
      }
    }

    addAchievement() {
      this.achievementForm.achievements.push({key: Date.now()});
    }

    deleteNewAchievement(achIndex: number) {
      let position = this.achievementPosition.get(this.achievementForm.achievements[achIndex].id);
      if (position != undefined) {
        this.disabledAchievements[position] = false;
      }
      this.achievementForm.achievements.splice(achIndex, 1);
    }

    deleteExistingAch(achIndex: number) {
      if (this.user.achievements != undefined) {
        let ach = {
          id: this.user.achievements[achIndex].id
        };

        let position = this.achievementPosition.get(ach.id);
        if (position != undefined) {
          this.disabledAchievements[position] = false;
        }

        this.deletedAchievements.add(ach.id);
        this.user.achievements.splice(achIndex, 1);
      }
    }

    selectedNewAch(achId: number) {
      let position = this.achievementPosition.get(achId);
      if (position != undefined) {
        this.disabledAchievements[position] = true;
      }
    }

    submitAchievements(formName: string) {
      (this.$refs[formName] as any).validate((valid: any) => {
        if (valid) {
          this.screenLock = true;
          let url = "/api/student/achievements";


          this.achievementForm.achievements.forEach((ach: any) => {
            this.deletedAchievements.forEach(dAch => {
              if (ach.id == dAch) {
                this.deletedAchievements.delete(dAch);
              }
            });
          });

          if (this.deletedAchievements.size > 0) {
            let config = {
              headers: {
                'Authorization': this.$store.state.token
              },
              data: Array.from(this.deletedAchievements)
            };

            Axios.delete(url, config)
              .then((response: AxiosResponse) => {
                this.saveAchievements();
              })
              .catch((error: AxiosError) => {
                this.getMe()
                  .then((response: AxiosResponse) => {
                    this.user = response.data;
                    this.resetAchievements();
                  })
                  .catch((error: AxiosError) => {

                  });
                if (error.response != undefined && error.response.data != undefined) {
                  let e = error.response.data as Error;
                  this.$message({
                    message: e.info,
                    type: 'error'
                  });
                } else {
                  this.$message({
                    message: 'При выполнении запроса произошла ошибка, информация не обновлена',
                    type: 'error'
                  });
                }
                this.achievementForm.achievements = [];
                this.screenLock = false;
              });
          } else {
            this.saveAchievements();
          }
        } else {
          return false;
        }
      });
    }

    saveAchievements() {
      let url = "/api/student/achievements";
      let config = {
        headers: {
          'Authorization': this.$store.state.token
        }
      };

      if (this.achievementForm.achievements.length > 0) {
        let data = this.achievementForm.achievements.map((a: any) => a.id);
        Axios.post(url, data, config)
          .then((response: AxiosResponse) => {
            this.getMe()
              .then((response: AxiosResponse) => {
                this.user = response.data;
                this.$message({
                  message: 'Информация о достижениях успешно обновлена',
                  type: 'success'
                });
                this.achievementForm.achievements = [];
                this.screenLock = false;
              })
              .catch((e: AxiosError) => {

              });
          })
          .catch((error: AxiosError) => {
            this.$message({
              message: 'При выполнении запроса произошла ошибка, информация не обновлена',
              type: 'error'
            });
            this.achievementForm.achievements = [];
            this.screenLock = false;
          });
      } else {
        this.screenLock = false;
        this.$message({
          message: 'Информация о достижениях успешно обновлена',
          type: 'success'
        });
      }
    }

  }
</script>

<style scoped>
  .el-card {
    padding: 10px;
  }

  .info {
    width: 100%;
    text-align: end;
    padding: 5px;
    color: #303133;
  }

  .name {
    font-size: 1.1em;
    font-weight: bold;
  }

  .birth-date {
    margin-top: 5px;
    font-style: italic;
  }

  .card-title {
    font-size: 1.1em;
    color: #409EFF;
    font-weight: bold;
    text-align: center;
    margin-bottom: 5px;
  }

  .exam-row {
    margin-bottom: 5px;
    text-align: start;
    font-style: italic;
    color: #909399;
    height: 40px;
  }

  .delete-button {
    float: right;
  }

  .subject-name {
    float: left;
    clear: left;
  }

  .score-input {
    float: left;
    width: 20%;
  }

  .addButton {
    font-size: 0.8em;
    clear: both;
    float: left;
  }

  .saveButton {
    font-size: 0.8em;
    float: right;
  }

  .ach-row {
    margin-bottom: 10px;
    text-align: start;
    font-style: italic;
    color: #909399;
    height: 50px;
  }

  .note {
    margin-top: 30px;
    margin-bottom: 5px;
    font-size: 0.65em;
    text-align: end;
  }

  .ratings {
    padding: 0;
  }

  .rating-header {
    margin-bottom: 10px;
    font-size: 0.95em;
    color: #909399;
    font-weight: bold;
    border-bottom: solid 2px #DCDFE6;
    text-align: center;
  }

  .rating-row {
    padding: 10px;
    font-size: 0.9em;
    color: #909399;
    margin-top: 25px;
    text-align: center;
  }

  a {
    text-decoration: none;
    color: #409EFF;
  }

  .originals {
    border: solid 3px #909399;
  }

  .success-speciality {
    background: rgba(80, 250, 58, 0.575);
  }

  .col-name{
    font-size: 0.95em;
    color: #909399;
    font-weight: bold;
  }

  .row-mobile{
    margin-top: 5px;
    border-bottom: solid 2px #909399; 
  }


  @media only screen and (max-width: 992px) {
    .el-card {
      padding: 10px;
      margin-top: 30px;
    }

    .note{
      margin-bottom: -30px;
    }
  }
</style>