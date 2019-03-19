<template>
    <div id="form">
        <el-form ref="form"
                 :model="form"
                 label-width="120px">


            <el-form-item label="Name">
                <el-input v-model="form.name"></el-input>
            </el-form-item>


        </el-form>
    </div>
</template>

<script>
    import {Component, Vue} from 'vue-property-decorator'

    @Component
    export default class Registration extends Vue{
        form: {
            name: '',
            region: '',
            date1: '',
            date2: '',
            delivery: false,
            type: [],
            resource: '',
            desc: '',
        };
    }
</script>

<style scoped>

</style>
