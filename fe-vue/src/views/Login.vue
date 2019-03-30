<template>
  <el-card style="width: 500px; margin: auto">
    <div slot="header" class="clearfix" style="text-align: center">
      <span>Вход</span>
    </div>
    
    <el-form
      ref="form"
      :label-position="'left'"
      :rules="rules"
      :model="form"
      label-width="70px"
    >
      <el-form-item label="E-mail" prop="username" v-bind:error="errorEmail">
        <el-input v-model="form.username" type="email"></el-input>
      </el-form-item>
      
      <el-form-item label="Пароль" prop="password" v-bind:error="errorPassword">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      
      <el-button type="primary" @click="submitForm('form')">Войти</el-button>
      <br />
      <!-- <el-button type="text">Забыли пароль?</el-button> -->
    </el-form>
  </el-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Axios,{ AxiosError,AxiosResponse,AxiosInstance } from "axios";
import Error from '@/types/Error';

@Component
export default class Login extends Vue {
  
  readonly url: string = "/api/public/sign-in";
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
        Axios.post(this.url, this.form, this.config)
        .then((r: AxiosResponse) => {
          this.$store.state.token = r.data;      
        })
        .catch((e: AxiosError) => {
          if(e.response !== undefined){
            let errors = e.response.data as Error;
            let error = errors.inputErrors[0]
            
            switch(error.field){
              case "email": 
                  this.errorEmail = error.info;
                  break;
              case "password": 
                  this.errorPassword = error.info;
                  break;
            }
          }
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
