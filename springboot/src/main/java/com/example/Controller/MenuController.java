package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Entity.Menu;
import com.example.Service.MenuService;
import com.example.Service.RoleMenuService;
import com.example.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Slf4j
@Validated
public class MenuController {

    @Autowired
    private MenuService menuServcie;

    @GetMapping("/getMenuList")
    public ResponseResult getMenuList(){
//        IPage<User> answerVOIPage = usersServcie.getAllPage(answerVOPage,username);
        List<Menu> MenuList=menuServcie.getTotalPermission();
        return new ResponseResult(200,"查找数据成功",MenuList);
    }

    @GetMapping("/getSelectMenuTree")
    public ResponseResult getSelectMenuTree(){
        List<Menu> SelectMenuTreeList=menuServcie.getTotalPermission();
        return new ResponseResult(200,"查找数据成功",SelectMenuTreeList);
    }


    //    获取数据详情
    @RequestMapping("/getMenuById")
    public ResponseResult getMenuById(int id){
        Menu menu=menuServcie.getById(id);
        return new ResponseResult(200,"获取菜单栏数据成功",menu);
    }

    @PostMapping("/updateMenu")
    public ResponseResult updateMenu(@RequestBody Menu menu){
        menuServcie.updateById(menu);
        return new ResponseResult(200,"修改菜单栏成功");
    }

    @PostMapping("/addMenuParam")
    public ResponseResult addMenuParam(@Validated @RequestBody Menu menu){
        return menuServcie.insertMenu(menu);
    }

    @DeleteMapping("/deleteMenu")
    @Transactional
//    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResponseResult deleteMenu(int id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("parentid",id);
        int total=menuServcie.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"该菜单栏子菜单还存在数据");
        }
        menuServcie.removeById(id);
        return new ResponseResult(200,"删除菜单栏数据成功");
    }

    @Autowired
    private RoleMenuService roleMenuServcie;

    @GetMapping("/getCheckMenu")
    public ResponseResult getCheckMenu(int roleid){
        List<Menu> menuList=roleMenuServcie.getCheckMenu(roleid);
        return new ResponseResult(200,"获取菜单栏已选择数据成功",menuList);
    }

    @RequestMapping("/deleteByIds")
    @Transactional
    public ResponseResult deleteByIds(@RequestBody int[] ids,int roleid){
        roleMenuServcie.deleteByIds(ids,roleid);
        return new ResponseResult(200,"分配菜单成功");
    }


}
