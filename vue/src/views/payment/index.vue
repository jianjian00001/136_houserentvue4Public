<template>
  <div>
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline margin-top-20 margin-left-20"
    >
      <el-form-item label="住户名:">
        <el-input v-model="formInline.expensename" placeholder="请输入住户" />
      </el-form-item>
      <el-form-item label="房屋号:">
        <el-input v-model="formInline.housecode" placeholder="请输入房屋号" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <div class="margin-top-20 margin-bottom-20">
      <el-table :data="state.tableData" style="width: 100%">
        <el-table-column type="index" width="50" label="ID"> </el-table-column>
        <el-table-column prop="buildnumber" label="楼栋" />
        <el-table-column prop="housecode" label="房屋编号" />
        <el-table-column prop="expensename" label="费用名称" />
        <el-table-column prop="price" label="元/㎡" />
        <el-table-column prop="area" label="房屋大小（㎡）" />
        <el-table-column prop="totalmoney" label="总共" />
        <el-table-column prop="createtime" label="创建时间" />
        <el-table-column label="支付方式">
          <template #default="scope">
            <el-tag v-if="scope.row.paymethod == 0" type="danger">微信</el-tag>
            <el-tag v-if="scope.row.paymethod == 1" type="success"
              >支付宝</el-tag
            >
            <el-tag v-if="scope.row.paymethod == 2" type="success">银联</el-tag>
            <el-tag v-if="scope.row.paymethod == 3" type="success">现金</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="是否缴费">
          <template #default="scope">
            <el-tag v-if="scope.row.ispay == 0" type="danger">未交</el-tag>
            <el-tag v-if="scope.row.ispay == 1" type="success">已交</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="jiaofeitime" label="缴费时间" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="jiaofei(scope.row)"
              v-if="scope.row.ispay == 0"
              >缴费</el-button
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
        <el-form-item label="支付方式">
          <el-select
            v-model="param.paymethod"
            class="m-2"
            placeholder="请选择支付方式"
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
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirm()">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
      
    <script setup>
import { reactive, onMounted } from "vue";

import { getPagePayment, paymethod } from "@/api/booktime";

import { ElMessage } from "element-plus";

let formInline = reactive({
  expensename: "",
  housecode: "",
});

let state = reactive({
  tableData: "",
  dialogVisible: false,
  options: [
    {
      value: "0",
      label: "微信",
    },
    {
      value: "1",
      label: "支付宝",
    },
    {
      value: "2",
      label: "银联",
    },
    {
      value: "3",
      label: "现金",
    },
  ],
});

let param = reactive({});

let page = reactive({
  page: 1,
  size: 10,
});

onMounted(() => {
  search();
});

let confirm = () => {
  paymethod(param.id, param.paymethod).then((res) => {
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
};

let onSubmit = () => {
  search();
};

let search = () => {
  getPagePayment(
    page.page,
    page.size,
    formInline.expensename,
    formInline.housecode
  ).then((res) => {
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

let jiaofei = (row) => {
  state.dialogVisible = true;
  param.id = "";
  param.paymethod = "";
  param.id = row.id;
};
</script>
      
  <style scoped>
</style>