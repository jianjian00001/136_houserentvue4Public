<template>
  <div>
    <div class="font-size-20 margin-top-20" style="margin-left: 200px">
      个人信息
    </div>

    <el-form :model="form" label-width="120px" style="margin-top: 40px">
      <el-form-item label="呢称：">
        <el-input v-model="state.param.nickname" style="width: 400px" />
      </el-form-item>
      <el-form-item label="性别：">
        <el-radio-group v-model="state.param.sex" class="ml-4">
          <el-radio label="1" size="large">男</el-radio>
          <el-radio label="2" size="large">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="邮箱：">
        <el-input v-model="state.param.email" style="width: 400px" />
      </el-form-item>
      <el-form-item label="真实姓名：">
        <el-input v-model="state.param.truename" style="width: 400px" />
      </el-form-item>
      <el-form-item label="电话：">
        <el-input v-model="state.param.tel" style="width: 400px" />
      </el-form-item>
      <el-form-item label="头像：">
          <el-upload
            class="avatar-uploader"
            :headers="state.headers"
            action="http://localhost:8888/api/user/addpicture"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :data="state.param"
          >
            <img v-if="state.param.pic" :src="state.param.pic" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit" v-hasPermission="'user:person:search'">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { Plus } from '@element-plus/icons-vue'
import { reactive, ref, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { updateUserById, getperson } from "@/api/user";
let state = reactive({
  param: {
    name: "",
    tel: "",
    address: "",
    email: "",
    sex: "",
    pic:""
  },

  headers: {
        token: sessionStorage.getItem("token"), //headers属性中添加token，这个属性是el-upload自带的用来设置上传请求头部
      },
});

onMounted(() => {
  getperson().then((res) => {
    console.log(res, 123456);
    if (res.code == 200) {
      state.param = res.data;
      console.log(state.param);
    }
  });
});

let handleAvatarSuccess=(res)=> {
      console.log(res);
      state.param.pic = res;
    }

let beforeAvatarUpload = (file) => {
  const isJPG = file.type === "image/jpeg";
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isJPG) {
    this.$message.error("上传头像图片只能是 JPG 格式!");
  }
  if (!isLt2M) {
    this.$message.error("上传头像图片大小不能超过 2MB!");
  }
  return isJPG && isLt2M;
};

let onSubmit = () => {
  updateUserById(state.param).then((res) => {
    if (res.code == 200) {
      ElMessage({
        message: "修改信息成功",
        type: "success",
      });
    }
  });
};
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 10px dashed #d64747;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #0c58c1;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
