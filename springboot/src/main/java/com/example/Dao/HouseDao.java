package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.House;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseDao extends BaseMapper<House> {
    IPage<House> getPage(Page page, String housename);

    IPage<House> getPageHouseNormal(Page page, String housename);

    IPage<House> getPageHouseByPerson(Page page, String housename,int userid);
}
