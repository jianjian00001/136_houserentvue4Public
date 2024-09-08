package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.Role;

public interface RoleService extends IService<Role> {
    IPage<Role> getPageRole(Page<Role> page, String rolename);

}
