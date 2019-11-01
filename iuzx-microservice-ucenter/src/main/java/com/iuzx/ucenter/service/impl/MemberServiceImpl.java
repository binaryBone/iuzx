package com.iuzx.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iuzx.ucenter.entity.Member;
import com.iuzx.ucenter.mapper.MemberMapper;
import com.iuzx.ucenter.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Maguai
 * @since 2019-10-30
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Integer userRegisterCounter(String day) {
        return baseMapper.userRegisterCounter(day);
    }
}
