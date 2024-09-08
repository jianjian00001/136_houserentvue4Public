<template>
    <div class="cart">
      <div class="logo">
        <el-row :gutter="10">
          <el-col :span="6">
            <el-card>
              <div class="grid-content">
                <div class="grid-cont-center">
                  <div class="grid-num">{{headParam.usertotal}}</div>
                  <div>使用用户人数</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <div class="grid-content">
                <div class="grid-cont-center">
                  <div class="grid-num">{{headParam.tousuTotal}}</div>
                  <div>投诉和维修总数</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <div class="grid-content">
                <div class="grid-cont-center">
                  <div class="grid-num">{{headParam.paymentTotal}}</div>
                  <div>缴费数量</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="5">
            <el-card>
              <div class="grid-content">
                <div class="grid-cont-center">
                  <div class="grid-num">{{songListCount}}</div>
                  <div>问题药品数量</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="11">
            <h3 class="mgb20">用户性别比例</h3>
            <div style="background-color:white">
              <div id="myChart" :style="{width: '280px', height: '400px'}"></div>
            </div>
          </el-col>
          <el-col :span="11">
            <h3 class="mgb20">出入库药品报损药品</h3>
            <div style="background-color:white">
              <div id="myChart1" :style="{width: '280px', height: '400px'}"></div>
            </div>
          </el-col>
        </el-row>
      </div>

      <div class="logo1">
      </div>
    </div>
  </template>

<script>
import { reactive, toRefs, onMounted, ref } from "vue";
import {headTotalParam,vertical,circle} from "@/api/bigdata.js";
import { ElMessageBox, ElMessage } from "element-plus";
import * as echarts from 'echarts';
export default {
  setup() {
    let state=reactive({
      headParam:{
        userTotal:0,
        kuchunTotal:0,
        storeTotal:0
      }
    })
    let param=reactive({
      key:[],
      value:[]
    })
    let circleParam=reactive({

    })
    let myChart=ref()
    let chartDom=ref()
    onMounted(() => {
      myChart.value = echarts.init(document.getElementById('myChart'))
      chartDom.value = document.getElementById('myChart1');
      vertical().then(res=>{
        console.log(res,1111)
        param.key=res.data.key
        param.value=res.data.value
        console.log(param,222222)
        drawLine()
      })
      circle().then(res=>{
        console.log(res)
        circleParam=res.data
        drawLine1()
      })
       headTotal()

    });
    let headTotal=()=>{
      headTotalParam().then(res=>{
        console.log(res)
        state.headParam.usertotal=res.data.usertotal;
        state.headParam.tousuTotal=res.data.tousuTotal;
        state.headParam.paymentTotal=res.data.paymentTotal
      })
    }

    let drawLine1=()=>{
      var myChart =echarts.init(chartDom.value);
      var option;

      option = {
        // title: { text: '过期与非过期药品比例' },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '药品信息',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data:circleParam
          }
        ]
      };

      option && myChart.setOption(option);

    }
    let drawLine=()=>{
      // 基于准备好的dom，初始化echarts实例
      // let myChart = echarts.init(document.getElementById('myChart'))
      // 绘制图表
      myChart.value.setOption({
        // title: { text: '使用性别比例' },
        tooltip: {},
        xAxis: {
          data: param.key
          // data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {},
        series: [{
          name: '人数',
          itemStyle: {
            normal: {
              color: function(params) {
                // 给出颜色组
                var colorList = ['#cca272','#74608f','#cca272','#74608f'];
                return colorList[params.dataIndex]
              },
            }
          },
          type: 'bar',
          data: param.value
          // data: [120, 200, 150, 80, 70, 110, 130],
        }]
      });
    }

    return {
      ...toRefs(state),
      param
    };
  },
};
</script>



  <style scoped>
  .logo{
    width: 98%;
  }
  .cart .logo1{
    display: flex;
    justify-content: space-evenly;
  }
  .grid-content {
    display: flex;
    align-items: center;
    height: 50px;
  }

  .grid-cont-center {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: darkgray;
  }

  .grid-num {
    font-size: 30px;
    font-weight: bold;
  }
  .mgb20 {
    margin-bottom: 10px;
  }
  </style>

