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
import org.wzp.oauth2.entity.FileList;
import org.wzp.oauth2.enumeration.ResultCodeEnum;
import org.wzp.oauth2.mapper.FileListMapper;
import org.wzp.oauth2.util.RedisUtil;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.util.StringUtil;
import org.wzp.oauth2.vo.FileListVO;
import org.wzp.oauth2.vo.IdVO;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/15 10:25
 */
@Api(tags = "文件管理")
@Slf4j
@RestController
@RequestMapping("/back/file")
public class FileListController extends BaseConfig {

    @Resource
    private FileListMapper fileListMapper;
    @Resource
    private RedisUtil redisUtil;


    @ApiOperation("新增文件")
    @ApiOperationSupport(ignoreParameters = {"id", "updatedTime"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public Result<FileList> save(@RequestBody FileListVO fileListVO) {
        if (StringUtil.isEmpty(fileListVO.getFileName())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        FileList fileList = new FileList();
        BeanUtils.copyProperties(fileListVO, fileList);
        fileListMapper.insertSelective(fileList);
        log.info("存储成功");
        return Result.ok(fileList);
    }


    @ApiOperation("修改文件")
    @ApiOperationSupport(ignoreParameters = {"updatedTime"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/update")
    public Result<FileList> update(@RequestBody FileListVO fileListVO) {
        if (StringUtil.isEmpty(fileListVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        FileList fileList = fileListMapper.selectByPrimaryKey(fileListVO.getId());
        if (fileList == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        BeanUtils.copyProperties(fileListVO, fileList);
        fileListMapper.updateByPrimaryKeySelective(fileList);
        log.info("修改成功");
        return Result.ok(fileList);
    }


    @ApiOperation("根据id获取文件")
    @PostMapping("/getOne")
    public Result<FileList> getOne(@RequestBody IdVO idVO) {
        if (StringUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        FileList fileList = fileListMapper.selectByPrimaryKey(idVO.getId());
        if (fileList == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        log.info("获取id为" + idVO.getId() + "的文件数据成功");
        return Result.ok(fileList);
    }


    @ApiOperation("根据id删除文件")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/delete")
    public Result delete(@RequestBody IdVO idVO) {
        if (StringUtil.isEmpty(idVO.getId())) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        FileList fileList = fileListMapper.selectByPrimaryKey(idVO.getId());
        if (fileList == null) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        fileList.setRemoved(true);
        fileListMapper.updateByPrimaryKeySelective(fileList);
        log.info("删除id为" + idVO.getId() + "的文件数据成功");
        return Result.ok();
    }


    @ApiOperation("根据条件查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件名", dataType = "string", paramType = "query", example = "xxxx"),
            @ApiImplicitParam(name = "removed", value = "是否已删除", dataType = "string", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "page", value = "页数，从0开始", dataType = "int", paramType = "query", example = "0"),
            @ApiImplicitParam(name = "sort", value = "排序规则，可传入多个sort参数", dataType = "string", paramType = "query", example = "createdAt desc")
    })
    @PostMapping("/findAll")
    public Result<PageInfo<List<FileList>>> findAll(@RequestBody HashMap<String, Object> map) {
        getPageRequest(map);
        //判断key是否存在，存在则从redis取，不存在则查询数据库
        String key = getKey("fileList", map);
        boolean hasKey = redisUtil.hasKey(key);
        PageInfo pageInfo;
        if (hasKey) {
            pageInfo = (PageInfo) redisUtil.get(key);
        } else {
            if (!StringUtil.isEmpty(map.get("fileName"))) {
                map.put("fileName", map.get("fileName"));
            }
            if (!StringUtil.isEmpty(map.get("removed"))) {
                map.put("removed", map.get("removed"));
            }
            List<FileList> list = fileListMapper.findAllBySome(map);
            pageInfo = new PageInfo<>(list);
            redisUtil.set(key, pageInfo);
        }
        log.info("根据条件查询文件数据成功");
        return Result.ok(pageInfo);
    }


}
