package prompto.aws;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.intrinsic.PromptoDocument;


@Category(AwsTest.class)
public class TestEFS extends AWSTestBase {


	@Test
	public void listsFileSystems() {
		EFS awsEfs = new EFS(efs);
		List<PromptoDocument<String, Object>> fileSystems = awsEfs.listFileSystems();
		fileSystems = fileSystems.stream()
				.filter(this::hasTag)
				.collect(Collectors.toList());
		assertEquals(0, fileSystems.size());
	}
	
	@Test
	public void createsAndDropsFileSystem() {
		EFS awsEfs = new EFS(efs);
		List<PromptoDocument<String, Object>> fileSystems = awsEfs.listFileSystems();
		fileSystems = fileSystems.stream()
				.filter(this::hasTag)
				.collect(Collectors.toList());
		assertEquals(0, fileSystems.size());
		String fileSystemId = awsEfs.createFileSystem("test-fs", null, false, true);
		fileSystems = awsEfs.listFileSystems();
		fileSystems = fileSystems.stream()
				.filter(this::hasTag)
				.collect(Collectors.toList());
		assertEquals(1, fileSystems.size());
		awsEfs.dropFileSystem(fileSystemId, true);		
		fileSystems = awsEfs.listFileSystems();
		fileSystems = fileSystems.stream()
				.filter(this::hasTag)
				.collect(Collectors.toList());
		assertEquals(0, fileSystems.size());
	}
	
	@Test
	public void createsAndDeletesMountTarget() throws InterruptedException {
		EFS awsEfs = new EFS(efs);
		String fileSystemId = null;
		String mountTargetId = null;
		try {
			fileSystemId = awsEfs.createFileSystem("test-fs", null, false, true);
			String subnetId = fetchASubnetId();
			mountTargetId = awsEfs.createMountTarget(fileSystemId, subnetId, null, true);
		} finally {
			if(mountTargetId != null)
				awsEfs.dropMountTarget(mountTargetId, true);
			awsEfs.dropFileSystem(fileSystemId, false);
		}
	}
	
	private String fetchASubnetId() {
		EC2 awsEc2 = new EC2(ec2);
		List<PromptoDocument<String, Object>> subnets = awsEc2.listSubnets();
		return (String)subnets.get(0).get("subnetId");
	}

	boolean hasTag(PromptoDocument<String, Object> doc) {
		return "test-fs".equals(doc.get("Name"));
	}
	
	
}
