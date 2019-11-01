package com.iuzx.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iuzx.edu.entity.Subject;
import com.iuzx.edu.vo.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Maguai
 * @since 2019-10-11
 */
public interface SubjectService extends IService<Subject> {

    List<String> importFile(MultipartFile file) throws Exception;

    List<SubjectNestedVo> nestedList();
}
