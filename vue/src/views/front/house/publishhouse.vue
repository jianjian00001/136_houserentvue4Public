<template>
  <div class="flex-justify-content-center">
    <div style="width: 800px">
      <div class="flex jc-between margin-top-10">
        <span class="font-size-20 padding-top-20" style="font-weight: 700"
          >发布房屋：</span
        >
        <el-button type="primary" @click="add">发布</el-button>
      </div>
    </div>
  </div>

  <div class="flex-justify-content-center margin-top-10">
    <el-form>
      <el-row :gutter="5">
        <el-col :span="8">
          <el-form-item label="房屋名称">
            <el-input v-model="param.housename" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="小区名称">
            <el-input v-model="param.community" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="房屋大小（㎡）">
            <el-input v-model="param.area" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="5">
        <el-col :span="8">
          <el-form-item label="地址:">
            <el-input v-model="param.address" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="房屋朝向">
            <el-input v-model="param.direction" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="房屋价钱（元/月）">
            <el-input v-model="param.price" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="5">
        <el-col :span="8">
          <el-form-item label="房屋押金">
            <el-input v-model="param.yajin" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注">
            <el-input v-model="param.remark" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="房屋类型">
            <el-select
              v-model="param.housetypeid"
              class="m-2"
              placeholder="请选择房屋类型"
              size="large"
              style="width: 240px"
            >
              <el-option
                v-for="item in state.houseOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="5">
        <el-col :span="8">
          <el-form-item label="租赁方式">
            <el-input v-model="param.rentway" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="图片：">
        <el-upload
          v-model:file-list="fileList"
          action="#"
          :on-change="httpRequest"
          :auto-upload="false"
          list-type="picture-card"
          :on-preview="handlePicturePreview"
          :on-remove="handleRemove"
          limit="1"
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
  </div>
</template>

<script setup>
import { onMounted, reactive,ref } from "vue";

import {houseAddPicture,deleteHousePicture,addHouse } from "@/api/house"
import {ElMessage } from "element-plus"
import { getAllHouseType } from "@/api/housetype";

let param = reactive({
  houseuserid:sessionStorage.getItem("userid")
});

let state = reactive({
  houseOptions: [],
});

const fileList = ref([]);

onMounted(()=>{
  getAllHouseType().then((res) => {
    for (let i = 0; i < res.data.length; i++) {
      let optionsData = {};
      optionsData.value = res.data[i].id;
      optionsData.label = res.data[i].housetypename;
      state.houseOptions.push(optionsData);
    }
  });
})

let add=()=>{
  addHouse(param).then((res) => {
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
  
} 


let handleRemove = async (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles, 9966);
  let formData = new FormData();
  formData.append("filepath", uploadFile.url);
  await deleteHousePicture(formData).then((res) => {
    console.log(res);
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
      param.photo=""
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
let httpRequest = async (val) => {
  // 上传图片
  let formData = new FormData();
  formData.append("file", val.raw);
  console.log("v你说的那是你家")
  await houseAddPicture(formData).then((res) => {
    console.log(res.data.photo,"CBA撒市场部");
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
      fileList.value[ fileList.value.length-1].url = res.data.photo;
      param.photo=res.data.photo
      console.log(fileList.value, "餐宿长难句");
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