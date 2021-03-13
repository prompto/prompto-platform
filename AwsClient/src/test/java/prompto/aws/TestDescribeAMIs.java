package prompto.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateImageRequest;
import software.amazon.awssdk.services.ec2.model.CreateImageResponse;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;
import software.amazon.awssdk.services.ec2.model.DescribeImagesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeImagesResponse;
import software.amazon.awssdk.services.ec2.model.ImageState;
import software.amazon.awssdk.services.ec2.model.Tag;

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
		Ec2Client client = Ec2Client.builder()
			.region(Region.AP_SOUTHEAST_1)
			.credentialsProvider(StaticCredentialsProvider.create(credentials))
			.build();
		PromptoList<PromptoDocument<String, Object>> result = new EC2(client).listAMIsWithOwnerAndName("838901125615", "centos-prompto-v0.0.215");
		result.forEach(System.out::println);
		assertEquals(1, result.size());
		result = new EC2(ec2).listAMIsWithOwnerAndName("838901125615", "centos-prompto-*");
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void createsAMIsetsTagAndWaitsForAvailability() {
		CreateImageRequest request = CreateImageRequest.builder()
			.instanceId("i-0295d2ed128657bff")
			.name("prompto-test-ami")
			.build();
		CreateImageResponse result = ec2.createImage(request);
		String imageId = result.imageId();
		CreateTagsRequest tagsRequest = CreateTagsRequest.builder()
			.resources(imageId)
			.tags(Tag.builder().key("Name").value("prompto-test-ami").build())
			.build();
		CreateTagsResponse tagsResult = ec2.createTags(tagsRequest);
		assertNotNull(tagsResult);
		DescribeImagesRequest describeRequest = DescribeImagesRequest.builder()
			.imageIds(imageId)
			.build();
		ImageState state = ImageState.PENDING;
		long start = System.currentTimeMillis();
		while(state != ImageState.AVAILABLE && (System.currentTimeMillis() - start < 3*60*1000)) {
			DescribeImagesResponse describeResult = ec2.describeImages(describeRequest);
			state = describeResult.images().get(0).state();
		}
	}

}
