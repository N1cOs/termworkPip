<template>
  <div></div>
</template>

<script>
import { Component, Vue } from "vue-property-decorator";
import RatingRecord from "@/types/RatingRecord";

@Component
export default class Ratings extends Vue {
    ratings: RatingRecord[]
}
</script>

<style scoped></style>
