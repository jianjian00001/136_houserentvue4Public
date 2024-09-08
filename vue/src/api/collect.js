
import requests from "@/utils/request";

export function collectHouseByUser(param) {//导出方法
    return requests({
        url: `/collect/collectHouseByUser?houseid=${param}`,
        method: 'get',
    })
}

export function cancelCollectHouse(param) {//导出方法
    return requests({
        url: `/collect/cancelCollectHouse?houseid=${param}`,
        method: 'get',
    })
}

export function collectHouseCount(param) {//导出方法
    return requests({
        url: `/collect/collectHouseCount?houseid=${param}`,
        method: 'get',
    })
}

export function getPageCollectByPerson(page,size,housename) {//导出方法
    return requests({
        url: `/collect/getPageCollectByPerson?page=${page}&size=${size}&housename=${housename}`,
        method: 'get',
    })
}

export function getPageRemarkAll(page,size,housename) {//导出方法
    return requests({
        url: `/collect/getPageRemarkAll?page=${page}&size=${size}&housename=${housename}`,
        method: 'get',
    })
}

export function deleteCollect(param) {//导出方法
    return requests({
        url: `/collect/deleteCollect?id=${param}`,
        method: 'get',
    })
}