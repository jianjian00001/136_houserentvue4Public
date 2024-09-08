package com.example.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.Bugao;
import com.example.Entity.Collect;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BugaoDao extends BaseMapper<Bugao> {

    IPage<Bugao> getPageBugaoAll(Page page, String title);

}
