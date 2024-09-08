<template>
  <div>
    <el-result
        icon="error"
        title="找不到页面"
        sub-title="404"
      >
        <template #extra>
          <el-button type="primary" @click="gotoLogin">登录界面</el-button>
        </template>
      </el-result>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router"

let router=useRouter()

let gotoLogin=()=>{
    router.push({
        path:'/login'
    })
}
</script>


<style scoped>
</style>