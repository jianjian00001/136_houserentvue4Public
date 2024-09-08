<template>
  <div>
    <div
      class="background-black flex flex-space-between"
      style="
        height: 50px;
        color: aliceblue;
        margin-left: 150px;
        margin-right: 150px;
        margin-top: 10px;
      "
    >
      <span class="padding-left-10">房屋详情</span>
      <span class="padding-right-10">当前位置为房屋详情</span>
    </div>
    <div class="text-center margin-top-10">
      <p class="font-size-40">{{ param.housename }}</p>
      <div class="margin-top-10">房源维护时间：{{ param.publishtime }}</div>
    </div>
    <div class="flex flex-justify-content-center margin-top-20">
      <img :src="param.photo" class="imgstyle" />
      <div class="margin-left-10">
        <div style="color: red; font-size: 20px">{{ param.price }}元/月</div>
        <div>
          <div class="margin-top-10">租赁方式：{{ param.rentway }}</div>
          <div class="flex margin-top-10">
            <div>房屋类型：{{ param.housetypename }}</div>
            <div class="margin-left-10">房屋面积：{{ param.area }}㎡</div>
          </div>

          <div class="margin-top-10">精装修</div>
          <div class="margin-top-10">朝向楼层：{{ param.direction }}</div>
          <div class="margin-top-10">地址：{{ param.address }}</div>
          <div class="margin-top-10">备注：{{ param.remark }}</div>
        </div>
        <el-button type="primary" class="margin-top-10" @click="bookHouse(param.id)"
          >预约看房</el-button
        >
        <!-- <el-button type="success" @click="onSubmit" class="margin-top-10"
          >租房</el-button
        > -->
        <el-button
          type="success"
          @click="cancelCollect"
          class="margin-top-10"
          v-if="state.hascollect == 1"
          >取消收藏</el-button
        >
        <el-button
          type="primary"
          @click="collectHouse"
          class="margin-top-10"
          v-if="state.hascollect == 0"
          >收藏</el-button
        >
      </div>
    </div>

    <el-menu
      :default-active="state.activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      style="text-align: center; display: flex; justify-content: center"
    >
      <el-menu-item index="1">房屋信息</el-menu-item>
      <!-- <el-menu-item index="2">地址和交通</el-menu-item> -->
      <el-menu-item index="3">费用信息</el-menu-item>
      <el-menu-item index="4">房源描述</el-menu-item>
      <el-menu-item index="5">评论</el-menu-item>
    </el-menu>

    <router-view />

    <el-dialog
      v-model="state.dialogVisible"
      title="预约时间"
      width="500"
      :before-close="handleClose"
    >
      <el-form :model="form" label-width="120px">
        <el-form-item label="时间">
          <el-date-picker
            v-model="uploadParam.booktime"
            type="datetime"
            :disabled-date="disabledDate"
            placeholder="请选择预约时间"
            value-format="YYYY-MM-DD hh:mm:ss"

          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="state.dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="conform">
            预约
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getHouseById } from "@/api/house";
import {
  collectHouseByUser,
  cancelCollectHouse,
  collectHouseCount,
} from "@/api/collect";
import { ElMessage } from "element-plus";

import {addBookTime} from "@/api/booktime"

import houseBase from "@/views/front/house/Detail/houseBase.vue";
let router = useRouter();

let state = reactive({
  activeIndex: "1",
  id: "",
  hascollect: 0,
  dialogVisible: false,
});

let param = reactive({});

let uploadParam=reactive({

})

let bookHouse = (id) => {
  uploadParam.houseid=id
  state.dialogVisible = true;
};

let conform=()=>{
  addBookTime(uploadParam).then(res=>{
    console.log(res,"把彩色的黑")
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  })
}

let disabledDate=(time)=>{
  return time.getTime() < Date.now()
}

let cancelCollect = () => {
  cancelCollectHouse(state.id).then((res) => {
    if (res.code == 200) {
      state.hascollect = 0;
      ElMessage({
        message: res.msg,
        type: "success",
      });
    } else {
      state.hascollect = 1;
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};

onMounted(() => {
  const route = useRoute();
  state.id = route.query.id;
  router.push({
    path: "/houseBase",
    query: {
      id: state.id,
    },
  });
  getHouseById(state.id).then((res) => {
    console.log(res, "哈市");
    param.id = res.data.id;
    param.housetypename = res.data.housetypename;
    param.publishtime = res.data.publishtime;
    param.direction = res.data.direction;
    param.remark = res.data.remark;
    param.price = res.data.price;
    param.address = res.data.address;
    param.rentway = res.data.rentway;
    param.area = res.data.area;
    param.community = res.data.community;
    param.housedetailid = res.data.housedetailid;
    param.housename = res.data.housename;
    param.housestatus = res.data.housestatus;
    param.housetypeid = res.data.housetypeid;
    param.houseuserid = res.data.houseuserid;
    param.photo = res.data.photo;
  });
  collectHouseCount(state.id).then((res) => {
    if (res.code == 200) {
      state.hascollect = 1;
    } else {
      state.hascollect = 0;
    }
  });
});

let collectHouse = () => {
  collectHouseByUser(state.id).then((res) => {
    console.log(res);
    if (res.code == 200) {
      state.hascollect = 1;
      ElMessage({
        message: res.msg,
        type: "success",
      });
    } else {
      state.hascollect = 0;
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};

let handleSelect = (key) => {
  console.log(key);
  state.activeIndex = key;
  if (state.activeIndex == "1") {
    console.log(state.activeIndex, "dsfnujsnfjd");
    router.push({
      path: "/houseBase",
      query: {
        id: state.id,
      },
    });
  }
  if (state.activeIndex == "3") {
    console.log(state.activeIndex, "dsfnujsnfjd");
    router.push({
      path: "/expense",
      query: {
        id: state.id,
      },
    });
  }
  if (state.activeIndex == "5") {
    console.log(state.activeIndex, "dsfnujsnfjd");
    router.push({
      path: "/remark",
      query: {
        id: state.id,
      },
    });
  }
  if (state.activeIndex == "4") {
    router.push({
      path: "/housedescriptionFront",
      query: {
        id: state.id,
      },
    });
  }
};
</script>


<style scoped>
.font-size-40 {
  font-size: 40px;
}
.imgstyle {
  width: 400px;
  height: 300px;
}
p {
  margin: 0;
  padding: 0;
}
.text-center {
  text-align: center;
}
.flex-justify-content-center {
  justify-content: center;
  /* align-items: center; */
}

.background-black {
  background-color: #695555;
}

.flex-space-between {
  justify-content: space-between;
  align-items: center;
}
</style>