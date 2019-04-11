<template>
  <div class="ratings-wrapper">
    <div class="form-apply">
      <el-row :gutter="20" class="scores">
        <el-col :md="12" :sm="24" :xs="24" class="exams">
          <el-card>
            <el-form
              ref="form"
              :model="form"
            >
              <el-form-item label="Подать оригиналы?" prop="originals">
                <el-switch v-model="form.originals"></el-switch>
              </el-form-item>
              <el-form-item label="Приоритетность специальности" prop="priority">
                <el-radio-group v-model="form.priority">
                  <el-radio-button label="1"></el-radio-button>
                  <el-radio-button label="2"></el-radio-button>
                  <el-radio-button label="3"></el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-form>
            <el-button @click="applyForSpeciality">
              Подать документы на специальность
            </el-button>
            <div style="color: red;">
              {{this.errorInfo}}
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div v-if="ratings.length !== 0">
      <div v-if="ratings.ege.length!==0 || ratings.bvi.length !== 0 ">
        <el-table
          lazy
          v-loading="loading"
          element-loading-text="Loading"
          :data="ratings.ege"
          :row-class-name="checkOriginalsSent"
          width="100%"
          :default-sort = "{prop: 'place'}"
        >
          <el-table-column
            label="ФИО"
            width="240"
            fixed
          >
            <template slot-scope="scope">
              {{ computeName(scope.row.student.surname, scope.row.student.name, scope.row.student.patronymic, ) }}
            </template>
          </el-table-column>
          <el-table-column prop="place" label="Место" width="70" sortable fixed/>
          <el-table-column prop="placeOriginal" label="Место по оригиналам" width="110" sortable/>
          <el-table-column prop="totalScore" label="Сумма баллов" width="75"/>
          <el-table-column
            v-for="(subject, index) in ratings.ege[0].student.exams"
            :key="subject.id"
            :label="subject.name"
            width="140"
          >
            <template slot-scope="scope">
              {{ scope.row.student.exams[index].score }}
            </template>
          </el-table-column>
          <el-table-column prop="priority" label="Приоритетность специальности"/>
          <el-table-column prop="submissionDate" label="Дата подачи заявления"/>
          <el-table-column prop="originals" label="Поданы оригиналы?">
            <template slot-scope="scope">
              {{ scope.row.originals ? "Да" : "Нет" }}
            </template>
          </el-table-column>
          <el-table-column prop="success" label="Проходит по оригиналам?">
            <template slot-scope="scope">
              {{ scope.row.success ? "Да" : "Нет" }}
            </template>
          </el-table-column>

        </el-table>
      </div>
    </div>
  </div>

</template>

<script lang="ts">
  import {Component, Prop, Vue} from "vue-property-decorator";
  import RatingRecord from "@/types/RatingRecord";
  import Speciality from "@/types/Speciality";
  import Axios, {AxiosError, AxiosResponse} from 'axios';
  import ElRadioButton from "element-ui/packages/radio/src/radio-button.vue";

  @Component({
    components: {ElRadioButton}
  })
  export default class Ratings extends Vue {
    config: any = {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': this.$store.state.token
      }
    };
    ratings: RatingRecord[] = [];
    speciality?: Speciality;

    loading = true;
    errorInfo: string = '';

    priority: number = 1;
    originals: boolean = false;
    olympiadId: number = -1;
    successfullyApplied: boolean = false;

    private form: any = {
      priority: 3,
      originals: false,
      olympiadId: undefined,
    };

    @Prop({required: true})
    specId!: number;

    mounted() {
      this.getRatings();
    }

    checkOriginalsSent({row}: any) {
      if (!row.originals)
        return "row--originals-sent";
      else
        return "";
    }

    applyForSpeciality() {
      Axios.post("/api/student/speciality/" + this.specId, {
        specialityId: this.$route.params.specId,
        priority: this.form.priority,
        originals: this.form.originals,
        olympiadId: this.form.olympiadId
      }, this.config)
        .then((res: AxiosResponse) => {
          this.successfullyApplied = true;
          this.errorInfo = '';
        })
        .catch((e: AxiosError) => {
          if (e.response !== undefined) {
            let errorInfo = e.response.data as Error;
            this.errorInfo = (errorInfo as any).info;
          }
        });
    }

    getRatings() {
      Axios.get("/api/public/ratings/" + this.specId)
        .then((res: AxiosResponse) => {
          this.loading = false;
          this.ratings = res.data as RatingRecord[];
          this.errorInfo = '';
        })
        .catch((e: AxiosError) => {
          if (e.response !== undefined) {
            let errorInfo = e.response.data as Error;
            this.errorInfo = (errorInfo as any).info;
          }
        });
    }

    computeName(surname: string, name: string, patronymic: string) {
      if (patronymic != undefined)
        return surname + " " + name + " " + patronymic;
      else
        return surname + " " + name;
    }
  }
</script>

<style lang="scss">
  .row--originals-sent {
    background: #8CB6C0 !important;
  }

  .ratings-wrapper {
    & > div {
      margin-top: 40px;
    }
  }

</style>
