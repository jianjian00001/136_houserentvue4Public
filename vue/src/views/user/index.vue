<template>
  <div>
    <div class="flex page">
      <div class="flex-aj-center" style="width: 8%">用户名：</div>
      <el-input
        v-model="search"
        placeholder="请输入用户名"
        style="width: 200px"
      />
      <el-button type="primary" class="margin-left-20" @click="searchUsername"
        >查询</el-button
      >
      <el-button type="success" @click="add">添加</el-button>
    </div>

    <div class="margin-top-20 margin-bottom-20">
      <el-table :data="state.tableData" style="width: 100%">
        <el-table-column type="index" width="50" label="ID"> </el-table-column>
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="truename" label="真实姓名" />
        <el-table-column prop="password" label="密码" />
        <el-table-column prop="tel" label="电话" />
        <el-table-column prop="cardid" label="身份证" width="200"/>
        <el-table-column label="性别">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px" v-if="scope.row.sex == 1"
                >男</span
              >
              <span style="margin-left: 10px" v-if="scope.row.sex == 2"
                >女</span
              >
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button type="info" size="small" @click="assignRole(scope.row)"
              >分配角色</el-button
            >
            <el-button
              type="success"
              size="small"
              @click="resetPassword(scope.row)"
              >重置密码</el-button
            >
            <!-- <div class="margin-top-10">
              <el-button
              type="primary"
              size="small"
              @click="updateUser(scope.row)"  v-hasPermission="'sys:user:update'"
              >修改</el-button
            > -->
            <div class="margin-top-10">
              <el-button
                type="primary"
                size="small"
                @click="updateUser(scope.row)"
                >修改</el-button
              >
              <el-button
                type="danger"
                size="small"
                @click="deleteOneUser(scope.row)"
                >删除</el-button
              >
            </div>
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

    <el-dialog v-model="state.dialogVisible" :title="title" width="70%">
      <div class="flex">
        <div class="flex-aj-center">
          <span style="width: 30%">用户名：</span>
          <el-input
            v-model="param.username"
            placeholder="请输入用户名"
            class="input"
          />
        </div>
        <div class="flex-aj-center margin-left-20">
          <span style="width: 30%">呢称：</span>
          <el-input
            v-model="param.nickname"
            placeholder="请输入名字"
            class="input"
          />
        </div>

        <div class="flex-aj-center margin-left-20">
          <span style="width: 30%">密码：</span>
          <el-input
            v-model="param.password"
            placeholder="请输入密码"
            type="password"
            class="input"
          />
        </div>
      </div>

      <div class="flex margin-top-20">
        <div class="flex-aj-center">
          <span style="width: 30%">电话：</span>
          <el-input
            v-model="param.tel"
            placeholder="请输入电话"
            class="input"
          />
        </div>
        <div class="flex-aj-center margin-left-20">
          <span style="width: 30%">邮箱：</span>
          <el-input
            v-model="param.email"
            placeholder="请输入密码"
            class="input"
          />
        </div>
        <div class="flex-aj-center margin-left-20">
          <span style="width: 30%">真实姓名：</span>
          <el-input
            v-model="param.truename"
            placeholder="请输入真实名字"
            class="input"
          />
        </div>
      </div>

      <div class="margin-top-20 flex flex-center">
        <span class="width-100">性别：</span>
        <el-radio-group v-model="param.sex">
          <el-radio label="1" size="large">男</el-radio>
          <el-radio label="2" size="large">女</el-radio>
        </el-radio-group>
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirm()">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog
      v-model="state.dialogVisibleRole"
      title="分配角色"
      width="30%"
      :before-close="handleClose"
    >
      <el-checkbox-group
        v-model="state.checkedRoleData"
        @change="handleCheckedCitiesChange"
      >
        <el-checkbox v-for="item in state.selectRoleData" :key="item" :label="item">{{
          item
        }}</el-checkbox>
      </el-checkbox-group>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="state.dialogVisibleRole = false">取消</el-button>
          <el-button type="primary" @click="queren"> 确认 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, onMounted, ref } from "vue";
import {
  getPageUser,
  getUserID,
  deleteOneUserParam,
  addUserParam,
  update,
  resetNewPassword,
  getAllRoles,
  updateUserRole,
  getCheckedRoles,
} from "@/api/user";
import { ElMessageBox, ElMessage } from "element-plus";

let state = reactive({
  tableData: [],
  search: "",
  dialogVisible: false,
  value2: "",
  title: "修改用户信息",
  dialogVisibleRole: false,
  checkedRoleData:[],
  selectRoleData:[],
  paramid: 1,
  roleid: 1,
});
let param = reactive({
  username: "",
  truename: "",
  password: "",
  phone: "",
  sex: "",
  email: "",
});
let page = reactive({
  total: "",
  page: 1,
  size: 10,
});
let getDataList = () => {
  getPageUser(page.page, page.size, state.search).then((res) => {
    if (res.code == 200) {
      state.tableData = res.data.records;
      page.total = res.data.total;
    } else {
      ElMessage({
        message: res.msg,
        grouping: true,
        type: "error",
      });
    }
  });
};

onMounted(() => {
  getDataList();
});

let updateUser = (row) => {
  console.log(row.id);
  getUserID(row.id).then((res) => {
    console.log(res);
    if (res.code == 200) {
      param.id = res.data.id;
      param.username = res.data.username;
      param.truename = res.data.truename;
      (param.nickname = res.data.nickname)
        (param.password = res.data.password)
        (param.tel = res.data.tel)
        (param.sex = res.data.sex)
        (param.email = res.data.email)
    }
  });
  state.dialogVisible = true;
};

let confirm = () => {
  state.dialogVisible = false;
  if (param.id) {
    update(param).then((res) => {
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
  } else {
    addUserParam(param).then((res) => {
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
  searchUsername();
};
let searchUsername = () => {
  getDataList();
};

let deleteOneUser = (row) => {
  ElMessageBox.confirm("你确定要删除这个用户嘛?", "Warning", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteOneUserParam(row.id).then((res) => {
        console.log(res);
        if (res.code == 200) {
          ElMessage({
            message: res.msg,
            type: "success",
          });
          getDataList();
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
  getDataList();
};

let add = () => {
  state.dialogVisible = true;
  state.title = "添加用户信息";
  param.id = "";
  param.username = "";
  param.nickname = "";
  param.password = "";
  param.tel = "";
  param.sex = "";
  param.email = "";
};

let assignRole = async (row) => {
  state.dialogVisibleRole = true;
  state.paramid = row.id;
  state.checkedRoleData=[]
  state.selectRoleData=[]
  getAllRoles().then((res) => {
    for (let i = 0; i < res.data.length; i++) {
      state.selectRoleData.push(res.data[i].rolename);
    }
  });
  getCheckedRoles(row.id).then((res) => {
    console.log(res.data, 6666);
    for (let i = 0; i < res.data.length; i++) {
      state.checkedRoleData.push(res.data[i].rolename);
    }
  });

};

let handleCheckedCitiesChange = (val) => {
  console.log(val,"三大V你说的那就");
  state.checkedRoleData=val
};

let resetPassword = (row) => {
  resetNewPassword(row.id).then((res) => {
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
    getDataList();
  });
};

let queren = () => {
  state.dialogVisibleRole = false;
  updateUserRole(state.paramid, state.checkedRoleData).then((res) => {
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
.fixed {
  position: fixed;
  bottom: 20px;
  right: 20px;
}
.margin-bottom-20 {
  margin-bottom: 20px;
}
</style>
