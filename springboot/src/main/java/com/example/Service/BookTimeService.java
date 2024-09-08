package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.BookHouse;
import com.example.Entity.Collect;

public interface BookTimeService extends IService<BookHouse> {

    IPage<BookHouse> getPageHouseBooktByPerson(Page<BookHouse> page, String housename, int userid);

    IPage<BookHouse> getPageHouseBooktByHouse(Page<BookHouse> page, String housename, int userid);

    IPage<BookHouse> getPageHouseBooktAll(Page<BookHouse> page, String housename);

}
