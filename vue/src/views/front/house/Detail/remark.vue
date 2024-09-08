<template>
  <div class="flex-justify-content-center">
    <div style="width: 800px">
      <div class="flex jc-between margin-top-10">
        <span class="font-size-20 padding-top-20" style="font-weight: 700"
          >评论：</span
        >
        <el-button type="primary" @click="remark">评论</el-button>
      </div>

      <div v-loading="loading">
        <div  v-for="item in state.tableData" :key="item">
          <div class="flex-start margin-top-20 grey">
            <span>{{item.username}}</span>
            <div class="padding-left-20">{{item.createtime}}</div>
          </div>
          <div class="flex flex-between margin-top-15">
            <div>
              <div class="padding-left-10">{{item.remarkname}}</div>
            </div>
            <div class="padding-top-20 flex" v-if="item.userid==state.userid">
              <el-button class="margin-left-10" @click="deleteOneRemark(item)">删除</el-button>
            </div>
          </div>
        </div>

      </div>

      <el-button type="primary" size="mini" plain @click="getMore()"
        >加载更多</el-button
      >
    </div>
  </div>

  <el-dialog
    v-model="state.dialogVisible"
    title="评论"
    width="30%"
    :before-close="handleClose"
  >
    <el-form :model="form" label-width="120px">
      <el-form-item label="请输入内容">
        <el-input v-model="param.remarkname" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="conform"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { addRemark,getPageRemark,deleteRemark } from "@/api/remark";
import { ElMessage } from "element-plus";

let router = useRouter();
const route = useRoute();

let state = reactive({
  dialogVisible: false,
  tableData:[],
  userid:sessionStorage.getItem("userid"),
});


let param = reactive({
  houseid:""
});

let loading = ref(false);

let page=reactive({
  page:1,
  size:10
})

let getMore=()=>{
  page.page=page.page+1
  state.isMore=true
  loading.value=true
  getPageRemark(page.page, page.size,param.houseid).then((res) => {
    if (res.code == 200) {
      state.tableData.push(...res.data.records);
      page.total = res.data.total+page.total;
      loading.value=false
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
}

let search = () => {
  getPageRemark(page.page, page.size,param.houseid).then((res) => {
    if (res.code == 200) {
      console.log(res, 9996666);
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

let deleteOneRemark=(row)=>{
  loading.value=true
  deleteRemark(row.id).then(res=>{
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
      page.page=1
      page.size=10
      loading.value=false
      search()
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  })
}


onMounted(() => {
  state.userid=sessionStorage.getItem("userid")
  state.id = route.query.id;
  param.houseid=route.query.id;
  state.isMore=true
  page.page=1
  search()
});

let conform = () => {
  state.dialogVisible = false;
  addRemark(param).then((res) => {
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success",
      });
      page.page=1
      page.size=10
      search()
    } else {
      ElMessage({
        message: res.msg,
        type: "error",
      });
    }
  });
};

let remark = () => {
  state.dialogVisible = true;
  param.remarkname=""
};
</script>


<style scoped>
</style>