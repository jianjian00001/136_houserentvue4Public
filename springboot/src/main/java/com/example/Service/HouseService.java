package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.House;

public interface HouseService extends IService<House> {
    IPage<House> getPage(Page<House> page, String housename);


    IPage<House> getPageHouseNormal(Page<House> page, String housename);


    IPage<House> getPageHouseByPerson(Page<House> page, String housename,int userid);

}
