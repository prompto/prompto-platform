package prompto.aws;

import java.nio.charset.Charset;

import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;
import prompto.utils.Logger;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

public class S3 {

	static final Logger logger = new Logger();

	@SuppressWarnings("resource")
	public static S3 newInstance(String awsRegion, String login, String password) {
		S3ClientBuilder builder = S3Client.builder()
				.region(Region.of(awsRegion));
		if(login!=null && password!=null) {
			AwsCredentials credentials = AwsBasicCredentials.create(login, password);
			builder = builder.credentialsProvider(StaticCredentialsProvider.create(credentials));
		}
		return new S3(builder.build());
	}
	
	S3Client s3;
	
	public S3(S3Client s3) {
		this.s3 = s3;
	}
	
	@Override
	public void finalize() {
		this.s3.close();
	}

	public PromptoList<String> listBucketNames() {
		try {
			var response = s3.listBuckets();
			return response.buckets().stream()
					.map(Bucket::name)
					.collect(PromptoList.collector());
		} catch(Throwable t) {
			logger.error(()->"While listing buckets", t);
			return null;
		}
	}

	public PromptoDocument<String, Object> listObjects(String bucketName, String prefix, String delimiter, String continuationToken, Long maxCount) {
		try {
			var builder = ListObjectsV2Request.builder()
					.bucket(bucketName);
			if(prefix != null && prefix.length() > 0)
				builder = builder.prefix(prefix);
			if(delimiter != null && delimiter.length() > 0)
				builder = builder.delimiter(delimiter);
			if(continuationToken != null && continuationToken.length() > 0)
				builder = builder.continuationToken(continuationToken);
			if(maxCount != null && maxCount > 0)
				builder = builder.maxKeys(maxCount.intValue());
			var response = s3.listObjectsV2(builder.build());
			return Converter.convertPojo(response);
		} catch(Throwable t) {
			logger.error(()->"While listing objects in bucket " + bucketName, t);
			return null;
		}
	}

	public String fetchObjectText(String bucketName, String key, String charset) {
		try {
			var bytes = fetchObject(bucketName, key);
			return bytes.asString(Charset.forName(charset));
		} catch(Throwable t) {
			logger.error(()->"While fetching object " + key + " in bucket " + bucketName, t);
			return null;
		}
	}

	private ResponseBytes<GetObjectResponse> fetchObject(String bucketName, String key) {
		var request = GetObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.build();
		return s3.getObjectAsBytes(request);
	}

}
