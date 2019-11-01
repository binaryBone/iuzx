package com.iuzx.ucenter.service;

import com.iuzx.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Maguai
 * @since 2019-10-30
 */
public interface MemberService extends IService<Member> {

    Integer userRegisterCounter(String day);
}
