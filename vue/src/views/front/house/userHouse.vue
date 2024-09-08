<template>
  <div class="text-center">
    <div class="block text-center">
      <el-carousel height="300px">
        <el-carousel-item v-for="item in 4" :key="item">
          <img src="http://localhost:8888/images/lunbo/lunbo1.png" />
          <img src="http://localhost:8888/images/lunbo/lunbo2.png" />
          <h3 class="small justify-center" text="2xl">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div>
      <div
        class="background-black flex flex-space-between"
        style="
          height: 50px;
          color: aliceblue;
          margin-left: 150px;
          margin-right: 150px;
          margin-top: 10px;
        "
      >
        <span class="padding-left-5">房屋信息</span>
        <span class="padding-right-5">当前位置为房屋信息</span>
      </div>

      <el-form
        :model="form"
        label-width="120px"
        inline
        class="margin-top-10 text-center"
      >
        <el-form-item label="请输入房屋名称">
          <el-input v-model="state.form.housename" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
          <el-button>重置</el-button>
        </el-form-item>
      </el-form>

      <div
        class="flex flex-space-start flex-wrap"
        style="
          margin-left: 130px;
          margin-right: 130px;
          margin-top: 10px;
          cursor: pointer;
        "
      >
        <div
          class="block"
          style="padding-right: 10px"
          v-for="item in state.tableData"
          :key="item"
        >
          <div @click="gotoHouse(item.id)">
            <img :src="item.photo" class="imgStyle" />
            <div>{{ item.housename }}</div>
            <div>
              <span>{{ item.rentway }}| </span>
              <span>{{ item.area }}㎡ |</span>
            </div>
            <div>
              <span>{{ item.housetypename }} |</span>
              <span>{{ item.direction }} |</span>
            </div>
            <div>￥{{ item.price }}元/月</div>
          </div>
        </div>
      </div>

      <el-pagination
        background
        layout="prev, pager, next"
        :total="page.total"
        :page-size="10"
        class="flex-aj-center margin-top-10"
        @current-change="currentchange"
      />
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getPageHouseNormal } from '@/api/house'
import { ElMessage } from 'element-plus'

let router = useRouter()

let page = reactive({
  page: 1,
  size: 10,
  total: 10,
})

let gotoHouse = (val) => {
  console.log(val)
  router.push({
    path: '/houseBase',
    query: {
      id: val,
    },
  })
}

let state = reactive({
  form: {
    housename: '',
  },
})

let currentchange = (val) => {
  page.page = val
  search()
}

let search = () => {
  getPageHouseNormal(page.page, page.size, state.form.housename).then((res) => {
    if (res.code == 200) {
      console.log(res, 9996666)
      state.tableData = res.data.records
      page.total = res.data.total
    } else {
      ElMessage({
        message: res.msg,
        type: 'error',
      })
    }
  })
}

onMounted(() => {
  search()
})

let onSubmit = () => {
  search()
}
</script>

<style scoped>
.el-menu--horizontal {
  display: flex;
  justify-content: center;
}
.background-black {
  background-color: #695555;
}

.flex {
  display: flex;
}
.flex-space-between {
  justify-content: space-between;
  align-items: center;
}

.flex-space-start {
  justify-content: flex-start;
}

.padding-left-5 {
  padding-left: 5px;
}
.padding-right-5 {
  padding-right: 5px;
}

.flex-wrap {
  flex-wrap: wrap;
}

.imgStyle {
  width: 280px;
  height: 200px;
  border-radius: 25px;
  box-shadow: 5px 5px 2px gray;
}
.margin-top-10 {
  margin-top: 10px;
}

.text-center {
  text-align: center;
}
</style>
