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
          <el-table-column prop="housename" label="收藏房屋名称" />
          <el-table-column prop="createtime" label="创建时间" />
          <el-table-column prop="username" label="用户" />
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <!-- <el-button
                  type="primary"
                  size="small"
                  @click="editHouseTypeParam(scope.row)"
                  >编辑</el-button
                > -->
              <el-button
                type="danger"
                size="small"
                @click="deleteCollectParam(scope.row)"
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
  import { reactive, onMounted } from "vue";

  import {getPageRemarkAll,deleteCollect} from "@/api/collect"
  
  
  import { ElMessage, ElMessageBox } from "element-plus";
  
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
  
  let param = reactive({});
  
  onMounted(() => {
    search();
  });
  
  let onSubmit = () => {
    search();
  };
  
  let search = () => {
    getPageRemarkAll(page.page, page.size, formInline.housename).then((res) => {
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
  
  let deleteCollectParam = (row) => {
    ElMessageBox.confirm("你确定要删除这个收藏吗?", "Warning", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(() => {
        deleteCollect(row.id).then((res) => {
          console.log(res);
          if (res.code == 200) {
            ElMessage({
              message: res.msg,
              type: "success",
            });
            search();
          } else {
            ElMessage({
              message: res.msg,
              type: "error",
            });
          }
        });
      })
      .catch(() => {});
  };
  
  let currentchange = (val) => {
    page.page = val;
    search();
  };
  </script>
          
      <style scoped>
  </style>