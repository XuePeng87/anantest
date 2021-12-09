package cn.woyun.anov.sdk.cpsp.oss;

import java.io.InputStream;

/**
 * OSS的业务处理接口。
 *
 * @author xuepeng
 */
public interface OssService {

    /**
     * 上传对象。
     *
     * @param bucket      桶名称。
     * @param name        文件名。
     * @param inputStream 文件流。
     * @return OSS对象地址。
     */
    String putObject(final String bucket, final String name, InputStream inputStream);

}
