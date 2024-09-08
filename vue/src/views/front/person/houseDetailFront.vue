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
          <el-button type="primary" @click="add">添加</el-button>
        </el-form-item>
      </el-form>
      <div class="margin-top-20 margin-bottom-20">
        <el-table :data="state.tableData" style="width: 100%">
          <el-table-column type="index" width="50" label="ID"> </el-table-column>
          <el-table-column prop="housename" label="房屋名称" />
  
          <el-table-column label="空调">
            <template #default="scope">
              <el-tag size="small" v-if="scope.row.hasair == 1">有</el-tag>
              <el-tag size="small" v-else type="error">无</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="床">
            <template #default="scope">
              <el-tag size="small" v-if="scope.row.hasbed == 1">有</el-tag>
              <el-tag size="small" v-else type="error">无</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="热水器">
            <template #default="scope">
              <el-tag size="small" v-if="scope.row.hasheater == 1">有</el-tag>
              <el-tag size="small" v-else type="error">无</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="电话">
            <template #default="scope">
              <el-tag size="small" v-if="scope.row.hasphone == 1">有</el-tag>
              <el-tag size="small" v-else type="error">无</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="冰箱">
            <template #default="scope">
              <el-tag size="small" v-if="scope.row.hasrefrigerator == 1"
                >有</el-tag
              >
              <el-tag size="small" v-else type="error">无</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="洗衣机">
            <template #default="scope">
              <el-tag size="small" v-if="scope.row.haswasher == 1">有</el-tag>
              <el-tag size="small" v-else type="error">无</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="电话">
            <template #default="scope">
              <el-tag size="small" v-if="scope.row.haswatch == 1">有</el-tag>
              <el-tag size="small" v-else type="error">无</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="宽带">
            <template #default="scope">
              <el-tag size="small" v-if="scope.row.hasbroadband == 1">有</el-tag>
              <el-tag size="small" v-else type="error">无</el-tag>
            </template>
          </el-table-column>
  
          <el-table-column prop="createtime" label="创建时间" />
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button
                type="primary"
                size="small"
                @click="updateHouseDetailParam(scope.row)"
                >修改</el-button
              >
              <el-button
                type="danger"
                size="small"
                @click="deleteOneHouseDetail(scope.row)"
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
  
      <el-dialog v-model="state.dialogVisible" :title="state.title" width="30%">
        <div style="height: 300px;overflow: auto;">
          <el-form>
          <el-form-item label="房屋：">
            <el-button type="primary" @click="chooseHouse">选择房屋</el-button>
            <div class="flex">
              <span>{{ param.housename }}</span>
            </div>
          </el-form-item>
          <el-form-item label="空调：">
            <el-radio-group v-model="param.hasair" class="ml-4">
              <el-radio :label="1" size="large">有</el-radio>
              <el-radio :label="2" size="large">无</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="床：">
            <el-radio-group v-model="param.hasbed" class="ml-4">
              <el-radio :label="1" size="large">有</el-radio>
              <el-radio :label="2" size="large">无</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="热水器：">
            <el-radio-group v-model="param.hasheater" class="ml-4">
              <el-radio :label="1" size="large">有</el-radio>
              <el-radio :label="2" size="large">无</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="电话：">
            <el-radio-group v-model="param.hasphone" class="ml-4">
              <el-radio :label="1" size="large">有</el-radio>
              <el-radio :label="2" size="large">无</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="冰箱：">
            <el-radio-group v-model="param.hasrefrigerator" class="ml-4">
              <el-radio :label="1" size="large">有</el-radio>
              <el-radio :label="2" size="large">无</el-radio>
            </el-radio-group>
          </el-form-item>
  
          <el-form-item label="洗衣机：">
            <el-radio-group v-model="param.haswasher" class="ml-4">
              <el-radio :label="1" size="large">有</el-radio>
              <el-radio :label="2" size="large">无</el-radio>
            </el-radio-group>
          </el-form-item>
  
          <el-form-item label="电视：">
            <el-radio-group v-model="param.haswatch" class="ml-4">
              <el-radio :label="1" size="large">有</el-radio>
              <el-radio :label="2" size="large">无</el-radio>
            </el-radio-group>
          </el-form-item>
  
          <el-form-item label="宽带：">
            <el-radio-group v-model="param.hasbroadband" class="ml-4">
              <el-radio :label="1" size="large">有</el-radio>
              <el-radio :label="2" size="large">无</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
  
        </div>      
        
  
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="confirm()">确认</el-button>
          </span>
        </template>
      </el-dialog>
  
      <el-dialog
        v-model="state.houseDialogVisible"
        title="房屋"
        width="60%"
        :before-close="handleClose"
      >
        <el-form :inline="true">
          <el-form-item label="房屋名称:">
            <el-input
              v-model="formInline.housename"
              placeholder="请输入房屋名称"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="innerSearch">查询</el-button>
          </el-form-item>
        </el-form>
  
        <div class="margin-top-5">
          <el-table
            :data="state.innerTableData"
            style="width: 100%"
            @row-click="clickRow"
          >
            <el-table-column type="index" width="50" label="ID">
            </el-table-column>
            <el-table-column prop="housename" label="房屋名称" />
            <el-table-column prop="housetypename" label="房屋类型" />
          </el-table>
        </div>
  
        <el-pagination
          background
          layout="prev, pager, next"
          :total="innerPage.total"
          :page-size="10"
          class="fixed"
          @current-change="InnerCurrentchange"
        />
  
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="state.houseDialogVisible = false">取消</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </template>
    
  <script setup>
  import { reactive, onMounted } from "vue";
  
  import {
    getPageHouseDetailByPerson,
    addHouseDetail,
    getHouseDetailById,
    updateHouseDetail,
    deleteHouseDetail
  } from "@/api/houseDetail";
  
  import { ElMessage, ElMessageBox } from "element-plus";
  import { getPageHouseByPerson } from "@/api/house";
  
  let formInline = reactive({
    housename: "",
  });
  
  let state = reactive({
    tableData: "",
    dialogVisible: false,
    title: "添加",
    houseDialogVisible: false,
    innerTableData: "",
  });
  
  let page = reactive({
    page: 1,
    size: 10,
  });
  
  let innerPage = reactive({
    page: 1,
    size: 10,
    total: 1,
  });
  
  let param = reactive({});
  
  onMounted(() => {
    search();
  });
  
  let clickRow = (row) => {
    console.log(row, 66666);
    param.houseid = row.id;
    param.housename = row.housename;
    console.log(param.houseid, param.housename);
    state.houseDialogVisible = false;
  };
  
  let chooseHouse = () => {
    state.houseDialogVisible = true;
    innerSearch();
  };
  
  let onSubmit = () => {
    search();
  };
  
  let search = () => {
    getPageHouseDetailByPerson(page.page, page.size, formInline.housename).then((res) => {
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
  
  let currentchange = (val) => {
    page.page = val;
    search();
  };
  
  let InnerCurrentchange = (val) => {
    innerPage.page = val;
    innerSearch();
  };
  
  // 房屋查询
  let innerSearch = () => {
    getPageHouseByPerson(innerPage.page, innerPage.size, formInline.housename).then(
      (res) => {
        console.log(res, 111);
        if (res.code == 200) {
          state.innerTableData = res.data.records;
          innerPage.total = res.data.total;
        } else {
          ElMessage({
            message: res.msg,
            type: "error",
          });
        }
      }
    );
  };
  
  let updateHouseDetailParam = (row) => {
    state.dialogVisible = true;
    state.title="编辑"
    // 获取详情页
    getHouseDetailById(row.id).then((res) => {
      console.log(res);
      param.id = res.data.id;
      param.housename = res.data.housename;
      param.hasair = res.data.hasair;
      param.hasbed = res.data.hasbed;
      param.hasheater = res.data.hasheater;
      param.hasphone = res.data.hasphone;
      param.hasrefrigerator = res.data.hasrefrigerator;
      param.haswasher = res.data.haswasher;
      param.hasbroadband=res.data.hasbroadband
      param.haswatch = res.data.haswatch;
      param.houseid = res.data.houseid;
      param.createtime = res.data.createtime;
    });
  };
  
  let confirm = () => {
    if (param.id) {
      updateHouseDetail(param).then((res) => {
        console.log(res);
        if (res.code == 200) {
          ElMessage({
            message: res.msg,
            type: "success",
          });
          search()
        } else {
          ElMessage({
            message: res.msg,
            type: "error",
          });
        }
      });
    } else {
      console.log(param, 455454664);
      addHouseDetail(param).then((res) => {
        console.log(res);
        if (res.code == 200) {
          ElMessage({
            message: res.msg,
            type: "success",
          });
          search()
        } else {
          ElMessage({
            message: res.msg,
            type: "error",
          });
        }
      });
    }
    search();
    state.dialogVisible = false;
  };
  
  let deleteOneHouseDetail = (row) => {
    ElMessageBox.confirm("你确定要删除这个房屋摆设嘛?", "Warning", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(() => {
        deleteHouseDetail(row.id).then((res) => {
          console.log(res);
          if (res.code == 200) {
            ElMessage({
              message: res.msg,
              type: "success",
            });
            search()
          } else {
            ElMessage({
              message: res.msg,
              type: "error",
            });
          }
        });
      })
      .catch(() => {});
    search();
  };
  
  let add = () => {
    state.dialogVisible = true;
    param.hasair = "";
    param.hasbed = "";
    param.hasheater = "";
    param.hasphone = "";
    param.hasrefrigerator = "";
    param.haswasher = "";
    param.haswatch = "";
    param.houseid = "";
    param.housename = "";
    param.id = "";
    param.createtime = "";
    param.hasbroadband=""
  };
  </script>
    
    <style scoped>
  </style>