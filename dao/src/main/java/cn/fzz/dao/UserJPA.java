package cn.fzz.dao;

import cn.fzz.bean.UserEntity;
import cn.fzz.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserJPA extends BaseRepository<UserEntity, Long> {
    //查询大于20岁的用户
    @Query(value = "select * from user where age > ?1", nativeQuery = true)
    List<UserEntity> nativeQuery(int age);

    //根据邮箱查询用户
    @Query(value = "select * from user where email = ?1", nativeQuery = true)
    UserEntity getByEmail(String email);
    //根据邮箱查询用户
    @Query(value = "select * from user where username = ?1", nativeQuery = true)
    UserEntity getByUsername(String username);

    //根据用户名、密码删除一条数据
    @Modifying
    @Query(value = "delete from user where username = ?1 and password = ?2", nativeQuery = true)
    void deleteQuery(String name, String pwd);
}