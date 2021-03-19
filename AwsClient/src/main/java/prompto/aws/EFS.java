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
import software.amazon.awssdk.services.efs.model.CreateMountTargetRequest;
import software.amazon.awssdk.services.efs.model.CreateMountTargetResponse;
import software.amazon.awssdk.services.efs.model.DeleteFileSystemRequest;
import software.amazon.awssdk.services.efs.model.DeleteMountTargetRequest;
import software.amazon.awssdk.services.efs.model.DescribeFileSystemsRequest;
import software.amazon.awssdk.services.efs.model.DescribeFileSystemsResponse;
import software.amazon.awssdk.services.efs.model.DescribeMountTargetsRequest;
import software.amazon.awssdk.services.efs.model.DescribeMountTargetsResponse;
import software.amazon.awssdk.services.efs.model.FileSystemNotFoundException;
import software.amazon.awssdk.services.efs.model.LifeCycleState;
import software.amazon.awssdk.services.efs.model.MountTargetNotFoundException;
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
				.map(fs -> {
					PromptoDocument<String, Object> doc = Converter.convertPojo(fs);
					promoteNameTag(fs.tags(), doc);
					return doc;
				})
				.collect(Collectors.toList());
	}
	
	
	public String createFileSystem(String name, String availabilityZone, Boolean backup, Boolean waitForAvailability) {
		CreateFileSystemRequest.Builder createRequest = CreateFileSystemRequest.builder()
				.tags(Tag.builder().key("Name").value("test-fs").build());
		if(availabilityZone != null)
			createRequest = createRequest.availabilityZoneName(availabilityZone);
		if(backup != null)
			createRequest = createRequest.backup(backup);
		CreateFileSystemResponse createResponse = efs.createFileSystem(createRequest.build());
		String fileSystemId = createResponse.fileSystemId();
		if(waitForAvailability!=null && waitForAvailability)
			waitForFSState(fileSystemId, LifeCycleState.AVAILABLE);
		return fileSystemId;
	}
	
	
	public void dropFileSystem(String fileSystemId, Boolean waitForDestruction) {
		DeleteFileSystemRequest deleteRequest = DeleteFileSystemRequest.builder()
				.fileSystemId(fileSystemId)
				.build();
		efs.deleteFileSystem(deleteRequest);
		if(waitForDestruction!=null && waitForDestruction)
			waitForFSState(fileSystemId, LifeCycleState.DELETED);
	}
	
	private void waitForFSState(String fileSystemId, LifeCycleState state) {
		DescribeFileSystemsRequest describeFSRequest = DescribeFileSystemsRequest.builder()
				.fileSystemId(fileSystemId)
				.build();
		LifeCycleState read = null;
		long start = System.currentTimeMillis();
		do {
			try {
				DescribeFileSystemsResponse describeFSResponse = efs.describeFileSystems(describeFSRequest);
				read = describeFSResponse.fileSystems().get(0).lifeCycleState();
			} catch(FileSystemNotFoundException e) {
				read = LifeCycleState.DELETED;
			}
			if(read != state)
				Utils.unsafeSleep(1000);
		} while (read != state && read != LifeCycleState.DELETED && (System.currentTimeMillis() - start < 10*60*1000));
	}
	
	
	public List<PromptoDocument<String, Object>> listMountTargets(String fileSystemId) {
		DescribeMountTargetsRequest request = DescribeMountTargetsRequest.builder()
				.fileSystemId(fileSystemId)
				.build();
		DescribeMountTargetsResponse response = efs.describeMountTargets(request);
		return response.mountTargets().stream()
				.map(Converter::convertPojo)
				.collect(Collectors.toList());
	}

	public String createMountTarget(String fileSystemId, String subnetId, List<String> securityGroups, Boolean waitForAvailability) {
		CreateMountTargetRequest.Builder createRequest = CreateMountTargetRequest.builder()
				.fileSystemId(fileSystemId)
				.subnetId(subnetId);
		if(securityGroups!=null)
			createRequest = createRequest.securityGroups(securityGroups);
		CreateMountTargetResponse createResponse = efs.createMountTarget(createRequest.build());
		String mountTargetId = createResponse.mountTargetId();
		if(waitForAvailability!=null && waitForAvailability)
			waitForMTState(mountTargetId, LifeCycleState.AVAILABLE);
		return mountTargetId;
	}
	
	
	public void dropMountTarget(String mountTargetId, Boolean waitForDestruction) {
		DeleteMountTargetRequest deleteMTRequest = DeleteMountTargetRequest.builder()
				.mountTargetId(mountTargetId)
				.build();
		efs.deleteMountTarget(deleteMTRequest);
		if(waitForDestruction!=null && waitForDestruction)
			waitForMTState(mountTargetId, LifeCycleState.DELETED);
	}

	
	private void waitForMTState(String mountTargetId, LifeCycleState state) {
		DescribeMountTargetsRequest describeMTRequest = DescribeMountTargetsRequest.builder()
				.mountTargetId(mountTargetId)
				.build();
		LifeCycleState read = null;
		long start = System.currentTimeMillis();
		do {
			try {
				DescribeMountTargetsResponse describeMTResponse = efs.describeMountTargets(describeMTRequest);
				read = describeMTResponse.mountTargets().get(0).lifeCycleState();
			} catch(MountTargetNotFoundException e) {
				read = LifeCycleState.DELETED;
			}
			if(read != state)
				Utils.unsafeSleep(1000);
		} while (read != state  && read != LifeCycleState.DELETED && (System.currentTimeMillis() - start < 10*60*1000));
	}

	public static void promoteNameTag(List<Tag> tags, PromptoDocument<String, Object> doc) {
		Tag tag = tags.stream()
				.filter((t)->"Name".equals(t.key()))
				.findFirst()
				.orElse(null);
		if(tag!=null)
			doc.put("Name", tag.value());
		else
			doc.put("Name", "<anonymous>");
	}

}
