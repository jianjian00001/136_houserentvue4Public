package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.BookHouse;
import com.example.Entity.Collect;
import com.example.Entity.House;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookTimeDao extends BaseMapper<BookHouse> {
    IPage<BookHouse> getPageHouseBooktByPerson(Page page, String housename, int userid);

    IPage<BookHouse> getPageHouseBooktByHouse(Page page, String housename, int userid);

    IPage<BookHouse> getPageHouseBooktAll(Page page, String housename);

}
