package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.Collect;
import com.example.Entity.House;
import com.example.Entity.Remark;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectDao extends BaseMapper<Collect> {

    IPage<Collect> getPageCollectByPerson(Page page, String housename, int userid);


    IPage<Collect> getPageRemarkAll(Page page, String housename);

}
