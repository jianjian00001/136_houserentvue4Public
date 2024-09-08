import requests from "@/utils/request";

export function getPageRole(page,size,rolename) {//导出方法
    return requests({
        url: `/role/getPageRole?page=${page}&size=${size}&rolename=${rolename}`,
        method: 'get',
    })
}

export function updateRoleParam(parameter) {
    return requests({
        url: `/role/updateRole`,
        method: 'post',
        data:parameter
    })
}

//查询详情
export function getRoleID(id) {//导出方法
    return requests({
        url: `/role/getRoleID?id=${id}`,
        method: 'get',
    })
}


// 删除数据
export function deleteOneRoleParam(id) {//导出方法
    return requests({
        url: `/role/deleteOneRole?id=${id}`,
        method: 'get',
    })
}

// 添加数据
export function addRoleParam(param) {//导出方法
    return requests({
        url: `/role/addRole`,
        method: 'post',
        data:param
    })
}