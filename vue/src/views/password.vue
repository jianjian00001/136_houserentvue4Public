<template>
  <div style="width: 400px; margin: 100px auto">
    <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">找回密码</div>
    <div class="flex-aj-center margin-top-20">
      <div class="flex">
        <p style="width: 50px">用户：</p>
        <el-input v-model="state.form.username" type="text" placeholder="请输入账户" style="width: 400px"/>
      </div>
    </div>


    <div class="flex-aj-center" style="width: 200px" v-if="state.form.username==''"></div>

    <div class="flex-aj-center margin-top-20">
      <div class="flex" style="margin-left: 150px">
        <p style="width: 50px;">邮箱：</p>
        <div class="flex" style="margin-right: 150px">
          <el-input v-model="state.form.email" type="text" placeholder="请输入邮箱" style="width: 300px;"/>
          <el-button type="text" style="width: 100px;height: 50px;background-color: #ccc" @click="yanzhengma"
                     v-if="state.yinchang">发送验证码</el-button>
          <el-button type="text" style="width: 100px;height: 50px;background-color: #ccc" v-if="!state.yinchang">{{state.innerhtml}}</el-button>
        </div>

      </div>

    </div>


    <div class="flex-aj-center margin-top-20">
      <div class="flex">
        <p style="width: 70px;margin-left: -20px">验证码：</p>
        <el-input v-model="state.form.code" type="text" placeholder="请输入验证码" style="width: 400px"/>
      </div>
    </div>

    <div class="flex-aj-center margin-top-20" v-if="state.testyanzhengma">
      <div class="flex">
        <p style="width: 70px;margin-left: -20px">密码：</p>
        <el-input v-model="state.form.password" type="password" placeholder="请输入重置密码" style="width: 400px"/>
      </div>
    </div>

    <div class="flex-aj-center margin-top-20" v-if="!state.testyanzhengma">
        <el-button style="width: 100%" type="primary" @click="testcode" class="margin-right-20">下一步</el-button>
    </div>


    <div class="flex-aj-center margin-top-20" v-if="state.testyanzhengma">
      <el-button style="width: 100%" type="primary" @click="searchpassword" class="margin-right-20">找回密码</el-button>
      <div class="flex flex-between">
        <el-button type="text" @click="login">已有账号，前往登录 >></el-button>
      </div>
    </div>
  </div>


</template>

<script setup>
import {reactive, onMounted} from 'vue'
import {testusernameemail,sendEmail,testcodename,updatepassword} from '@/api/login'
import {ElMessage,ElNotification} from 'element-plus'
import {useRouter} from 'vue-router'

let router = useRouter();

let state = reactive({
  fixStyle: '',
  form: {username: "", email: "",code:"",password:""},
  firstusernamexianshi:false,
  yinchang:true,
  innerhtml:"60秒后可重送",
  testyanzhengma:false
})
onMounted(() => {
})

let login=()=>{
  router.push({path:'/login'})
}
let yanzhengma=()=>{
  console.log(state.form)
  testusernameemail(state.form).then(res=>{
    console.log(res)
    if(res.code==500){
      ElMessage({
        message: '不存在该用户和邮箱',
        type: 'error',
      })
      return
    }else{
      ElMessage({
        message: '存在该用户和邮箱',
        type: 'success',
      })
      countDown()
      state.yinchang=false
      ElNotification({
        title: '验证码发送成功,请注意查看',
      })
      sendEmail(state.form.email).then(res=>{
        console.log(res)
      })
    }
  })
}
function countDown(seconds = 60) {
  // 定时器
  let timer = setInterval(() => {
    seconds--
    let result = seconds + '秒后可重发'

    // 把转换后的结果显示出来
    state.innerhtml = result

    if (seconds <= 0) {
      clearInterval(timer)
      console.log('倒计时结束，清除定时器，避免内存溢出')
      state.yinchang=true
    }
  }, 1000)
}
let testcode=()=>{
  testcodename(state.form.code).then(res=>{
    if(res.code==200){
      state.testyanzhengma=true
    }
  })
}
let searchpassword=()=>{
  state.form.password=Encrypt(state.form.password.trim())
  updatepassword(state.form).then(res=>{
    console.log(res)
    if(res.code=200){
      ElMessage({
        message: '密码修改成功,请登录',
        type: 'success',
      })
      setTimeout(() =>       router.push({path:'/login'})
          , 2*1000); // 1000 = 1s

    }
  })
}
</script>

<style scoped>

</style>
