package com.haitao.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.controller.dto.UserDTO;
import com.haitao.springboot.controller.dto.UserPasswordDTO;
import com.haitao.springboot.entity.StudentTeacher;
import com.haitao.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-01-26
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);


    List<User> selectTeaUsers();

    void setStudentTeacher(StudentTeacher studentTeacher);

//    void saveOrUpdateUser(User user);

    Object findByStNum(String stNum, String role);
}
