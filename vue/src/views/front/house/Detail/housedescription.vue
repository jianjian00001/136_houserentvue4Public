<template>
  <div class="flex-justify-content-center">
    <div style="width: 800px">
      <div class="flex jc-between margin-top-10">
        <span class="font-size-20 padding-top-20" style="font-weight: 700"
          >图片：</span
        >
      </div>
      <div class="flex flex-wrap" v-if="param.photo != ''">
        <div
          v-for="item in param.photo.split(',')"
          :key="item"
          class="padding-left-10 padding-top-5"
        >
          <img :src="item" class="imgstyle" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { getHouseDescriptionByHouseID } from "@/api/housedescription";
import { useRoute, useRouter } from "vue-router";
let router = useRouter();
const route = useRoute();
import { ElMessage } from "element-plus";

let param = reactive({
  photo: "",
});

onMounted(() => {
  param.houseid = route.query.id;
  console.log(param.houseid);
  getHouseDescriptionByHouseID(param.houseid).then((res) => {
    console.log(res);
    if (res.code == 200) {
      param.id = res.data.id;
      param.housename = res.data.housename;
      param.photo = res.data.photo;
      param.createtime = res.data.createtime;
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
});
</script>



<style scoped>
</style>