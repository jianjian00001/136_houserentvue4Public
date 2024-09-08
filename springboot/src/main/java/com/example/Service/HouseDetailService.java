package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.House;
import com.example.Entity.HouseDetail;

public interface HouseDetailService extends IService<HouseDetail> {
    IPage<HouseDetail> getPage(Page<HouseDetail> page, String housename);

    IPage<HouseDetail> getPageHouseDetailByPerson(Page<HouseDetail> page, String housename,int userid);
}
