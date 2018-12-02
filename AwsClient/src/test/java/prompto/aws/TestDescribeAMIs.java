package prompto.aws;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.amazonaws.services.ec2.model.CreateImageRequest;
import com.amazonaws.services.ec2.model.CreateImageResult;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.Tag;

@Category(AwsTest.class)
public class TestDescribeAMIs extends AWSTestBase {

	@Test
	public void describeAMIsReturnsData() throws Exception {
		DescribeImagesRequest request = new DescribeImagesRequest()
			.withOwners("self");
		DescribeImagesResult result = ec2.describeImages(request);
		System.out.println(result.toString());
	}
	
	@Test
	public void canCreateAMIsetTagAndWaitForAvailability() {
		CreateImageRequest request = new CreateImageRequest()
			.withInstanceId("i-0295d2ed128657bff")
			.withName("prompto-test-ami");
		CreateImageResult result = ec2.createImage(request);
		String imageId = result.getImageId();
		CreateTagsRequest tagsRequest = new CreateTagsRequest()
			.withResources(imageId)
			.withTags(new Tag("Name","prompto-test-ami"));
		CreateTagsResult tagsResult = ec2.createTags(tagsRequest);
		assertNotNull(tagsResult);
		DescribeImagesRequest describeRequest = new DescribeImagesRequest()
			.withImageIds(imageId);
		String state = "pending";
		long start = System.currentTimeMillis();
		while(!"available".equals(state) && (System.currentTimeMillis() - start < 3*60*1000)) {
			DescribeImagesResult describeResult = ec2.describeImages(describeRequest);
			state = describeResult.getImages().get(0).getState();
		}
	}

}
