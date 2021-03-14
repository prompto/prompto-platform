package prompto.aws;

import java.util.List;
import java.util.stream.Collectors;

import prompto.intrinsic.PromptoDocument;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.efs.EfsClient;
import software.amazon.awssdk.services.efs.EfsClientBuilder;
import software.amazon.awssdk.services.efs.model.CreateFileSystemRequest;
import software.amazon.awssdk.services.efs.model.CreateFileSystemResponse;
import software.amazon.awssdk.services.efs.model.DeleteFileSystemRequest;
import software.amazon.awssdk.services.efs.model.DescribeFileSystemsResponse;
import software.amazon.awssdk.services.efs.model.Tag;

public class EFS {

	public static EFS newInstance(String awsRegion, String login, String password) {
		EfsClientBuilder builder = EfsClient.builder()
				.region(Region.of(awsRegion));
		if(login!=null && password!=null) {
			AwsCredentials credentials = AwsBasicCredentials.create(login, password);
			builder = builder.credentialsProvider(StaticCredentialsProvider.create(credentials));
		}
		return new EFS(builder.build());
	}
	
	EfsClient efs;
	
	public EFS(EfsClient efs) {
		this.efs = efs;
	}
	
	public List<PromptoDocument<String, Object>> listFileSystems() {
		DescribeFileSystemsResponse result = efs.describeFileSystems();
		return result.fileSystems().stream()
				.map(Converter::convertPojo)
				.collect(Collectors.toList());
	}
	
	
	public String createFileSystem(String name, String availabilityZone, Boolean backup) {
		CreateFileSystemRequest.Builder createRequest = CreateFileSystemRequest.builder()
				.tags(Tag.builder().key("Name").value("test-fs").build());
		if(availabilityZone != null)
			createRequest = createRequest.availabilityZoneName(availabilityZone);
		if(backup != null)
			createRequest = createRequest.backup(backup);
		CreateFileSystemResponse createResponse = efs.createFileSystem(createRequest.build());
		return createResponse.fileSystemId();

	}
	
	
	public void destroyFileSystem(String fileSystemId) {
		DeleteFileSystemRequest deleteRequest = DeleteFileSystemRequest.builder()
				.fileSystemId(fileSystemId)
				.build();
		efs.deleteFileSystem(deleteRequest);
	}
}
