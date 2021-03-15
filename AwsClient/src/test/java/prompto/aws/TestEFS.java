package prompto.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import software.amazon.awssdk.services.ec2.model.DescribeSubnetsResponse;
import software.amazon.awssdk.services.ec2.model.Subnet;
import software.amazon.awssdk.services.efs.model.CreateFileSystemRequest;
import software.amazon.awssdk.services.efs.model.CreateFileSystemResponse;
import software.amazon.awssdk.services.efs.model.CreateMountTargetRequest;
import software.amazon.awssdk.services.efs.model.CreateMountTargetResponse;
import software.amazon.awssdk.services.efs.model.DeleteFileSystemRequest;
import software.amazon.awssdk.services.efs.model.DeleteFileSystemResponse;
import software.amazon.awssdk.services.efs.model.DeleteMountTargetRequest;
import software.amazon.awssdk.services.efs.model.DescribeFileSystemsRequest;
import software.amazon.awssdk.services.efs.model.DescribeFileSystemsResponse;
import software.amazon.awssdk.services.efs.model.DescribeMountTargetsRequest;
import software.amazon.awssdk.services.efs.model.DescribeMountTargetsResponse;
import software.amazon.awssdk.services.efs.model.FileSystemDescription;
import software.amazon.awssdk.services.efs.model.LifeCycleState;
import software.amazon.awssdk.services.efs.model.Tag;


@Category(AwsTest.class)
public class TestEFS extends AWSTestBase {


	@Test
	public void listsFileSystems() {
		DescribeFileSystemsResponse result = efs.describeFileSystems();
		List<FileSystemDescription> fileSystems = result.fileSystems().stream()
				.filter(this::hasTag)
				.collect(Collectors.toList());
		assertEquals(0, fileSystems.size());
	}
	
	@Test
	public void createsAndDeletesFileSystem() {
		DescribeFileSystemsResponse describeResult = efs.describeFileSystems();
		List<FileSystemDescription> fileSystems = describeResult.fileSystems().stream()
				.filter(this::hasTag)
				.collect(Collectors.toList());
		assertEquals(0, fileSystems.size());
		CreateFileSystemRequest createRequest = CreateFileSystemRequest.builder()
				.tags(Tag.builder().key("Name").value("test-fs").build())
				.build();
		CreateFileSystemResponse createResponse = efs.createFileSystem(createRequest);
		String systemId = createResponse.fileSystemId();
		describeResult = efs.describeFileSystems();
		fileSystems = describeResult.fileSystems().stream()
				.filter(this::hasTag)
				.collect(Collectors.toList());
		assertEquals(1, fileSystems.size());
		DeleteFileSystemRequest deleteRequest = DeleteFileSystemRequest.builder()
				.fileSystemId(systemId)
				.build();
		DeleteFileSystemResponse deleteResponse = efs.deleteFileSystem(deleteRequest);
		assertNotNull(deleteResponse);
		describeResult = efs.describeFileSystems();
		fileSystems = describeResult.fileSystems().stream()
				.filter(this::hasTag)
				.filter(fs -> fs.lifeCycleState()!=LifeCycleState.DELETING)
				.collect(Collectors.toList());
		assertEquals(0, fileSystems.size());
	}
	
	@Test
	public void createsAndDeletesMountTarget() throws InterruptedException {
		String fileSystemId = null;
		String mountTargetId = null;
		try {
			fileSystemId = createAnEFS();
			waitForFSState(fileSystemId, LifeCycleState.AVAILABLE);
			String subnetId = fetchASubnetId();
			mountTargetId = createAMountTarget(fileSystemId, subnetId);
			waitForMTState(mountTargetId, LifeCycleState.AVAILABLE);
		} finally {
			if(mountTargetId != null) {
				deleteMountTarget(mountTargetId);
				waitForMTState(mountTargetId, LifeCycleState.DELETED);
			}
			deleteFS(fileSystemId);
		}
	}
	
	private void deleteMountTarget(String mountTargetId) {
		DeleteMountTargetRequest deleteMTRequest = DeleteMountTargetRequest.builder()
				.mountTargetId(mountTargetId)
				.build();
		efs.deleteMountTarget(deleteMTRequest);
	}

	private void waitForMTState(String mountTargetId, LifeCycleState state) throws InterruptedException {
		LifeCycleState lcs = null;
		do {
			Thread.sleep(1000);
			DescribeMountTargetsRequest describeMTRequest = DescribeMountTargetsRequest.builder()
					.mountTargetId(mountTargetId)
					.build();
			DescribeMountTargetsResponse describeMTResponse = efs.describeMountTargets(describeMTRequest);
			lcs = describeMTResponse.mountTargets().get(0).lifeCycleState();
		} while (lcs != state);
	}

	private void deleteFS(String fileSystemId) {
		DeleteFileSystemRequest deleteFSRequest = DeleteFileSystemRequest.builder()
			.fileSystemId(fileSystemId)
			.build();
		efs.deleteFileSystem(deleteFSRequest);
	}
	
	private String createAMountTarget(String fileSystemId, String subnetId) {
		CreateMountTargetRequest createMTRequest = CreateMountTargetRequest.builder()
				.fileSystemId(fileSystemId)
				.subnetId(subnetId)
				.build();
		CreateMountTargetResponse createMTResponse = efs.createMountTarget(createMTRequest);
		return createMTResponse.mountTargetId();
	}

	private void waitForFSState(String fileSystemId, LifeCycleState state) throws InterruptedException {
		LifeCycleState lcs = null;
		do {
			Thread.sleep(1000);
			DescribeFileSystemsRequest describeFSRequest = DescribeFileSystemsRequest.builder()
					.fileSystemId(fileSystemId)
					.build();
			DescribeFileSystemsResponse describeFSResponse = efs.describeFileSystems(describeFSRequest);
			lcs = describeFSResponse.fileSystems().get(0).lifeCycleState();
		} while (lcs != state);
	}

	private String createAnEFS() {
		CreateFileSystemRequest createFSRequest = CreateFileSystemRequest.builder()
				.tags(Tag.builder().key("Name").value("test-fs").build())
				.build();
		CreateFileSystemResponse createFSResponse = efs.createFileSystem(createFSRequest);
		return createFSResponse.fileSystemId();
	}

	private String fetchASubnetId() {
		DescribeSubnetsResponse subnetsResponse = ec2.describeSubnets();
		Subnet subnet = subnetsResponse.subnets().get(0);
		return subnet.subnetId();
	}

	boolean hasTag(FileSystemDescription fs) {
		return fs.tags() != null && fs.tags().stream()
					.filter(t -> "Name".equals(t.key()))
					.anyMatch(t->"test-fs".equals(t.value()));
	}
	
	
}
