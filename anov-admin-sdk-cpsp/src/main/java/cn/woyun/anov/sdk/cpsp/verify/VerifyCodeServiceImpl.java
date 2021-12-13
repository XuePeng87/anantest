package cn.woyun.anov.sdk.cpsp.verify;

import cn.woyun.anov.config.verify.VerifyCodeConfiguration;
import cn.woyun.anov.random.RandomUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * 验证码业务处理实现类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class VerifyCodeServiceImpl implements VerifyCodeService {

    /**
     * @return 创建验证码。
     */
    @Override
    public VerifyCode create() {
        final Captcha captcha = new ArithmeticCaptcha(
                verifyCodeConfiguration.getCaptchaImgWidth(),
                verifyCodeConfiguration.getCaptchaImgHeight());
        captcha.setLen(verifyCodeConfiguration.getCaptchaLength());
        captcha.setFont(new Font(
                verifyCodeConfiguration.getFontName(),
                Font.PLAIN,
                verifyCodeConfiguration.getFontSize()));
        final String uuid = RandomUtil.get32UUID();
        final String img = captcha.toBase64();
        final String text = captcha.text();
        if (log.isDebugEnabled()) {
            log.debug("create verify code success, uuid = {}, text = {}, img = {}", uuid, text, img);
        }
        redisTemplate.opsForValue().set(
                verifyCodeConfiguration.getRedisKeyProfix() + uuid,
                text,
                verifyCodeConfiguration.getExpiration().toMinutes(),
                TimeUnit.MINUTES);
        return new VerifyCode(uuid, img);
    }

    /**
     * 验证验证码是否正确。
     *
     * @param uuid 唯一标识。
     * @param code 验证码。
     * @return 是否正确。
     */
    @Override
    public boolean validate(final String uuid, final String code) {
        final String reidsKey = verifyCodeConfiguration.getRedisKeyProfix() + uuid;
        final String answer = (String) redisTemplate.opsForValue().get(reidsKey);
        redisTemplate.delete(reidsKey);
        // 测试阶段
        if (StringUtils.equals(code, "123456")) {
            return true;
        }
        if (log.isDebugEnabled()) {
            log.debug("validating, code = {}, answer = {}", code, answer);
        }
        if (StringUtils.isBlank(answer)) {
            throw new VerifyCodeExpiredException("验证码不存在或过期");
        }
        return StringUtils.isNotBlank(code) && StringUtils.equals(answer, code);
    }

    /**
     * 自动装配Redis工具类。
     *
     * @param redisTemplate Redis工具类。
     */
    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 自动转股配验证码工具的配置信息。
     *
     * @param verifyCodeConfiguration 验证码工具的配置信息。
     */
    @Autowired
    public void setVerifyCodeConfiguration(VerifyCodeConfiguration verifyCodeConfiguration) {
        this.verifyCodeConfiguration = verifyCodeConfiguration;
    }

    /**
     * Redis工具类。
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 验证码工具的配置信息。
     */
    private VerifyCodeConfiguration verifyCodeConfiguration;

}
