package com.haitao.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Constants;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.controller.dto.UserDTO;
import com.haitao.springboot.controller.dto.UserPasswordDTO;
import com.haitao.springboot.entity.StudentTeacher;
import com.haitao.springboot.entity.User;
import com.haitao.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴海韬
 * @since 2022-01-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${files.upload.path}")
    private String filesUploadPath;

    @Resource
    private IUserService userService;

//    登录
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }
//    注册
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String stNum = userDTO.getStNum();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(stNum)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    // 新增或者更新用户信息
    @PostMapping
    public Result save(@RequestBody User user) {
        if (user.getId() == null && user.getPassword() == null) {  // 新增用户默认密码
            user.setPassword("123");
        }
        return Result.success(userService.saveOrUpdate(user));
//        if(user.getRole().equals("ROLE_ADMIN") ){
//            return Result.success(userService.saveOrUpdate(user));
//        }else {
//            if(!"".equals(user.getStNum()) && user.getStNum() != null){
//                userService.saveOrUpdateUser(user);
//                return Result.success();
//            } else {
//                return Result.error(Constants.CODE_400,"参数不能为空");
//            }
//        }
    }
    /**
     * 修改密码
     * @param userPasswordDTO
     * @return
     */
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

//    按照id删除用户
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeByIds(ids));
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/tea")
    public Result findAllTea() {
        return Result.success(userService.selectTeaUsers());
    }

//    获取角色表
    @GetMapping("/role/{role}")
    public Result findUsersByRole(@PathVariable String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", role);
        List<User> list = userService.list(queryWrapper);
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/stNum")
    public Result findByStNum(@RequestParam String stNum , @RequestParam String role) {
        return Result.success(userService.findByStNum(stNum,role));
    }

    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String stNum) {
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!username.equals("") && username != null){
            queryWrapper.eq("username", username);
        }
        if(!stNum.equals("") && stNum != null){
            queryWrapper.eq("st_num", stNum);
        }
            return Result.success(userService.page(page,queryWrapper));
    }

    @PostMapping("/student-teacher")
    public Result saveStudentTeacher(@RequestBody StudentTeacher studentTeacher) {
        userService.setStudentTeacher(studentTeacher);
        return Result.success();
    }

}

