package com.iuzx.statistics.service;

import com.iuzx.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Maguai
 * @since 2019-10-30
 */
public interface DailyService extends IService<Daily> {

    void createStatisticsByDay(String day);

}
