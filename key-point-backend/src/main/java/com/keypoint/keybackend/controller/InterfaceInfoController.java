package com.keypoint.keybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.keypoint.keybackend.annotation.AuthCheck;
import com.keypoint.keybackend.common.*;
import com.keypoint.keybackend.exception.BusinessException;
import com.keypoint.keybackend.model.dto.interfaceinfo.*;
import com.keypoint.keybackend.model.entity.InterfaceInfo;
import com.keypoint.keybackend.model.enums.InterfaceStatusEnum;
import com.keypoint.keybackend.model.vo.UserVO;
import com.keypoint.keybackend.service.InterfaceInfoService;
import com.keypoint.keybackend.service.UserService;
import com.keypoint.keybackend.utils.AssertUtil;
import icu.qimuu.qiapisdk.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.keypoint.keybackend.constant.UserConstant.ADMIN_ROLE;

/**
 * 帖子接口
 *
 * @author qimu
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private UserService userService;
    @Resource
    private ApiService apiService;

    /**
     * 添加接口信息
     * 创建
     *
     * @param interfaceInfoAddRequest 接口信息添加请求
     * @param request                 请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        UserVO loginUser = userService.getLoginUser(request);
        //当前用户不存在
        AssertUtil.isNull(loginUser, new BusinessException(ErrorCode.OPERATION_ERROR));
        //请求实体类不能为空
        AssertUtil.isNull(interfaceInfoAddRequest, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(interfaceInfoService.addInterfaceInfo(interfaceInfoAddRequest, loginUser, request));
    }


    /**
     * 删除接口信息
     *
     * @param deleteRequest 删除请求
     * @param request       请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        AssertUtil.anyNull(new BusinessException(ErrorCode.PARAMS_ERROR), deleteRequest, deleteRequest.getId());
        AssertUtil.isTrue(deleteRequest.getId() <= 0, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(interfaceInfoService.removeById(deleteRequest));
    }

    /**
     * 更新接口头像url
     *
     * @param request                          请求
     * @param interfaceInfoUpdateAvatarRequest 界面信息更新头像请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/updateInterfaceInfoAvatar")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfoAvatarUrl(@RequestBody InterfaceInfoUpdateAvatarRequest interfaceInfoUpdateAvatarRequest,
                                                              HttpServletRequest request) {
        AssertUtil.anyNull(new BusinessException(ErrorCode.PARAMS_ERROR), interfaceInfoUpdateAvatarRequest, interfaceInfoUpdateAvatarRequest.getId(), interfaceInfoUpdateAvatarRequest.getAvatarUrl());
        AssertUtil.isTrue(interfaceInfoUpdateAvatarRequest.getId() <= 0, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(interfaceInfoService.updateById(null));
    }

    /**
     * 更新接口信息
     *
     * @param interfaceInfoUpdateRequest 接口信息更新请求
     * @param request                    请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        AssertUtil.isNull(interfaceInfoUpdateRequest, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(interfaceInfoService.updateInterfaceInfoById(interfaceInfoUpdateRequest));
    }

    /**
     * 通过id获取接口信息
     *
     * @param id id
     * @return {@link BaseResponse}<{@link InterfaceInfo}>
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        AssertUtil.isTrue(id <= 0, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(interfaceInfoService.getById(id));
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest 接口信息查询请求
     * @return {@link BaseResponse}<{@link List}<{@link InterfaceInfo}>>
     */
    @AuthCheck(mustRole = ADMIN_ROLE)
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        AssertUtil.isNull(interfaceInfoQueryRequest, new BusinessException(ErrorCode.PARAMS_ERROR));
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest 接口信息查询请求
     * @param request                   请求
     * @return {@link BaseResponse}<{@link Page}<{@link InterfaceInfo}>>
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        AssertUtil.isNull(interfaceInfoQueryRequest, new BusinessException(ErrorCode.PARAMS_ERROR));
        // 限制爬虫
        AssertUtil.isTrue(interfaceInfoQueryRequest.getPageSize() > 50, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(interfaceInfoService.getPageList(request, interfaceInfoQueryRequest));
    }

    /**
     * 按搜索文本页查询数据
     *
     * @param interfaceInfoQueryRequest 接口信息查询请求
     * @param request                   请求
     * @return {@link BaseResponse}<{@link Page}<{@link InterfaceInfo}>>
     */
    @GetMapping("/get/searchText")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoBySearchTextPage(InterfaceInfoSearchTextRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        AssertUtil.isNull(interfaceInfoQueryRequest, new BusinessException(ErrorCode.PARAMS_ERROR));
        // 限制爬虫
        AssertUtil.isTrue(interfaceInfoQueryRequest.getPageSize() > 100, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(interfaceInfoService.listInterfaceInfoBySearchTextPage(interfaceInfoQueryRequest, request));
    }

    /**
     * 发布
     *
     * @param idRequest id请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @AuthCheck(mustRole = ADMIN_ROLE)
    @PostMapping("/online")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        AssertUtil.isTrue(Objects.isNull(idRequest) || idRequest.getId() <= 0, new BusinessException(ErrorCode.PARAMS_ERROR));
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(idRequest.getId());
        AssertUtil.isNull(interfaceInfo, new BusinessException(ErrorCode.PARAMS_ERROR));
        interfaceInfo.setStatus(InterfaceStatusEnum.ONLINE.getValue());
        return ResultUtils.success(interfaceInfoService.updateById(interfaceInfo));
    }

    /**
     * 下线
     *
     * @param idRequest id请求
     * @param request   请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        AssertUtil.isTrue(Objects.isNull(idRequest) || idRequest.getId() <= 0, new BusinessException(ErrorCode.PARAMS_ERROR));
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(idRequest.getId());
        AssertUtil.isNull(interfaceInfo, new BusinessException(ErrorCode.PARAMS_ERROR));
        interfaceInfo.setStatus(InterfaceStatusEnum.OFFLINE.getValue());
        return ResultUtils.success(interfaceInfoService.updateById(interfaceInfo));
    }

    // endregion

    /**
     * 调用接口
     *
     * @param invokeRequest id请求
     * @param request       请求
     * @return {@link BaseResponse}<{@link Object}>
     */
    @PostMapping("/invoke")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<Object> invokeInterface(@RequestBody InvokeRequest invokeRequest, HttpServletRequest request) {

        return null;
    }


}
