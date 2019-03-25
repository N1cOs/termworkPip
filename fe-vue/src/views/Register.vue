<template>
  <el-card style="margin: auto;">
    <div slot="header" class="clearfix" style="text-align: center">
      <span>Register</span>
    </div>
    <!--!!! don't forget to mention prop attribute to validate el-form-input with rules passed !!!-->
    <el-form
      :label-position="'left'"
      ref="form"
      :rules="rules"
      :model="form"
      label-width="120px"
    >
      <el-form-item label="Surname" prop="surname">
        <el-input v-model="form.surname" type=""></el-input>
      </el-form-item>
      <el-form-item label="Name" prop="name">
        <el-input v-model="form.name" type=""></el-input>
      </el-form-item>

      <el-form-item label="Patronymic" prop="">
        <el-input v-model="form.patronymic" type=""></el-input>
      </el-form-item>
      <el-form-item label="Birth date">
        <el-date-picker
          type="date"
          placeholder="Pick a date"
          v-model="form.birthDate"
          style="width: 100%;"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="Passport" prop="serialNumber">
        <el-input
          v-model="form.serialNumber"
          type=""
          placeholder="xxxx xxxxxx"
        ></el-input>
      </el-form-item>
      <el-form-item label="E-mail" prop="email">
        <el-input v-model="form.email" type="email"></el-input>
      </el-form-item>
      <el-form-item label="Phone" prop="phone">
        <el-input v-model="form.phone" type=""></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>

      <el-button type="primary" @click="submitForm('form')">Register</el-button>
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

@Component
export default class Register extends Vue {
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
      { min: 6, message: "Your password is too short!" }
    ],
    serialNumber: [
      {
        required: true,
        min: 11,
        message: 'Please enter passport serial number in form: "xxxx xxxxxx"',
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
