package prompto.aws;

import java.util.Base64;

import org.junit.Ignore;
import org.junit.Test;

import com.amazonaws.services.ec2.model.CreateTagsResult;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

@Ignore("Only run these manually!!!")
public class TestRunInstance extends TestBase {

	@Test
	public void testThatRunInstanceSucceeds() throws Exception {
		String userData = Base64.getEncoder().encodeToString("{\"prompto-role\":\"prompto-web-site\"}".getBytes());
		RunInstancesRequest runRequest = new RunInstancesRequest()
			.withImageId("ami-d2c924b2")
			.withInstanceType("t2.micro")
			.withMinCount(1)
            .withMaxCount(1)
            .withKeyName("prompto-admin")
            .withSecurityGroups("default")
            .withUserData(userData);
		RunInstancesResult runResult = ec2.runInstances(runRequest);
		System.out.println(runResult.toString());
		CreateTagsRequest tagsRequest = new CreateTagsRequest()
			.withResources(runResult.getReservation().getInstances().get(0).getInstanceId())
			.withTags(new Tag("Name","prompto-web-server-003"));
		CreateTagsResult tagsResult = ec2.createTags(tagsRequest);
		System.out.println(tagsResult.toString());
		boolean running = false;
		do {
			Thread.sleep(1000);
			DescribeInstanceStatusRequest statusRequest = new DescribeInstanceStatusRequest()
				.withInstanceIds(runResult.getReservation().getInstances().get(0).getInstanceId())
				.withIncludeAllInstances(true);
			DescribeInstanceStatusResult statusResult = ec2.describeInstanceStatus(statusRequest);
			System.out.println(statusResult.toString());
			running = statusResult.getInstanceStatuses()
					.get(0)
					.getInstanceState()
					.getCode()==16;
		} while(!running);
		TerminateInstancesRequest delRequest = new TerminateInstancesRequest()
			.withInstanceIds(runResult.getReservation().getInstances().get(0).getInstanceId());
		TerminateInstancesResult delResult = ec2.terminateInstances(delRequest);
		System.out.println(delResult.toString());
	}

}
