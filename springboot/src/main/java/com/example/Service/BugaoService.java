package com.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Entity.BookHouse;
import com.example.Entity.Bugao;

public interface BugaoService extends IService<Bugao> {

    IPage<Bugao> getPageBugaoAll(Page<Bugao> page, String title);

}
