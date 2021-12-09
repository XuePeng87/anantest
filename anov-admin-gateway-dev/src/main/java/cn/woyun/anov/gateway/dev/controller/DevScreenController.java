package cn.woyun.anov.gateway.dev.controller;

import cn.woyun.anov.gateway.dev.bean.resposne.DevScreenResponseBean;
import cn.woyun.anov.gateway.dev.exception.screen.DevScreenUploadImgException;
import cn.woyun.anov.gateway.dev.serivce.DevScreenServiceProxy;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtx;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtxManager;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.cpsp.oss.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Objects;

/**
 * 大屏的API类。
 *
 * @author xuepeng
 */
@RestController
@Slf4j
@RequestMapping("/devs")
@Api(tags = "大屏API接口")
public class DevScreenController extends BaseController {

    /**
     * 验证大屏。
     *
     * @param key 大屏唯一标识。
     */
    @GetMapping("/v1/{key}/verify")
    @ApiOperation(value = "大屏认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Token", value = "访问令牌", required = true),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Client-Code", value = "客户端唯一标识"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Clinet-Target-Code", value = "目标客户端唯一标识"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Client-Type", value = "客户端类型"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Version", value = "大屏框架版本"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Core-Version", value = "大屏内核版本"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Level", value = "大屏等级"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Debug", value = "大屏开发环境"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Dir", value = "大屏开发目录"),
            @ApiImplicitParam(paramType = "header", name = "GA", value = "统计分析数据"),
            @ApiImplicitParam(paramType = "path", name = "key", value = "大屏唯一标识", required = true)
    })
    public HttpResult<DevScreenResponseBean> verify(@Valid @NotBlank(message = "大屏标识不能为空")
                                                    @Length(max = 12, message = "大屏标识长度不能大于12")
                                                    @PathVariable(value = "key") final String key) {
        final DevScreenResponseBean result = devScreenServiceProxy.verify(key, super.getHttpServletRequest());
        return DefaultHttpResultFactory.success("大屏信息。", result);
    }

    /**
     * 上传大屏截图。
     *
     * @param file 图片。
     * @param path 图片页面地址。
     * @param key  大屏唯一标识。
     * @return 是否上传成功。
     */
    @PostMapping("/v1/{key}/upload")
    @ApiOperation(value = "上传大屏截图")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "key", value = "项目唯一标识", required = true),
            @ApiImplicitParam(paramType = "form", name = "file", value = "图片文件", required = true),
            @ApiImplicitParam(paramType = "form", name = "path", value = "截屏页面", required = true)
    })
    public HttpResult<Boolean> uploadMonitorPic(@RequestParam("file") MultipartFile file, @RequestParam String path,
                                                @Valid @NotBlank(message = "大屏标识不能为空")
                                                @Length(max = 12, message = "大屏标识长度不能大于12")
                                                @PathVariable(value = "key") final String key) {
        String fileUrl;
        try {
            fileUrl = ossService.putObject("anov-test", "mgt/screen/" + file.getOriginalFilename(), file.getInputStream());
        } catch (Exception e) {
            log.error("upload picture failed. key is {}, cause is {}", key, e.getMessage());
            throw new DevScreenUploadImgException("上传图片失败。");
        }
        if (!devScreenServiceProxy.saveMonitorPic(key, fileUrl, path)) {
            throw new DevScreenUploadImgException("上传图片成功，但保存到数据库失败。");
        }
        return DefaultHttpResultFactory.success("图片上传成功。", Boolean.TRUE);
    }

    /**
     * 根据项目和客户端标识强制下线大屏客户端。
     *
     * @param key  项目标识。
     * @param code 客户端标识。
     * @return 是否下线成功。
     */
    @PostMapping("/v1/{key}/clients/{code}/quit")
    @ApiOperation(value = "强制下线客户端")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "key", value = "项目唯一标识", required = true),
            @ApiImplicitParam(paramType = "path", name = "code", value = "客户端唯一标识", required = true)
    })
    public HttpResult<Boolean> devScreenQuit(@Valid @NotBlank(message = "大屏标识不能为空") @Length(max = 12, message = "大屏标识长度不能大于12") @PathVariable(value = "key") final String key,
                                             @Valid @NotBlank(message = "客户端标识不能为空") @Length(max = 6, message = "大屏标识长度不能大于6") @PathVariable(value = "code") final String code) {
        WebSocketCtx ctx = webSocketCtxManager.findByCode(code + key);
        if (Objects.isNull(ctx)) {
            return DefaultHttpResultFactory.fail("大屏客户端不在线。", Boolean.FALSE);
        }
        try {
            ctx.getSession().close();
        } catch (IOException e) {
            log.error("server error, cause is {}", e.getMessage());
            return DefaultHttpResultFactory.error("服务器异常。", Boolean.FALSE);
        }
        return DefaultHttpResultFactory.success("大屏下线。", Boolean.FALSE);
    }

    /**
     * 自动装配大屏的业务处理接口。
     *
     * @param devScreenServiceProxy 大屏的业务处理接口。
     */
    @Autowired
    public void setDevScreenServiceProxy(DevScreenServiceProxy devScreenServiceProxy) {
        this.devScreenServiceProxy = devScreenServiceProxy;
    }


    /**
     * 自动装配WebSocket连接管理器。
     *
     * @param webSocketCtxManager WebSocket连接管理器。
     */
    @Autowired
    public void setWebSocketCtxManager(WebSocketCtxManager webSocketCtxManager) {
        this.webSocketCtxManager = webSocketCtxManager;
    }

    /**
     * 自动装配OSS的业务处理接口。
     *
     * @param ossService OSS的业务处理接口。
     */
    @Autowired
    public void setOssService(OssService ossService) {
        this.ossService = ossService;
    }

    /**
     * 大屏的业务处理接口。
     */
    private DevScreenServiceProxy devScreenServiceProxy;

    /**
     * OSS的业务处理接口。
     */
    private OssService ossService;

    /**
     * WebSocket连接管理器。
     */
    private WebSocketCtxManager webSocketCtxManager;

}
