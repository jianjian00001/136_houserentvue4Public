<template>
  <div>
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline margin-top-20 margin-left-20"
    >
      <el-form-item label="房屋名:">
        <el-input v-model="formInline.housename" placeholder="请输入房屋名" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <div class="margin-top-20 margin-bottom-20">
      <el-table :data="state.tableData" style="width: 100%">
        <el-table-column type="index" width="50" label="ID"> </el-table-column>
        <el-table-column prop="housename" label="房屋名称" />
        <el-table-column prop="community" label="小区" />
        <el-table-column label="房屋图片">
          <template #default="scope">
            <div
              style="display: flex; align-items: center"
              v-if="scope.row.photo"
            >
              <img
                :src="scope.row.photo"
                alt=""
                style="width: 100px; height: 100px"
              />
            </div>
            <div v-else>暂时无图片</div>
          </template>
        </el-table-column>
        <el-table-column prop="area" label="房屋大小（㎡）" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="housetypename" label="房屋类型" />
        <el-table-column label="房屋状态">
          <template #default="scope">
            <el-tag
              class="ml-2"
              type="success"
              v-if="scope.row.housestatus == '0'"
              >正常</el-tag
            >
            <el-tag
              class="ml-2"
              type="danger"
              v-if="scope.row.housestatus == '1'"
              >禁用</el-tag
            >
            <el-tag class="ml-2" type="info" v-if="scope.row.housestatus == '2'"
              >下架</el-tag
            >
            <el-tag
              class="ml-2"
              type="warning"
              v-if="scope.row.housestatus == '3'"
              >锁定</el-tag
            >
            <el-tag class="ml-2" v-if="scope.row.housestatus == '4'"
              >审核中</el-tag
            >
          </template>
        </el-table-column>

        <el-table-column prop="price" label="房屋价钱（月/元）" />
        <el-table-column prop="yajin" label="房屋押金" />

        <el-table-column prop="username" label="房主" />

        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="updateHouse(scope.row)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="small"
              @click="deleteOneHouse(scope.row)"
              class="margin-top-5"
              >删除</el-button
            >
            <el-button
              type="success"
              size="small"
              @click="moreHouse(scope.row)"
              class="margin-top-5"
              >更多详情</el-button
            >

            <el-button
              type="primay"
              size="small"
              @click="houseExmine(scope.row)"
              class="margin-top-5"
              >操作</el-button
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

    <el-dialog
      v-model="state.houseDialogVisible"
      :title="form.title"
      :show-close="false"
      width="60%"
    >
      <el-form>
        <el-row :gutter="10">
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

        <el-row :gutter="10">
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

        <el-row :gutter="10">
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

        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="房主:">
              <el-button type="primary" @click="chooseHouseUser"
                >选择房主</el-button
              >
              <span>{{ param.username }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="租赁方式">
              <el-input v-model="param.rentway" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="图片">
          <el-upload
            v-model:file-list="fileList"
            class="upload-demo"
            action="#"
            :on-change="httpRequest"
            :auto-upload="false"
            limit="1"
            :on-remove="handleRemove"
            list-type="picture"
            :on-preview="handlePicturePreview"
          >
            <el-button type="primary">上传图片</el-button>
            <template #tip>
              <div class="el-upload__tip">请上传图片</div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="state.houseDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirm()">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog
      v-model="state.houseDialogVisibleMoreDetail"
      :title="form.title"
      width="60%"
    >
      <el-form>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="发布时间：">
              {{ param.publishtime }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="房屋朝向">
              <el-input v-model="param.direction" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="备注:">
              <el-input v-model="param.remark" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="地址:">
              <el-input v-model="param.address" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="房屋价钱（元/月）">
              <el-input v-model="param.price" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="租赁方式">
              <el-input v-model="param.rentway" disabled />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="state.houseDialogVisibleMoreDetail = false"
            >取消</el-button
          >
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="state.houseDialogVisiblExmine" title="审核" width="30%">
      <el-form>
        <el-form-item label="审核：">
          <el-select
            v-model="param.housestatus"
            class="m-2"
            placeholder="请选择状态"
            size="large"
            style="width: 240px"
          >
            <el-option
              v-for="item in state.options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="houseExmineConform">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog
      v-model="state.userDialogVisible"
      title="用户"
      width="50%"
      :before-close="handleClose"
    >
      <el-form :inline="true">
        <el-form-item label="用户名称:">
          <el-input
            v-model="formInline.username"
            placeholder="请输入用户名称"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="innerSearch">查询</el-button>
        </el-form-item>
      </el-form>

      <div class="margin-top-5" style="height: 300px; overflow: auto">
        <el-table
          :data="state.innerTableData"
          style="width: 100%"
          @row-click="clickRow"
        >
          <el-table-column type="index" width="50" label="ID">
          </el-table-column>
          <el-table-column prop="username" label="用户名称" />
          <el-table-column prop="truename" label="真实姓名" />
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

    <el-dialog v-model="dialogVisible">
      <div class="demo-image__preview">
        <el-image
          style="width: 100px; height: 100px"
          :src="param.photo"
          :zoom-rate="1.2"
          :max-scale="7"
          :min-scale="0.2"
          :preview-src-list="param.photo"
          :initial-index="4"
          fit="cover"
        />
      </div>
    </el-dialog>
  </div>
</template>
      
    <script setup>
import { reactive, onMounted, ref } from "vue";

import {
  addHouse,
  updateHouseParam,
  deleteHouse,
  getHouseById,
  changeHouseStatus,
  houseAddPicture,
  deleteHousePicture,
  getPageHouseByPerson
} from "@/api/house";

import { getAllHouseType } from "@/api/housetype";

import { ElMessage, ElMessageBox } from "element-plus";
import { getPageUser } from "@/api/user";

let formInline = reactive({
  housename: "",
  username: "",
});

let state = reactive({
  tableData: "",
  dialogVisible: false,
  options: [],
  placeholder: "",
  houseDialogVisible: false,
  houseDialogVisibleMoreDetail: false,
  houseDialogVisiblExmine: false,
  userDialogVisible: false,
  innerTableData: "",
  options: [
    {
      value: "0",
      label: "正常",
    },
    {
      value: "2",
      label: "下架",
    },
  ],
  houseOptions: [],
});

let page = reactive({
  page: 1,
  size: 10,
});

let innerPage = reactive({
  page: 1,
  size: 10,
});

let form = reactive({
  title: "房屋添加",
});

let param = reactive({
  photo: "",
});

let dialogVisible = ref(false);
let dialogImageUrl = ref();

const fileList = ref([
  {
    name: "food.jpeg",
    url: "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
  },
]);

const handlePicturePreview = (file, files) => {
  console.log(file, "八十多吧但是不会");
  dialogImageUrl.value = file.url;
  dialogVisible.value = true;
};

let httpRequest = (val) => {
  // 上传图片
  let formData = new FormData();
  formData.append("file", val.raw);
  houseAddPicture(formData).then((res) => {
    console.log(res);
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
      param.photo = res.data.photo;
      param.name = res.data.name;
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};

let handleRemove = async (file) => {
  let formData = new FormData();
  formData.append("filepath", param.photo);
  await deleteHousePicture(formData).then((res) => {
    console.log(res);
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
      param.photo = "";
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};

let InnerCurrentchange = (val) => {
  innerPage.page = val;
  innerSearch();
};

let clickRow = (row) => {
  param.houseuserid = row.id;
  param.username = row.username;
  console.log(param.houseuserid, param.username);
  state.userDialogVisible = false;
};

let innerSearch = () => {
  getPageUser(page.page, page.size, formInline.username).then((res) => {
    if (res.code == 200) {
      state.innerTableData = res.data.records;
      innerPage.total = res.data.total;
    } else {
      ElMessage({
        message: res.msg,
        grouping: true,
        type: "error",
      });
    }
  });
};

let houseExmine = (row) => {
  state.houseDialogVisiblExmine = true;
  param.id = row.id;
  param.housestatus = "";
};

let houseExmineConform = () => {
  changeHouseStatus(param.id, param.housestatus).then((res) => {
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
      state.houseDialogVisiblExmine = false;
      search();
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};

onMounted(() => {
  search();
});

let onSubmit = () => {
  search();
};

let search = () => {
    getPageHouseByPerson(page.page, page.size, formInline.housename).then((res) => {
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

let updateHouse = (row) => {
  state.houseOptions = [];
  fileList.value = [];
  getAllHouseType().then((res) => {
    for (let i = 0; i < res.data.length; i++) {
      let optionsData = {};
      optionsData.value = res.data[i].id;
      optionsData.label = res.data[i].housetypename;
      state.houseOptions.push(optionsData);
    }
  });
  state.houseDialogVisible = true;
  param.id = row.id;
  param.housetypename = row.housetypename;
  param.direction = row.direction;
  param.remark = row.remark;
  param.price = row.price;
  param.address = row.address;
  param.rentway = row.rentway;
  param.area = row.area;
  param.community = row.community;
  param.housedetailid = row.housedetailid;
  param.housename = row.housename;
  param.housestatus = row.housestatus;
  param.housetypeid = row.housetypeid;
  param.houseuserid = row.houseuserid;
  param.photo = row.photo;
  console.log(param.photo, "出纳十几年");
  console.log(param.photo.split("/")[param.photo.split("/").length - 1]);
  if (param.photo != "") {
    let pictureParam = {
      name: param.photo.split("/")[param.photo.split("/").length - 1],
      url: param.photo,
    };
    fileList.value.push(pictureParam);
    console.log(fileList.value, "啊收到货巴巴多斯");
  }

  param.publishtime = row.publishtime;
  param.remark = row.remark;
  param.username = row.username;
  param.yajin = row.yajin;
};

let confirm = () => {
  if (param.id) {
    state.title = "房屋更新";
    updateHouseParam(param).then((res) => {
      if (res.code == 200) {
        ElMessage({
          message: res.msg,
          type: "success",
        });
        state.houseDialogVisible = false;
        search();
      } else {
        ElMessage({
          message: res.msg,
          type: "error",
        });
      }
    });
  } else {
    addHouse(param).then((res) => {
      console.log(res);
      if (res.code == 200) {
        ElMessage({
          message: res.msg,
          type: "success",
        });
        state.houseDialogVisible = false;
        search();
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

let deleteOneHouse = (row) => {
  ElMessageBox.confirm("你确定要删除这个房屋嘛?", "Warning", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteHouse(row.id).then((res) => {
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

let chooseHouseUser = () => {
  state.userDialogVisible = true;
  innerSearch();
};

let moreHouse = (row) => {
  state.houseDialogVisibleMoreDetail = true;
  getHouseById(row.id).then((res) => {
    console.log(res, 888888);
    param.publishtime = res.data.publishtime;
    param.direction = res.data.direction;
    param.remark = res.data.remark;
    param.price = res.data.price;
    param.address = res.data.address;
    param.rentway = res.data.rentway;
  });
};
</script>
      
  <style scoped>
</style>