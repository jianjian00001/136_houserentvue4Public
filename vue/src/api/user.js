
import requests from "@/utils/request";

export function login(username,password) {//导出方法
    return requests({
        url: `/user/login?username=${username}&password=${password}`,
        method: 'get',
    })
}

export function insertUser(username,password) {//导出方法
    return requests({
        url: `/user/insertUser?username=${username}&password=${password}`,
        method: 'get',
    })
}

export function getperson(){
    return requests({
        url: `/user/getperson`,
        method: 'get',
    })
}

export function getAllPermission(){
    return requests({
        url: `/user/getAllPermission`,
        method: 'get',
    })
}

export function resetNewPassword(param) {//导出方法
    return requests({
        url: `/user/resetPassword?id=${param}`,
        method: 'get',
    })
}

export function getCheckedRoles(id) {//导出方法
    return requests({
        url: `/user/getCheckedRoles?id=${id}`,
        method: 'get',
    })
}


export function updateUserById(param) {//导出方法
    return requests({
        url: `/user/updateUserById`,
        method: 'post',
        data:param
    })
}



//导入请求拦截器request
export function getPageUser(page,size,username) {//导出方法
    return requests({
        url: `/user/getPageUser?page=${page}&size=${size}&username=${username}`,
        method: 'get',
    })
}

//查询详情
export function getUserID(id) {//导出方法
    return requests({
        url: `/user/getUserID?id=${id}`,
        method: 'get',
    })
}

export function deleteOneUserParam(id) {//导出方法
    return requests({
        url: `/user/deleteOneUser?id=${id}`,
        method: 'delete',
    })
}

export function addUserParam(param) {//导出方法
    return requests({
        url: `/user/adduser`,
        method: 'post',
        data:param
    })
}

export function update(param) {//导出方法
    return requests({
        url: `/user/updateUser`,
        method: 'post',
        data:param
    })
}

export function getAllRoles() {//导出方法
    return requests({
        url: `/role/getAllRoles`,
        method: 'get',
    })
}


export function updateUserRole(userid,param) {//导出方法
    return requests({
        url: `/user/updateUserRole?userid=${userid}`,
        method: 'post',
        data:param
    })
}