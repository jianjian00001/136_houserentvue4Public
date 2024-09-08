package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.HouseDetail;
import com.example.Entity.Remark;

public interface RemarkService extends IService<Remark> {
    IPage<Remark> getPageRemark(Page<Remark> page,int houseid);


    IPage<Remark> getPageRemarkByPerson(Page<Remark> page,String housename,int userid);


    IPage<Remark> getPageRemarkAll(Page<Remark> page,String housename);


}
