<template>
    <form
            @submit="checkForm"
            action=""
            method="post"
    >
        <div v-if="errs.length">
            <div
                    v-for="error in errs"
                    :key="error.type"
            >
                <h4>Problem: <i>{{error.type}}</i></h4>
                Description: {{error}}
            </div>
        </div>

        <p>
            <label for="surname">Surname</label>
            <input
                    id="surname"
                    v-model="user.surname"
                    type="text"
                    name="surname"
            >
        </p>

        <p>
            <label for="patronymic">Patronymic</label>
            <input
                    id="patronymic"
                    v-model="user.patronymic"
                    type="text"
                    name="patronymic"
            >
        </p>

        <p>
            <label for="birthDate">Dirth date</label>
            <input
                    id="birthDate"
                    v-model="user.birthDate"
                    type="text"
                    name="birthDate"
            >
        </p>

        <p>
            <label for="serialNumber">Serial number</label>
            <input
                    id="serialNumber"
                    v-model="user.serialNumber"
                    type="text"
                    name="serialNumber"
            >
        </p>

        <p>
            <label for="email">E-mail</label>
            <input
                    id="email"
                    v-model="user.email"
                    type="text"
                    name="email"
            >
        </p>

        <p>
            <label for="password">Password</label>
            <input
                    id="password"
                    v-model="user.password"
                    type="text"
                    name="password"
            >
        </p>
        <p>
            <label>Exams</label>
            <select>
                <option
                        v-for="subj in subjects"
                        :key="subj.id"
                        value="{{subj.id}}"
                >
                    {{subj.name}}
                </option>

            </select>
            <input
                    type="number"
                    v-model="subjectPoints"
                    placeholder="{{Math.random()*101}}"
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
    import User from "@/types/User";
    import Error from "@/types/Error"
    import Exam from "@/types/Exam";
    import Subject from "@/types/Subject";

    @Component
    export default class Register extends Vue {

        errs: Error[] = [];
        user: User;
        exams: Exam[] = [];
        subjects: Subject[];
        subjectPoints: number;

        addExamToList() {

            this.subjectPoints = null
        }

        checkForm() {
            return true;
        }
    }

</script>

<style scoped>

</style>
