package cn.woyun.anov.gateway.management.controller.dev;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.bean.request.dev.DevScreenClientQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.request.dev.DevScreenClientQuitRequestBean;
import cn.woyun.anov.gateway.management.bean.response.dev.DevScreenClientResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.json.JsonMapper;
import cn.woyun.anov.page.PageResult;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.sdk.dev.exception.DevScreenClientQuitException;
import cn.woyun.anov.sdk.dev.service.DevScreenClientService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * 大屏客户端API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/devs/clients")
@Api(tags = "大屏客户端API接口")
@Slf4j
@SaCheckLogin
public class DevScreenClientController extends BaseController {

    /**
     * 根据条件分页查询大屏客户端。
     *
     * @param devScreenClientQueryRequestBean 大屏的查询请求参数类。
     * @return 客户端集合。
     */
    @PostMapping("/v1/conditions")
    @OperationLog(system = "管理平台", module = "大屏客户端", description = "根据条件分页查询大屏客户端", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "根据条件分页查询大屏客户端")
    public HttpResult<PageResult<DevScreenClientResponseBean>> findByConditionAndPage(@Valid @RequestBody final DevScreenClientQueryRequestBean devScreenClientQueryRequestBean) {
        final DevScreenClient devScreenClient = BeanUtil.objToObj(devScreenClientQueryRequestBean, DevScreenClient.class);
        final Page<DevScreenClient> page = pageParamToPage(devScreenClientQueryRequestBean.getPage());
        final Page<DevScreenClient> devScreenClients = devScreenClientService.findByPageAndCondition(page, devScreenClient);
        final PageResult<DevScreenClientResponseBean> result = pageToPageResult(devScreenClients, DevScreenClientResponseBean.class);
        return DefaultHttpResultFactory.success("根据条件分页查询大屏客户端成功。", result);
    }

    /**
     * 强制断开大屏客户端的连接。
     *
     * @param devScreenClientQuitRequestBean 断开连接的请求参数对象。
     * @return 是否断开成功。
     */
    @PutMapping("/v1/quit")
    @OperationLog(system = "管理平台", module = "大屏客户端", description = "强制断开大屏连接", type = OperationTypeEnum.FORCE)
    @ApiOperation(value = "强制断开大屏连接")
    public HttpResult<Boolean> quit(@Valid @RequestBody DevScreenClientQuitRequestBean devScreenClientQuitRequestBean) {
        try {
            // 远程调用大屏API
            final ResponseEntity<String> quit = restTemplate.postForEntity(
                    "http://localhost:45672/api/devs/v1/{key}/clients/{code}/quit",
                    null,
                    String.class,
                    devScreenClientQuitRequestBean.getScreenKey(),
                    devScreenClientQuitRequestBean.getClientCode()
            );
            JsonNode jsonNode = JsonMapper.INSTANCE.getInstance().readTree(quit.getBody());
            if (jsonNode.get("code").asInt() == 20000) {
                return DefaultHttpResultFactory.success("大屏强制下线成功。", Boolean.TRUE);
            } else {
                throw new DevScreenClientQuitException("大屏强制下线失败，" + jsonNode.get("msg").asText());
            }
        } catch (HttpStatusCodeException e) {
            log.error("invoke dev screen quit API error, code is {}", e.getStatusCode());
            throw new DevScreenClientQuitException("调用大屏强制下线API失败，错误码：" + e.getStatusCode());
        } catch (Exception e) {
            log.error("server is error, cause is {}", e.getMessage());
            return DefaultHttpResultFactory.error("服务器错误", Boolean.FALSE);
        }
    }

    /**
     * 自动装配大屏客户端业务处理接口。
     *
     * @param devScreenClientService 大屏客户端业务处理接口。
     */
    @Autowired
    public void setDevScreenClientService(DevScreenClientService devScreenClientService) {
        this.devScreenClientService = devScreenClientService;
    }

    /**
     * 自动装配HttpClient工具。
     *
     * @param restTemplate HttpClient工具。
     */
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 大屏客户端业务处理接口。
     */
    private DevScreenClientService devScreenClientService;

    /**
     * HttpClient工具。
     */
    private RestTemplate restTemplate;

}
