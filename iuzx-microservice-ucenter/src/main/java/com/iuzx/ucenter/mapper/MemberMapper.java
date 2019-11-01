package com.iuzx.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iuzx.ucenter.entity.Member;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Maguai
 * @since 2019-10-30
 */

@Repository
public interface MemberMapper extends BaseMapper<Member> {

    Integer userRegisterCounter(String day);
}
