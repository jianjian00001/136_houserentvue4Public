<template>
  <el-container>
    <el-header>
      <div class="flex flex-space-between background-black">
        <span
          style="font-size: 20px; padding-left: 20px; color: aliceblue"
          class="cursorPointer"
          >房屋租赁管理系统</span
        >
        <div
          class="flex padding-right-20 cursorPointer"
          style="color: aliceblue"
          v-if="state.isLogin == 0"
        >
          <div @click="gotoBack" class="margin-right-10">后台界面</div>
          <div style="margin-right: 20px" @click="gotoLogin">登录</div>
          <div @click="gotoRegister">注册</div>
        </div>
        <div
          v-if="state.isLogin == 1"
          class="flex cursorPointer"
          style="color: aliceblue"
        >
          <div @click="gotoBack" class="margin-right-10">后台界面</div>
          <div class="margin-right-10">{{ state.username }}</div>
          <div @click="removeLogin">退出登录</div>
        </div>
      </div>
    </el-header>
    <el-main>
      <el-menu
        :default-active="state.activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        style="text-align: center"
      >
        <el-menu-item index="1">房屋信息</el-menu-item>
        <el-menu-item index="2">公告信息</el-menu-item>
        <!-- <el-menu-item index="3">留言反馈</el-menu-item> -->

        <el-menu-item index="4">发布租房</el-menu-item>
        <el-menu-item index="5">个人中心</el-menu-item>
      </el-menu>

      <router-view />
    </el-main>

    <el-footer>
      <div
        class="background-black margin-top-10"
        style="height: 100px; text-align: center"
      >
        <div class="padding-top-10" style="color: aliceblue">
          <div>房屋租赁管理系统 @版权所有</div>
          <div>京ICP备05023036号</div>
        </div>
        <span style="font-size: 20px; padding-left: 20px; color: aliceblue"
          >房屋租赁管理系统</span
        >
        <div></div>
      </div>
    </el-footer>
  </el-container>

  <el-dialog
    v-model="state.dialogVisible"
    :title="state.title"
    width="40%"
    :before-close="handleClose"
  >
    <el-form :model="form" label-width="120px">
      <el-form-item label="用户名">
        <el-input v-model="state.form.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="state.form.password" type="password" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" v-if="state.title == '登录'"
          >登录</el-button
        >
        <el-button type="primary" @click="register" v-if="state.title == '注册'"
          >注册</el-button
        >
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script setup>
import { login, insertUser, getperson } from '@/api/user'
import { useRouter } from 'vue-router'
import { reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

let router = useRouter()

let state = reactive({
  username: sessionStorage.getItem('username')
    ? ''
    : sessionStorage.getItem('username'),
  activeIndex: '1',
  dialogVisible: false,
  isLogin: sessionStorage.getItem('isLogin') ? '1' : '0',
  form: {
    username: '',
    password: '',
  },
  title: '登录',
})

onMounted(() => {
  state.username = sessionStorage.getItem('username')
  if (sessionStorage.getItem('token')) {
    state.isLogin = '1'
  }
  // getperson().then((res) => {
  //   console.log(res, 123456);
  //   if (res.code == 200) {
  //     state.param = res.data;

  //   }
  // });
})

let gotoBack = () => {
  router.push('/layout')
}

let removeLogin = () => {
  state.isLogin = 0
  sessionStorage.setItem('isLogin', 0)
  sessionStorage.removeItem('token')
  sessionStorage.removeItem('addRouterData')
  sessionStorage.removeItem('username')
  sessionStorage.removeItem('userid')
  // location.reload();
}

let onSubmit = () => {
  login(state.form.username, state.form.password).then((res) => {
    console.log(res, 666)
    if (res.code == 200) {
      ElMessage({
        type: 'success',
        message: '登录成功',
      })
      // 登录成功的时候更新当前路由
      sessionStorage.setItem('isLogin', '1')
      state.isLogin = 1
      state.username = res.data.user.username
      sessionStorage.setItem('token', JSON.stringify(res.data.token))
      sessionStorage.setItem('username', res.data.user.username)
      sessionStorage.setItem('userid', res.data.user.id)
      state.dialogVisible = false
    } else {
      ElMessage({
        type: 'error',
        message: res.msg,
      })
      sessionStorage.setItem('isLogin', '0')
    }
  })
}

const handleSelect = (key) => {
  state.activeIndex = key
  if (state.activeIndex == '1') {
    router.push({
      path: '/userhouse',
    })
  }
  if (state.activeIndex == '2') {
    router.push({
      path: '/bugaofront',
    })
  }
  if (state.activeIndex == '4') {
    router.push({
      path: '/publishhouse',
    })
  }

  if (state.activeIndex == '5') {
    router.push({
      path: '/frontperson',
    })
  }
}

let gotoLogin = () => {
  // state.dialogVisible = true;
  state.form = {}
  state.title = '登录'
  router.push({
    path: '/login',
  })
}

let register = () => {
  insertUser(state.form.username, state.form.password).then((res) => {
    console.log(res)
    if (res.code == 200) {
      ElMessage({
        type: 'success',
        message: '注册成功',
      })
      state.dialogVisible = false
    } else {
      ElMessage({
        type: 'error',
        message: res.msg,
      })
    }
  })
}

let gotoRegister = () => {
  state.dialogVisible = true
  state.title = '注册'
}
</script>

<style scoped>
.el-header {
  text-align: center;
  height: 50px;
  line-height: 50px;
}
.el-main {
  color: #333;
  /* text-align: center; */
}

.flex {
  display: flex;
}
.flex-space-between {
  justify-content: space-between;
  align-items: center;
}

.flex-space-start {
  justify-content: flex-start;
}

.el-menu--horizontal {
  display: flex;
  justify-content: center;
}
.background-black {
  background-color: #695555;
}

.el-header {
  padding: 0;
}
.el-main {
  padding: 0;
}

.padding-right-20 {
  padding-right: 20px;
}

.el-footer {
  padding: 0;
}
.margin-top-10 {
  margin-top: 10px;
}
</style>
