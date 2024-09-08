<template>
  <div class="wrapper">
    <div
      style="
        margin: 130px auto;
        background-color: #fff;
        width: 350px;
        height: 250px;
        padding: 20px;
        border-radius: 10px;
      "
    >
      <div style="height: 60px; line-height: 60px; text-align: center">
        <b style="color: #303133; font-weight: bold"> 房屋租赁后台管理系统 </b>
      </div>
      <el-form :rules="rules" ref="loginForm">
        <el-form-item prop="code" label="账号：">
          <el-input
            size="medium"
            placeholder="请输入账号"
            :prefix-icon="User"
            v-model.trim="state.form.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码：">
          <el-input
            size="medium"
            placeholder="请输入密码"
            show-password
            :prefix-icon="User"
            v-model.trim="state.form.password"
          ></el-input>
        </el-form-item>
        <div>
          <div class="flex-aj-center">
            <el-button type="primary" size="medium" @click="userlogin(1)"
              >登 录</el-button
            >
            <el-button type="primary" size="medium" @click="gotoRegister"
              >前往注册</el-button
            >
          </div>
          <div class="flex-aj-center margin-top-10">
            <el-button type="success" size="medium" @click="userlogin(2)"
              >登录前端</el-button
            >
            <el-button type="success" size="medium" @click="gotoFront()"
              >前端界面</el-button
            >
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { getUserlist, getAllPermission } from "@/api/login";
import { ElMessage } from "element-plus";
import { useRouter, useRoute } from "vue-router";
import { User } from "@element-plus/icons-vue";

let router = useRouter();

let state = reactive({
  fixStyle: "",
  form: { username: "", password: "" },
});
onMounted(() => {});

let gotoFront=()=>{
  router.push({
    path:'/userhouse'
  })
}

const userlogin = (param) => {
  console.log(param);
  let data = {};
  data.username = state.form.username;
  data.password = state.form.password;
  getUserlist(data).then((res) => {
    console.log(res, 555);
    if (res.code == 200) {
      ElMessage({
        type: "success",
        message: "登录成功",
      });
      // 登录成功的时候更新当前路由
      sessionStorage.setItem("username", state.form.username);
      sessionStorage.setItem("token", JSON.stringify(res.data.token));
      sessionStorage.setItem("userid", res.data.user.id);
      getAllPermission().then((res) => {
        sessionStorage.setItem("addRouterData", JSON.stringify(res.data));
      });
      if (param == 1) {
        router.push({ path: "/layout" });
      }
      if (param == 2) {
        router.push({ path: "/userhouse" });
      }
    } else {
      ElMessage({
        type: "error",
        message: res.msg,
      });
      router.push({ path: "/login" });
    }
  });
};
let searchpassword = () => {
  console.log(router);
  router.push({ path: "/password" });
};
let gotoRegister = () => {
  router.push({ path: "/register" });
};
</script>

<style scoped>
.wrapper {
  height: 100vh;
}
.login-wrapper {
  width: 450px;
  height: 400px;
  background-color: #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
}
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #76545b, #c68121);
  overflow: hidden;
}
</style>
