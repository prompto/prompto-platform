package prompto.aws;

import java.util.Base64;
import java.util.List;

import prompto.intrinsic.PromptoConverter;
import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AllocateAddressResult;
import com.amazonaws.services.ec2.model.AssociateAddressRequest;
import com.amazonaws.services.ec2.model.AssociateAddressResult;
import com.amazonaws.services.ec2.model.CreateImageRequest;
import com.amazonaws.services.ec2.model.CreateImageResult;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeAddressesRequest;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DisassociateAddressRequest;
import com.amazonaws.services.ec2.model.IamInstanceProfileSpecification;
import com.amazonaws.services.ec2.model.ReleaseAddressRequest;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EC2 {
	
	public static EC2 newInstance(String awsRegion, String login, String password) {
		AmazonEC2ClientBuilder builder = AmazonEC2ClientBuilder.standard()
				.withRegion(awsRegion);
		if(login!=null && password!=null) {
			AWSCredentials credentials = new BasicAWSCredentials(login, password);
			builder = builder.withCredentials(new AWSStaticCredentialsProvider(credentials));
		}
		return new EC2(builder.build());
	}
	
	AmazonEC2 ec2;

	public EC2(AmazonEC2 ec2) {
		this.ec2 = ec2;
	}
	
	public String runInstance(String imageId, String instanceType, String keyName, String iamRoleName, PromptoList<String> securityGroups, String userData) {
		userData = Base64.getEncoder().encodeToString(userData.getBytes());
		RunInstancesRequest runRequest = new RunInstancesRequest()
			.withImageId(imageId)
			.withInstanceType(instanceType)
			.withUserData(userData)
			.withMinCount(1)
            .withMaxCount(1)
            .withSecurityGroups(securityGroups)
            .withKeyName(keyName);
		if(iamRoleName!=null && !iamRoleName.isEmpty()) {
			IamInstanceProfileSpecification iamProfile = (iamRoleName==null || iamRoleName.isEmpty()) ? null : new IamInstanceProfileSpecification().withName(iamRoleName);
			runRequest = runRequest.withIamInstanceProfile(iamProfile);
		}
		RunInstancesResult runResult = ec2.runInstances(runRequest);
		return runResult.getReservation().getInstances().get(0).getInstanceId();

	}
	
	public void setInstanceName(String instanceId, String name) {
		CreateTagsRequest tagsRequest = new CreateTagsRequest()
			.withResources(instanceId)
			.withTags(new Tag("Name", name));
		ec2.createTags(tagsRequest);
	}
	
	public void startInstance(String instanceId) {
		StartInstancesRequest startRequest = new StartInstancesRequest()
			.withInstanceIds(instanceId);
		ec2.startInstances(startRequest);
	}
	
	
	public void stopInstance(String instanceId) {
		StopInstancesRequest stopRequest = new StopInstancesRequest()
			.withInstanceIds(instanceId);
		ec2.stopInstances(stopRequest);
	}

	
	public void dropInstance(String instanceId) {
		TerminateInstancesRequest dropRequest = new TerminateInstancesRequest()
			.withInstanceIds(instanceId);
		ec2.terminateInstances(dropRequest);
	}

	
	@SuppressWarnings("unchecked")
	public PromptoList<PromptoDocument<String, Object>> listInstances() {
		PromptoList<PromptoDocument<String, Object>> list = new PromptoList<PromptoDocument<String,Object>>(true);
		DescribeInstancesResult result = ec2.describeInstances();
		result.getReservations().forEach((r)->{
			r.getInstances().forEach((i)->{
				JsonNode json = new ObjectMapper().valueToTree(i);
				Object prompto = PromptoConverter.nodeToPrompto(json);
				assert (prompto instanceof PromptoDocument);
				PromptoDocument<String, Object> doc = (PromptoDocument<String, Object>)prompto;
				promoteNameTag(i.getTags(), doc);
				list.add(doc); 
			});
		});
		return list;
	}

	private void promoteNameTag(List<Tag> tags, PromptoDocument<String, Object> doc) {
		Tag tag = tags.stream()
				.filter((t)->"Name".equals(t.getKey()))
				.findFirst()
				.orElse(null);
		if(tag!=null)
			doc.put("Name", tag.getValue());
		else
			doc.put("Name", "<anonymous>");
	}

	@SuppressWarnings("unchecked")
	public PromptoList<PromptoDocument<String, Object>> listIpAddresses() {
		PromptoList<PromptoDocument<String, Object>> list = new PromptoList<PromptoDocument<String,Object>>(true);
		DescribeAddressesResult result = ec2.describeAddresses();
		result.getAddresses().forEach((a)->{
			JsonNode json = new ObjectMapper().valueToTree(a);
			Object prompto = PromptoConverter.nodeToPrompto(json);
			assert (prompto instanceof PromptoDocument);
			PromptoDocument<String, Object> doc = (PromptoDocument<String, Object>)prompto;
			list.add(doc); 
		});
		return list;
	}
	
	public String getAddressIdForIpAddress(String ipAddress) {
		DescribeAddressesRequest req = new DescribeAddressesRequest()
			.withPublicIps(ipAddress);
		DescribeAddressesResult res = ec2.describeAddresses(req);
		if(res.getAddresses().isEmpty())
			return null;
		else
			return res.getAddresses().get(0).getAllocationId();
	}
	
	public PromptoDocument<String, Object> createIpAddress() {
		AllocateAddressResult newResult = ec2.allocateAddress();
		PromptoDocument<String, Object> doc = new PromptoDocument<>();
		doc.put("allocationId", newResult.getAllocationId());
		doc.put("publicIp", newResult.getPublicIp());
		doc.put("domain", newResult.getDomain());
		return doc;
	}
	
	public void setIpAddressName(String addressId, String name) {
		CreateTagsRequest tagsRequest = new CreateTagsRequest()
			.withResources(addressId)
			.withTags(new Tag("Name", name));
		ec2.createTags(tagsRequest);
	}

	
	public String associateIPAddress(String instanceId, String addressId) {
		AssociateAddressRequest assocRequest = new AssociateAddressRequest()
		.withAllocationId(addressId)
		.withInstanceId(instanceId);
		AssociateAddressResult assocResult = ec2.associateAddress(assocRequest);
		return assocResult.getAssociationId();
	}
	
	public void dissociateIPAddress(String associationId) {
		DisassociateAddressRequest dissocRequest = new DisassociateAddressRequest()
			.withAssociationId(associationId);
		ec2.disassociateAddress(dissocRequest);
	}
	
	public void dropIPAddress(String addressId) {
		ReleaseAddressRequest dropRequest = new ReleaseAddressRequest()
			.withAllocationId(addressId);
		ec2.releaseAddress(dropRequest);
	}
	
	@SuppressWarnings("unchecked")
	public PromptoList<PromptoDocument<String, Object>> listOwnedAMIs() {
		PromptoList<PromptoDocument<String, Object>> list = new PromptoList<PromptoDocument<String,Object>>(true);
		DescribeImagesRequest request = new DescribeImagesRequest()
		.withOwners("self");
		DescribeImagesResult result = ec2.describeImages(request);
		result.getImages().forEach((i)->{
			JsonNode json = new ObjectMapper().valueToTree(i);
			Object prompto = PromptoConverter.nodeToPrompto(json);
			assert (prompto instanceof PromptoDocument);
			PromptoDocument<String, Object> doc = (PromptoDocument<String, Object>)prompto;
			promoteNameTag(i.getTags(), doc);
			list.add(doc); 
		});
		return list;
	}
	
	public String createAMI(String instanceId, String name, boolean waitForAvailability) {
		CreateImageRequest request = new CreateImageRequest()
			.withInstanceId(instanceId)
			.withName(name);
		CreateImageResult result = ec2.createImage(request);
		String imageId = result.getImageId();
		CreateTagsRequest tagsRequest = new CreateTagsRequest()
			.withResources(imageId)
			.withTags(new Tag("Name", name));
		ec2.createTags(tagsRequest);
		if(waitForAvailability) {
			DescribeImagesRequest describeRequest = new DescribeImagesRequest()
				.withImageIds(imageId);
			String state = "pending";
			long start = System.currentTimeMillis();
			while(!"available".equals(state) && (System.currentTimeMillis() - start < 3*60*1000)) {
				unsafeSleep(1000);
				DescribeImagesResult describeResult = ec2.describeImages(describeRequest);
				state = describeResult.getImages().get(0).getState();
			}
		}
		return imageId;
	}
	
	public static void unsafeSleep(long millis) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
