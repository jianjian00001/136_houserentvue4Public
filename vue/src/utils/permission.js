import Layout from "@/layout/index.vue";
import router from "@/router";

export function activeRouter() {
    let addData = JSON.parse(sessionStorage.getItem("addRouterData"))
    if (addData) {
        let root = {
            path: '/layout',
            name: 'Layout',
            component: Layout,
            children: [
                {
                    path: '/person',
                    name: 'person',
                    component: () => import('../views/person.vue')
                },
                {
                    path: '/updatepassword',
                    name: 'updatepassword',
                    component: () => import('../views/updatepassword.vue')
                },
            ]
        }
        for (let i = 0; i < addData.length; i++) {
            // console.log(addData[i].children)
            if (addData[i].children == null) {
                continue;
            }
            for (let j = 0; j < addData[i].children.length; j++) {
                // console.log(addData[i].children[j])
                // console.log(addData[i].children[j].component);
                let obj = {
                    path: addData[i].children[j].path,
                    name: addData[i].children[j].menuname,
                    component: () => import(`@/views` + addData[i].children[j].component + `.vue`)
                };
                root.children.push(obj)
            }
        }
        if (router) {
            router.addRoute(root)
        }
    }

}
