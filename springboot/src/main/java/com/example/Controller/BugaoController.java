package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.Bugao;
import com.example.Entity.Collect;
import com.example.Service.BugaoService;
import com.example.Service.CollectService;
import com.example.annotation.PassToken;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@RequestMapping("/api/bugao")
@RestController
public class BugaoController {

    @Autowired
    private BugaoService bugaoService;


    @GetMapping("/getPageBugaoAll")
    @PassToken
    public ResponseResult getPageRemarkAll(int page,int size,String title){
        Page<Bugao> answerVOPage = new Page<>(page,size);
        IPage<Bugao> answerVOIPage = bugaoService.getPageBugaoAll(answerVOPage,title);
        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @RequestMapping( "/bugaoAddPicture")
    public ResponseResult uploadFile(MultipartFile file) throws IOException {
        String filePath =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\bugao";

        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        //新的文件名字
        String newFileName = UUID.randomUUID() + originalFilename;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        String picturePath="http://localhost:8888/images/bugao/" + newFileName;
        HashMap hashMap=new HashMap();
        hashMap.put("url",picturePath);
        hashMap.put("name",originalFilename);
        return new ResponseResult(200,"上传图片成功",hashMap);
    }

    @RequestMapping("/addBugao")
    public ResponseResult addBugao(@RequestBody Bugao bugao){
        bugao.setCreatetime(new Date());
        bugao.setUpdatetime(new Date());
        bugaoService.save(bugao);
        return new ResponseResult(200,"添加布告成功");
    }

    @GetMapping("/getBugaoID")
    @PassToken
    public ResponseResult getBugaoID(int id){
        Bugao bugao=bugaoService.getById(id);
        return new ResponseResult(200,"获取布告信息成功",bugao);
    }

    @PostMapping("/updateBugao")
    public ResponseResult updateBugao(@RequestBody Bugao bugao){
        boolean flag=bugaoService.updateById(bugao);
        if (flag){
            return new ResponseResult(200,"更改数据成功");
        }else{
            return new ResponseResult(200,"更新布告信息成功");
        }
    }

    @GetMapping("/deleteBugao")
    public ResponseResult deleteBugao(int id){
        boolean flag=bugaoService.removeById(id);
        if (flag){
            return new ResponseResult(200,"删除布告数据成功");
        }else{
            return new ResponseResult(100,"删除布告数据成功");
        }
    }

}
