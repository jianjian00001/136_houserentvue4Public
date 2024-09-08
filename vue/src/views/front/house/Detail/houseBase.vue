<template>
  <div class="flex-justify-content-center">
    <div style="width: 800px">
      <span class="font-size-20 padding-top-20" style="font-weight: 700"
        >基本信息：</span
      >
      <div class="margin-left-10 margin-top-20 padding-left-40">
        面积：{{param.area}}㎡
      </div>
      <div class="margin-left-10 margin-top-10 padding-left-40 flex-between">
        <div>租赁方式：{{param.rentway}}</div>
        <div>房屋类型：{{ param.housetypename}}</div>
        <div>小区：{{param.community}}</div>
      </div>

      <div
        class="flex flex-between margin-top-10 padding-left-40 margin-left-10"
      >
        <div>朝向：{{param.direction}}</div>
        <div>用水：民水</div>
        <div>用电：民电</div>
      </div>

      <div class="font-size-20 margin-top-20" style="font-weight: 700">
        配套设施：
      </div>
      <div class="flex flex-between font-size-20 margin-top-20">
        <div class="flex flex-direction-column flex-colum-center" :style="form.hasrefrigerator=='1'?'color:#409eff':'color:grey'">
          <el-icon><Refrigerator /></el-icon>
          <span>冰箱 </span>
        </div>
        <div class="flex flex-direction-column flex-colum-center" :style="form.haswatch=='1'?'color:#409eff':'color:grey'">
          <el-icon><Platform /></el-icon>
          <span>电视 </span>
        </div>
        <div class="flex flex-direction-column flex-colum-center" :style="form.hasphone=='1'?'color:#409eff':'color:grey'">
          <el-icon><Phone /></el-icon>
          <span>电话 </span>
        </div>
        <div class="flex flex-direction-column flex-colum-center" :style="form.hasair=='1'?'color:#409eff':'color:grey'">
          <el-icon><HotWater /></el-icon>
          <span>空调 </span>
        </div>
      </div>
      <div class="flex flex-between font-size-20 margin-top-20">
        <div class="flex flex-direction-column flex-colum-center"  :style="form.haswasher=='1'?'color:#409eff':'color:grey'">
          <el-icon><ShoppingTrolley /></el-icon>
          <span>洗衣机 </span>
        </div>
        <div class="flex flex-direction-column flex-colum-center"  :style="form.hasheater=='1'?'color:#409eff':'color:grey'">
          <el-icon><Pouring /></el-icon>
          <span>热水器 </span>
        </div>

        <div class="flex flex-direction-column flex-colum-center"  :style="form.hasbed=='1'?'color:#409eff':'color:grey'">
          <el-icon><FolderRemove /></el-icon>
          <span>床 </span>
        </div>

        <div class="flex flex-direction-column flex-colum-center" :style="form.hasbroadband=='1'?'color:#409eff':'color:grey'">
          <el-icon><MagicStick /></el-icon>
          <span>宽带 </span>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import {reactive,onMounted} from "vue"

import {getHouseById} from "@/api/house"
import {ElMessage } from "element-plus"
import {useRoute,useRouter} from "vue-router"
import { getHouseDetailById,getHouseDetailByHouseID } from "@/api/houseDetail";

let param=reactive({

})

let form=reactive({

})

let state=reactive({
  id:""
})

onMounted(() => {
  const route = useRoute();
  state.id = route.query.id;
  getHouseById(state.id).then((res) => {
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
  getHouseDetailByHouseID(state.id).then((res) => {
    console.log(res, 666666);
    if (res.code == 200) {
      state.dialogVisible = true;
      form.hasair = res.data.hasair;
      form.hasbed = res.data.hasbed;
      form.hasheater = res.data.hasheater;
      form.hasphone = res.data.hasphone;
      form.hasrefrigerator = res.data.hasrefrigerator;
      form.haswatch = res.data.haswatch;
      form.hasbroadband = res.data.hasbroadband;
      form.haswasher = res.data.haswasher;
    } else {
      // ElMessage({
      //   message: res.msg,
      //   type: "error",
      // });
    }
  });


});

</script>



<style scoped>
</style>