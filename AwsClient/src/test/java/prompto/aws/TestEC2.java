package prompto.aws;

import java.util.Base64;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import software.amazon.awssdk.services.ec2.model.*;

@Category(AwsTest.class)
public class TestEC2 extends AWSTestBase {

	@Test
	public void runsInstance() throws Exception {
		String userData = Base64.getEncoder().encodeToString("{\"prompto-role\":\"prompto-web-site\"}".getBytes());
		RunInstancesRequest runRequest = RunInstancesRequest.builder()
			.imageId("ami-08a28a73")
			.instanceType("t2.micro")
			.minCount(1)
            .maxCount(1)
            .keyName("prompto-admin")
            .securityGroups("default")
            .userData(userData)
            .build();
		RunInstancesResponse runResult = ec2.runInstances(runRequest);
		System.out.println(runResult.toString());
		String instanceId = runResult.instances().get(0).instanceId();
		CreateTagsRequest tagsRequest = CreateTagsRequest.builder()
			.resources(instanceId)
			.tags(Tag.builder().key("Name").value("prompto-web-server-003").build())
			.build();
		CreateTagsResponse tagsResult = ec2.createTags(tagsRequest);
		System.out.println(tagsResult.toString());
		boolean running = false;
		do {
			Thread.sleep(1000);
			DescribeInstanceStatusRequest statusRequest = DescribeInstanceStatusRequest.builder()
				.instanceIds(instanceId)
				.includeAllInstances(true)
				.build();
			DescribeInstanceStatusResponse statusResult = ec2.describeInstanceStatus(statusRequest);
			System.out.println(statusResult.toString());
			running = statusResult.instanceStatuses()
					.get(0)
					.instanceState()
					.code()==16;
		} while(!running);
		StopInstancesRequest stopRequest = StopInstancesRequest.builder()
				.instanceIds(instanceId)
				.build();
		StopInstancesResponse stopResult = ec2.stopInstances(stopRequest);
		System.out.println(stopResult.toString());
		TerminateInstancesRequest delRequest = TerminateInstancesRequest.builder()
			.instanceIds(instanceId)
			.build();
		TerminateInstancesResponse delResult = ec2.terminateInstances(delRequest);
		System.out.println(delResult.toString());
	}
	
	@Test
	public void listsAvailabilityZones() {
		DescribeAvailabilityZonesResponse response = ec2.describeAvailabilityZones();
		response.availabilityZones().forEach(zone -> {
			System.out.println("zone: " + zone.zoneName());
			System.out.println("region: " + zone.regionName());
		});
	}

	
	@Test
	public void listsSubnets() {
		DescribeSubnetsResponse response = ec2.describeSubnets();
		response.subnets().forEach(subnet -> {
			System.out.println("zone: " + subnet.availabilityZoneId());
			System.out.println("id: " + subnet.subnetId());
		});
	}

	
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
}
