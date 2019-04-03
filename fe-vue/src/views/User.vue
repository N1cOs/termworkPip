<template>
  <div>
    <el-form>
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
      <el-button @click="saveExams">Сохранить экзамен(ы)</el-button>
    </el-form>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import Subject from "@/types/Subject";
  import Axios, {AxiosResponse} from "axios";
  import Olympiad from "@/types/Olympiad";

  @Component
  export default class User extends Vue {
    form = {
      exams: [],
      olympiadsId: [],
      achievementsId: [],
    }
    config: any = {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.$store.state.token
      }
    }

    private subjectsList: Subject[] = [{"id": 1, "name": "Русский язык"}, {
      "id": 2,
      "name": "Математика (базовая)"
    }, {"id": 3, "name": "Математика (профиль)"}] as Subject[];
    private olympiadsList: any[] = [{id: 1}]
    private achievementsList: any[] = [{id: 1}]
    readonly subjectsUrl: string = "/api/public/subjects";
    readonly olympiadsUrl: string = "/api/public/olympiads";
    readonly achievementsUrl: string = "/api/public/achievements";

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
    }

    removeOlympiad(olympiad) {
      let index = this.form.olympiadsId.indexOf(olympiad);
      if (index != -1) {
        this.form.olympiadsId.splice(index, 1);
      }
    }

    removeAchievement(achievement) {
      let index = this.form.achievementsId.indexOf(achievement);
      if (index != -1) {
        this.form.achievementsId.splice(index, 1);
      }
    }

    saveExams() {
      Axios.post("/api/student/exams", this.form.exams, this.config)
        .then(
          (res: AxiosResponse) => {
            console.log(res.status);
          }
        );
    }
  }
  Vue.component("User", User);

</script>