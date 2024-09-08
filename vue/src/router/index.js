import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'
import login from '../views/login.vue'
import Layout from "../layout/index.vue";
import { activeRouter } from "../utils/permission"
import { getAllPermission } from '@/api/login'

const routes = [
  {
    path: '/',
    name: 'home',
    component: login
  },
  {
    path: '/:catchAll(.*)',
    name: 'not-found',
    component: () => import('@/views/NotFound/index.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/login.vue")
  },
  {
    path: '/password',
    name: 'password',
    component: () => import('@/views/password.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/register.vue')
  },

  {
    path: '/homeIndex',
    name: 'homeIndex',
    component: () => import('../views/front/homeIndex.vue'),
    children: [
      {
        path: '/publishhouse',
        name: 'publishhouse',
        component: () => import('../views/front/house/publishhouse.vue')
      },
      {
        path: '/userhouse',
        name: 'userhouse',
        component: () => import('../views/front/house/userHouse.vue')
      },
      {
        path: '/frontperson',
        name: 'frontperson',
        component: () => import('../views/front/person/frontperson.vue'),
        children: [
          {
            path: '/frontPersonDetail',
            name: 'frontPersonDetail',
            component: () => import('../views/front/person/frontPersonDetail.vue')
          },
          {
            path: '/remarkmenu',
            name: 'remarkmenu',
            component: () => import('../views/front/person/remarkmenu.vue')
          },
          
          {
            path: '/collectfront',
            name: 'collectfront',
            component: () => import('../views/front/person/collectfront.vue')
          },
          {
            path: '/housefront',
            name: 'housefront',
            component: () => import('../views/front/person/housefront.vue')
          },
          {
            path: '/housedescriptionfrontPerson',
            name: 'housedescriptionfrontPerson',
            component: () => import('../views/front/person/housedescriptionfront.vue')
          },
          {
            path: '/houseBookFront',
            name: 'houseBookFront',
            component: () => import('../views/front/person/houseBookFront.vue')
          },
          {
            path: '/houseBookFront',
            name: 'houseBookFront',
            component: () => import('../views/front/person/houseBookFront.vue')
          },
          {
            path: '/houseBookHouseFront',
            name: 'houseBookHouseFront',
            component: () => import('../views/front/person/houseBookHouseFront.vue')
          },
          
          {
            path: '/houseDetailFront',
            name: 'houseDetailFront',
            component: () => import('../views/front/person/houseDetailFront.vue')
          },
        ]
      },
      {
        path: '/houseDetail',
        name: 'houseDetail',
        component: () => import('../views/front/house/houseDetail.vue'),
        children: [
          {
            path: '/houseBase',
            name: 'houseBase',
            component: () => import('../views/front/house/Detail/houseBase.vue')
          },
          {
            path: '/expense',
            name: 'expense',
            component: () => import('../views/front/house/Detail/expense.vue')
          },
          {
            path: '/remark',
            name: 'remark',
            component: () => import('../views/front/house/Detail/remark.vue')
          },
          {
            path: '/housedescriptionFront',
            name: 'housedescriptionFront',
            component: () => import('../views/front/house/Detail/housedescription.vue')
          },
        
        ]
      },
      {
        path: '/bugaofront',
        name: 'bugaofront',
        component: () => import('../views/front/house/bugaofront.vue')
      },
      {
        path: '/bugaofrontDetail',
        name: 'bugaofrontDetail',
        component: () => import('../views/front/house/bugaofrontDetail.vue')
      }
    ]
  },


  {
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
]



const router = createRouter({
  history: createWebHashHistory(),
  routes
})

let registerRouteFresh = true;

let whiteList=['/login','/register','/password','/userhouse','/bugaofront','/bugaofrontDetail',
]

router.beforeEach((to, from, next) => {
  console.log(to.path, "老壳痛")
  if (whiteList.includes(to.path)) {
    next()
    return
  }
  // if (to.path === '/login' || to.path === '/register' || to.path === '/password'||to.path==='/userhouse') {
  //   next()
  //   return
  // }
  if (sessionStorage.getItem("token")) {
    if (registerRouteFresh) {
      registerRouteFresh = false
      activeRouter()
      // next()
      next({ ...to, replace: true });
    } else {
      registerRouteFresh = true
      next()
    }
  } else {
    next("/login")
  }
  next()
})


export default router
