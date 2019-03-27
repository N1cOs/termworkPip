<template>
  <el-card style="width: 500px; margin: auto;">
    <div slot="header" class="clearfix" style="text-align: center">
      <span>Login</span>
    </div>
    <!--!!! don't forget to mention prop attribute to validate el-form-input with rules passed !!!-->
    <el-form
      ref="form"
      :label-position="'left'"
      :rules="rules"
      :model="form"
      label-width="120px"
    >
      <el-form-item label="E-mail" prop="email">
        <el-input v-model="form.email" type="email"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      <el-button type="primary" @click="submitForm('form')">Sign in</el-button>
      <br />
      <el-button type="text">forgot password?</el-button>
    </el-form>
  </el-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { AxiosError, AxiosResponse, AxiosInstance } from "axios";
import http from "http-common";

@Component
export default class Login extends Vue {
  
  readonly loginUrl: string = "/api/public/sign-in";
  
  form = {
    email: "",
    password: ""
  };

  rules = {
    email: [
      {
        required: true,
        message: "Please enter your email address",
        trigger: "blur"
      },
      {
        type: "email",
        message: "Please enter a valid email address",
        trigger: "blur"
      }
    ],
    password: [
      {
        required: true,
        message: "Please enter your password",
        trigger: "blur"
      },
      { min: 4, message: "Your password is too short!" }
    ]
  };
  submitForm(formName: any) {
    (this.$refs[formName] as any).validate((valid: any) => {
      if (valid){
        http.post(this.loginUrl, {
          body: this.form
        })
        .then((r: AxiosResponse) => {

        })
        .catch((e: AxiosError) => {
          console.log(e)
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
