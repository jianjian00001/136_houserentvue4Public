package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Entity.RoleMenu;
import com.example.Entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu> {
}
