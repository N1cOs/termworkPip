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
      Экзамен
      <el-form-item
        v-for="(exam, index) in form.exams"
        :key="exam.id"
      >
        <el-select
          v-model="form.exams[index].subjectId"
        >
          <el-option
            v-for="subj in subjectsList"
            :key="subj.id"
            :label="subj.name"
            :value="subj.id"
          >
          </el-option>
        </el-select>
        <el-input v-model="form.exams[index].score" placeholder="Введите количество баллов за экзамен">
        </el-input>
        <el-button @click="removeExam(exam)">Убрать экзамен</el-button>
      </el-form-item>
      <el-button @click="addExam">
        Добавить экзамен
      </el-button>
      Олимпиады
      <el-form-item
        v-for="(olympiad, index) in form.olympiadsId"
        :key="olympiad.id"
      >
        <el-select
          v-model="form.olympiadsId[index]"
        >
          <el-option
            v-for="olympiad in olympiadsList"
            :key="olympiad.id"
            :label="olympiad.name"
            :value="olympiad.id"
          >
          </el-option>
        </el-select>
        <el-button @click="removeOlympiad(olympiad)">Убрать олимпиаду</el-button>
      </el-form-item>
      <el-button @click="addOlympiad">
        Добавить олимпиаду
      </el-button>
      Индивидуальные достижения
      <el-form-item
        v-for="(achievement, index) in form.achievementsId"
        :key="achievement.id"
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
        <el-button @click="removeAchievement(achievement)">Убрать достижение</el-button>
      </el-form-item>
      <el-button @click="addAchievement">
        Добавить достижние
      </el-button>

      <el-button type="primary" @click="submitForm('form')">Зарегестрироваться</el-button>
    </el-form>
  </el-card>
</template>

<script lang="ts">
  import {Component, Vue, Watch} from "vue-property-decorator";
  import Error from "@/types/Error";
  import Exam from "@/types/Exam";
  import Subject from "@/types/Subject";
  import User from "@/types/User";
  import Axios, {AxiosResponse, AxiosError} from 'axios';
  import Olympiad from "@/types/Olympiad";

  @Component
  export default class Register extends Vue {
    readonly url: string = "/api/public/sign-up";
    readonly subjectsUrl: string = "/api/public/subjects";
    readonly olympiadsUrl: string = "/api/public/olympiads";
    readonly achievementsUrl: string = "/api/public/achievements";
    readonly config: any = {
      headers: {
        'Content-Type': 'application/json'
      }
    }
    addingExam: number = -1;

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

    subjectPoints: number = 0;

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
    private olympiadsList: any[] = [{id: 1}]
    private achievementsList: any[] = [{id: 1}]

    created() {
      this.getSubjects()
      this.getOlympiads()
      this.getAchievements()
    }

    getOlympiads() {
      Axios.get(this.olympiadsUrl)
        .then((res: AxiosResponse) => {
          this.olympiadsList = res.data as Subject[]
        })
    }

    getAchievements() {
      Axios.get(this.achievementsUrl)
        .then((res: AxiosResponse) => {
          this.achievementsList = res.data as Olympiad[]
        })
    }

    getSubjects() {
      Axios.get(this.subjectsUrl)
        .then((res: AxiosResponse) => {
          this.subjectsList = res.data
        })
    }

    addExam() {
      this.form.exams.push({
        subjectId: null,
        score: null
      })
    }

    addAchievement() {
      this.form.achievementsId.push("Выберите ИД")
    }
    addOlympiad() {
      this.form.olympiadsId.push("Выберите олимпиаду")
    }

    removeExam(exam: any) {
      let index = this.form.exams.indexOf(exam)
      if (index != -1) {
        this.form.exams.splice(index, 1)
      }
    }

    removeOlympiad(olympiad) {
      let index = this.form.olympiadsId.indexOf(olympiad)
      if (index != -1) {
        this.form.olympiadsId.splice(index, 1)
      }
    }

    removeAchievement(achievement) {
      let index = this.form.achievementsId.indexOf(achievement)
      if (index != -1) {
        this.form.achievementsId.splice(index, 1)
      }
    }

    submitForm(formName: any) {
      (this.$refs[formName] as any).validate((valid: any) => {
        if (valid) {
          Axios.post(this.url, this.form, this.config)
            .then((response: AxiosResponse) => {

            })
            .catch((e: AxiosError) => {

            })
        } else {
          return false
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
