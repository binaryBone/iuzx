package com.iuzx.edu.controller.admin;

import com.iuzx.common.constants.ResultCodeEnum;
import com.iuzx.common.exception.MaguaiException;
import com.iuzx.common.vo.R;
import com.iuzx.edu.service.SubjectService;
import com.iuzx.edu.vo.SubjectNestedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/edu/subject")
@CrossOrigin
@Api("课程分类管理")
@Slf4j
public class SubjectAdminController {


    @Autowired
    private SubjectService subjectService;


    @ApiOperation(value = "课程文件批量上传")
    @PostMapping("import")
    public R importSubjectFile(
            @ApiParam(name = "file",required = true,value = "课程文件")
            @RequestParam MultipartFile file
    ){
        try {
            List<String> strings = subjectService.importFile(file);
            if (strings.size() == 0){
                return R.ok().message("文件导入成功");
            }else {
                return R.error().message("文件导入失败").data("errorMsg",strings);
            }
        } catch (Exception e) {
           log.error(e.getMessage());
           throw new MaguaiException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }


    @ApiOperation(value = "嵌套数据列表")
    @GetMapping
    public R nestedList(){

        List<SubjectNestedVo> subjectNestedVoList = subjectService.nestedList();
        return R.ok().data("items", subjectNestedVoList);
    }

}
