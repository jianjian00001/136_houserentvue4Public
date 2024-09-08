package com.example.Controller;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.House;
import com.example.Entity.HouseDetail;
import com.example.Entity.Housedescription;
import com.example.Service.CollectService;
import com.example.Service.HouseDescriptionService;
import com.example.Service.HouseService;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping("/api/housedescription")
@RestController
public class HousedescriptionController {

    @Autowired
    private HouseDescriptionService houseDescriptionService;

    @RequestMapping("/getPageHouseDescription")
    public ResponseResult getPageHouseDescription(int page,int size,String housename){
        Page<Housedescription> answerVOPage = new Page<>(page,size);
        IPage<Housedescription> answerVOIPage = houseDescriptionService.getPage(answerVOPage,housename);
        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @RequestMapping("/getPageHouseDescriptionByPerson")
    public ResponseResult getPageHouseDescriptionByPerson(int page, int size, String housename, HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);
        Page<Housedescription> answerVOPage = new Page<>(page,size);
        IPage<Housedescription> answerVOIPage = houseDescriptionService.getPageHouseDescriptionByPerson(answerVOPage,housename,userid);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @RequestMapping( "/houseDescribeAddPicture")
    public ResponseResult uploadFile(MultipartFile file) throws IOException {
        String filePath =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\houseDescribe";

        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        //新的文件名字
        String newFileName = UUID.randomUUID() + originalFilename;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        String picturePath="http://localhost:8888/images/houseDescribe/" + newFileName;
        HashMap hashMap=new HashMap();
        hashMap.put("photo",picturePath);
        hashMap.put("name",originalFilename);
        return new ResponseResult(200,"上传图片成功",picturePath);
    }


    @DeleteMapping("/deleteHouseDescriptionPic")
    public ResponseResult deleteHousePicture(String filepath){
        if(!filepath.equals("")) {
            String[] fields = filepath.split("/");
            System.out.println(fields);
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\houseDescribe\\" + fields[fields.length - 1];
            System.out.println(filePath);
            File fileOld = new File(filePath);
//            路径是个文件且不为空时删除文件
            boolean flag = fileOld.delete();
            if (!flag) {
                return new ResponseResult(100, "删除图片失败");
            }
        }
        return new ResponseResult(200,"删除图片成功");
    }

    @PostMapping("/addHouseDescription")
    public ResponseResult addHouseDescription(@RequestBody @Validated Housedescription housedescription){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("houseid",housedescription.getHouseid());
        int total=houseDescriptionService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"已存在图片详情了，请选择更改");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< housedescription.getPhototemp().size();i++){
            System.out.println(housedescription.getPhototemp().get(i).get("url"));
            sb.append(housedescription.getPhototemp().get(i).get("url"));
            if(housedescription.getPhototemp().size()-1!=i){
                sb.append(",");
            }
        }
        housedescription.setPhoto(sb.toString());
        housedescription.setCreatetime(new Date());
        houseDescriptionService.save(housedescription);
        return new ResponseResult(200,"添加数据成功");
    }

    @DeleteMapping("/deleteHouseDescription")
    public ResponseResult deleteHouseDescription(int id){
        Housedescription housedescription=houseDescriptionService.getById(id);
        String[] strarray=housedescription.getPhoto().split(",");//得到一个List数组
        for (int i = 0; i < strarray.length; i++){
            if(!strarray[i].equals("")) {
                String[] fields = strarray[i].split("/");
                String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\houseDescribe\\" + fields[fields.length - 1];
                System.out.println(filePath);
                File fileOld = new File(filePath);
//            路径是个文件且不为空时删除文件
                boolean flag = fileOld.delete();
                if (!flag) {
                    return new ResponseResult(100, "删除图片失败");
                }
            }
        }
        houseDescriptionService.removeById(id);

        return new ResponseResult(200,"删除房屋详情成功");
    }

    @Autowired
    private HouseService houseService;

    @GetMapping("/getHouseDescriptionByID")
    public ResponseResult getHouseDescriptionByID(int id){
        Housedescription housedescription=houseDescriptionService.getById(id);
        if(housedescription==null){
            return new ResponseResult(100,"房东没有添加房屋详情图片");
        }
        if(housedescription.getPhoto().length()!=0){
            String[] strarray=housedescription.getPhoto().split(",");//得到一个List数组
            List list=new ArrayList();
            for (int i = 0; i < strarray.length; i++){
                HashMap hashMap=new HashMap();
                hashMap.put("name","test数据");
                hashMap.put("url",strarray[i]);
                list.add(hashMap);
            }
            housedescription.setPhototemp(list);
        }
        House house=houseService.getById(housedescription.getHouseid());
        housedescription.setHousename(house.getHousename());
        return new ResponseResult(200,"获取图片详情成功",housedescription);
    }

    @GetMapping("/getHouseDescriptionByHouseID")
    public ResponseResult getHouseDescriptionByHouseID(int houseid){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("houseid",houseid);
        Housedescription housedescription=houseDescriptionService.getOne(queryWrapper);
        if(housedescription==null){
            return new ResponseResult(100,"房东没有添加房屋详情图片");
        }
        if(housedescription.getPhoto().length()!=0){
            String[] strarray=housedescription.getPhoto().split(",");//得到一个List数组
            List list=new ArrayList();
            for (int i = 0; i < strarray.length; i++){
                HashMap hashMap=new HashMap();
                hashMap.put("name","test数据");
                hashMap.put("url",strarray[i]);
                list.add(hashMap);
            }
            housedescription.setPhototemp(list);
        }
        House house=houseService.getById(housedescription.getHouseid());
        housedescription.setHousename(house.getHousename());
        return new ResponseResult(200,"获取图片详情成功",housedescription);
    }

    @PostMapping("/updateHouseDescription")
    public ResponseResult updateHouseDescription(@RequestBody Housedescription housedescription){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< housedescription.getPhototemp().size();i++){
            System.out.println(housedescription.getPhototemp().get(i).get("url"));
            sb.append(housedescription.getPhototemp().get(i).get("url"));
            if(housedescription.getPhototemp().size()-1!=i){
                sb.append(",");
            }
        }
        housedescription.setPhoto(sb.toString());
        boolean flag=houseDescriptionService.updateById(housedescription);
        if (flag){
            return new ResponseResult(200,"更改数据成功");
        }else {
            return new ResponseResult(100, "更改数据失败");
        }
    }
}
