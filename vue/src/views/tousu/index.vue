<template>
  <div>
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline margin-top-20 margin-left-20"
    >
      <el-form-item label="投诉电话:">
        <el-input
          v-model="formInline.tosuphone"
          placeholder="请输入投诉者电话"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button type="primary" @click="add">添加</el-button>
      </el-form-item>
    </el-form>
    <div class="margin-top-20 margin-bottom-20">
      <el-table :data="state.tableData" style="width: 100%">
        <el-table-column type="index" width="50" label="ID"> </el-table-column>
        <el-table-column prop="tousuname" label="投诉内容" />
        <el-table-column prop="tousuphone" label="投诉电话" />
        <el-table-column prop="createtime" label="创建时间" />
        <el-table-column prop="exminetime" label="处理时间" />
        <el-table-column label="处理状态">
          <template #default="scope">
            <el-tag class="ml-2" type="info" v-if="scope.row.status == '0'"
              >待审核</el-tag
            >
            <el-tag class="ml-2" type="success" v-if="scope.row.status == '1'"
              >审核通过</el-tag
            >
            <el-tag class="ml-2" type="danger" v-if="scope.row.status == '2'"
              >审核驳回</el-tag
            >
            <el-tag class="ml-2" type="warning" v-if="scope.row.status == '3'"
              >处理中</el-tag
            >
            <el-tag class="ml-2" type="success" v-if="scope.row.status == '4'"
              >处理完毕</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="huifu" label="处理意见" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button type="success" size="small" @click="tousuDeal(scope.row)" v-if="scope.row.status!='4'"
            v-hasPermission="'sys:tousu:statusChange'"
              >投诉处理</el-button
            >
            <el-button
              type="primary"
              size="small"
              @click="updateOneTousu(scope.row)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="small"
              @click="deleteOneTousu(scope.row)"
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

    <el-dialog v-model="state.dialogVisible" :title="title" width="30%">
      <el-form :model="form">
        <el-form-item label="投诉内容：">
          <el-input v-model="param.tousuname" />
        </el-form-item>
        <el-form-item label="投诉者电话：">
          <el-input v-model="param.tousuphone" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirm()">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="state.tousudialogVisible" :title="title" width="30%">
      <el-form :model="form">
        <el-form-item label="投诉处理状态：">
          <el-select
            v-model="param.status"
            class="m-2"
            placeholder="请输入投诉处理状态"
            size="large"
          >
            <el-option
              v-for="item in state.options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="投诉回复：">
          <el-input v-model="param.huifu" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="tousudialogVisible = false">取消</el-button>
          <el-button type="primary" @click="tousuConfirm()">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
    
  <script setup>
import { reactive, onMounted } from "vue";

import {
  getPageTousu,
  getTousuID,
  updateTousu,
  addTousuParam,
  deleteTousu,
  statusChange
} from "@/api/tousu";

import { ElMessage, ElMessageBox } from "element-plus";

let formInline = reactive({
  tosuphone: "",
});

let state = reactive({
  tableData: "",
  dialogVisible: false,
  tousudialogVisible: false,
  options: [
    {
      value: "0",
      label: "待审核",
    },
    {
      value: "1",
      label: "审核通过",
    },
    {
      value: "2",
      label: "审核驳回",
    },
    {
      value: "3",
      label: "处理中",
    },
    {
      value: "4",
      label: "处理完毕",
    },
  ],
});

let page = reactive({
  page: 1,
  size: 10,
});

let param = reactive({});

onMounted(() => {
  search();
});

let tousuDeal = (row) => {
  state.tousudialogVisible = true;
  param.status=""
  param.huifu=""
  param.id=row.id
  getTousuID(row.id).then((res) => {
    console.log(res);
    param.id = res.data.id;
    param.tousuname = res.data.tousuname;
    param.tousuphone = res.data.tousuphone;
    param.createtime = res.data.createtime;
    param.exminetime = res.data.exminetime;
    param.status = res.data.status;
    param.huifu = res.data.huifu;
    param.userid = res.data.userid;
  });
};

let tousuConfirm=()=>{
  state.tousudialogVisible=false
  statusChange(param.id,param.status,param.huifu).then(res=>{
    console.log(res)
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
      onSubmit()
  })
}


let onSubmit = () => {
  search();
};

let search = () => {
  getPageTousu(page.page, page.size, formInline.title).then((res) => {
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

let updateOneTousu = (row) => {
  state.dialogVisible = true;
  // 获取详情页
  getTousuID(row.id).then((res) => {
    console.log(res);
    param.id = res.data.id;
    param.tousuname = res.data.tousuname;
    param.tousuphone = res.data.tousuphone;
    param.createtime = res.data.createtime;
    param.exminetime = res.data.exminetime;
    param.status = res.data.status;
    param.huifu = res.data.huifu;
    param.userid = res.data.userid;
  });
};

let confirm = () => {
  if (param.id) {
    updateTousu(param).then((res) => {
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
      onSubmit()
    });
  } else {
    console.log(param, 455454664);
    addTousuParam(param).then((res) => {
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
    onSubmit()
  }
  state.dialogVisible = false;
};

let deleteOneTousu = (row) => {
  ElMessageBox.confirm("你确定要删除这个楼宇嘛?", "Warning", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteTousu(row.id).then((res) => {
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
        onSubmit()
      });
    })
    .catch(() => {});
};

let add = () => {
  state.dialogVisible = true;
  param.id = "";
  param.tousuname = "";
  param.tousuphone = "";
  param.createtime = "";
  param.exminetime = "";
  param.status = "";
  param.huifu = "";
  param.userid = "";
};
</script>
    
    <style scoped>
</style>