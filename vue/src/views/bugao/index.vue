<template>
  <div>
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline margin-top-20 margin-left-20"
    >
      <el-form-item label="公告名称:">
        <el-input v-model="formInline.title" placeholder="请输入公告名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button type="primary" @click="add">添加</el-button>
      </el-form-item>
    </el-form>
    <div class="margin-top-20 margin-bottom-20">
      <el-table :data="state.tableData" style="width: 100%">
        <el-table-column type="index" width="50" label="ID"> </el-table-column>
        <el-table-column prop="title" label="公告标题" />
        <el-table-column label="公告描述">
          <template #default="scope">
            <div
              style="display: flex; align-items: center;overflow: auto;height: 100px;"
            >
              {{ scope.row.description }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="createtime" label="创建时间" />
        <el-table-column prop="createby" label="创建人" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="updateOneBugao(scope.row)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="small"
              @click="deleteOneBugao(scope.row)"
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
      <div style="height: 400px; overflow: auto">
        <el-form :model="form">
          <el-form-item label="布告标题：">
            <el-input v-model="param.title" />
          </el-form-item>
          <el-form-item label="内容：">
            <div style="border: 1px solid #ccc">
              <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :defaultConfig="toolbarConfig"
                :mode="mode"
              />
              <Editor
                style="height: 500px; overflow-y: hidden"
                v-model="param.description"
                :defaultConfig="editorConfig"
                :mode="mode"
                @onCreated="handleCreated"
              />
            </div>
          </el-form-item>
          <!-- <el-form-item label="创建人：">
            <el-input v-model="param.createby" />
          </el-form-item> -->
        </el-form>
      </div>

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
import { reactive, onMounted, ref, shallowRef, onBeforeUnmount } from "vue";
import "@wangeditor/editor/dist/css/style.css";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";

import {
  getPageBugao,
  getBugaoID,
  updateBugao,
  addBugaoParam,
  deleteBugao,
  bugaoAddPicture
} from "@/api/bugao";

import { ElMessage, ElMessageBox } from "element-plus";

let formInline = reactive({
  title: "",
});

const editorRef = shallowRef();


const toolbarConfig = {};
const editorConfig = {
  placeholder: "请输入内容...",
  MENU_CONF: {
    uploadImage: {
      customUpload: ""
    },
  },

};

editorConfig.MENU_CONF.uploadImage.customUpload =  (resultFiles, insertImgFn)=> {
    // resultFiles 是 input 中选中的文件列表
    // insertImgFn 是获取图片 url 后，插入到编辑器的方法

    // 上传图片，返回结果，将图片插入到编辑器中
    uploadImage(resultFiles,insertImgFn)
  }

let uploadImage = (file, insertFn) => {
  let imgData = new FormData();
  imgData.append("file", file);
  //调接口，上传图片
  bugaoAddPicture(imgData).then((res) => {
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
      insertFn(res.data.url);
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});

const handleCreated = (editor) => {
  editorRef.value = editor; // 记录 editor 实例，重要！
};

let state = reactive({
  tableData: "",
  dialogVisible: false,
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
  getPageBugao(page.page, page.size, formInline.title).then((res) => {
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

let updateOneBugao = (row) => {
  state.dialogVisible = true;
  // 获取详情页
  getBugaoID(row.id).then((res) => {
    console.log(res);
    param.id = res.data.id;
    param.title = res.data.title;
    param.description = res.data.description;
    param.createtime = res.data.createtime;
    param.createby = res.data.createby;
  });
};

let confirm = () => {
  if (param.id) {
    updateBugao(param).then((res) => {
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
    console.log(param, 455454664);
    addBugaoParam(param).then((res) => {
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
  onSubmit();
  state.dialogVisible = false;
};

let deleteOneBugao = (row) => {
  ElMessageBox.confirm("你确定要删除这个楼宇嘛?", "Warning", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteBugao(row.id).then((res) => {
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
  param.title = "";
  param.photo = "";
  param.createtime = "";
  param.createby = "";
  param.description=""
};
</script>
    
    <style scoped>
</style>