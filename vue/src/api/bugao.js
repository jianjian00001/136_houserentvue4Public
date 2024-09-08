
import requests from "@/utils/request";

export function getPageBugao(page,size,title) {//导出方法
    return requests({
        url: `/bugao/getPageBugaoAll?page=${page}&size=${size}&title=${title}`,
        method: 'get',
    })
}

export function bugaoAddPicture(param) {//导出方法
    return requests({
        url: `/bugao/bugaoAddPicture`,
        method: 'post',
        data: param
    })
}

//查询详情
export function getBugaoID(id) {//导出方法
    return requests({
        url: `/bugao/getBugaoID?id=${id}`,
        method: 'get',
    })
}


export function updateBugao(param) {//导出方法
    return requests({
        url: `/bugao/updateBugao`,
        method: 'post',
        data:param
    })
}

export function addBugaoParam(param) {//导出方法
    return requests({
        url: `/bugao/addBugao`,
        method: 'post',
        data:param
    })
}

export function deleteBugao(id) {//导出方法
    console.log(id)
    return requests({
        url: `/bugao/deleteBugao?id=${id}`,
        method: 'get',
    })
}

export function getAllBuildings() {//导出方法
    return requests({
        url: `/building/getAllBuildings`,
        method: 'get',
    })
}
