
import requests from "@/utils/request";

//导入请求拦截器request

export function getTotalMenu() {//导出方法
    return requests({
        url: `/menu/getMenuList`,
        method: 'get',
    })
}

export function getCheckMenu(roleid){
    return requests({
        url: `/menu/getCheckMenu?roleid=${roleid}`,
        method: 'get'
    })
}

export function roleMenudeleteByIds(param,menuRoleId){
    console.log(param,"价差大怒")
    return requests({
        method:"delete",
        url:`/menu/deleteByIds?roleid=${menuRoleId}`,
        data:param
    })
}











export function getSelectMenuTree() {//导出方法
    return requests({
        url: `/menu/getSelectMenuTree`,
        method: 'get',
    })
}

export function addMenuParam(param){
    return requests({
        url: `/menu/addMenuParam`,
        method: 'post',
        data:param
    })
}

export function deleteMenuOne(param){
    return requests({
        url: `/menu/deleteMenu?id=${param}`,
        method: 'delete',
    })
}

export function getMenuById(id) {//导出方法
    return requests({
        url: `/menu/getMenuById?id=${id}`,
        method: 'get',
    })
}


export function updateMenuParam(param){
    return requests({
        url: `/menu/updateMenu`,
        method: 'post',
        data:param
    })
}