
import requests from "@/utils/request";

export function headTotalParam() {//导出方法
    return requests({
        url: `/bigdata/head`,
        method: 'get',
    })
}

export function vertical() {//导出方法
    return requests({
        url: `/bigdata/vertical`,
        method: 'get',
    })
}

export function circle() {//导出方法
    return requests({
        url: `/bigdata/circle`,
        method: 'get',
    })
}
