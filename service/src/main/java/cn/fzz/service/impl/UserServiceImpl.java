package cn.fzz.service.impl;

import cn.fzz.bean.UserEntity;
import cn.fzz.dao.UserJPA;
import cn.fzz.service.UserService;
import org.omg.CORBA.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fanzezhen on 2017/12/13.
 * Desc:
 */
@Component
@ComponentScan(basePackages = {"cn.fzz.dao", "cn.fzz.repository"})
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserJPA userJPA;

    @Autowired
    public UserServiceImpl(UserJPA userJPA) {
        this.userJPA = userJPA;
    }

    /**
     * 把一条User数据插入数据库
     * 当商品不存在或者为空时会抛出自定义异常 <b>SystemException<b>
     *
     * @param paramter
     * @return int
     * @throws SystemException
     */
    @Override
    public int saveUser(UserEntity paramter) throws SystemException {
        UserEntity userEntity = userJPA.save(paramter);
//        int rtnCode = userMapper.saveUser(paramter);
        logger.info("保存用户信息: " + paramter + "\n保存结果： " + userEntity);
        return 0;
    }

    @Override
    public List<UserEntity> getAll() throws SecurityException {
        return userJPA.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) throws SystemException {
        return userJPA.getOne(id);
    }
}
