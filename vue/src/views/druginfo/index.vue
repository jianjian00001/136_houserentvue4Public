<template>
  <div>
    <div class="flex page flex-center">
      <span style="width: 7%">药品详情：</span>
      <el-input
        v-model="search"
        placeholder="请输入药品名称"
        style="width: 200px"
      />
      <el-button type="primary" class="margin-left-20" @click="searchType"
        >查询</el-button
      >
      <el-button type="success" @click="add">添加</el-button>
    </div>

    <div class="margin-top-20">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="drugcode" label="药品编码" />
        <el-table-column prop="drugname" label="药品名称" />
        <el-table-column label="药品类型" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.drugtype.drugtype
              }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="药品剂型" width="180">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.medicine.medicine
              }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="suppliername" label="供应商" />
        <el-table-column label="药品图片">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <img
                :src="scope.row.photo"
                alt=""
                style="width: 100px; height: 100px"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="createtime" label="创建时间" />
        <el-table-column prop="updatetime" label="操作时间" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              link
              type="primary"
              size="small"
              @click="updateDrugInfo(scope.row)"
              >修改</el-button
            >
            <el-button
              link
              type="primary"
              size="small"
              @click="deleteDrugInfo(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="dialogVisible" title="修改药品类型" width="50%">
      <div class="flex a-center">
        <div class="flex a-center">
          <el-form-item label="药品编号:" label-width="100px">
            <el-input v-model="param.drugcode" placeholder="请输入药品编号" />
          </el-form-item>
        </div>
        <div class="flex a-center">
          <el-form-item label="药品名称:" label-width="100px">
            <el-input v-model="param.drugname" placeholder="请输入药品名称" />
          </el-form-item>
        </div>
      </div>

      <div class="flex a-center">
        <div class="flex margin-top-20">
          <el-form-item label="药品类型:" label-width="100px">
            <el-select
              v-model="param.drugtypeid"
              filterable
              placeholder="请选择药品类型"
            >
              <el-option
                v-for="item in drugtypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </div>
        <div class="flex margin-top-20">
          <el-form-item label="药品剂型:" label-width="100px">
            <el-select
              v-model="param.medicineid"
              filterable
              placeholder="请选择药品剂型"
            >
              <el-option
                v-for="item in medicineOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </div>
      </div>

      <div class="margin-top-20">
        <el-form-item label="药品供应商：" label-width="100px">
          <el-input
            v-model="param.suppliername"
            placeholder="请输入药品供应商"
            class="input"
          />
        </el-form-item>
      </div>

      <div class="margin-top-20 flex flex-colum-center">
        <el-form-item label="药品图片：" label-width="100px">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8888/api/druginfo/addpicture"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :headers="headers"
          >
            <img v-if="param.photo" :src="param.photo" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirm()">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <el-drawer
      v-model="drawer"
      title="添加药品信息"
      :with-header="false"
      direction="rtl"
      :size="600"
    >
      <span>添加药品信息</span>
      <div class="flex margin-top-20 al-center">
        <span class="width-100">药品编码：</span>
        <el-input
          v-model="param.drugcode"
          placeholder="请输入药品编码"
          class="input"
        />
      </div>
      <div class="flex margin-top-20 al-center">
        <span class="width-100">药品名称：</span>
        <el-input
          v-model="param.drugname"
          placeholder="请输入药品名称"
          class="input"
        />
      </div>
      <div class="flex margin-top-20">
        <span class="margin-right-5">药品类型：</span>
        <el-select
          v-model="param.drugtypeid"
          filterable
          placeholder="请选择药品类型"
        >
          <el-option
            v-for="item in drugtypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
      <div class="flex margin-top-20">
        <span class="margin-right-5">药品剂型：</span>
        <el-select
          v-model="param.medicineid"
          filterable
          placeholder="请选择药品剂型"
        >
          <el-option
            v-for="item in medicineOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
      <div class="margin-top-20 flex al-center">
        <span class="width-100">药品供应商：</span>
        <el-input
          v-model="param.suppliername"
          placeholder="请输入药品供应商"
          class="input"
        />
      </div>

      <div class="margin-top-20 flex flex-colum-center">
        <span>药品图片：</span>
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8888/api/druginfo/addpicture"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :headers="headers"
        >
          <img v-if="param.photo" :src="param.photo" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </div>

      <div class="fixedright padding-right-40">
        <el-button @click="drawer = false">取消</el-button>
        <el-button type="primary" @click="addDrugInfo()">确认</el-button>
      </div>
    </el-drawer>

    <div class="flex-end">
      <el-pagination
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-size="pageSize"
        :total="totalPage"
        :pager-count="5"
        background
        layout="prev, pager, next"
      ></el-pagination>
    </div>
  </div>
</template>

  <script>
import { reactive, toRefs, onMounted, ref } from "vue";
// import {
//   getAllDrugInfo,
//   getDrugInfoId,
//   update,
//   addDrugInfoParam,
//   deleteDrugInfoRow,
//   getAllDrugType,
//   getAllMedicine,
// } from "@/api/collect";
import type, { ElMessageBox, ElMessage, UploadProps } from "element-plus";

export default {
  setup() {
    let headers = reactive({
      headers: {},
    });
    let state = reactive({
      tableData: [],
      search: "",
      dialogVisible: false,
      value2: "",
    });
    let param = reactive({
      id: "",
      drugcode: "",
      drugname: "",
      drugtypeid: "",
      suppliername: "",
      photo: "",
      medicineid: "",
      updatetime: "",
      createtime: "",
    });

    let page = reactive({
      pageSize: 10,
      pageIndex: 1,
      totalPage: 100,
    });

    let options = reactive({
      drugtypeOptions: [],
      medicineOptions: [],
    });

    // 当前页
    let currentChangeHandle = (val) => {
      page.pageIndex = val;
      getDataList();
    };

    let getDataList = () => {
      let params = {};
      if (state.search != "" || state.search != undefined) {
        params.drugname = state.search;
      }
      getAllDrugInfo(params, page.pageIndex, page.pageSize).then((res) => {
        console.log(res);
        if (res.code == 200) {
          state.tableData = res.data.list;
          page.totalPage = res.data.total;
        }
      });
    };

    const handleAvatarSuccess = (response, uploadFile) => {
      console.log(response);
      param.photo = response;
    };

    const beforeAvatarUpload = (file) => {
      console.log(file);
      if (file.type !== "image/jpeg") {
        ElMessage.error("图片不是image/jpeg");
        return false;
      } else if (file.size / 1024 / 1024 > 2) {
        ElMessage.error("图片不能超过2MB!");
        return false;
      }
      return true;
    };

    onMounted(() => {
      getDataList();
      getAllDrugTypeData();
      getAllMedicineData();
      headers["token"] = sessionStorage.getItem("token");
    });

    let getAllMedicineData = async () => {
      await getAllMedicine().then((res) => {
        for (let i = 0; i < res.data.length; i++) {
          let optionsData = {};
          optionsData.value = res.data[i].id;
          optionsData.label = res.data[i].medicine;
          console.log(optionsData);
          options.medicineOptions.push(optionsData);
        }
        console.log(options.medicineOptions, 11111);
      });
    };

    let getAllDrugTypeData = async () => {
      await getAllDrugType().then((res) => {
        for (let i = 0; i < res.data.length; i++) {
          let optionsData = {};
          optionsData.value = res.data[i].id;
          optionsData.label = res.data[i].drugtype;
          console.log(optionsData);
          options.drugtypeOptions.push(optionsData);
        }
        console.log(options.drugtypeOptions, 11111);
      });
    };

    let updateDrugInfo = (row) => {
      (param.id = ""),
        (param.drugcode = ""),
        (param.drugname = ""),
        (param.drugtypeid = ""),
        (param.suppliername = ""),
        (param.photo = ""),
        (param.medicineid = ""),
        (param.updatetime = ""),
        (param.createtime = "");
      console.log(row.id);
      getDrugInfoId(row.id).then((res) => {
        console.log(res);
        if (res.code == 200) {
          (param.id = res.data.id),
            (param.drugcode = res.data.drugcode),
            (param.drugname = res.data.drugname),
            (param.drugtypeid = res.data.drugtypeid),
            (param.suppliername = res.data.suppliername),
            (param.photo = res.data.photo),
            (param.medicineid = res.data.medicineid),
            (param.updatetime = res.data.updatetime),
            (param.createtime = res.data.createtime),
            console.log(param, 99999);
        }
      });
      state.dialogVisible = true;
    };

    let confirm = () => {
      state.dialogVisible = false;
      update(param).then((res) => {
        console.log(res);
        if (res.code == 200) {
          ElMessage({
            message: "修改成功",
            type: "success",
          });
          (param.id = ""),
            (param.drugcode = ""),
            (param.drugname = ""),
            (param.drugtypeid = ""),
            (param.suppliername = ""),
            (param.photo = ""),
            (param.medicineid = ""),
            (param.updatetime = ""),
            (param.createtime = "");
          getDataList();
        } else {
          ElMessage({
            message: "修改失败",
            type: "error",
          });
        }
      });
    };
    let searchType = () => {
      getDataList();
    };

    const drawer = ref(false);

    let add = () => {
      drawer.value = true;
      (param.id = ""),
        (param.drugcode = ""),
        (param.drugname = ""),
        (param.drugtypeid = ""),
        (param.suppliername = ""),
        (param.photo = ""),
        (param.medicineid = ""),
        (param.updatetime = ""),
        (param.createtime = "");
      console.log(param, "daadadada");
    };
    let addDrugInfo = () => {
      addDrugInfoParam(param).then((res) => {
        if (res.code == 200) {
          ElMessage({
            message: "添加成功",
            type: "success",
          });
        } else {
          ElMessage({
            message: "添加失败",
            type: "error",
          });
        }
      });
      drawer.value = false;
      getDataList();
    };

    let deleteDrugInfo = (row) => {
      // console.log(row.id);
      // deleteDrugInfoRow(row.id).then((res) => {
      //   console.log(res);
      //   if(res.code==200){

      //   }
      // });
      console.log(row);
      ElMessageBox.confirm("你确定要删除药品嘛?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteDrugInfoRow(row.id).then((res) => {
            console.log(res);
            if (res.code == 200) {
              ElMessage({
                type: "success",
                message: "删除成功",
              });
              getDataList();
            } else {
              ElMessage({
                type: "error",
                message: "删除失败",
              });
            }
          });
        })
        .catch(() => {
          ElMessage({
            type: "info",
            message: "Delete canceled",
          });
        });
      getDataList();
    };

    return {
      ...toRefs(headers),
      ...toRefs(state),
      param,
      ...toRefs(page),
      ...toRefs(options),
      updateDrugInfo,
      confirm,
      drawer,
      currentChangeHandle,
      searchType,
      add,
      addDrugInfo,
      deleteDrugInfo,
      handleAvatarSuccess,
      beforeAvatarUpload,
    };
  },
};
</script>

  <style scoped>
.page {
  margin-top: 20px;
  padding-left: 20px;
}
.flex-center {
  display: flex;
  align-items: center;
}
.fixedright {
  position: fixed;
  bottom: 0;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>