package prompto.aws;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Tag;

@Category(AwsTest.class)
public class TestDescribeInstances extends AWSTestBase {

	@Test
	public void describesInstances() throws Exception {
		DescribeInstancesResponse result = ec2.describeInstances();
		System.out.println(result.toString());
	}
	
	@Test
	public void tagsAreAddedToInstance() throws Exception {
		Instance instance = Instance.builder()
			.tags(Tag.builder().key("k").value("v").build())
			.build();
		String data = instance.toString();
		assertTrue(data.contains("Tags"));
	}

}
