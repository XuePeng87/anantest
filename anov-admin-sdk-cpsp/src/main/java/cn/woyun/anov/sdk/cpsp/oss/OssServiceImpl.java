package cn.woyun.anov.sdk.cpsp.oss;

import cn.woyun.anov.config.oss.OssConfiguration;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

/**
 * OSS的业务处理实现类。
 *
 * @author xuepeng
 */
@Service
public class OssServiceImpl implements OssService {
    
    /**
     * 上传对象。
     *
     * @param bucket      桶名称。
     * @param name        文件名。
     * @param inputStream 文件流。
     * @return OSS对象地址。
     */
    @Override
    public String putObject(String bucket, String name, InputStream inputStream) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(
                ossConfiguration.getEndpoint(),
                Region.getRegion(Regions.CN_NORTH_1).getName()
        );
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                ossConfiguration.getAk(),
                ossConfiguration.getSk()
        );
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        ClientConfiguration clientConfig = new ClientConfiguration();
        if (BooleanUtils.isTrue(ossConfiguration.getSecurt())) {
            clientConfig.setProtocol(Protocol.HTTPS);
        } else {
            clientConfig.setProtocol(Protocol.HTTP);
        }
        AmazonS3 client = AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfig)
                .withClientConfiguration(clientConfig)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(true)
                .withForceGlobalBucketAccessEnabled(true)
                .build();
        ObjectMetadata omd = new ObjectMetadata();
        omd.setContentType("octet-stream");
        client.putObject(new PutObjectRequest(
                bucket,
                name,
                inputStream,
                omd
        ).withCannedAcl(CannedAccessControlList.PublicRead));
        URL url = client.getUrl(bucket, name);
        return url.toString();
    }

    /**
     * 自动装配验证码工具的配置信息。
     *
     * @param ossConfiguration 验证码工具的配置信息。
     */
    @Autowired
    public void setOssConfiguration(OssConfiguration ossConfiguration) {
        this.ossConfiguration = ossConfiguration;
    }

    /**
     * 验证码工具的配置信息。
     */
    private OssConfiguration ossConfiguration;

}
