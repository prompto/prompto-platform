package prompto.aws;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;

@Category(AwsTest.class)
public class TestDescribeAMIs extends AWSTestBase {

	@Test
	public void testThatDescribeAMIsReturnsData() throws Exception {
		DescribeImagesRequest request = new DescribeImagesRequest()
			.withOwners("self");
		DescribeImagesResult result = ec2.describeImages(request);
		System.out.println(result.toString());
	}

}
