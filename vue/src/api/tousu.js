
import requests from "@/utils/request";

export function getPageTousu(page,size,tosuphone) {//导出方法
    return requests({
        url: `/tousu/getPageTousu?page=${page}&size=${size}&tosuphone=${tosuphone}`,
        method: 'get',
    })
}

//查询详情
export function getTousuID(id) {//导出方法
    return requests({
        url: `/tousu/getTousuID?id=${id}`,
        method: 'get',
    })
}


export function updateTousu(param) {//导出方法
    return requests({
        url: `/tousu/updateTousu`,
        method: 'post',
        data:param
    })
}

export function addTousuParam(param) {//导出方法
    return requests({
        url: `/tousu/addTousu`,
        method: 'post',
        data:param
    })
}

export function deleteTousu(id) {//导出方法
    console.log(id)
    return requests({
        url: `/tousu/deleteTousu?id=${id}`,
        method: 'get',
    })
}

export function statusChange(id,status,huifu) {//导出方法
    return requests({
        url: `/tousu/statusChange?id=${id}&status=${status}&huifu=${huifu}`,
        method: 'get',
    })
}