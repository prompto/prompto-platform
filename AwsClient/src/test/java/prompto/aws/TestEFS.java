package prompto.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.intrinsic.PromptoDocument;


@Category(AwsTest.class)
public class TestEFS extends AWSTestBase {

	@Test
	public void propertiesAreLowerCased() {
		EFS awsEfs = new EFS(efs);
		List<PromptoDocument<String, Object>> fileSystems = awsEfs.listFileSystems();
		if(fileSystems.size() > 0) {
			PromptoDocument<String, Object> doc = fileSystems.get(0);
			Set<String> keys = doc.keySet();
			boolean allLowerCase = keys.stream().filter(s -> !"Name".equals(s)).allMatch(s -> Character.isLowerCase(s.charAt(0)));
			assertTrue(allLowerCase);
		}
	}

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
	public void createsListsAndDeletesMountTarget() throws InterruptedException {
		EFS awsEfs = new EFS(efs);
		String fileSystemId = null;
		String mountTargetId = null;
		List<PromptoDocument<String, Object>> mountTargets = Collections.emptyList();
		try {
			fileSystemId = awsEfs.createFileSystem("test-fs", null, false, true);
			String subnetId = fetchASubnetId();
			mountTargetId = awsEfs.createMountTarget(fileSystemId, subnetId, null, true);
			mountTargets = awsEfs.listMountTargets(fileSystemId);
			assertEquals(1, mountTargets.size());
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
