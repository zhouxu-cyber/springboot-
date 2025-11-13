package org.kob.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.kob.backend.pojo.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //MyBatis-Plus 提供的通用 Mapper 接口，其中封装了大量常用的数据库操作方法（CRUD）
}
