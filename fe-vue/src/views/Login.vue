<template>
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">

        <el-form-item label="E-mail" prop="email">
            <el-input v-model="form.email" type="email"></el-input>
        </el-form-item>
        <el-form-item label="Password">
            <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>


    </el-form>
</template>

<script>
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class Login extends Vue {

        form = {
            email: '',
            password: ''
        };

        rules= {
            email: [
                { required: true, message: 'Please enter you email address', trigger: 'blur' },
                { type: 'email', message: 'Please enter a valid email address', trigger: 'blur' },
                ],
            password: [
                { required: true, message: 'Please enter your password', trigger: 'blur' },
                { min: 6, message: 'Your password is too short!' },
                ],
        }
    }
</script>
