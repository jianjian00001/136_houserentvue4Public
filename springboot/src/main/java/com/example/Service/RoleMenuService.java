package com.example.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.Menu;
import com.example.Entity.RoleMenu;
import com.example.Entity.UserRole;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenu> {

    public List<Menu> getCheckMenu(int roleid);

    boolean deleteByIds(int[] ids,int roleid);

}
