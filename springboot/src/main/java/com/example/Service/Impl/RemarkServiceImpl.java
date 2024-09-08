package com.example.Service.Impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dao.CollectDao;
import com.example.Dao.HouseDao;
import com.example.Dao.RemarkDao;
import com.example.Entity.Collect;
import com.example.Entity.House;
import com.example.Entity.HouseDetail;
import com.example.Entity.Remark;
import com.example.Service.CollectService;
import com.example.Service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RemarkServiceImpl extends ServiceImpl<RemarkDao, Remark> implements RemarkService {


    @Autowired
    private RemarkDao remarkDao;

    @Override
    public IPage<Remark> getPageRemark(Page<Remark> page,int houseid) {
        return remarkDao.getPageRemark(page,houseid);
    }

    @Override
    public IPage<Remark> getPageRemarkByPerson(Page<Remark> page, String housename, int userid) {
        return remarkDao.getPageRemarkByPerson(page,housename,userid);
    }

    @Override
    public IPage<Remark> getPageRemarkAll(Page<Remark> page, String housename) {
        return remarkDao.getPageRemarkAll(page,housename);
    }
}
