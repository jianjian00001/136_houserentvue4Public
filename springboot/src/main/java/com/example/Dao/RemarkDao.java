package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.Remark;
import com.example.Entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemarkDao extends BaseMapper<Remark> {

    IPage<Remark> getPageRemark(Page page,int houseid);


    IPage<Remark> getPageRemarkByPerson(Page page,String housename,int userid);


    IPage<Remark> getPageRemarkAll(Page page,String housename);


}
