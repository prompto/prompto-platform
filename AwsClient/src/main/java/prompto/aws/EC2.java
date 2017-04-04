package prompto.aws;

import java.util.Base64;

import prompto.intrinsic.PromptoConverter;
import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class EC2 {
	
	public static EC2 newInstance(String ec2Region, String login, String password) {
		AmazonEC2ClientBuilder builder = AmazonEC2ClientBuilder.standard()
				.withRegion(ec2Region);
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
	
	public String runInstance(String imageId, String instanceType, String keyName, PromptoList<String> securityGroups, String userData) {
		userData = Base64.getEncoder().encodeToString(userData.getBytes());
		RunInstancesRequest runRequest = new RunInstancesRequest()
			.withImageId(imageId)
			.withInstanceType(instanceType)
			.withMinCount(1)
            .withMaxCount(1)
            .withKeyName(keyName)
            .withSecurityGroups(securityGroups)
            .withUserData(userData);
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
				promoteNameTag(i, doc);
				list.add(doc); 
			});
		});
		return list;
	}

	private void promoteNameTag(Instance instance, PromptoDocument<String, Object> doc) {
		Tag tag = instance.getTags().stream()
				.filter((t)->"Name".equals(t.getKey()))
				.findFirst()
				.orElse(null);
		if(tag!=null)
			doc.put("Name", tag.getValue());
	}

}
