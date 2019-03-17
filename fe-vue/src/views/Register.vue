<template>
    <form
            @submit="checkForm"
            action=""
            method="post"
    >
        <div v-if="errs!==[]">
            <div
                    v-for="error in errs"
                    :key="error.type"
            >
                <h4>Problem: <i>{{error.type}}</i></h4>
                Description: {{error}}
            </div>
        </div>

        <p>
            <label for="user.surname">Surname</label>
            <input
                    :id="user.surname"
                    v-model.lazy="user.surname"
                    type="text"
                    name="surname"
            >
        </p>

        <p>
            <label for="user.patronymic">Patronymic</label>
            <input
                    :id="user.patronymic"
                    v-model="user.patronymic"
                    type="text"
                    name="patronymic"
            >
        </p>

        <p>
            <label for="user.birthDate">Dirth date</label>
            <input
                    :id="user.birthDate"
                    v-model="user.birthDate"
                    type="text"
                    name="birthDate"
            >
        </p>

        <p>
            <label for="user.serialNumber">Serial number</label>
            <input
                    id="user.serialNumber"
                    v-model="user.serialNumber"
                    type="text"
                    name="serialNumber"
            >
        </p>

        <p>
            <label for="user.email">E-mail</label>
            <input
                    id="user.email"
                    v-model="user.email"
                    type="text"
                    name="email"
            >
        </p>

        <p>
            <label for="user.password">Password</label>
            <input
                    id="user.password"
                    v-model="user.password"
                    type="text"
                    name="password"
            >
        </p>

        <div
                v-for="exam in exams"
                class="added-exams"
        >
            {{}}
        </div>
        <p>
            <label>Add an exam</label>
            <select>
                <option
                        v-for="subj in subjects"
                        :key="subj.id"
                        :aria-valuenow="smth"
                >
                    {{subj.name}}
                </option>

            </select>
            <input
                    type="number"
                    v-model="subjectPoints"
                    :aria-placeholder="80"
            >
            <button @click="addExamToList">Add exam</button>
        </p>

        <input
                type="submit"
                value="Submit!"
        >
        <!-- for submit: @click.prevent=""-->

    </form>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import Error from "@/types/Error"
    import Exam from "@/types/Exam";
    import Subject from "@/types/Subject";
    // import subjs from "@/mock/Subjects"
    import User from "@/types/User";

    @Component
    export default class Register extends Vue {

        errs: Error[] = [];
        private user: User = <User>{};
        exams: Exam[] = [];
        subjects: Subject[] = [
            {
                id: 1,
                name: ""
            }
        ];
        subjectPoints: number = 0;

        addExamToList() {
            // exam: Exam
            // exam.id =
            this.subjectPoints = 0
        }

        checkForm() {
            return true;
        }
    }

</script>

<style scoped>

</style>
