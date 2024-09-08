
import requests from "@/utils/request";

export function getPageHouseBooktByPerson(page,size,housename) {//导出方法
    return requests({
        url: `/booktime/getPageHouseBooktByPerson?page=${page}&size=${size}&housename=${housename}`,
        method: 'get',
    })
}

export function getPageHouseBooktByHouse(page,size,housename) {//导出方法
    return requests({
        url: `/booktime/getPageHouseBooktByHouse?page=${page}&size=${size}&housename=${housename}`,
        method: 'get',
    })
}


export function getPageHouseBooktAll(page,size,housename) {//导出方法
    return requests({
        url: `/booktime/getPageHouseBooktAll?page=${page}&size=${size}&housename=${housename}`,
        method: 'get',
    })
}

export function addBookTime(param) {//导出方法
    return requests({
        url: `/booktime/addBookTime`,
        method: 'post',
        data:param
    })
}


export function deleteHouseBook(param) {//导出方法
    return requests({
        url: `/booktime/deleteHouseBook?id=${param}`,
        method: 'delete',
    })
}



export function getUserBookUserID(param) {//导出方法
    return requests({
        url: `/user/getUserBookUserID?id=${param}`,
        method: 'get',
    })
}