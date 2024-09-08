package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.Menu;
import com.example.Entity.Role;
import com.example.Entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    IPage<User> getPageUser(Page<User> page, String username);


    List<Menu> getAllPermission(int id);

//    List<Role> getAllRole(int id);

}
