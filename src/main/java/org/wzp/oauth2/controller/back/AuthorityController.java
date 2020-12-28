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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.entity.Authority;
import org.wzp.oauth2.entity.RoleAuthority;
import org.wzp.oauth2.enumeration.ResultCodeEnum;
import org.wzp.oauth2.mapper.AuthorityMapper;
import org.wzp.oauth2.mapper.RoleAuthorityMapper;
import org.wzp.oauth2.util.RedisUtil;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.util.ObjUtil;
import org.wzp.oauth2.vo.AuthorityVO;
import org.wzp.oauth2.vo.IdVO;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/16 16:05
 */
@Api(tags = "权限管理")
@Slf4j
@RestController
@RequestMapping("/back/authority")
public class AuthorityController extends BaseConfig {

    @Resource
    private AuthorityMapper authorityMapper;
    @Resource
    private RoleAuthorityMapper roleAuthorityMapper;
    @Resource
    private RedisUtil redisUtil;


    @ApiOperation("新增权限")
    @ApiOperationSupport(ignoreParameters = {"id", "updatedTime"})
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/save")
    public Result<Authority> save(@RequestBody AuthorityVO authorityVO) {
        if (ObjUtil.isEmpty(authorityVO.getName())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Authority authority = new Authority();
        authorityVO.setName("ROLE_" + authorityVO.getName());
        BeanUtils.copyProperties(authorityVO, authority);
        authorityMapper.insertSelective(authority);
        log.info("存储成功");
        return Result.ok(authority);
    }


    @ApiOperation("修改权限")
    @ApiOperationSupport(ignoreParameters = {"updatedTime"})
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/update")
    public Result<Authority> update(@RequestBody AuthorityVO authorityVO) {
        if (ObjUtil.isEmpty(authorityVO.getName())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Authority authority = authorityMapper.selectByPrimaryKey(authorityVO.getId());
        if (authority == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        authorityVO.setName("ROLE_" + authorityVO.getName());
        BeanUtils.copyProperties(authorityVO, authority);
        authorityMapper.updateByPrimaryKeySelective(authority);
        log.info("修改成功");
        return Result.ok(authority);
    }


    @ApiOperation("删除权限")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/delete")
    public Result delete(@RequestBody IdVO idVO) {
        if (ObjUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Long id = idVO.getId();
        Authority authority = authorityMapper.selectByPrimaryKey(id);
        if (authority == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        List<RoleAuthority> list = roleAuthorityMapper.selectByAuthorityId(id);
        if (!ObjUtil.isEmptyList(list)) {
            return Result.error(ResultCodeEnum.AUTHORITY_HAS_USE);
        }
        authorityMapper.deleteByPrimaryKey(id);
        log.info("权限id为：" + idVO.getId() + "的数据删除成功");
        return Result.ok();
    }


    @ApiOperation("根据id获取权限")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/getOne")
    public Result<Authority> getOne(@RequestBody IdVO idVO) {
        if (ObjUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Authority authority = authorityMapper.selectByPrimaryKey(idVO.getId());
        if (authority == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        log.info("获取id为：" + idVO.getId() + "的数据成功");
        return Result.ok(authority);
    }


    @ApiOperation("根据条件查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "权限名字", dataType = "string", paramType = "query", example = "管理员"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "page", value = "页数，从0开始", dataType = "int", paramType = "query", example = "0"),
            @ApiImplicitParam(name = "sort", value = "排序规则，可传入多个sort参数", dataType = "string", paramType = "query", example = "createdAt desc")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/findAll")
    public Result<PageInfo<List<Authority>>> findAll(@RequestBody HashMap<String, Object> map) {
        getPageRequest(map);
        String key = getKey("authorityList", map);
        boolean hasKey = redisUtil.hasKey(key);
        PageInfo pageInfo;
        if (hasKey) {
            pageInfo = (PageInfo) redisUtil.get(key);
        } else {
            if (!ObjUtil.isEmpty(map.get("name"))) {
                map.put("name", map.get("name"));
            }
            List<Authority> list = authorityMapper.findAllBySome(map);
            pageInfo = new PageInfo<>(list);
            redisUtil.set(key, pageInfo);
        }
        log.info("根据条件查询文件数据成功");
        return Result.ok(pageInfo);
    }


}
