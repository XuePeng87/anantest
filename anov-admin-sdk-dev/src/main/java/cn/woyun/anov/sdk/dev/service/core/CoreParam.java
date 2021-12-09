package cn.woyun.anov.sdk.dev.service.core;

import cn.woyun.anov.json.JsonUtil;
import cn.woyun.anov.sdk.dev.entity.DevScreen;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 芯片生成参数。
 *
 * @author xuepeng
 */
@Data
public class CoreParam {

    /**
     * 构造函数。
     */
    private CoreParam() {
    }

    /**
     * 创建生成芯片的参数。
     *
     * @param devScreen 大屏。
     * @return 芯片参数。
     */
    public static String createCoreParam(final DevScreen devScreen) {
        final CoreParam coreParam = new CoreParam();
        coreParam.setDueDate(devScreen.getScreenExpireTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        coreParam.setCopyright(devScreen.getDeptName());
        coreParam.setHost(Collections.singletonList("localhost"));
        coreParam.setProjName(devScreen.getScreenName());
        coreParam.setProjId(devScreen.getScreenKey());
        if (CollectionUtils.isNotEmpty(devScreen.getFuncs())) {
            for (final String func : devScreen.getFuncs()) {
                coreParam.getPanel().getPermission().add(func);
            }
        }
        return JsonUtil.convertObj2Str(coreParam);
    }

    /**
     * 有效期。
     */
    private String dueDate;

    /**
     * 部门。
     */
    private String copyright;

    /**
     * 用户信息。
     */
    private User user = new User();

    /**
     * IP地址。
     */
    private List<String> host = new ArrayList<>();

    /**
     * 项目名称。
     */
    private String projName;

    /**
     * 项目编号。
     */
    private String projId;

    /**
     * 面板权限。
     */
    private Panel panel = new Panel();

    /**
     * 语音识别是否激活。
     */
    private VoiceRecognize voiceRecognize = new VoiceRecognize();

    /**
     * 手势识别是否激活。
     */
    private GestureRecognize gestureRecognize = new GestureRecognize();

    /**
     * 面部识别是否激活。
     */
    private FaceRecognize faceRecognize = new FaceRecognize();

    /**
     * 语音合成是否激活。
     */
    private VoiceFeedback voiceFeedback = new VoiceFeedback();

    /**
     * 光感识别是否激活。
     */
    private LightSensor lightSensor = new LightSensor();

    /**
     * 用户信息。
     */
    @Data
    public static class User {

        /**
         * 帐号。
         */
        private String name = StringUtils.EMPTY;

        /**
         * 密码。
         */
        private String pwd = StringUtils.EMPTY;

    }

    /**
     * 面板权限。
     */
    @Data
    public static class Panel {

        /**
         * 权限集合。
         */
        private List<String> permission = new ArrayList<>();

    }

    /**
     * 语音识别。
     */
    @Data
    public static class VoiceRecognize {

        /**
         * 是否激活。
         */
        private Boolean isStart = Boolean.FALSE;

    }

    /**
     * 手势识别。
     */
    @Data
    public static class GestureRecognize {

        /**
         * 是否激活。
         */
        private Boolean isStart = Boolean.FALSE;

    }

    /**
     * 面部识别。
     */
    @Data
    public static class FaceRecognize {

        /**
         * 是否激活。
         */
        private Boolean isStart = Boolean.FALSE;

    }

    /**
     * 语音合成。
     */
    @Data
    public static class VoiceFeedback {

        /**
         * 是否激活。
         */
        private Boolean isStart = Boolean.FALSE;

    }

    /**
     * 光感识别。
     */
    @Data
    public static class LightSensor {

        /**
         * 是否激活。
         */
        private Boolean isStart = Boolean.FALSE;

    }

}
