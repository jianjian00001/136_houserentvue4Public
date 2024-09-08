package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.HouseDetail;
import com.example.Entity.Housedescription;

public interface HouseDescriptionService extends IService<Housedescription> {
    IPage<Housedescription> getPage(Page<Housedescription> page, String housename);


    IPage<Housedescription> getPageHouseDescriptionByPerson(Page<Housedescription> page, String housename,int userid);

}
