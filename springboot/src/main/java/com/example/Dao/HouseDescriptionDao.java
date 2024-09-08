package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.HouseDetail;
import com.example.Entity.Housedescription;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseDescriptionDao extends BaseMapper<Housedescription> {
    IPage<Housedescription> getPage(Page page, String housename);

    IPage<Housedescription> getPageHouseDescriptionByPerson(Page page,String housename,int userid);
}
