<template>
  <div>
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline margin-top-20 margin-left-20"
    >
      <el-form-item label="房屋名称:">
        <el-input v-model="formInline.housename" placeholder="请输入房屋名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <div class="margin-top-20 margin-bottom-20">
      <el-table :data="state.tableData" style="width: 100%">
        <el-table-column type="index" width="50" label="ID"> </el-table-column>
        <el-table-column prop="housename" label="房屋名称" />
        <el-table-column prop="createtime" label="创建时间" />
        <el-table-column prop="remarkname" label="评论" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <!-- <el-button
              type="primary"
              size="small"
              @click="editHouseRemarkParam(scope.row)"
              >编辑</el-button
            > -->
            <el-button
              type="danger"
              size="small"
              @click="deleteHouseRemarkParam(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="page.total"
      :page-size="10"
      class="fixed"
      @current-change="currentchange"
    />
  </div>
</template>

<script setup>
import { reactive, onMounted, ref } from "vue";

import { getPageRemarkByPerson,deleteRemark } from "@/api/remark";

import {ElMessageBox,ElMessage} from "element-plus"

let formInline = reactive({
  housename: "",
});

let state = reactive({
  tableData: "",
});

let page = reactive({
  page: 1,
  size: 10,
});

let editHouseRemarkParam=(row)=>{

}

onMounted(() => {
  search();
});

let onSubmit=()=>{
  search()
}

let deleteHouseRemarkParam=(row)=>{
   ElMessageBox.confirm("你确定要删除这个评论嘛?", "Warning", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(() => {
        deleteRemark(row.id).then(res=>{
          console.log(res);
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
        });
        search()
      })
      .catch(() => {});
}

let currentchange=(val)=>{
  page.page=val;
  search()
}

let search = () => {
  getPageRemarkByPerson(page.page, page.size, formInline.housename).then(
    (res) => {
      if (res.code == 200) {
        state.tableData = res.data.records;
        page.total = res.data.total;
      } else {
        ElMessage({
          message: res.msg,
          type: "error",
        });
      }
    }
  );
};
</script>


<style scoped>
</style>