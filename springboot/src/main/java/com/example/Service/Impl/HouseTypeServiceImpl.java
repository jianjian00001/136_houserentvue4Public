package com.example.Service.Impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dao.HouseDao;
import com.example.Dao.HouseTypeDao;
import com.example.Entity.House;
import com.example.Entity.HouseType;
import com.example.Service.HouseService;
import com.example.Service.HouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HouseTypeServiceImpl extends ServiceImpl<HouseTypeDao, HouseType> implements HouseTypeService {

    @Autowired
    private HouseTypeDao houseTypeDao;

    @Override
    public IPage<HouseType> getPage(Page<HouseType> page, String housetypename) {
        return houseTypeDao.getPage(page,housetypename);
    }
}
