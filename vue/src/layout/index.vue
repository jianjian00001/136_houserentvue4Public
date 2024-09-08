<template>
  <div>
    <div
      style="
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid #ccc;
        display: flex;
      "
      class="head"
    >
      <div
        style="
          width: 200px;
          padding-left: 30px;
          font-weight: bold;
          color: dodgerblue;
        "
        @click="tap"
      >
        房屋租赁管理系统
      </div>
      <div style="flex: 1"></div>
      <div style="color: #ccc;margin-right: 20px;" @click="gotoFront">
        前台界面
      </div>
      <div>
        <el-dropdown>
          <span class="el-dropdown-link fx">
            <img v-if="state.param.pic" :src="state.param.pic" class="avatar" />
            <div class="columflex white">
              <span class="margin-top-10">{{ state.param.username }}</span>
            </div>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="route.push('/person')"
                >个人信息</el-dropdown-item
              >
              <el-dropdown-item @click="route.push('/updatepassword')"
                >修改密码</el-dropdown-item
              >
              <el-dropdown-item @click="exit">退出系统</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <div style="display: flex; height: calc(100vh - 70px)">
      <div class="aside">
        <el-aside width="200px">
          <el-scrollbar>
            <div v-for="(item, index) in state.asideData" :key="item.id">
              <el-menu
                active-text-color="#ffd04b"
                background-color="#545c64"
                class="el-menu-vertical-demo"
                text-color="#fff"
                @open="handleOpen"
                @close="handleClose"
              >
                <el-sub-menu :index="index">
                  <template #title>
                    <el-icon style="vertical-align: middle">
                      <component :is="item.icon"></component>
                    </el-icon>
                    {{ item.menuname }}
                  </template>

                  <el-menu-item-group
                    v-for="(item1, j) in item.children"
                    :key="item1.id"
                  >
                    <el-menu-item
                      :index="index + '-' + j"
                      @click="menuHandler(item1)"
                      class="flex"
                    >
                      <el-icon>
                        <component :is="item1.icon"></component>
                      </el-icon>
                      {{ item1.menuname }}</el-menu-item
                    >
                  </el-menu-item-group>
                </el-sub-menu>
              </el-menu>
            </div>
          </el-scrollbar>
        </el-aside>
      </div>
      <router-view
        style="flex: 1; height: calc(100vh - 110px); overflow: auto"
      />
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
// import { getAllPermission,getperson } from "@/api/login";
import { getperson, getAllPermission } from "@/api/user";

const route = useRouter();

let state = reactive({
  param: "",
  asideData: {},
});

const router = useRouter();

onMounted(() => {
  getAllPermission().then((res) => {
    console.log(res);
    state.asideData = res.data;
  });
  getperson().then((res) => {
    console.log(res, 123456);
    if (res.code == 200) {
      state.param = res.data;
    }
  });
});

let gotoFront=()=>{
  router.push("/userhouse")
}

let menuHandler = (val) => {
  console.log(val);
  router.push({ path: val.path });
};

let exit = () => {
  router.push("/login");
  sessionStorage.removeItem("addRouterData");
  sessionStorage.removeItem("username");
  sessionStorage.removeItem("token");
};
</script>

<style scoped>
.fx {
  display: flex;
}
.columflex {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-left: 10px;
}
.head {
  background-color: #545c64;
  padding-top: 10px;
}
.aside {
  background-color: #545c64;
  height: calc(100vh - 70px);
  overflow: auto;
}

.avatar{
  width: 40px;
  height: 40px;
  border-radius: 20px;
}
</style>
