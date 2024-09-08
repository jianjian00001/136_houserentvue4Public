package com.example.Service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dao.MenuDao;
import com.example.Dao.RoleMenuDao;
import com.example.Dao.UserRoleDao;
import com.example.Entity.*;
import com.example.Dao.UserDao;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public IPage<User> getPageUser(Page<User> page, String username) {
        return userDao.getPageUser(page,username);
    }

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public List<Menu> getAllPermission(int id) {
//        根据用户id查询角色
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",id);
        List<UserRole> userRoleList=userRoleDao.selectList(queryWrapper);
        List<UserRole> menuList=userRoleDao.selectList(null);
        List<List<RoleMenu>> menuListFirst=new ArrayList<>();
        List<RoleMenu> menuListSecond=new ArrayList<>();
//        根据角色查询menu
        for (UserRole item : userRoleList){
            System.out.println(item+"5555");
            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("roleid",item.getRoleid());
            List<RoleMenu> roleMenuList=roleMenuDao.selectList(queryWrapper1);
            menuListFirst.add(roleMenuList);
        }
        for(List<RoleMenu> element: menuListFirst)
        {
            System.out.println(element);
            for (RoleMenu item1:element){
                menuListSecond.add(item1);
            }
        }

        List<Menu> menuList1=new ArrayList<>();
//        获取menu的list
        for(RoleMenu element2: menuListSecond){
            Menu menu=menuDao.selectById(element2.getMenuid());
            menuList1.add(menu);
        }

//        对menu进行去重
//        List<Menu> menuListUnique = new ArrayList<>();
        // 去重
        ArrayList<Menu>list1=menuList1.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                ()->new TreeSet<>(Comparator.comparing(Menu::getId))),ArrayList::new));


        List<Menu> newmenuList=new ArrayList<>();
        for(Menu menuInfo : list1){
            // 一级菜单的parentId是0
            if(menuInfo.getParentid() == 0){
//                menuInfo.setChildren(null);
                newmenuList.add(menuInfo);

            }
        }
        newmenuList.sort(Comparator.comparingInt(Menu::getSortnumber));
        for(Menu parentMenuInfo : newmenuList){
            parentMenuInfo.setChildren(getChilde(parentMenuInfo.getId(), list1));
        }
        return newmenuList;
    }

//    @Override
//    public List<Role> getAllRole(int id) {
////        根据用户id查询角色
//            QueryWrapper queryWrapper=new QueryWrapper();
//            queryWrapper.eq("userid",id);
//            List<UserRole> userRoleList=userRoleDao.selectList(queryWrapper);
//            List<UserRole> menuList=userRoleDao.selectList(null);
//            List<List<RoleMenu>> menuListFirst=new ArrayList<>();
//            List<RoleMenu> menuListSecond=new ArrayList<>();
////        根据角色查询menu
//            for (UserRole item : userRoleList){
//                System.out.println(item+"5555");
//                QueryWrapper queryWrapper1=new QueryWrapper();
//                queryWrapper1.eq("roleid",item.getRoleid());
//                List<RoleMenu> roleMenuList=roleMenuDao.selectList(queryWrapper1);
//                menuListFirst.add(roleMenuList);
//            }
//         return menuListFirst;
//    }


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
