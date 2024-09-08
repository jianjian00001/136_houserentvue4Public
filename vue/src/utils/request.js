//对axios进行二次封装
import axios from 'axios'
// //引入进度条
// import nprogress from 'nprogress'
// //引入进度条样式
// import 'nprogress/nprogress.css'
import { ElMessage } from 'element-plus'
import router from "@/router";


//利用axios对象的方法create，去创建一个axios实例对象
//下面的request其实就是axios，只不过可以进行配置
const requests = axios.create({
    //配置对象

    //配置基础路径，让发送请求的路径端口号后面都加上api
    baseURL: '/api',
    //设置请求超时的时间
    timeout: 10000,
    // 设置允许跨域
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})

//请求拦截器：在发请求之前，请求拦截器可以检测到，就可以在发送请求之前进行一些操作
requests.interceptors.request.use((config,a) => {
    //config是一个配置对象，对象里有一个header请求头属性
    // console.log(config.ququest,"阿石创那是承诺践诺擦拭");
    config.headers.token = JSON.parse(sessionStorage.getItem('token')) // 请求头带上token
    // console.log(config.headers.token)
    //让发送请求的进度条开始动
    // nprogress.start()
    return config
})

//响应拦截器
requests.interceptors.response.use(
    (res) => {

        //成功的回调函数，当数据返回成功之后，响应拦截器可以检测到，就可以做一些操作
        console.log(res,9999)
        // if(res.data.code=='0'){
        //     router.push('/login')
        // }
        //让发送请求的进度条完成
        // nprogress.done()

        return res.data
    },
    (error) => {
        //相应失败的回调函数
        ElMessage({
            message: `系统错误，请联系管理员`,
            grouping: true,
            type: 'error',
        })

        return new Promise.reject(new Error('failed'))
    }
)

//对外暴露axios

export default requests

