package cn.fzz.service;

import cn.fzz.bean.UserEntity;
import org.omg.CORBA.SystemException;

import java.util.List;

/**
 * Created by fanzezhen on 2017/12/13.
 * Desc:
 */
public interface UserService {
    /**
     * 用户类
     *
     * @return
     * @throws SystemException
     */

    /**
     * 把一条User数据插入数据库
     * 当商品不存在或者为空时会抛出自定义异常 <b>SystemException<b>
     * @return int
     * @throws SystemException
     */
    public int saveUser(UserEntity paramter) throws SystemException;

    public List<UserEntity> getAll() throws SystemException;

    public UserEntity getUserById(Long id) throws SystemException;
}
