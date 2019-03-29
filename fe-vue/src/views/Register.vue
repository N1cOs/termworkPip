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

      <el-button type="primary" @click="submitForm('form')">Зарегестрироваться</el-button>
    </el-form>
  </el-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Error from "@/types/Error";
import Exam from "@/types/Exam";
import Subject from "@/types/Subject";
// import subjs from "@/mock/Subjects"
import User from "@/types/User";
import Axios,{ AxiosResponse, AxiosError } from 'axios';

@Component
export default class Register extends Vue {
  readonly url: string = "/api/public/sign-up";
  readonly config: any = {
    headers: {
      'Content-Type': 'application/json'
    }
  }
  

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
      { min: 6, message: "Пароль должен быть длинее 6 символов" }
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

  subjects: Subject[] = [
    {
      id: 1,
      name: ""
    }
  ];
  subjectPoints: number = 0;

  private form: any = {
    surname: "",
    name: "",
    patronymic: "",
    birthDate: new Date(),
    serialNumber: "",
    email: "",
    phone: "",
    password: ""
    // exams: [],
    // subjects: [],
    // olympiads: [],
    // errors: []
  };

  submitForm(formName: any) {
    (this.$refs[formName] as any).validate((valid: any) => {
      if (valid){
        Axios.post(this.url, this.form, this.config)
        .then((response: AxiosResponse) => {

        })
        .catch((e: AxiosError) => {

        })
      }
      else{
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
