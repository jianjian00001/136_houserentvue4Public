import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import * as echarts from 'echarts';
// import "@/style/flex.css"
import "@/base.css"

// import { hasPermission } from '@/utils/permissionDirect'



let app = createApp(App);
app.directive("hasPermission", {
    mounted: (el, binding) => {
        console.log(el, binding, "天下无敌")
        let permissions = JSON.parse(sessionStorage.getItem("addRouterData"))
        console.log(permissions, "天下无敌fdbssfb")
        let flag = false
        var reg = /[\t\r\f\n\s]*/g;
        for (let i = 0; i < permissions.length; i++) {
            for (let j = 0; j < permissions[i].children.length; j++) {
                if (permissions[i].children[j].children != null) {
                    for (let k = 0; k < permissions[i].children[j].children.length; k++) {
                        if (permissions[i].children[j].children[k].perms != null) {
                            if (binding.value == permissions[i].children[j].children[k].perms.replace(reg, '')) {
                                console.log(binding.value, permissions[i].children[j].children[k].perms, "格式化数据")
                                flag = true
                                break;
                            }
                        }

                    }
                }

            }
        }
        if (!flag) {
            if (!el.parentNode) {
                el.style.display = 'none'
            } else {
                el.parentNode.removeChild(el)
            }
        }
    }
})
app.use(ElementPlus)
app.use(echarts)
app.use(router).mount('#app')
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
