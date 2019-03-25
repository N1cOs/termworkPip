<template>
  <div>
    <el-card
      style="width: 80%;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);"
    >
      <el-container>
        <el-header>About project</el-header>
        <el-container>
          <el-aside width="80px">
            <router-link to="/"
              ><img src="../assets/person.png" alt=""
            /></router-link>
            <router-link to="/"
              ><img src="../assets/rating.png" alt=""
            /></router-link>
            <router-link to="/"
              ><img src="../assets/vuz.png" alt=""
            /></router-link>
          </el-aside>
          <el-main>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias
            aliquid culpa dolore ducimus eaque eligendi, enim et exercitationem
            illo, in incidunt iste labore non porro recusandae reprehenderit
            unde voluptatem, voluptatum! Lorem ipsum dolor sit amet, consectetur
            adipisicing elit. Commodi distinctio fuga officiis porro, rem
            repellendus sit temporibus. Aperiam aut, blanditiis ea id iure
            labore laboriosam magni praesentium provident sequi vero.
            <br />
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi cum
            cupiditate distinctio dolorem id laudantium magnam magni nam nobis
            non odit optio pariatur quisquam, reprehenderit sint ut
            voluptatibus? Culpa, quasi?
            <br />
	          <div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab amet fugit, iusto labore natus non quod
		          unde voluptate! Aperiam cumque dolorum quo ratione? At consequatur exercitationem iste officiis quae,
		          rerum!
	          </div>
	          <div>Beatae dignissimos neque praesentium provident quis recusandae rem tempore vel vitae voluptatum?
		          Aliquam aliquid, aut consequuntur eveniet ex, fugiat nesciunt nobis perspiciatis, quae quisquam reiciendis
		          reprehenderit unde ut veritatis voluptatibus.
	          </div>
          </el-main>
        </el-container>
      </el-container>
    </el-card>
  </div>
</template>

<script>
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Main extends Vue {}
Vue.component("Main", Main);
</script>

<style scoped></style>
