package com.example.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.Menu;
import com.example.utils.ResponseResult;

import java.util.List;

public interface MenuService extends IService<Menu> {
    public List<Menu> getTotalPermission();

    ResponseResult insertMenu(Menu menu);
}
