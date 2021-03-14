package prompto.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import software.amazon.awssdk.services.efs.model.CreateFileSystemRequest;
import software.amazon.awssdk.services.efs.model.CreateFileSystemResponse;
import software.amazon.awssdk.services.efs.model.DeleteFileSystemRequest;
import software.amazon.awssdk.services.efs.model.DeleteFileSystemResponse;
import software.amazon.awssdk.services.efs.model.DescribeFileSystemsResponse;
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
	
	boolean hasTag(FileSystemDescription fs) {
		return fs.tags() != null && fs.tags().stream()
					.filter(t -> "Name".equals(t.key()))
					.anyMatch(t->"test-fs".equals(t.value()));
	}
	
	
}
