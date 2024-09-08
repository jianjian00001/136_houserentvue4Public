package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.House;
import com.example.Entity.HouseType;
import com.example.Service.HouseService;
import com.example.Service.HouseTypeService;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/api/housetype")
@RestController
public class HouseTypeController {

    @Autowired
    private HouseTypeService houseTypeService;

    @RequestMapping("/getPageHouseType")
    public ResponseResult getPageHouseType(int page,int size,String housetypename){
        Page<HouseType> answerVOPage = new Page<>(page,size);
        IPage<HouseType> answerVOIPage = houseTypeService.getPage(answerVOPage,housetypename);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @PostMapping("/addHouseType")
    public ResponseResult addHouseType(@RequestBody @Validated HouseType houseType){
        houseType.setCreatetime(new Date());
        houseType.setUpdatetime(new Date());
        boolean total=houseTypeService.save(houseType);
        if(!total){
            return new ResponseResult(100,"添加房屋类型失败");
        }
        return new ResponseResult(200,"添加房屋类型成功");
    }

    @GetMapping("/getHouseTypeByID")
    public ResponseResult getHouseTypeByID(int id){
        HouseType houseType=houseTypeService.getById(id);
        return new ResponseResult(200,"获取房屋类型成功",houseType);
    }


    @PostMapping("/updateHouseType")
    public ResponseResult updateHouseType(@RequestBody HouseType houseType){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("housetypename",houseType.getHousetypename());
        int total=houseTypeService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"更改房屋类型失败，已经存在该类型");
        }
        houseType.setUpdatetime(new Date());
        houseTypeService.updateById(houseType);
        return new ResponseResult(200,"更新数据成功");
    }

    @Autowired
    private HouseService houseService;

    @DeleteMapping("/deleteHouseType")
    public ResponseResult deleteHouseType(int id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("housetypeid",id);
        int total=houseService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"该房屋类型已经房屋页面使用");
        }

        houseTypeService.removeById(id);
        return new ResponseResult(200,"删除数据成功");
    }


    @GetMapping("/getAllHouseType")
    public ResponseResult getAllHouseType(){
        List<HouseType> houseTypeList=houseTypeService.list();
        return new ResponseResult(200,"查找下拉框数据成功",houseTypeList);
    }

}
