package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.Collect;
import com.example.Entity.Remark;
import com.example.Service.CollectService;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/collect")
@RestController
public class CollectController {

    @Autowired
    private CollectService collectService;

    @GetMapping("/collectHouseByUser")
    public ResponseResult collectHouseByUser(HttpServletRequest httpServletRequest,int houseid){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);

        Collect collect=new Collect();
        collect.setCreatetime(new Date());
        collect.setHouseid(houseid);
        collect.setUserid(userid);

        boolean flag=collectService.save(collect);
        if(flag){
            return new ResponseResult(200,"收藏成功");
        }else{
            return new ResponseResult(100,"收藏失败");
        }
    }


    @GetMapping("/cancelCollectHouse")
    public ResponseResult cancelCollectHouse(HttpServletRequest httpServletRequest,int houseid){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("houseid",houseid);
        queryWrapper.eq("userid",userid);
        boolean flag=collectService.remove(queryWrapper);
        if(flag){
            return new ResponseResult(200,"取消数据成功");
        }else{
            return new ResponseResult(100,"取消数据失败");
        }
    }


    @GetMapping("/collectHouseCount")
    public ResponseResult collectHouseCount(HttpServletRequest httpServletRequest,int houseid){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("houseid",houseid);
        queryWrapper.eq("userid",userid);
        int total=collectService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(200,"已收藏");
        }else {
            return new ResponseResult(100,"没收藏");
        }
    }

    @GetMapping("/getPageCollectByPerson")
    public ResponseResult getPageCollectByPerson(int page,int size,String housename,HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);
        Page<Collect> answerVOPage = new Page<>(page,size);
        IPage<Collect> answerVOIPage = collectService.getPageCollectByPerson(answerVOPage,housename,userid);
        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @GetMapping("/getPageRemarkAll")
    public ResponseResult getPageRemarkAll(int page,int size,String housename){
        Page<Collect> answerVOPage = new Page<>(page,size);
        IPage<Collect> answerVOIPage = collectService.getPageRemarkAll(answerVOPage,housename);
        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @GetMapping("/deleteCollect")
    public ResponseResult deleteCollect(int id){
        boolean flag=collectService.removeById(id);
        if (flag){
            return new ResponseResult(200,"取消收藏成功");
        }else {
            return new ResponseResult(100,"取消收藏失败");
        }
    }
}
