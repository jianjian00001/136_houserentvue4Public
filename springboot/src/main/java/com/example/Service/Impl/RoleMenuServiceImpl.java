package com.example.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dao.MenuDao;
import com.example.Dao.RoleMenuDao;
import com.example.Dao.UserRoleDao;
import com.example.Entity.Menu;
import com.example.Entity.RoleMenu;
import com.example.Entity.UserRole;
import com.example.Service.MenuService;
import com.example.Service.RoleMenuService;
import com.example.Service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> getCheckMenu(int roleid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("roleid",roleid);
        List<RoleMenu> roleMenuList=roleMenuDao.selectList(queryWrapper);
        List<Menu> menuList = new ArrayList<>();

        for (RoleMenu item:roleMenuList){
            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("id",item.getMenuid());
            Menu menu=menuDao.selectOne(queryWrapper1);
            menuList.add(menu);
        }
        ArrayList<Menu> list1=menuList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                ()->new TreeSet<>(Comparator.comparing(Menu::getId))),ArrayList::new));
//        List<Menu> data = menuDao.selectList(null);
        //先找到所有的一级分类
        List<Menu> newmenuList=new ArrayList<>();
        for(Menu menuInfo : list1){
            // 一级菜单的parentId是0
            if(menuInfo.getParentid() == 0){
                newmenuList.add(menuInfo);
            }
        }
        newmenuList.sort(Comparator.comparingInt(Menu::getSortnumber));
        // 为一级菜单设置子菜单，getChild是递归调用的
        for(Menu parentMenuInfo : newmenuList){
            parentMenuInfo.setChildren(getChilde(parentMenuInfo.getId(), list1));
        }
        return newmenuList;
    }

    @Override
    public boolean deleteByIds(int[] ids, int roleid) {
//        把全部的roleid的全部删除
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("roleid",roleid);
        roleMenuDao.delete(queryWrapper);
        for(int i=0;i<ids.length;i++){
            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setMenuid(ids[i]);
            roleMenu.setRoleid(roleid);
            roleMenuDao.insert(roleMenu);
        }
        return true;
    }

    public List<Menu> getChilde(Integer id, List<Menu> rootList){
        //子级
        List<Menu> childList = new ArrayList<>();
        for(Menu menuInfo : rootList){
            // 遍历所有节点，将父级id与传过来的id比较
            if(menuInfo.getParentid().equals(id)){
                childList.add(menuInfo);
            }
        }
        childList.sort(Comparator.comparingInt(Menu::getSortnumber));
        // 把子级的子级再循环一遍
        for(Menu sonMenuInfo : childList){
            sonMenuInfo.setChildren(getChilde(sonMenuInfo.getId(), rootList));
        }
        // 递归退出条件
        if (childList.size() == 0){
            return null;
        }
        return childList;
    }
}
