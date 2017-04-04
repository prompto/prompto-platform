package prompto.aws;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.amazonaws.services.ec2.model.DescribeInstancesResult;

@Category(AwsTest.class)
public class TestDescribeInstances extends EC2TestBase {

	@Test
	public void testThatDescribeInstancesReturnsData() throws Exception {
		DescribeInstancesResult result = ec2.describeInstances();
		System.out.println(result.toString());
	}

}
