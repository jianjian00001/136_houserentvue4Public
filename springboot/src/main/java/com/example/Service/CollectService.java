package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.Collect;
import com.example.Entity.HouseDetail;
import com.example.Entity.Remark;

public interface CollectService extends IService<Collect> {

    IPage<Collect> getPageCollectByPerson(Page<Collect> page, String housename, int userid);

    IPage<Collect> getPageRemarkAll(Page<Collect> page, String housename);

}
