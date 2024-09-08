<template>
  <div class="flex-justify-content-center">
    <div class="font-size-30 margin-top-20">
      {{ param.title }}
    </div>
  </div>
  <div class="flex-justify-content-center">
    <div class="margin-top-20">{{ param.createtime }}</div>
  </div>

  <div class="flex-justify-content-center">
    <div v-html="param.description"></div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { useRoute } from "vue-router";
import { getBugaoID } from "@/api/bugao";

let param = reactive({});

let state = reactive({
  id: "",
});

onMounted(() => {
  const route = useRoute();
  state.id = route.query.id;
  getBugaoID(state.id).then((res) => {
    console.log(res);
    param.id = res.data.id;
    param.title = res.data.title;
    param.description = res.data.description;
    param.createtime = res.data.createtime;
    param.createby = res.data.createby;
  });
});
</script>

<style scoped>
</style>