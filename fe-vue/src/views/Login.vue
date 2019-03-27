<template>
  <el-card style="width: 500px; margin: auto;">
    <div slot="header" class="clearfix" style="text-align: center">
      <span>Вход</span>
    </div>
    <!--!!! don't forget to mention prop attribute to validate el-form-input with rules passed !!!-->
    <el-form
      ref="form"
      :label-position="'left'"
      :rules="rules"
      :model="form"
      label-width="120px"
    >
      <el-form-item label="E-mail" prop="username">
        <div>{{errorEmail}}</div>
        <el-input v-model="form.username" type="email"></el-input>
      </el-form-item>
      <div>{{errorPassword}}</div>
      <el-form-item label="Пароль" prop="password">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      <el-button type="primary" @click="submitForm('form')">Войти</el-button>
      <br />
      <el-button type="text">Забыли пароль?</el-button>
    </el-form>
  </el-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Axios,{ AxiosError,AxiosResponse,AxiosInstance } from "axios";

@Component
export default class Login extends Vue {
  
  readonly loginUrl: string = "/api/public/sign-in";
  readonly config: any = {
    headers: {
      'Content-Type': 'application/json'
    }
  }

  errorEmail: string = ""
  errorPassword: string = ""
  
  form = {
    username: "",
    password: ""
  };

  rules = {
    username: [
      {
        required: true,
        message: "Пожалуйста, введите ваш адрес электронной почты",
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
      { min: 4, message: "Пароль должен быть длинее 4 символов" }
    ]
  };
  submitForm(formName: any) {
    (this.$refs[formName] as any).validate((valid: any) => {
      if (valid){
        Axios.post(this.loginUrl, this.form, this.config)
        .then((r: AxiosResponse) => {
          this.$store.state.token = r.data;       
        })
        .catch((e: AxiosError) => {
          //ToDo: add error handling
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
/*@media screen and (max-width: 500px) {*/
  /*.el-card {*/
    /*width: 300px;*/
    /*margin-bottom: 40px;*/
  /*}*/
/*}*/
</style>
