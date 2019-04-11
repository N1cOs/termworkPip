<template>
  <el-card style="margin: auto;">
    <div slot="header" class="clearfix" style="text-align: center">
      <span>Регистрация</span>
    </div>
    <!--!!! don't forget to mention prop attribute to validate el-form-input with rules passed !!!-->
    <el-form
      :label-position="'left'"
      ref="form"
      :rules="rules"
      :model="form"
      label-width="120px"
    >
      <el-form-item label="Фамилия" prop="surname">
        <el-input v-model="form.surname" type=""></el-input>
      </el-form-item>
      <el-form-item label="Имя" prop="name">
        <el-input v-model="form.name" type=""></el-input>
      </el-form-item>
      <el-form-item label="Отчество" prop="">
        <el-input v-model="form.patronymic" type=""></el-input>
      </el-form-item>
      <el-form-item label="Дата рождения">
        <el-date-picker
          type="date"
          placeholder="Pick a date"
          v-model="form.birthDate"
          style="width: 100%;"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="Паспорт" prop="serialNumber">
        <el-input
          v-model="form.serialNumber"
          type=""
          placeholder="xxxx xxxxxx"
        ></el-input>
      </el-form-item>
      <el-form-item label="E-mail" prop="email">
        <el-input v-model="form.email" type="email"></el-input>
      </el-form-item>
      <el-form-item label="Телефон" prop="phone">
        <el-input v-model="form.phone" type=""></el-input>
      </el-form-item>
      <el-form-item label="Пароль" prop="password">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      <el-button style="margin-top: 30px;" type="primary" @click="submitForm('form')">Зарегестрироваться</el-button>
      <el-row :gutter="20">
        <el-col :md="8" :sm="16" :xs="16">
          <h3>Экзамен</h3>
          <el-button @click="addExam">Добавить экзамен</el-button>

          <el-form-item
            v-for="(exam, index) in form.exams"
            :key="exam.id"
            style="border: 1px solid #EEEEEE; padding: 5px; margin-top: 15px;"
          >
            <el-row>
              <el-row style="margin-top: 10px;">
                <el-form-item
                  :prop="'exams.'+index+'.subjectId'"
                  :rules="{ required: true, message: 'Выберите экзамен', trigger: 'blur' }"
                  style="width: 200px; margin: auto;  "
                >
                  <el-select
                    v-model="form.exams[index].subjectId"
                    @change="selectNewExam"
                  >
                    <el-option
                      v-for="(subj, ind) in subjectsList"
                      :key="subj.id"
                      :label="subj.name"
                      :value="subj.id"
                      :disabled="disabledExams[ind]"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-row>
              <el-row style="margin-top: 10px; margin-bottom: 20px;">
                <el-form-item
                  label="Количество баллов"
                  :prop="'exams.'+index+'.score'"
                  :rules="[
                  {required: true, message: 'Введите количество баллов', trigger: 'blur'},
                  {
                    type: 'number', min: 1, max: 100,
                    message: 'Количество баллов должно быть больше 0, но не больше 100',
                    trigger: 'blur'
                  }
                ]"
                  label-width="300"
                  style="width: 200px; margin: auto;"
                >
                  <el-input v-model.number="form.exams[index].score" type="number"
                            placeholder="Введите количество баллов за экзамен"/>
                </el-form-item>
              </el-row>
              <el-row>
                <el-button @click="removeExam(exam)" icon="el-icon-delete" circle/>
              </el-row>
            </el-row>
          </el-form-item>

        </el-col>
        <el-col :md="8" :sm="16" :xs="16">
          <h3>Олимпиады</h3>
          <el-button @click="addOlympiad">
            Добавить олимпиаду
          </el-button>
          <el-form-item
            v-for="(olympiad, index) in form.olympiadsId"
            :key="olympiad.id"
            style="width: 200px; margin: 20px auto; border: 1px solid #EEEEEE; padding: 5px"
          >
            <el-select
              v-model="form.olympiadsId[index]"
            >
              <el-option
                v-for="(olympiad) in olympiadsList"
                :key="olympiad.id"
                :label="olympiad.name"
                :value="olympiad.id"
              >
              </el-option>
            </el-select>
            <el-button @click="removeOlympiad(olympiad)" icon="el-icon-delete" style="margin: 10px auto;" circle/>
          </el-form-item>

        </el-col>
        <el-col :md="8" :sm="16" :xs="16">
          <h3>Индивидуальные достижения</h3>
          <el-button @click="addAchievement">
            Добавить достижние
          </el-button>
          <el-form-item
            v-for="(achievement, index) in form.achievementsId"
            :key="achievement.id"
            style="width: 200px; margin: 20px auto; border: 1px solid #EEEEEE; padding: 5px"
          >
            <el-select
              v-model="form.achievementsId[index]"
            >
              <el-option
                v-for="achievement in achievementsList"
                :key="achievement.id"
                :label="achievement.name"
                :value="achievement.id"
              >
              </el-option>
            </el-select>
            <el-button @click="removeAchievement(achievement)" icon="el-icon-delete" style="margin: 10px auto;" circle/>
          </el-form-item>

        </el-col>
      </el-row>

    </el-form>
  </el-card>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator";
  import Subject from "@/types/Subject";
  import Axios, {AxiosError, AxiosResponse} from 'axios';
  import Olympiad from "@/types/Olympiad";

  @Component
  export default class Register extends Vue {
    readonly url: string = "/api/public/sign-up";
    readonly subjectsUrl: string = "/api/public/subjects";
    readonly olympiadsUrl: string = "/api/public/olympiads";
    readonly achievementsUrl: string = "/api/public/achievements";
    readonly config: any = {
      headers: {
        'Content-Type': 'application/json',
      }
    };

    disabledExams: boolean[] = [];
    lastValueChanged: number = -1;
    rules = {
      email: [
        {
          required: true,
          message: "Пожалуйста, введите адрес электронной почты",
          trigger: "blur"
        },
        {
          type: "email",
          message: "Пожалуйста, введите существующий адрес электронной почты",
          trigger: "blur"
        }
      ],
      password: [
        {
          required: true,
          message: "Пожалуйста, введите ваш пароль",
          trigger: "blur"
        },
        {min: 6, message: "Пароль должен быть длинее 6 символов"}
      ],
      serialNumber: [
        {
          required: true,
          min: 11,
          message: 'Пожалуйста введите паспортные данные в формате:серия номер',
          trigger: "blur"
        }
      ]
    };

    private form: any = {
      surname: "",
      name: "",
      patronymic: "",
      birthDate: new Date(),
      serialNumber: "",
      email: "",
      phone: "",
      password: "",
      exams: [],
      olympiadsId: [],
      achievementsId: [],
    };

    private subjectsList: Subject[] = [{"id": 1, "name": "Русский язык"}, {
      "id": 2,
      "name": "Математика (базовая)"
    }, {"id": 3, "name": "Математика (профиль)"}] as Subject[];
    private olympiadsList: any[] = [{id: 1}];
    private achievementsList: any[] = [{id: 1}];

    saveValue(subjId: number) {
      this.lastValueChanged = subjId;
      console.log(this.lastValueChanged)
    }

    selectNewExam(subjId: number) {
      let position: Subject | undefined;
      position = this.subjectsList.find(function (element: any): boolean {
        return element.id == subjId;
      });
      if (position != undefined) {
        this.disabledExams[this.subjectsList.indexOf(position)] = true;
      }
    }

    created() {
      this.getSubjects();
      this.getOlympiads();
      this.getAchievements();
    }

    getOlympiads() {
      Axios.get(this.olympiadsUrl)
        .then((res: AxiosResponse) => {
          this.olympiadsList = res.data as Subject[];
        });
    }

    getAchievements() {
      Axios.get(this.achievementsUrl)
        .then((res: AxiosResponse) => {
          this.achievementsList = res.data as Olympiad[];
        });
    }

    getSubjects() {
      Axios.get(this.subjectsUrl)
        .then((res: AxiosResponse) => {
          this.subjectsList = res.data;
          this.subjectsList.forEach(() => {
            this.disabledExams.push(false);
          });
        });
    }

    addExam() {
      this.form.exams.push({
        subjectId: null,
        score: null
      });
    }

    addAchievement() {
      this.form.achievementsId.push("Выберите ИД");
    }

    addOlympiad() {
      this.form.olympiadsId.push("Выберите олимпиаду");
    }

    removeExam(exam: any) {
      let index = this.form.exams.indexOf(exam);
      if (index != -1) {
        this.form.exams.splice(index, 1);
      }
      let position: Subject | undefined;
      position = this.subjectsList.find((subject) => {
        return subject.id == exam.subjectId;
      });
      if (typeof position !== 'undefined') {
        this.disabledExams[this.subjectsList.indexOf(position)] = false;
      }
    }

    removeOlympiad(olympiad: any) {
      let index = this.form.olympiadsId.indexOf(olympiad);
      if (index != -1) {
        this.form.olympiadsId.splice(index, 1);
      }
    }

    removeAchievement(achievement: any) {
      let index = this.form.achievementsId.indexOf(achievement);
      if (index != -1) {
        this.form.achievementsId.splice(index, 1);
      }
    }

    submitForm(formName: any) {
      (this.$refs[formName] as any).validate((valid: any) => {
        if (valid) {
          Axios.post(this.url, this.form, this.config)
            .then((response: AxiosResponse) => {
              this.$router.push({name: 'signIn'});
            })
            .catch((e: AxiosError) => {

            });
        } else {
          return false;
        }
      });
    }
  }
</script>

<style scoped>
  @media screen and (max-width: 500px) {
    .el-form-item {
      width: 300px;
      margin-bottom: 40px;
    }
  }
</style>
