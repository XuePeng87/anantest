package cn.woyun.anov.sdk.cpsp.verify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCode {

    /**
     * 唯一标识。
     */
    private String uuid;

    /**
     * 验证图片。
     */
    private String img;

}
