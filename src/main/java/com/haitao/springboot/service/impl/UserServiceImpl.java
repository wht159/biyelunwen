package com.haitao.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Constants;
import com.haitao.springboot.common.RoleEnum;
import com.haitao.springboot.controller.dto.UserDTO;
import com.haitao.springboot.controller.dto.UserPasswordDTO;
import com.haitao.springboot.entity.*;
import com.haitao.springboot.exception.ServiceException;
import com.haitao.springboot.mapper.*;
import com.haitao.springboot.service.IMenuService;
import com.haitao.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haitao.springboot.utils.TokenUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-01-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Resource
    private UserMapper userMapper;

    @Resource
    private StuTeaMapper stuTeaMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }


    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            // 默认一个普通用户的角色
//            one.setRole(RoleEnum.ROLE_USER.toString());
            save(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }



    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }



    @Override
    public List<User> selectTeaUsers() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "role", "ROLE_TEACHER");
        return userMapper.selectList(queryWrapper);
    }


//    设置学生老师表
    @Override
    public void setStudentTeacher(StudentTeacher studentTeacher) {
        int stu_num = studentTeacher.getStu_num();
        int tea_num = studentTeacher.getTea_num();

        if(null == stuTeaMapper.getTeaNum(stu_num)){
            stuTeaMapper.addStuTea(stu_num,tea_num);
        }else {
            if(stuTeaMapper.getTeaNum(stu_num) != tea_num)
                stuTeaMapper.update(stu_num,tea_num);
        }

    }

//    @Override
//    public void saveOrUpdateUser(User user) {
//
//            if (user.getRole().equals("ROLE_STUDENT")){
//                QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("sno", user.getStNum());
//                if(studentMapper.selectCount(queryWrapper)!= 0){
//                    saveOrUpdate(user);
//                }else {
//                    throw new ServiceException(Constants.CODE_600, "查找不到学生工号");
//                }
//            }else if(user.getRole().equals("ROLE_TEACHER")){
//                QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("tno", user.getStNum());
//                if(teacherMapper.selectCount(queryWrapper)!= 0){
//                     saveOrUpdate(user);
//                }else {
//                    throw new ServiceException(Constants.CODE_600, "查找不到教师工号");
//                }
//            }else saveOrUpdate(user);
//    }

    @Override
    public Object findByStNum(String stNum, String role) {
        if (role.equals("ROLE_STUDENT")){
            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sno",stNum);
            return studentMapper.selectOne(queryWrapper);
        }else if (role.equals("ROLE_TEACHER")){
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tno",stNum);
            return teacherMapper.selectOne(queryWrapper);
        } else return null;
    }


    //    获取当前用户信息
    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

}
