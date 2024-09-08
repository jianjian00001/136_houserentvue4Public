// export const hasPermission = {
//             monted(el, binding) {
//                 console.log(el, binding, 1111)
//                 let permissions = JSON.parse(sessionStorage.getItem("addRouterData"))
//                 console.log(permissions, "test数据")
//                 let flag = false
//                 var reg = /[\t\r\f\n\s]*/g;
//                 for (let i = 0; i < permissions.length; i++) {
//                     // console.log(permissions[i], "test数据")
//                     for (let j = 0; j < permissions[i].children.length; j++) {
//                         if (permissions[i].children[j].button != null) {
//                             for (let k = 0; k < permissions[i].children[j].button.length; k++) {
//                                 // console.log(permissions[i].children[j].button[k])
//                                 if (permissions[i].children[j].button[k].perms != null) {
//                                     // console.log(permissions[i].children[j].button[k].perms);
//                                     // console.log(binding.value, permissions[i].children[j].button[k].perms, "擦拭")
//                                     // console.log(binding.value.length)
//                                     // console.log(permissions[i].children[j].button[k].perms.length)
//                                     // console.log(permissions[i].children[j].button[k].perms.replace(reg,'').length)
//                                     // console.log(binding.value == permissions[i].children[j].button[k].perms)
//                                     if (binding.value == permissions[i].children[j].button[k].perms.replace(reg,'')) {
//                                         console.log(binding.value, permissions[i].children[j].button[k].perms, "格式化数据")
//                                         flag = true
//                                         break;
//                                     }
//                                 }

//                             }
//                         }

//                     }
//                 }
//                 console.log(binding.value, "svsdj")
//                 //   let value = binding.value.split(',')
//                 //   console.log(value,"fhnsaudds")
//                 //   for (let v of value) {
//                 //     if (!permissions.includes(v)) {
//                 //       flag = false
//                 //     }
//                 //   }
//                 console.log(flag, 999)
//                 if (!flag) {
//                     if (!el.parentNode) {
//                         el.style.display = 'none'
//                     } else {
//                         el.parentNode.removeChild(el)
//                     }
//                 }
//             }
  
// }