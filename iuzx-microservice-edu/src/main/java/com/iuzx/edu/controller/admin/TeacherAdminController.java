package com.iuzx.edu.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iuzx.common.constants.ResultCodeEnum;
import com.iuzx.common.exception.MaguaiException;
import com.iuzx.common.vo.R;
import com.iuzx.edu.entity.Teacher;
import com.iuzx.edu.query.TeacherQuery;
import com.iuzx.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin //跨域
@Api("讲师管理")
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    @ApiOperation(value = "获取所有讲师列表")
    public R getTeacherList(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id删除讲师")
    public R deleteTeacherById(
            @PathVariable
            @ApiParam(name = "id",value = "讲师Id",required = true)
                    String id){
        teacherService.removeById(id);
        return R.ok();
    }


    @GetMapping("{pageNum}/{pageSize}")
    @ApiOperation(value = "分页获取讲师列表")
    public R getListByPage(
            @PathVariable
            @ApiParam(name = "pageNum",value = "当前页码",required = true)
                    Long pageNum,
            @ApiParam(name = "pageSize",value = "一页显示的条数",required = true)
            @PathVariable
                    Long pageSize
    ){
        if(pageNum <= 0 || pageSize <= 0){
            //throw new MaguaiException(21003, "参数不正确");
            throw new MaguaiException(ResultCodeEnum.PARAM_ERROR);
        }
        Page<Teacher> page = new Page<>(pageNum, pageSize);
        IPage<Teacher> iPage = teacherService.page(page, null);

        return R.ok().data("items",iPage.getRecords()).data("total",iPage.getTotal());
    }


    @GetMapping("query/{pageNum}/{pageSize}")
    @ApiOperation(value = "通过一些条件分页获取讲师列表")
    public R getListByPageAndTeacherQuery(
            @PathVariable
            @ApiParam(name = "pageNum",value = "当前页码",required = true)
                    Long pageNum,
            @ApiParam(name = "pageSize",value = "一页显示的条数",required = true)
            @PathVariable
                    Long pageSize,
            @ApiParam(name = "teacherQuery",value = "筛选条件",required = true)
            TeacherQuery teacherQuery
    ){
        if(pageNum <= 0 || pageSize <= 0){
            //throw new MaguaiException(21003, "参数不正确");
            throw new MaguaiException(ResultCodeEnum.PARAM_ERROR);
        }
        Page<Teacher> page = new Page<>(pageNum, pageSize);
        teacherService.pageQuery(page,teacherQuery);
        return R.ok().data("items",page.getRecords()).data("total",page.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "添加讲师对象")
    public R saveTeacher(
            @ApiParam(name = "teacher",value = "讲师对象",required = true)
            @RequestBody
            Teacher teacher
    ){
        teacherService.save(teacher);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "根据id修改讲师对象")
    public R updateById(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher){
        teacherService.updateById(teacher);
        return R.ok();
    }
}






