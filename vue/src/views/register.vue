<template>
    <div class="wrapper">
    <div
        style="margin: 130px auto; background-color: #fff; width:350px; height:250px;padding: 20px; border-radius:10px">
      <div style="height: 60px; line-height: 60px; text-align: center">
        <b style="color: #303133; font-weight: bold">
          注册
        </b>
      </div>
      <el-form ref="loginForm">
        <el-form-item prop="code" label="账号：" label-width="100px">
          <el-input size="medium" placeholder="请输入账号"
                    v-model.trim="state.form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码：" label-width="100px">
          <el-input size="medium" placeholder="请输入密码" show-password
                    v-model.trim="state.form.password"></el-input>
        </el-form-item>
        
        <div style="margin: 40px 0; display: flex;align-items: center;    justify-content: center;">
          <el-form-item>
            <el-button type="primary" size="medium"  @click="register1">注册</el-button>
            <el-button type="primary" size="medium"  @click="gotoLogin">前往登录</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>


</template>

<script setup>
import {reactive, onMounted} from 'vue'
import {register} from '@/api/login'
import {ElMessage} from 'element-plus'
import {useRouter} from 'vue-router'

let router = useRouter();

let state = reactive({
  form: {username: "", password: ""},
})

onMounted(() => {
})

let gotoLogin=()=>{
  router.push({path:'/login'})
}

let register1=()=>{
  register(state.form).then(res=>{
    console.log(res,666)
    if(res.code==200){
      ElMessage({
        message: '注册成功,即将跳转到登录页面',
        type: 'success',
      })
      router.push({path:'/login'})
    }else{
      ElMessage({
        message: res.msg,
        type: 'error',
      })
    }
  })
}

</script>

<style scoped>
.wrapper{
  height: 100vh;
}
.login-wrapper{
  width: 450px;
  height: 200px;
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
