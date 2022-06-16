package prompto.aws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.intrinsic.PromptoDocument;

@Category(AwsTest.class)
public class TestEC2 extends AWSTestBase {

	@Test
	public void runsInstance() throws Exception {
		EC2 awsEc2 = new EC2(ec2);
		String instanceId = awsEc2.runInstance("ami-08a28a73", "t2.micro", "prompto-admin", null, Collections.singletonList("default"), "{\"prompto-role\":\"prompto-web-site\"}");
		awsEc2.waitForInstanceState(instanceId, "pending", 10);
		awsEc2.setInstanceName(instanceId, "test-instance-tags");
		awsEc2.waitForInstanceState(instanceId, "running", 180);
		awsEc2.stopInstance(instanceId);
		awsEc2.dropInstance(instanceId);
	}
	
	@Test
	public void listsAvailabilityZones() {
		EC2 awsEc2 = new EC2(ec2);
		List<PromptoDocument<String, Object>> docs = awsEc2.listAvailabilityZones();
		assertTrue(docs.size()>=6);
		assertNotNull(docs.get(0).get("zoneName"));
		assertNotNull(docs.get(0).get("regionName"));
	}

	
	@Test
	public void listsSubnets() {
		EC2 awsEc2 = new EC2(ec2);
		List<PromptoDocument<String, Object>> docs = awsEc2.listSubnets();
		assertTrue(docs.size()>=6);
		assertNotNull(docs.get(0).get("availabilityZoneId"));
		assertNotNull(docs.get(0).get("subnetId"));
	}

	@Test
	public void listsSecurityGroups() {
		EC2 awsEc2 = new EC2(ec2);
		List<PromptoDocument<String, Object>> docs = awsEc2.listSecurityGroups();
		assertTrue(docs.size()>=2);
		assertNotNull(docs.get(0).get("groupName"));
		assertNotNull(docs.get(0).get("groupId"));
	}

	/*
	@Test
	public void listsEBSVolumesWithTag() throws Exception {
		DescribeVolumesRequest request = DescribeVolumesRequest.builder()
				.filters(Filter.builder()
					.name("tag:Name")
					.values("test-logs")
					.build())
				.build();
		DescribeVolumesResponse result = ec2.describeVolumes(request);
		result.volumes().forEach(volume -> {
			System.out.println("Id: " + volume.volumeId());
			System.out.println("Tags: " + volume.tags());
		});
	}
	
	@Test
	public void createsEBSVolumeWithTag() throws Exception {
		CreateVolumeRequest request = CreateVolumeRequest.builder()
				.availabilityZone("us-east-1a")
				.volumeType(VolumeType.GP3)
				.size(10)
				// .tagSpecifications(new TagSpecification[] { TagSpecification.builder().tags(Tag.builder().key("Name").value("test").build()).build() })
				.build();
		CreateVolumeResponse result = ec2.createVolume(request);
		System.out.println("Id: " + result.volumeId());
		System.out.println("VolumeType" + result.volumeType());
		System.out.println("Size: " + result.size());
		System.out.println("Tags: " + result.tags());
		CreateTagsRequest tagsRequest = CreateTagsRequest.builder()
				.resources(result.volumeId())
				.tags(Tag.builder().key("Name").value("test-tags").build())
				.build();
		CreateTagsResponse tagsResult = ec2.createTags(tagsRequest);
		System.out.println(tagsResult.toString());
		DescribeVolumesRequest listRequest = DescribeVolumesRequest.builder()
				.filters(Filter.builder()
					.name("tag:Name")
					.values("test-tags")
					.build())
				.build();
		DescribeVolumesResponse listResult = ec2.describeVolumes(listRequest);
		listResult.volumes().forEach(volume -> {
			System.out.println("Id: " + volume.volumeId());
			System.out.println("Tags: " + volume.tags());
		});
	}
	*/
}
