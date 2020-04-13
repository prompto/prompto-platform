package prompto.aws;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateImageRequest;
import com.amazonaws.services.ec2.model.CreateImageResult;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.Tag;

import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;

@Category(AwsTest.class)
public class TestDescribeAMIs extends AWSTestBase {

	@Test
	public void listsOwnedAMIs() throws Exception {
		PromptoList<PromptoDocument<String, Object>> result = new EC2(ec2).listOwnedAMIs();
		result.forEach(System.out::println);
		assertFalse(result.isEmpty());
	}
	
	
	@Test
	public void listsAMIsWithOwnerAndName() throws Exception {
		AmazonEC2 client = AmazonEC2ClientBuilder.standard()
			.withRegion(Regions.AP_SOUTHEAST_1)
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.build();
		PromptoList<PromptoDocument<String, Object>> result = new EC2(client).listAMIsWithOwnerAndName("838901125615", "centos-prompto-v0.0.122");
		result.forEach(System.out::println);
		assertEquals(1, result.size());
		result = new EC2(ec2).listAMIsWithOwnerAndName("838901125615", "centos-prompto-*");
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void createsAMIsetsTagAndWaitsForAvailability() {
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
