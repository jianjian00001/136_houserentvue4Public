
import requests from "@/utils/request";

export function getAllUserRole(param,pageIndex,pageSize) {//导出方法
    return requests({
        url: `/userrole/getAllUserRole?pageNum=${pageIndex}&pageSize=${pageSize}`,
        method: 'post',
        data:param
    })
}

export function getAllRoles(param) {//导出方法
    return requests({
        url: `/roles/getAllRoles`,
        method: 'post',
        data:param
    })
}

export function update(param) {//导出方法
    return requests({
        url: `/userrole/update`,
        method: 'post',
        data:param
    })
}
