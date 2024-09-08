package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.Role;
import com.example.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    IPage<Role> getPageRole(Page page, String rolename);

}
