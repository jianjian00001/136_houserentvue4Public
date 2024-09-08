package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.House;
import com.example.Entity.HouseType;
import com.example.Service.HouseDetailService;
import com.example.Service.HouseService;
import com.example.Service.HouseTypeService;
import com.example.Service.RemarkService;
import com.example.annotation.PassToken;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@RequestMapping("/api/house")
@RestController
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping("/getPageHouse")
    @PassToken
    public ResponseResult getPageHouse(int page,int size,String housename){
        Page<House> answerVOPage = new Page<>(page,size);
        IPage<House> answerVOIPage = houseService.getPage(answerVOPage,housename);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }


    @RequestMapping("/getPageHouseNormal")
    @PassToken
    public ResponseResult getPageHouseNormal(int page,int size,String housename){
        Page<House> answerVOPage = new Page<>(page,size);
        IPage<House> answerVOIPage = houseService.getPageHouseNormal(answerVOPage,housename);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @RequestMapping("/getPageHouseByPerson")
    public ResponseResult getPageHouseByPerson(int page, int size, String housename, HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);

        Page<House> answerVOPage = new Page<>(page,size);
        IPage<House> answerVOIPage = houseService.getPageHouseByPerson(answerVOPage,housename,userid);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }


    @Autowired
    private HouseTypeService houseTypeService;

    @GetMapping("/getHouseById")
    public ResponseResult getHouseById(int id){
        House house=houseService.getById(id);
         if(house.getHousetypeid()!=null){
             HouseType houseType=houseTypeService.getById(house.getHousetypeid());
             house.setHousetypename(houseType.getHousetypename());
         }
        return new ResponseResult(200,"查找数据成功",house);
    }

    @Autowired
    private HouseDetailService houseDetailService;

    @Autowired
    private RemarkService remarkService;

    @DeleteMapping("/deleteHouse")
    public ResponseResult deleteHouse(int id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("houseid",id);
        int total=houseDetailService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"该房间的摆设还存在数据，请先删除房间的摆设数据在删除房间数据");
        }
        int remarkTotal=remarkService.count(queryWrapper);
        if(remarkTotal>0){
            return new ResponseResult(100,"房屋评论下面存在评论，请先删除");
        }
        houseService.removeById(id);
        return new ResponseResult(200,"删除数据成功");
    }

    @GetMapping("/changeHouseStatus")
    public ResponseResult changeHouseStatus(int id,int housestatus){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("housestatus",housestatus);
        updateWrapper.eq("id",id);
        houseService.update(null,updateWrapper);
        return new ResponseResult(200,"更改状态成功");
    }

    @PostMapping("/addHouse")
    public ResponseResult addHouse(@RequestBody @Validated House house){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("housename",house.getHousename());
        int total=houseService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"已存在该房屋了");
        }
        house.setHousestatus(0);
        house.setPublishtime(new Date());
        boolean flag=houseService.save(house);
        if(!flag){
            return new ResponseResult(100,"添加失败");
        }
        return new ResponseResult(200,"添加数据成功");
    }


    @PostMapping("/updateHouse")
    public ResponseResult updateHouse(@RequestBody House house){
        House house1=houseService.getById(house.getId());
        if(!house1.getHousename().equals(house.getHousename())){
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("housename",house.getHousename());
            int total=houseService.count(queryWrapper);
            if(total>0){
                return new ResponseResult(100,"已存在该房屋了，请重新修改哈");
            }
        }
        houseService.updateById(house);
        return new ResponseResult(200,"修改数据成功");
    }


    @RequestMapping( "/houseAddPicture")
    public ResponseResult uploadFile(MultipartFile file) throws IOException {
        String filePath =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\house";

        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        //新的文件名字
        String newFileName = UUID.randomUUID() + originalFilename;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        String picturePath="http://localhost:8888/images/house/" + newFileName;
        HashMap hashMap=new HashMap();
        hashMap.put("photo",picturePath);
        hashMap.put("name",originalFilename);
        return new ResponseResult(200,"上传图片成功",hashMap);
    }

    @DeleteMapping("/deleteHousePicture")
    public ResponseResult deleteHousePicture(String filepath){
        if(!filepath.equals("")) {
            String[] fields = filepath.split("/");
            System.out.println(fields);
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\house\\" + fields[fields.length - 1];
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
}
