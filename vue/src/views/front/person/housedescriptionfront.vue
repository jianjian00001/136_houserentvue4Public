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
          <el-table-column prop="createtime" label="创建时间" />
          <el-table-column label="图片" width="600">
            <template #default="scope">
              <div class="flex flex-wrap" v-if="scope.row.photo != ''">
                <div
                  v-for="item in scope.row.photo.split(',')"
                  :key="item"
                  class="padding-left-5"
                >
                  <img :src="item" style="width: 100px; height: 100px" />
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button
                type="primary"
                size="small"
                @click="updateHouseDescriptionOne(scope.row)"
                >修改</el-button
              >
              <el-button
                type="danger"
                size="small"
                @click="deleteOneHouseDescription(scope.row)"
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
  
      <el-dialog v-model="state.dialogVisible" :title="title" width="60%">
        <el-form :model="form">
          <el-form-item label="房屋：">
            <el-button
              type="primary"
              round
              @click="chooseHouse"
              :disabled="param.id!=''?true:false"
              >选择房屋</el-button
            >
            <span>{{ param.housename }}</span>
          </el-form-item>
          <el-form-item label="图片：">
            <el-upload
              v-model:file-list="fileList"
              action="#"
              :on-change="httpRequest"
              :auto-upload="false"
              list-type="picture-card"
              :on-preview="handlePicturePreview"
              :on-remove="handleRemove"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>
  
            <el-dialog v-model="state.innerDialogVisible">
              <img
                w-full
                :src="state.dialogImageUrl"
                alt="Preview Image"
                style="height: 400px; height: 400px"
              />
            </el-dialog>
          </el-form-item>
        </el-form>
  
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="confirm()">确认</el-button>
          </span>
        </template>
      </el-dialog>
  
      <el-dialog v-model="state.houseDialogVisible" title="选择房屋" width="70%">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="房屋名称">
            <el-input
              v-model="formInline.housename"
              placeholder="请输入房屋名称"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查找</el-button>
          </el-form-item>
        </el-form>
  
        <div class="margin-top-20 margin-bottom-20">
          <el-table
            :data="state.innerTableData"
            style="width: 100%; height: 300px; overflow: auto"
            @row-click="rowClick"
          >
            <el-table-column type="index" width="50" label="ID">
            </el-table-column>
            <el-table-column prop="housename" label="房屋名称" />
            <el-table-column prop="community" label="小区" />
            <el-table-column prop="area" label="房屋大小（㎡）" />
            <el-table-column prop="address" label="地址" />
            <el-table-column prop="username" label="房主" />
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
      </el-dialog>
    </div>
  </template>
      
    <script setup>
  import { reactive, onMounted, ref } from "vue";
  
  import {
    houseDescribeAddPicture,
    deleteHouseDescriptionPic,
    addHouseDescription,
    getPageHouseDescriptionByPerson,
    deleteHouseDescription,
    getHouseDescriptionByID,
    updateHouseDescription
  } from "@/api/housedescription";
  
  import { ElMessage, ElMessageBox } from "element-plus";
  
  import { getPageHouseByPerson } from "@/api/house";
  
  let formInline = reactive({
    housename: "",
    housename: "",
  });
  
  let state = reactive({
    tableData: "",
    innerTableData: "",
    dialogVisible: false,
    innerDialogVisible: false,
    dialogImageUrl: "",
    houseDialogVisible: false,
  });
  
  let innerPage = reactive({
    page: 1,
    size: 10,
  });
  
  let page = reactive({
    page: 1,
    size: 10,
  });
  
  let param = reactive({
    id: "",
    housename: "",
    photo: "",
    houseid: "",
    phototemp:""
  });
  
  const fileList = ref([]);
  
  onMounted(() => {
    search();
  });
  
  let rowClick = (row) => {
    console.log(row, 123456);
    param.houseid = row.id;
    param.housename = row.housename;
    state.houseDialogVisible = false;
  };
  
  let InnerCurrentchange = (val) => {
    innerPage.page = val;
    innerSearch();
  };
  
  let chooseHouse = () => {
    state.houseDialogVisible = true;
    innerSearch();
  };
  
  let httpRequest = async (val) => {
    // 上传图片
    if (val.status !== "ready") return;
    const isLt10M = val.size / 1024 / 1024 < 10;
    if (val.type == "image/jpeg" || val.type == "image/png") {
      ElMessage({
        message: "上传必须为图片",
        type: "error",
      });
      fileList.value = [];
      return false;
    }
    if (!isLt10M) {
      ElMessage({
        message: "图片大小不能为10M",
        type: "error",
      });
      return false;
    }
    let formData = new FormData();
    formData.append("file", val.raw);
    await houseDescribeAddPicture(formData).then((res) => {
      console.log(res);
      if (res.code == 200) {
        ElMessage({
          message: res.msg,
          type: "success",
        });
        fileList.value[fileList.value.length - 1].url = res.data;
        console.log(fileList.value, "餐宿长难句");
      } else {
        ElMessage({
          message: res.msg,
          type: "error",
        });
      }
    });
  };
  
  const handlePicturePreview = (file, files) => {
    state.dialogImageUrl = file.url;
    state.innerDialogVisible = true;
  };
  
  let handleRemove = async (uploadFile, uploadFiles) => {
    console.log(uploadFile, uploadFiles, 9966);
    let formData = new FormData();
    formData.append("filepath", uploadFile.url);
    await deleteHouseDescriptionPic(formData).then((res) => {
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
  };
  
  let onSubmit = () => {
    search();
  };
  
  let search = () => {
    getPageHouseDescriptionByPerson(page.page, page.size, formInline.housename).then(
      (res) => {
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
      }
    );
  };
  
  let innerSearch = () => {
    getPageHouseByPerson(page.page, page.size, formInline.housename).then((res) => {
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
    });
  };
  
  let currentchange = (val) => {
    page.page = val;
    search();
  };
  
  let updateHouseDescriptionOne = (row) => {
    param.phototemp=""
    fileList.value=""
    state.dialogVisible = true;
    // 获取详情页
    getHouseDescriptionByID(row.id).then((res) => {
      console.log(res);
      if (res.code == 200) {
        param.id = res.data.id;
        param.housename = res.data.housename;
        fileList.value = res.data.phototemp;
        param.createtime = res.data.createtime;
      }
    });
  };
  
  let confirm = () => {
    if (param.id) {
      param.phototemp = fileList.value;
      console.log(param.phototemp,"test数据")
      updateHouseDescription(param).then((res) => {
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
      param.phototemp = fileList.value;
      addHouseDescription(param).then((res) => {
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
    state.dialogVisible = false;
  };
  
  let deleteOneHouseDescription = (row) => {
    ElMessageBox.confirm("你确定要删除这个房屋详情嘛?", "Warning", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(() => {
        deleteHouseDescription(row.id).then((res) => {
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
  };
  
  let add = () => {
    state.dialogVisible = true;
    param.id = "";
    param.housename = "";
    fileList.value=[]
    param.phototemp=""
  };
  </script>
      
      <style scoped>
  </style>