package org.wzp.oauth2.controller.back;

import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.entity.Role;
import org.wzp.oauth2.entity.RoleAuthority;
import org.wzp.oauth2.enumeration.ResultCodeEnum;
import org.wzp.oauth2.mapper.RoleAuthorityMapper;
import org.wzp.oauth2.mapper.RoleMapper;
import org.wzp.oauth2.util.RedisUtil;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.util.ObjUtil;
import org.wzp.oauth2.vo.IdVO;
import org.wzp.oauth2.vo.RoleVO;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/16 16:05
 */
@Api(tags = "角色管理")
@Slf4j
@RestController
@RequestMapping("/back/role")
public class RoleController extends BaseConfig {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleAuthorityMapper roleAuthorityMapper;
    @Resource
    private RedisUtil redisUtil;


    @ApiOperation("新增角色")
    @ApiOperationSupport(ignoreParameters = {"id", "updatedTime"})
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/save")
    public Result<Role> save(@RequestBody RoleVO roleVO) {
        if (ObjUtil.isEmpty(roleVO.getRoleName())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleVO, role);
        roleMapper.insertSelective(role);
        log.info("存储成功");
        return Result.ok(role);
    }


    @ApiOperation("修改角色")
    @ApiOperationSupport(ignoreParameters = {"updatedTime"})
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/update")
    public Result<Role> update(@RequestBody RoleVO roleVO) {
        if (ObjUtil.isEmpty(roleVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Role role = roleMapper.selectByPrimaryKey(roleVO.getId());
        if (role == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        BeanUtils.copyProperties(roleVO, role);
        roleMapper.updateByPrimaryKeySelective(role);
        log.info("修改成功");
        return Result.ok(role);
    }


    @ApiOperation("根据id删除角色")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/delete")
    public Result delete(@RequestBody IdVO idVO) {
        if (StringUtils.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Long id = idVO.getId();
        Role role = roleMapper.selectByPrimaryKey(id);
        if (role == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        List<RoleAuthority> list = roleAuthorityMapper.selectByRoleId(id);
        if (!ObjUtil.isEmptyList(list)) {
            roleAuthorityMapper.deleteByRoleId(id);
        }
        roleMapper.deleteByPrimaryKey(id);
        log.info("角色id为：" + idVO.getId() + "的数据删除成功");
        return Result.ok();
    }


    @ApiOperation("根据id获取角色")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/getOne")
    public Result<Role> getOne(@RequestBody IdVO idVO) {
        if (ObjUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Role role = roleMapper.selectByPrimaryKey(idVO.getId());
        if (role == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        log.info("获取id为：" + idVO.getId() + "的数据成功");
        return Result.ok(role);
    }


    @ApiOperation("根据条件查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名", dataType = "string", paramType = "query", example = "xxxx"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "page", value = "页数，从0开始", dataType = "int", paramType = "query", example = "0"),
            @ApiImplicitParam(name = "sort", value = "排序规则，可传入多个sort参数", dataType = "string", paramType = "query", example = "createdAt desc")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/findAll")
    public Result<PageInfo<List<Role>>> findAll(@RequestBody HashMap<String, Object> map) {
        getPageRequest(map);
        //判断key是否存在，存在则从redis取，不存在则查询数据库
        String key = getKey("roleList", map);
        boolean hasKey = redisUtil.hasKey(key);
        PageInfo pageInfo;
        if (hasKey) {
            pageInfo = (PageInfo) redisUtil.get(key);
        } else {
            if (!ObjUtil.isEmpty(map.get("roleName"))) {
                map.put("roleName", map.get("roleName"));
            }
            List<Role> list = roleMapper.findAllBySome(map);
            pageInfo = new PageInfo<>(list);
            redisUtil.set(key, pageInfo);
        }
        log.info("根据条件查询文件数据成功");
        return Result.ok(pageInfo);
    }


}
