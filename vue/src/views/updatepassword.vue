<template>
  <div>
    <div class=" font-size-20 margin-top-20" style="margin-left: 200px;">更新密码</div>
      <el-form :model="form" label-width="120px" class="margin-top-20">
      <el-form-item label="原密码">
        <el-input v-model="form.oldpassword" type="password" placeholder="请输入原密码" style="width: 400px;"/>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="form.newpassword" type="password"  placeholder="请输入新密码" style="width: 400px;"/>
      </el-form-item>
      <el-form-item label="再次新密码">
        <el-input v-model="form.renewpassword" type="password"  placeholder="请再次输入新密码" style="width: 400px;"/>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>

    
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { updatesecert } from "@/api/login";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

const form = reactive({
  oldpassword: "",
  newpassword: "",
  renewpassword: "",
});

const route = useRouter();

let onSubmit = () => {
  if(!form.oldpassword||!form.newpassword||!form.renewpassword){
    ElMessage({
        message: "输入框里不能为空",
        type: "error",
      });
  }
  if(form.newpassword!=form.renewpassword){
    ElMessage({
        message: "修改的密码必须为相同的",
        type: "error",
      });
  }
  updatesecert(form).then((res) => {
    if (res.code == 200) {
      ElMessage({
        message: "修改密码成功,请重新登录",
        type: "success",
      });
      route.push({ path: "/login" });
    }else{
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};
</script>

<style scoped>
</style>
