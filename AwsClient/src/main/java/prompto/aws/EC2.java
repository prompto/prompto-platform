package prompto.aws;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.Ec2ClientBuilder;
import software.amazon.awssdk.services.ec2.model.AllocateAddressResponse;
import software.amazon.awssdk.services.ec2.model.AssociateAddressRequest;
import software.amazon.awssdk.services.ec2.model.AssociateAddressResponse;
import software.amazon.awssdk.services.ec2.model.CopyImageRequest;
import software.amazon.awssdk.services.ec2.model.CopyImageResponse;
import software.amazon.awssdk.services.ec2.model.CreateImageRequest;
import software.amazon.awssdk.services.ec2.model.CreateImageResponse;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeAddressesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeAddressesResponse;
import software.amazon.awssdk.services.ec2.model.DescribeAvailabilityZonesResponse;
import software.amazon.awssdk.services.ec2.model.DescribeImagesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeImagesResponse;
import software.amazon.awssdk.services.ec2.model.DescribeInstanceStatusRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstanceStatusResponse;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.DescribeSubnetsResponse;
import software.amazon.awssdk.services.ec2.model.DisassociateAddressRequest;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Filter;
import software.amazon.awssdk.services.ec2.model.IamInstanceProfileSpecification;
import software.amazon.awssdk.services.ec2.model.ImageState;
import software.amazon.awssdk.services.ec2.model.InstanceState;
import software.amazon.awssdk.services.ec2.model.LaunchPermission;
import software.amazon.awssdk.services.ec2.model.LaunchPermissionModifications;
import software.amazon.awssdk.services.ec2.model.ModifyImageAttributeRequest;
import software.amazon.awssdk.services.ec2.model.PermissionGroup;
import software.amazon.awssdk.services.ec2.model.ReleaseAddressRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesRequest; 

public class EC2 {
	
	public static EC2 newInstance(String awsRegion, String login, String password) {
		Ec2ClientBuilder builder = Ec2Client.builder()
				.region(Region.of(awsRegion));
		if(login!=null && password!=null) {
			AwsCredentials credentials = AwsBasicCredentials.create(login, password);
			builder = builder.credentialsProvider(StaticCredentialsProvider.create(credentials));
		}
		return new EC2(builder.build());
	}
	
	Ec2Client ec2;
	
	public EC2(Ec2Client ec2) {
		this.ec2 = ec2;
	}
	
	public String runInstance(String imageId, String instanceType, String keyName, String iamRoleName, List<String> securityGroups, String userData) {
		try {
			RunInstancesRequest.Builder builder = RunInstancesRequest.builder()
				.imageId(imageId)
				.instanceType(instanceType)
				.minCount(1)
	            .maxCount(1)
	            .keyName(keyName);
			if(userData!=null)
				builder = builder.userData(Base64.getEncoder().encodeToString(userData.getBytes()));
			if(securityGroups!=null && !securityGroups.isEmpty())	
				builder = builder.securityGroups(securityGroups);
	        if(iamRoleName!=null && !iamRoleName.isEmpty()) {
				IamInstanceProfileSpecification iamProfile = null;
				if(iamRoleName!=null && !iamRoleName.isEmpty())
					iamProfile = IamInstanceProfileSpecification.builder().name(iamRoleName).build();
				builder = builder.iamInstanceProfile(iamProfile);
			}
			RunInstancesResponse runResult = ec2.runInstances(builder.build());
			return runResult.instances().get(0).instanceId();
		} catch(Ec2Exception e) {
			throw e;
		}

	}
	
	
	public void waitForInstanceState(String instanceId, String stateName, int timeOutInSecs) {
		DescribeInstanceStatusRequest statusRequest = DescribeInstanceStatusRequest.builder()
				.instanceIds(instanceId)
				.includeAllInstances(true)
				.build();
		InstanceState state = InstanceState.builder().name(stateName).build();
		InstanceState read = null;
		long start = System.currentTimeMillis();
		do {
			try {
				DescribeInstanceStatusResponse describeResult = ec2.describeInstanceStatus(statusRequest);
				read = describeResult.instanceStatuses().get(0).instanceState();
				if(read != state)
					Utils.unsafeSleep(1000);
			} catch(Ec2Exception e) {
				if("InvalidInstanceID.NotFound".equals(e.awsErrorDetails().errorCode()))
					read = InstanceState.builder().name("terminated").build();
				else
					throw e;
			}
		} while(read != state && (System.currentTimeMillis() - start < timeOutInSecs*1000)); 
	}
	
	public void setInstanceName(String instanceId, String name) {
		// this is typically called immediately after creating an instance
		// however, the instance may not exist yet (can take a few secs)
		waitForInstanceState(instanceId, "pending", 10);
		CreateTagsRequest.Builder tagsRequestBuilder = CreateTagsRequest.builder()
			.resources(instanceId)
			.tags(Tag.builder().key("Name").value(name).build());
		ec2.createTags(tagsRequestBuilder.build());
	}
	
	public void startInstance(String instanceId) {
		StartInstancesRequest startRequest = StartInstancesRequest.builder()
			.instanceIds(instanceId).build();
		ec2.startInstances(startRequest);
	}
	
	
	public void stopInstance(String instanceId) {
		StopInstancesRequest stopRequest = StopInstancesRequest.builder()
			.instanceIds(instanceId).build();
		ec2.stopInstances(stopRequest);
	}

	
	public void dropInstance(String instanceId) {
		TerminateInstancesRequest dropRequest = TerminateInstancesRequest.builder()
			.instanceIds(instanceId).build();
		ec2.terminateInstances(dropRequest);
	}

	
	public PromptoList<PromptoDocument<String, Object>> listInstances() {
		PromptoList<PromptoDocument<String, Object>> list = new PromptoList<PromptoDocument<String,Object>>(true);
		DescribeInstancesResponse result = ec2.describeInstances();
		result.reservations().forEach((r)->{
			r.instances().forEach((i)->{
				PromptoDocument<String, Object> doc = Converter.convertPojo(i);
				promoteNameTag(i.tags(), doc);
				list.add(doc); 
			});
		});
		return list;
	}

	public PromptoList<PromptoDocument<String, Object>> listIpAddresses() {
		PromptoList<PromptoDocument<String, Object>> list = new PromptoList<PromptoDocument<String,Object>>(true);
		DescribeAddressesResponse result = ec2.describeAddresses();
		result.addresses().forEach((a)->{
			PromptoDocument<String, Object> doc = Converter.convertPojo(a);
			promoteNameTag(a.tags(), doc);
			list.add(doc); 
		});
		return list;
	}
	
	public String getAddressIdForIpAddress(String ipAddress) {
		DescribeAddressesRequest req = DescribeAddressesRequest.builder()
			.publicIps(ipAddress)
			.build();
		try {
			DescribeAddressesResponse res = ec2.describeAddresses(req);
			if(res.addresses().isEmpty())
				return null;
			else
				return res.addresses().get(0).allocationId();
		} catch (Ec2Exception e) {
			if("InvalidAddress.NotFound".equals(e.awsErrorDetails().errorCode()))
				return null;
			throw e;
		}
	}
	
	public PromptoDocument<String, Object> createIpAddress() {
		AllocateAddressResponse newResult = ec2.allocateAddress();
		PromptoDocument<String, Object> doc = new PromptoDocument<>();
		doc.put("allocationId", newResult.allocationId());
		doc.put("publicIp", newResult.publicIp());
		doc.put("domain", newResult.domain());
		return doc;
	}
	
	public void setIpAddressName(String addressId, String name) {
		CreateTagsRequest tagsRequest = CreateTagsRequest.builder()
			.resources(addressId)
			.tags(Tag.builder().key("Name").value(name).build())
			.build();
		ec2.createTags(tagsRequest);
	}

	
	public String associateIPAddress(String instanceId, String addressId) {
		AssociateAddressRequest assocRequest = AssociateAddressRequest.builder()
		.allocationId(addressId)
		.instanceId(instanceId)
		.build();
		AssociateAddressResponse assocResult = ec2.associateAddress(assocRequest);
		return assocResult.associationId();
	}
	
	public void dissociateIPAddress(String associationId) {
		DisassociateAddressRequest dissocRequest = DisassociateAddressRequest.builder()
			.associationId(associationId)
			.build();
		ec2.disassociateAddress(dissocRequest);
	}
	
	public void dropIPAddress(String addressId) {
		ReleaseAddressRequest dropRequest = ReleaseAddressRequest.builder()
			.allocationId(addressId)
			.build();
		ec2.releaseAddress(dropRequest);
	}
	
	public PromptoList<PromptoDocument<String, Object>> listOwnedAMIs() {
		PromptoList<PromptoDocument<String, Object>> list = new PromptoList<PromptoDocument<String,Object>>(true);
		DescribeImagesRequest request = DescribeImagesRequest.builder()
		.owners("self")
		.build();
		DescribeImagesResponse result = ec2.describeImages(request);
		result.images().forEach(i->{
			PromptoDocument<String, Object> doc = Converter.convertPojo(i);
			promoteNameTag(i.tags(), doc);
			list.add(doc); 
		});
		return list;
	}
	
	public PromptoList<PromptoDocument<String, Object>> listAMIsWithOwnerAndName(String owner, String name) {
		PromptoList<PromptoDocument<String, Object>> list = new PromptoList<PromptoDocument<String,Object>>(true);
		DescribeImagesRequest.Builder builder = DescribeImagesRequest.builder();
		if(owner!=null || name!=null) {
			List<Filter> filters = new ArrayList<>();
			if(owner!=null)
				filters.add(Filter.builder().name("owner-id").values(owner).build());
			if(name!=null)
				filters.add(Filter.builder().name("name").values(name).build());
			builder = builder.filters(filters);
		}
		DescribeImagesResponse result = ec2.describeImages(builder.build());
		result.images().forEach((i)->{
			PromptoDocument<String, Object> doc = Converter.convertPojo(i);
			promoteNameTag(i.tags(), doc);
			list.add(doc); 
		});
		return list;
	}
	
	
	public String createAMI(String instanceId, String name, boolean waitForAvailability) {
		String imageId = createAMI(instanceId, name);
		setAMINameTag(imageId, name);
		if(waitForAvailability)
			waitForAMIState(imageId, ImageState.AVAILABLE);
		return imageId;
	}
	
	public String copyAMI(String srcImageId, String srcRegion, String name, boolean waitForAvailability) {
		CopyImageRequest request = CopyImageRequest.builder()
				.sourceImageId(srcImageId)
				.sourceRegion(srcRegion)
				.name(name)
				.description("Copied from " + srcImageId + " in " + srcRegion)
				.build();
		CopyImageResponse result = ec2.copyImage(request);
		String dstImageId = result.imageId();
		setAMINameTag(dstImageId, name);
		if(waitForAvailability)
			waitForAMIState(dstImageId, ImageState.AVAILABLE);
		return dstImageId;
	}

	public void setAMIPublic(String imageId) {
		LaunchPermissionModifications permissions = LaunchPermissionModifications.builder()
				.add(LaunchPermission.builder().group(PermissionGroup.ALL).build())
				.build();
		ModifyImageAttributeRequest request = ModifyImageAttributeRequest.builder()
				.imageId(imageId)
				.launchPermission(permissions)
				.build();
		ec2.modifyImageAttribute(request);
	}
	
	
	public List<PromptoDocument<String,Object>> listAvailabilityZones() {
		DescribeAvailabilityZonesResponse response = ec2.describeAvailabilityZones();
		return response.availabilityZones().stream()
				.map(Converter::convertPojo)
				.collect(Collectors.toList());
	}


	public List<PromptoDocument<String,Object>> listSubnets() {
		DescribeSubnetsResponse response = ec2.describeSubnets();
		return response.subnets().stream()
				.map(Converter::convertPojo)
				.collect(Collectors.toList());
	}

	private void waitForAMIState(String imageId, ImageState state) {
		DescribeImagesRequest describeRequest = DescribeImagesRequest.builder()
				.imageIds(imageId)
				.build();
		ImageState read = null;
		long start = System.currentTimeMillis();
		do {
			DescribeImagesResponse describeResult = ec2.describeImages(describeRequest);
			read = describeResult.images().get(0).state();
			if(read != state)
				Utils.unsafeSleep(1000);
		} while(read != state && (System.currentTimeMillis() - start < 10*60*1000));
	}

	private void setAMINameTag(String imageId, String name) {
		CreateTagsRequest request = CreateTagsRequest.builder()
				.resources(imageId)
				.tags(Tag.builder().key("Name").value(name).build())
				.build();
		ec2.createTags(request);
	}

	private String createAMI(String instanceId, String name) {
		CreateImageRequest request = CreateImageRequest.builder()
				.instanceId(instanceId)
				.name(name)
				.build();
		CreateImageResponse result = ec2.createImage(request);
		return result.imageId();
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
