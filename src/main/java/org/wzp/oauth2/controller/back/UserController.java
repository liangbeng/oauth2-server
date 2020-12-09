package org.wzp.oauth2.controller.back;

import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.config.CustomConfig;
import org.wzp.oauth2.entity.Authority;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.entity.UserRole;
import org.wzp.oauth2.enumeration.ResultCodeEnum;
import org.wzp.oauth2.mapper.AuthorityMapper;
import org.wzp.oauth2.mapper.UserMapper;
import org.wzp.oauth2.mapper.UserRoleMapper;
import org.wzp.oauth2.service.ExcelService;
import org.wzp.oauth2.service.RedisService;
import org.wzp.oauth2.util.IpUtil;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.util.StringUtil;
import org.wzp.oauth2.vo.IdVO;
import org.wzp.oauth2.vo.LoginVO;
import org.wzp.oauth2.vo.UpdatePasswordVO;
import org.wzp.oauth2.vo.UserVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/28 16:57
 */
@Api(tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("/back/user")
public class UserController extends BaseConfig {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AuthorityMapper authorityMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private ExcelService excelService;


    /**
     * @JsonFilters(@JsonFilter(target = Authority.class, includes = {"id","name"}, excludes = {"createdTime","updateTime"})
     * )
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVO loginVO) {
        if (StringUtil.isEmpty(loginVO.getUsername()) || StringUtils.isEmpty(loginVO.getPassword())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Map<String, Object> map1 = new HashMap<>(2);
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return Result.error(ResultCodeEnum.ERROR_USERNAME_OR_PASSWORD);
        }
        if (!user.getEnable()) {
            return Result.error(ResultCodeEnum.USER_NOT_ENABLE);
        }
        boolean flag = new BCryptPasswordEncoder().matches(password, user.getPassword());
        if (!flag) {
            return Result.error(ResultCodeEnum.ERROR_USERNAME_OR_PASSWORD);
        }
        Map<String, String> responseEntity = getToken(username, password);
        if (StringUtil.isEmptyMap(responseEntity)) {
            return Result.error(ResultCodeEnum.FORBIDDEN);
        }
        List<Authority> list = authorityMapper.findByUsername(username);
        if (!StringUtil.isEmptyList(list)) {
            user.setAuthorityList(list);
        }
        String ip = IpUtil.getRealIp(request);
        user.setLastLoginIp(ip);
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateByPrimaryKeySelective(user);
        map1.put("user", user);
        map1.put("token", responseEntity);
        return Result.ok(map1);
    }


    @ApiOperation("注册用户")
    @ApiOperationSupport(ignoreParameters = {"id", "updatedTime"})
    @PostMapping("/register")
    public Result<User> register(@RequestBody UserVO userVO) {
        if (StringUtil.isEmpty(userVO.getUsername())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        String username = userVO.getUsername();
        User user = userMapper.selectByUsername(username);
        if (user != null) {
            return Result.error(ResultCodeEnum.HAS_USER);
        }
        user = new User();
        userVO.setPassword(new BCryptPasswordEncoder().encode(CustomConfig.defaultPassword));
        BeanUtils.copyProperties(userVO, user);
        user.setRegisterIp(IpUtil.getRealIp(request));
        userMapper.insertSelective(user);
        return Result.ok(user);
    }


    @ApiOperation("修改用户")
    @ApiOperationSupport(ignoreParameters = {"updatedTime"})
    @PreAuthorize("hasAnyRole('ROLE_USER')")// 只能user角色才能访问该接口
    @PostMapping("/update")
    public Result<User> update(@RequestBody UserVO userVO) {
        if (StringUtil.isEmpty(userVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        User user = userMapper.selectByPrimaryKey(userVO.getId());
        if (user == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        if (!StringUtil.isEmpty(userVO.getUsername())) {
            User user1 = userMapper.selectByUsername(userVO.getUsername());
            if (user1 != null && !user1.getId().equals(userVO.getId())) {
                return Result.error(ResultCodeEnum.HAS_USER);
            }
        }
        BeanUtils.copyProperties(userVO, user);
        userMapper.updateByPrimaryKeySelective(user);
        if (!StringUtil.isEmptyList(userVO.getRoleIds())) {
            List<UserRole> userRoleList = userRoleMapper.selectByUserId(user.getId());
            if (!StringUtil.isEmptyList(userRoleList)) {
                userRoleMapper.deleteByUserId(user.getId());
            }
            List<Long> roleIds = userVO.getRoleIds();
            roleIds.forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);
            });
        }
        return Result.ok(user);
    }


    @ApiOperation("删除用户")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')") // 只能admin角色才能访问该接口
    @PostMapping("/delete")
    public Result delete(@RequestBody IdVO idVO) {
        if (StringUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        User user = userMapper.selectByPrimaryKey(idVO.getId());
        if (user == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        List<UserRole> list = userRoleMapper.selectByUserId(idVO.getId());
        if (!StringUtil.isEmptyList(list)) {
            list.forEach(userRole -> {
                userRoleMapper.deleteByPrimaryKey(userRole.getId());
            });
        }
        userMapper.deleteByPrimaryKey(idVO.getId());
        return Result.ok();
    }


    @ApiOperation("冻结用户")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')") // 只能admin角色才能访问该接口
    @PostMapping("/freeze")
    public Result freeze(@RequestBody IdVO idVO) {
        if (StringUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        User user = userMapper.selectByPrimaryKey(idVO.getId());
        if (user == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        user.setEnable(false);
        userMapper.updateByPrimaryKeySelective(user);
        return Result.ok();
    }


    @ApiOperation("激活用户")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')") // 只能admin角色才能访问该接口
    @PostMapping("/activation")
    public Result activation(@RequestBody IdVO idVO) {
        if (StringUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        User user = userMapper.selectByPrimaryKey(idVO.getId());
        if (user == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        user.setEnable(true);
        userMapper.updateByPrimaryKeySelective(user);
        return Result.ok();
    }


    @ApiOperation("根据id查询数据")
    @PostMapping("/getOne")
    public Result<User> getOne(@RequestBody IdVO idVO) {
        if (StringUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        User user = userMapper.selectByPrimaryKey(idVO.getId());
        if (user == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        return Result.ok(user);
    }


    @ApiOperation("根据条件查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "string", paramType = "query", example = "张三"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query", example = "admin"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "page", value = "页数，从0开始", dataType = "int", paramType = "query", example = "0"),
            @ApiImplicitParam(name = "sort", value = "排序规则，可传入多个sort参数", dataType = "string", paramType = "query", example = "createdAt desc")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/findAll")
    public Result<PageInfo> findAll(@RequestBody HashMap<String, Object> map) {
        getPageRequest(map);
        //判断key是否存在，存在则从redis取，不存在则查询数据库
        String key = getKey("userList", map);
        boolean hasKey = redisService.hasKey(key);
        PageInfo pageInfo;
        if (hasKey) {
            pageInfo = (PageInfo) redisService.get(key);
        } else {
            if (!StringUtil.isEmpty(map.get("name"))) {
                map.put("name", map.get("name"));
            }
            if (!StringUtil.isEmpty(map.get("username"))) {
                map.put("username", map.get("username"));
            }
            List<User> list = userMapper.findAllBySome(map);
            pageInfo = new PageInfo<>(list);
            redisService.set(key, pageInfo);
        }
        return Result.ok(pageInfo);
    }


    @ApiOperation("修改密码")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordVO updatePasswordVO) {
        if (StringUtil.isEmpty(updatePasswordVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        User user = userMapper.selectByPrimaryKey(updatePasswordVO.getId());
        if (user == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        String oldPassword = updatePasswordVO.getOldPassword();
        String newPassword = updatePasswordVO.getNewPassword();
        String oldPasswordStr = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean flag = bCryptPasswordEncoder.matches(oldPassword, oldPasswordStr);
        if (!flag) {
            return Result.error(ResultCodeEnum.OLD_PASSWORD_ERROR);
        }
        boolean flag1 = bCryptPasswordEncoder.matches(newPassword, oldPasswordStr);
        if (flag1) {
            return Result.error(ResultCodeEnum.NEW_PASSWORD_HAS_SAME);
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userMapper.updateByPrimaryKeySelective(user);
        redisService.loginOut(user.getUsername());
        return Result.ok();
    }


    @ApiOperation("重置密码")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody UpdatePasswordVO updatePasswordVO) {
        if (StringUtil.isEmpty(updatePasswordVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        User user = userMapper.selectByPrimaryKey(updatePasswordVO.getId());
        if (user == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        String newPassword = updatePasswordVO.getNewPassword();
        String oldPassword = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean flag = bCryptPasswordEncoder.matches(newPassword, oldPassword);
        if (flag) {
            return Result.error(ResultCodeEnum.PASSWORD_HAS_SAME);
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userMapper.updateByPrimaryKeySelective(user);
        redisService.loginOut(user.getUsername());
        return Result.ok();
    }


    @ApiOperation("excel导出")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/excelDownloads")
    public Result excelDownloads(@RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> map1 = new HashMap<>(5);
        if (!StringUtil.isEmpty(map.get("name"))) {
            map1.put("name", map.get("name"));
        }
        if (!StringUtil.isEmpty(map.get("username"))) {
            map1.put("username", map.get("username"));
        }
        List<User> list = userMapper.findAllBySome(map1);
        boolean getExcelData = excelService.getUserExcelData(list);
        // excel相关属性设置
        if (!getExcelData) {
            return Result.error(ResultCodeEnum.ERROR_EXCEL_DOWNLAND);
        }
        return null;
    }


    @ApiOperation("excel导入")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/importExcel")
    public Result importExcel(MultipartFile file) throws IOException {
        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            //读取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            //获取一共多少行
            int lastRowNum = sheet.getLastRowNum();
            //遍历sheet，保存用户
            excelService.setUserExcelData(sheet, lastRowNum);
        } catch (Exception e) {
            throw e;
        }
        return Result.ok(ResultCodeEnum.EXCEL_SUCCESS_IMPORT);
    }


}
