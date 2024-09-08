
import requests from "@/utils/request";

export function getPageBuilding(page,size,buildnumber) {//导出方法
    return requests({
        url: `/building/getPageBuilding?page=${page}&size=${size}&buildnumber=${buildnumber}`,
        method: 'get',
    })
}

//查询详情
export function getBuildingID(id) {//导出方法
    return requests({
        url: `/building/getBuildingID?id=${id}`,
        method: 'get',
    })
}


export function updateBuilding(param) {//导出方法
    return requests({
        url: `/building/updateBuilding`,
        method: 'post',
        data:param
    })
}

export function addBuildingParam(param) {//导出方法
    return requests({
        url: `/building/addBuilding`,
        method: 'post',
        data:param
    })
}

export function deleteBuilding(id) {//导出方法
    console.log(id)
    return requests({
        url: `/building/deleteBuilding?id=${id}`,
        method: 'get',
    })
}

export function getAllBuildings() {//导出方法
    return requests({
        url: `/building/getAllBuildings`,
        method: 'get',
    })
}
