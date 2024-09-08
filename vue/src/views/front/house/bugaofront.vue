<template>
  <div>
    <div class="flex-justify-content-center margin-top-10">
      <el-form :inline="true" class="demo-form-inline margin-top-10">
        <el-form-item label="布告标题">
          <el-input
            v-model="formInline.user"
            placeholder="布告标题"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit">查找</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="flex-justify-content-center">
      <div
        v-for="item in state.tableData"
        :key="item"
        class="flex-between cursorPointer"
        @click="gotoBugaoOne(item)"
      >
        <span>{{ item.title }}</span>
        <span
          >--------------------------------------------------------------------------------</span
        >
        <span>{{ item.createtime }}</span>
      </div>
    </div>

    <el-pagination
      background
      layout="prev, pager, next"
      :total="page.total"
      :page-size="10"
      class="fixed flex-justify-content-center"
      @current-change="currentchange"
    />
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import { getPageBugao, getBugaoID } from "@/api/bugao";
import { useRouter } from "vue-router";
import {ElMessage} from "element-plus"

let router = useRouter();

let formInline = reactive({
  title: "",
});

let page = reactive({
  page: 1,
  size: 10,
});

let state = reactive({
  tableData: "",
});

let param = reactive({});

let currentchange = (val) => {
  page.page = val;
  search();
};

let gotoBugaoOne = (row) => {
  router.push({
    path: "/bugaofrontDetail",
    query: {
      id: row.id,
    },
  });
};

onMounted(() => {
  search();
});

let onSubmit = () => {
  search();
};

let search = () => {
  getPageBugao(page.page, page.size, formInline.title).then((res) => {
    console.log(res, 111);
    if (res.code == 200) {
      state.tableData = res.data.records;
      page.total = res.data.total;
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};
</script>

<style scoped>
</style>