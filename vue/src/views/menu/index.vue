<template>
  <div>
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline margin-top-20 margin-left-20"
    >
      <el-form-item>
        <el-button type="primary" @click="add">添加</el-button>
      </el-form-item>
    </el-form>
    <div class="margin-top-20 margin-bottom-20">
      <el-table
        :data="state.tableData"
        style="width: 100%; margin-bottom: 20px"
        row-key="id"
        border
        default-expand-all
      >
        <el-table-column type="index" width="50" label="ID"> </el-table-column>
        <el-table-column prop="menuname" label="菜单栏名" sortable />
        <el-table-column prop="path" label="菜单路由" sortable />
        <el-table-column prop="perms" label="菜单权限" sortable />
        <el-table-column prop="sortnumber" label="顺序" sortable />
        <el-table-column label="菜单类型" width="180">
          <template #default="scope">
            <el-tag v-if="scope.row.type == 0" type="success">目录</el-tag>
            <el-tag v-if="scope.row.type == 1" type="danger">菜单</el-tag>
            <el-tag v-if="scope.row.type == 2" type="warning">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="updateMenu(scope.row)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="small"
              @click="deleteOneRole(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="state.dialogVisible" :title="title" width="30%">
      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="菜单类型">
          <el-radio-group
            v-model="param.type"
            :disabled="state.eidtDisabled"
            @change="handleChangeRadio"
          >
            <el-radio :label="0" size="large" border>目录</el-radio>
            <el-radio :label="1" size="large" border>菜单</el-radio>
            <el-radio :label="2" size="large" border>按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称">
          <el-input v-model="param.menuname" />
        </el-form-item>
        <el-form-item label="上级菜单" v-if="param.type != 0">
          <el-tree-select
            v-model="param.parentid"
            :data="state.selectData"
            check-strictly
            :render-after-expand="false"
            placeholder="请选择上级菜单"
            :disabled="state.eidtDisabled"
          />
        </el-form-item>
        <el-form-item label="菜单序号">
          <el-input-number
            v-model="param.sortnumber"
            :min="1"
            :max="10"
            :precision="0"
            @change="handleChange"
          />
        </el-form-item>
        <el-form-item label="路由地址" v-if="param.type == 1">
          <el-input v-model="param.path" />
        </el-form-item>
        <el-form-item label="组件名称" v-if="param.type == 1">
          <el-input v-model="param.component" />
        </el-form-item>
        <el-form-item label="权限字段" v-if="param.type == 2">
          <el-input v-model="param.perms" />
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
import { reactive, onMounted, watch } from "vue";
import {
  getTotalMenu,
  getSelectMenuTree,
  updateMenuParam,
  getMenuById,
  deleteMenuOne,
  addMenuParam,
} from "@/api/menu";
import { ElMessage, ElMessageBox } from "element-plus";

let state = reactive({
  tableData: [],
  dialogVisible: false,
  selectData: "",
  eidtDisabled: false,
});

let param = reactive({
  id: "",
  parentid: "",
  menuname: "",
  path: "",
  component: "",
  perms: "",
  sortnumber: 1,
  type: 0,
});

onMounted(() => {
  search();
});


let handleChangeRadio=(val)=>{
  param.id=""
  param.parentid=""
  param.type=val
  param.sortnumber=""
  param.menuname=""
  param.path=""
  param.component=""
  param.perms=""
}

let updateMenu = (row) => {
  state.dialogVisible = true;
  state.eidtDisabled = true;
  // 获取详情页
  getMenuById(row.id).then((res) => {
    console.log(res, 123456);
    // if (res.code == 200) {
      param.id = res.data.id;
      param.type = res.data.type;
      param.sortnumber = res.data.sortnumber;
      param.menuname = res.data.menuname;
      param.path = res.data.path;
      param.component = res.data.component;
      param.perms = res.data.perms;
      param.parentid = res.data.parentid;
  });
  getSelectMenuTree().then((res) => {
    console.log(res.data.length, 666);
    console.log(res.data);
    let paramData = [];
    for (let i = 0; i < res.data.length; i++) {
      let paramFormat = {
        value: "",
        label: "",
        children: [],
      };
      paramFormat.value = res.data[i].id;
      paramFormat.label = res.data[i].menuname;
      paramData.push(paramFormat);
      if (res.data[i].children!=null) {
        for (let j = 0; j < res.data[i].children.length; j++) {
          let paramFormat1 = {
            value: "",
            label: "",
            children: [],
          };
          paramFormat1.value = res.data[i].children[j].id;
          paramFormat1.label = res.data[i].children[j].menuname;
          paramData[i].children.push(paramFormat1);
        }
      }
    }
    state.selectData = paramData;
  });

};

let confirm = () => {
  if (param.id) {
    updateMenuParam(param).then((res) => {
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
    addMenuParam(param).then((res) => {
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
  ElMessageBox.confirm("你确定要删除这个菜单嘛?", "Warning", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteMenuOne(row.id).then((res) => {
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
        search();
      });
    })
    .catch(() => {});
};

let add = () => {
  state.dialogVisible = true;
  state.eidtDisabled = false;
  param.id=""
  param.parentid=""
  param.type=0
  param.sortnumber=""
  param.menuname=""
  param.path=""
  param.component=""
  param.perms=""
  getSelectMenuTree().then((res) => {
    console.log(res.data.length, 666);
    console.log(res.data);
    let paramData = [];
    for (let i = 0; i < res.data.length; i++) {
      let paramFormat = {
        value: "",
        label: "",
        children: [],
      };
      paramFormat.value = res.data[i].id;
      paramFormat.label = res.data[i].menuname;
      paramData.push(paramFormat);
      if (res.data[i].children!=null) {
        for (let j = 0; j < res.data[i].children.length; j++) {
          let paramFormat1 = {
            value: "",
            label: "",
            children: [],
          };
          paramFormat1.value = res.data[i].children[j].id;
          paramFormat1.label = res.data[i].children[j].menuname;
          paramData[i].children.push(paramFormat1);
        }
      }
    }
    state.selectData = paramData;
    console.log(state.selectData, 88888);
  });
};

let search = () => {
  getTotalMenu().then((res) => {
    console.log(res, "我真是醉了");
    if (res.code == 200) {
      state.tableData = res.data;
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
.demo-form-inline .el-input {
  --el-input-width: 220px;
}
</style>