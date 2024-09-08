package com.example.Service.Impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dao.HouseDao;
import com.example.Entity.House;
import com.example.Service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HouseServiceImpl extends ServiceImpl<HouseDao, House> implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Override
    public IPage<House> getPage(Page<House> page, String housename) {
        return houseDao.getPage(page,housename);
    }

    @Override
    public IPage<House> getPageHouseNormal(Page<House> page, String housename) {
        return houseDao.getPageHouseNormal(page,housename);
    }

    @Override
    public IPage<House> getPageHouseByPerson(Page<House> page, String housename, int userid) {
        return houseDao.getPageHouseByPerson(page,housename,userid);
    }
}
