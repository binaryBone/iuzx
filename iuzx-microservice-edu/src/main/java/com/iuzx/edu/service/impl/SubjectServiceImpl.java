package com.iuzx.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iuzx.common.util.ExcelImportUtil;
import com.iuzx.edu.entity.Subject;
import com.iuzx.edu.mapper.SubjectMapper;
import com.iuzx.edu.service.SubjectService;
import com.iuzx.edu.vo.SubjectNestedVo;
import com.iuzx.edu.vo.SubjectVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Maguai
 * @since 2019-10-11
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Transactional
    @Override
    public List<String> importFile(MultipartFile file) throws Exception {
        List<String> errorMsg = new ArrayList<>();
        ExcelImportUtil excelImportUtil = new ExcelImportUtil(file.getInputStream());
        Sheet sheet = excelImportUtil.getSheet();

        int rows = sheet.getPhysicalNumberOfRows();
        if (rows <= 1){
            errorMsg.add("请填写数据");
            return errorMsg;
        }

        for (int rowNum = 1; rowNum < rows; rowNum++) {
            Row rowData = sheet.getRow(rowNum);

            //获取一级分类
            String levelOneValue = "";
            String parentId = null;
            if(rowData != null){
                Cell levelOneCell = rowData.getCell(0);
                if (levelOneCell != null){
                    levelOneValue = excelImportUtil.getCellValue(levelOneCell).trim();
                    if (StringUtils.isEmpty(levelOneValue)){
                        errorMsg.add("第"+ rowNum +"行第一级分类为空");
                        continue;
                    }
                }
            }

            Subject subjectByTitle = this.getOneSubjectByTitle(levelOneValue);

            if (subjectByTitle == null){
                Subject subject = new Subject();
                subject.setTitle(levelOneValue);
                subject.setSort(rowNum);
                baseMapper.insert(subject);

            }else {
                parentId = subjectByTitle.getId();
            }

            //获取二级分类
            String levelTwoValue = "";
            Cell levelTwoCell = rowData.getCell(1);
            if(levelTwoCell != null){
                levelTwoValue = excelImportUtil.getCellValue(levelTwoCell).trim();
                if (StringUtils.isEmpty(levelTwoValue)) {
                    errorMsg.add("第" + rowNum + "行二级分类为空");
                    continue;
                }
            }

            //判断二级分类是否重复
            Subject subjectSub = this.getTwoSubjectByTitle(levelTwoValue, parentId);
            Subject subjectLevelTwo = null;
            if(subjectSub == null){
                //将二级分类存入数据库
                subjectLevelTwo = new Subject();
                subjectLevelTwo.setTitle(levelTwoValue);
                subjectLevelTwo.setParentId(parentId);
                subjectLevelTwo.setSort(rowNum);
                baseMapper.insert(subjectLevelTwo);//添加
            }

        }


        return errorMsg;
    }

    /**
     * 获取一级分类列表
     * @return
     */
    @Override
    public List<SubjectNestedVo> nestedList() {
        //最终要的到的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();

        //获取一级分类数据记录
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<Subject> subjects = baseMapper.selectList(queryWrapper);

        //获取二级分类数据记录 TODO

        //填充一级分类vo数据
        int count = subjects.size();
        for (int i = 0; i < count; i++) {
            Subject subject = subjects.get(i);

            //创建一级类别vo对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(subject, subjectNestedVo);
            subjectNestedVoArrayList.add(subjectNestedVo);

            //填充二级分类vo数据 TODO

            //获取二级分类数据记录
            QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.ne("parent_id", 0);
            queryWrapper2.orderByAsc("sort", "id");
            List<Subject> subSubjects = baseMapper.selectList(queryWrapper2);

            //填充二级分类vo数据
            ArrayList<SubjectVo> subjectVoArrayList = new ArrayList<>();
            int count2 = subSubjects.size();
            for (int j = 0; j < count2; j++) {

                Subject subSubject = subSubjects.get(j);
                if(subject.getId().equals(subSubject.getParentId())){

                    //创建二级类别vo对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject, subjectVo);
                    subjectVoArrayList.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subjectVoArrayList);
        }

        return subjectNestedVoArrayList;
    }

    /**
     * 判断一级分类是否重复
     * @param title
     * @return
     */
    private Subject getOneSubjectByTitle(String title){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",0);
        Subject subject = baseMapper.selectOne(wrapper);
        return subject;
    }


    /**
     * 判断二级分类是否重复
     * @param title
     * @param parentId
     * @return
     */
    private Subject getTwoSubjectByTitle(String title,String parentId){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",parentId);
        Subject subject = baseMapper.selectOne(wrapper);
        return subject;
    }
}
