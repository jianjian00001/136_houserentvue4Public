<template>
  <div>
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline margin-top-20 margin-left-20"
    >
      <el-form-item label="角色名:">
        <el-input v-model="formInline.rolename" placeholder="请输入角色名" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button type="primary" @click="add">添加</el-button>
      </el-form-item>
    </el-form>
    <div class="margin-top-20 margin-bottom-20">
      <el-table :data="state.tableData" style="width: 100%">
        <el-table-column type="index" width="50" label="ID"> </el-table-column>
        <el-table-column prop="rolename" label="角色名" />
        <el-table-column prop="rolekey" label="角色权限" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="updateRole(scope.row)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="small"
              @click="deleteOneRole(scope.row)"
              >删除</el-button
            >
            <el-button
              type="success"
              size="small"
              @click="assignMenu(scope.row)"
              >分配菜单</el-button
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
        <el-form-item label="角色名">
          <el-input v-model="param.rolename" />
        </el-form-item>
        <el-form-item label="角色字段">
          <el-input v-model="param.rolekey" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirm()">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog title="分配菜单" v-model="state.dialogVisibleMenu" width="30%">
      <div style="height: 400px;overflow: auto;">
        <el-tree
          :data="state.treedataTotal"
          show-checkbox
          default-expand-all
          node-key="id"
          ref="menuTree"
          highlight-current
          :props="state.defaultProps"
          :default-checked-keys="state.checkKeys"
          @check="checkNode"
          check-strictly="true"
        >
        </el-tree>
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="state.dialogVisibleMenu = false">取消</el-button>
          <el-button type="primary" @click="MenuConfirm()">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import {
  getPageRole,
  updateRoleParam,
  getRoleID,
  deleteOneRoleParam,
  addRoleParam,
} from "@/api/role";
import { ElMessage, ElMessageBox } from "element-plus";

import { getTotalMenu, getCheckMenu, roleMenudeleteByIds } from "@/api/menu";

let formInline = reactive({
  rolename: "",
});

let state = reactive({
  tableData: "",
  dialogVisible: false,
  dialogVisibleMenu: false,
  defaultProps: {
    children: "children",
    label: "menuname",
    id: "id",
  },
  treedataTotal: [],
  checkKeys: [],
  treedata: [],

  // 临时数据
  temporaryData: [],
  menuRoleId: "",
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
  getPageRole(page.page, page.size, formInline.rolename).then((res) => {
    console.log(res, 111);
    if (res.code == 200) {
      state.tableData = res.data.records;
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
};

let updateRole = (row) => {
  state.dialogVisible = true;
  // 获取详情页
  getRoleID(row.id).then((res) => {
    console.log(res);
    param.id = res.data.id;
    param.rolename = res.data.rolename;
    param.rolekey = res.data.rolekey;
  });
};

let confirm = () => {
  if (param.id) {
    updateRoleParam(param).then((res) => {
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
  } else {
    addRoleParam(param).then((res) => {
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
  }

  state.dialogVisible = false;
};

let deleteOneRole = (row) => {
  ElMessageBox.confirm("你确定要删除这个角色嘛?", "Warning", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteOneRoleParam(row.id).then((res) => {
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

let add = () => {
  state.dialogVisible = true;
  param.id = "";
  param.rolename = "";
  param.rolekey = "";
};

let assignMenu = (row) => {
  state.dialogVisibleMenu = true;
  state.menuRoleId = row.id;
  // 获取全部菜单栏
  getTotalMenu().then((res) => {
    console.log(res, 6666);
    if (res.code == 200) {
      state.treedataTotal = res.data;
    }
  });
  // 对数据进行处理
  state.checkKeys = [];
  // 获取选择的数据
  getCheckMenu(row.id).then((res) => {
    console.log(res, 555);
    if (res.code == 200) {
      state.treedata = res.data;
      console.log(state.treedata);
      console.log(state.checkKeys);
      for (let j = 0; j < state.treedata.length; j++) {
        state.checkKeys.push(state.treedata[j].id);
        if (state.treedata[j].children != null) {
          for (let i = 0; i < state.treedata[j].children.length; i++) {
            state.checkKeys.push(state.treedata[j].children[i].id);
            if (state.treedata[j].children[i].children != null) {
              for (
                let k = 0;
                k < state.treedata[j].children[i].children.length;
                k++
              ) {
                state.checkKeys.push(
                  state.treedata[j].children[i].children[k].id
                );
              }
            }
          }
        }
      }
      state.checkKeys = Array.from(new Set(state.checkKeys));
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};

let checkNode = (node, data) => {
  state.temporaryData = data.checkedKeys;
  console.log(node, data, 5555566666);
  console.log(state.temporaryData);
};

let MenuConfirm = () => {
  roleMenudeleteByIds(state.temporaryData, state.menuRoleId).then((res) => {
    console.log(res, 66);
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
  state.dialogVisibleMenu = false;
};
</script>

<style scoped>
</style>