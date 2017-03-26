package prompto.aws;

import org.junit.Ignore;
import org.junit.Test;

import com.amazonaws.services.ec2.model.DescribeInstancesResult;

@Ignore("Only run these manually!!!")
public class TestDescribeInstances extends TestBase {

	@Test
	public void testThatDescribeInstancesReturnsData() throws Exception {
		DescribeInstancesResult result = ec2.describeInstances();
		System.out.println(result.toString());
	}

}
