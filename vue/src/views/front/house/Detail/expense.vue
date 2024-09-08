<template>
  <div class="flex-justify-content-center">
    <div style="width: 800px">
      <span class="font-size-20 padding-top-20" style="font-weight: 700"
        >费用信息：</span
      >
      <div class="flex flex-between font-size-20 margin-top-20">
        <div class="flex flex-direction-column flex-colum-center">
          <span>付款方式 </span>
          <span>月份</span>
        </div>
        <div class="flex flex-direction-column flex-colum-center">
          <span>租金（元/月） </span>
          <span>{{param.price}}</span>
        </div>
        <div class="flex flex-direction-column flex-colum-center">
          <span>押金</span>
          <span>{{ param.yajin}}</span>
        </div>
        <div class="flex flex-direction-column flex-colum-center">
          <span>服务费 (元)</span>
          <span>无 </span>
        </div>
        <div class="flex flex-direction-column flex-colum-center">
          <span>中介费 (元)</span>
          <span>100 </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getHouseById } from "@/api/house";
let router = useRouter();
const route = useRoute();
let state = reactive({
  activeIndex: "1",
  id: "",
});

let param = reactive({});

onMounted(() => {
  state.id = route.query.id;
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
    param.yajin=res.data.yajin
  });
});
</script>

<style scoped>
</style>