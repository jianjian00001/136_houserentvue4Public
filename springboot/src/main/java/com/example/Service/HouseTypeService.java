package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.House;
import com.example.Entity.HouseType;

public interface HouseTypeService extends IService<HouseType> {
    IPage<HouseType> getPage(Page<HouseType> page, String housetypename);

}
