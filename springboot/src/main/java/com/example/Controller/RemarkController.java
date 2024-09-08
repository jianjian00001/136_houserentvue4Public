package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.Collect;
import com.example.Entity.House;
import com.example.Entity.Remark;
import com.example.Service.CollectService;
import com.example.Service.RemarkService;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/remark")
@RestController
public class RemarkController {

    @Autowired
    private RemarkService remarkService;

    @GetMapping("/getPageRemark")
    public ResponseResult getPageRemark(int page,int size,int houseid){
        Page<Remark> answerVOPage = new Page<>(page,size);
        IPage<Remark> answerVOIPage = remarkService.getPageRemark(answerVOPage,houseid);
        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @GetMapping("/getPageRemarkByPerson")
    public ResponseResult getPageRemarkByPerson(int page,int size,String housename,HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);
        Page<Remark> answerVOPage = new Page<>(page,size);
        IPage<Remark> answerVOIPage = remarkService.getPageRemarkByPerson(answerVOPage,housename,userid);
        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @GetMapping("/getPageRemarkAll")
    public ResponseResult getPageRemarkAll(int page,int size,String housename){
        Page<Remark> answerVOPage = new Page<>(page,size);
        IPage<Remark> answerVOIPage = remarkService.getPageRemarkAll(answerVOPage,housename);
        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @PostMapping("/addRemark")
    public ResponseResult addRemark(@RequestBody Remark remark,HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);
        remark.setUserid(userid);
        remark.setCreatetime(new Date());
        remark.setUpdatetime(new Date());
        boolean flag=remarkService.save(remark);
        if(flag){
            return new ResponseResult(200,"添加数据成功");
        }else{
            return new ResponseResult(100,"添加评论失败");
        }
    }

    @DeleteMapping("/deleteRemark")
    public ResponseResult deleteRemark(int id){
        boolean flag=remarkService.removeById(id);
        if (flag){
            return new ResponseResult(200,"删除数据成功");
        }else{
            return new ResponseResult(100,"删除评论失败");
        }
    }
}
