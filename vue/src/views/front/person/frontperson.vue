<template>
  <div>
    <el-container class="layout-container-demo" style="height: 500px">
      <el-aside width="200px">
        <el-scrollbar>
          <el-menu>
            <el-sub-menu index="1">
              <template #title>
                <el-icon><message /></el-icon>基础信息
              </template>
              <el-menu-item-group>
                <el-menu-item index="1-1" @click="gotoPerson">个人信息</el-menu-item>
                <el-menu-item index="1-2" @click="gotoRemark">评论</el-menu-item>
                <el-menu-item index="1-3" @click="gotoCollect">个人收藏</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>
                <el-icon><message /></el-icon>房屋信息
              </template>
              <el-menu-item-group>
                <el-menu-item index="2-1" @click="gotoHouse">房屋信息</el-menu-item>
                <el-menu-item index="2-2" @click="gotoHouseDescription">房屋描述</el-menu-item>
                <el-menu-item index="2-3" @click="gotoBook">我的预约</el-menu-item>
                <el-menu-item index="2-4" @click="gotoHouseBook">房屋预约</el-menu-item>
                <el-menu-item index="2-5" @click="gotoHouseDetail">房屋详情</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>

      <el-container>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router"
import {onMounted} from "vue"


let router=useRouter()


let gotoPerson=()=>{
   router.push({
    path:'/frontPersonDetail'
   })
}

let gotoRemark=()=>{
  router.push({
    path:'/remarkmenu'
  })
}

let gotoCollect=()=>{
  router.push({
    path:'/collectfront'
  })
}

let gotoHouse=()=>{
  router.push({
    path:'/housefront'
  })
}

let gotoHouseDescription=()=>{
  router.push({
    path:'/housedescriptionfrontPerson'
  })
}

let gotoBook=()=>{
  router.push({
    path:'/houseBookFront'
  })
}

let gotoHouseBook=()=>{
  router.push({
    path:'/houseBookHouseFront'
  })
}

let gotoHouseDetail=()=>{
  router.push({
    path:'/houseDetailFront'
  })
}

</script>


<style scoped>
</style>