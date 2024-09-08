import requests from "@/utils/request";

//导入请求拦截器request
export function getperson(){
    return requests({
        url: `/user/getperson`,
        method: 'get',
    })
}
export function updateUser(parameter) {
    return requests({
        url: `/user/updateUser`,
        method: 'post',
        data:parameter
    })
}
export function getUserlist(parameter) {//导出方法
    return requests({
        url: `/user/login?username=${parameter.username}&password=${parameter.password}`,
        method: 'get',
    })
}
export function getAllPermission(){
    return requests({
        url: `/user/getAllPermission`,
        method: 'get',
    })
}
export function updatesecert(param) {//导出方法
    return requests({
        url: `/user/updatesecert`,
        method: 'post',
        data:param
    })
}



export function register(parameter) {//导出方法
    console.log(parameter)
    return requests({
        url: `/user/insert?username=${parameter.username}&password=${parameter.password}`,
        method: 'get',
    })
}


export function testusername(parameter) {//导出方法
    console.log(parameter)
    return requests({
        url: `/users/testusername?username=${parameter}`,
        method: 'get',
    })
}

export function testcodename(parameter) {//导出方法
    console.log(parameter)
    return requests({
        url: `/users/testcode?code=${parameter}`,
        method: 'get',
    })
}

export function updatepassword(parameter) {//导出方法
    return requests({
        url: `/users/updatepassword?username=${parameter.username}&password=${parameter.password}&email=${parameter.email}`,
        method: 'get',
    })
}