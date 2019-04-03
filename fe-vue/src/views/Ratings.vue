<template>

  <el-container>
    <el-header>
      Speciality id{{this.$route.params.specId}}
    </el-header>
    <el-table
      v-loading=""
    >
    </el-table>
    <div>
      <el-button @click="applyForSpeciality">
        Подать документы на специальность в один клик
      </el-button>
    </div>
  </el-container>

</template>

<script lang="ts">
  import {Component, Vue, Watch} from "vue-property-decorator";
  import RatingRecord from "@/types/RatingRecord";
  import Speciality from "@/types/Speciality";
  import Axios, {AxiosResponse} from 'axios';
  import {spec} from "@/mock/subjectspecialitymock";

  @Component
  export default class Ratings extends Vue {
    config: any = {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': this.$store.state.token
      }
    };
    ratings?: RatingRecord[];
    speciality?: Speciality;
    loading = true;
    priority: number = 1;
    originals: boolean = false;
    olympiadId: number = -1;
    successfullyApplied: boolean = false;

    created() {
      this.getRatings();
    }

    @Watch('successfullyApplied')
    logApplication() {
      console.log("APPLIED!!!!!!");
    }

    applyForSpeciality() {
      Axios.post("/api/student/speciality/" + this.$route.params.specId, {
        specialityId: this.$route.params.specId,
        priority: this.priority,
        originals: this.originals,
        olympiadId: this.olympiadId
      }, this.config)
        .then((res: AxiosResponse) => {
          this.successfullyApplied = true;
        });
    }

    getRatings() {
      Axios.get("/api/public/ratings/" + this.$route.params.specId)
        .then(res => {
          // console.log(res)
          this.loading = false;
        });
    }
  }
</script>

<style scoped></style>
