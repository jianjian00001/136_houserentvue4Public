
import requests from "@/utils/request";

export function getPageRemark(page,size,houseid) {//导出方法
    return requests({
        url: `/remark/getPageRemark?page=${page}&size=${size}&houseid=${houseid}`,
        method: 'get',
    })
}

export function getPageRemarkAll(page,size,housename) {//导出方法
    return requests({
        url: `/remark/getPageRemarkAll?page=${page}&size=${size}&housename=${housename}`,
        method: 'get',
    })
}

export function getPageRemarkByPerson(page,size,housename) {//导出方法
    return requests({
        url: `/remark/getPageRemarkByPerson?page=${page}&size=${size}&housename=${housename}`,
        method: 'get',
    })
}


export function addRemark(param) {//导出方法
    return requests({
        url: `/remark/addRemark`,
        method: 'post',
        data:param
    })
}

export function deleteRemark(param) {//导出方法
    return requests({
        url: `/remark/deleteRemark?id=${param}`,
        method: 'delete',
    })
}