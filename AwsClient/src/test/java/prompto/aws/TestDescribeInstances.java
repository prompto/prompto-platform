package prompto.aws;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Category(AwsTest.class)
public class TestDescribeInstances extends EC2TestBase {

	@Test
	public void testThatDescribeInstancesReturnsData() throws Exception {
		DescribeInstancesResult result = ec2.describeInstances();
		System.out.println(result.toString());
	}
	
	@Test
	public void testInstanceTag() throws Exception {
		Instance instance = new Instance()
			.withTags(new Tag().withKey("k").withValue("v"));
		JsonNode node = new ObjectMapper().valueToTree(instance);
		assertNotNull(node.get("tags"));
	}

}
