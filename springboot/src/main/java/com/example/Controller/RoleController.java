package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.Role;
import com.example.Entity.RoleMenu;
import com.example.Entity.UserRole;
import com.example.Service.RoleMenuService;
import com.example.Service.RoleService;
import com.example.Service.UserRoleService;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleServcie;

    @GetMapping("/getPageRole")
//    @PreAuthorize("hasAuthority('sys:user:search')")
    public ResponseResult getPageRole(int page, int size, String rolename){
        Page<Role> answerVOPage = new Page<>(page,size);
        IPage<Role> answerVOIPage = roleServcie.getPageRole(answerVOPage,rolename);
        return new ResponseResult(200,"查找数据成功",answerVOIPage);
    }

    @GetMapping("/getRoleID")
    public ResponseResult getRoleID(int id){
        Role role=roleServcie.getById(id);
        return new ResponseResult(200,"数据查找成功",role);
    }

    @PostMapping("/updateRole")
    public ResponseResult updateRole(@RequestBody Role role){
        boolean total=roleServcie.updateById(role);
        if(total){
            return new ResponseResult(200,"修改数据成功");
        }else{
            return new ResponseResult(100,"修改数据失败");
        }
    }

    @PostMapping("/addRole")
    public ResponseResult addRole(@Validated @RequestBody Role role){
        role.setCreatetime(new Date());
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("rolename",role.getRolename());
        int zongshu=roleServcie.count(queryWrapper);
        if(zongshu>0){
            return new ResponseResult(100,"已存在该角色");
        }
        boolean total=roleServcie.save(role);
        if(total){
            return new ResponseResult(200,"添加用户成功");
        }else{
            return new ResponseResult(100,"添加用户失败");
        }
    }

    @Autowired
    private RoleMenuService roleMenuService;


    @GetMapping("/deleteOneRole")
//    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResponseResult deleteOneUser(int id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("roleid",id);
        int totalRoleMenu=roleMenuService.count(queryWrapper);
        if(totalRoleMenu>0){
            return new ResponseResult(100,"菜单栏和角色还有联系，禁止删除");
        }
        boolean total=roleServcie.removeById(id);

        if(total){
            return new ResponseResult(200,"删除数据成功");

        }else{
            return new ResponseResult(100,"删除用户数据失败");
        }
    }

    @GetMapping("/getAllRoles")
    public ResponseResult getAllRoles(){
        List<Role> roleList=roleServcie.list();
        return new ResponseResult(200,"获取数据成功",roleList);
    }

}
