package com.example.Service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dao.MenuDao;
import com.example.Entity.Menu;
import com.example.Service.MenuService;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private MenuDao menuDao;


    @Override

    public List<Menu> getTotalPermission() {
        // 用boot获取分类数据   selectAll在springboot中是获取数据表里的所有数据
        List<Menu> data = menuDao.selectList(null);
        //定义新的list
        List<Menu> menuList = new ArrayList<>();
        //先找到所有的一级分类
        for(Menu menuInfo : data){
            // 一级菜单的parentId是0
            if(menuInfo.getParentid() == 0){
                menuList.add(menuInfo);
            }
        }
        menuList.sort(Comparator.comparingInt(Menu::getSortnumber));
        // 为一级菜单设置子菜单，getChild是递归调用的
        for(Menu parentMenuInfo : menuList){
            parentMenuInfo.setChildren(getChilde(parentMenuInfo.getId(), data));
        }
        return menuList;
    }

    @Override
    @Transactional
    public ResponseResult insertMenu(Menu menu) {
        if(menu.getType()==2){
//            查询菜单栏的id是不是为目录
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("id",menu.getParentid());
            Menu menu1=menuDao.selectOne(queryWrapper);
            if(menu1.getType()==0){
                return new ResponseResult(50,"按钮的上级必须为菜单栏");
            }
        }
        if(menu.getType()==1){
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("id",menu.getParentid());
            Menu menu1=menuDao.selectOne(queryWrapper);
            if(menu1.getType()==1){
                return new ResponseResult(50,"菜单栏上级必须为目录");
            }
        }
        if(menu.getType()==0){
            menu.setParentid(0);
        }
        menuDao.insert(menu);
        return new ResponseResult(200,"添加菜单栏成功");
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
