package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.House;
import com.example.Entity.HouseDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseDetailDao extends BaseMapper<HouseDetail> {
    IPage<HouseDetail> getPage(Page page, String housename);


    IPage<HouseDetail> getPageHouseDetailByPerson(Page page, String housename,int userid);

}
