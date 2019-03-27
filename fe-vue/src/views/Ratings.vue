<template>

  <el-container>
      <el-header>
        {{this.$route.params.specId}}
      </el-header>

    <el-container>

    </el-container>
  </el-container>

</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import RatingRecord from "@/types/RatingRecord";

    @Component
    export default class Ratings extends Vue {
        ratings?: RatingRecord[]
    }
</script>

<style scoped></style>
