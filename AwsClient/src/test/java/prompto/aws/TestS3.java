package prompto.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(AwsTest.class)
public class TestS3 extends AWSTestBase {

	@Test
	public void listsBuckets() {
		var awsS3 = new S3(s3);
		var buckets = awsS3.listBucketNames();
		assertTrue(buckets.size() > 0);
	}
	
	@Test
	public void listsObjects() {
		var awsS3 = new S3(s3);
		var response = awsS3.listObjects("amalthea-greenbook", null, null, null, 10L);
		var contents = response.get("contents");
		assertTrue(contents instanceof List);
		assertEquals(10, ((List<?>)contents).size());
		var continuation = response.get("nextContinuationToken");
		assertTrue(continuation instanceof String);
		var response2 = awsS3.listObjects("amalthea-greenbook", null, null, continuation.toString(), 10L);
		var contents2 = response2.get("contents");
		assertTrue(contents2 instanceof List);
		var object1 = ((List<?>)contents).get(0);
		var object2 = ((List<?>)contents2).get(0);
		assertNotEquals(object1, object2);
	}
	
	@Test
	public void fetchesObjectText() {
		var awsS3 = new S3(s3);
		var text = awsS3.fetchObjectText("amalthea-greenbook", "LEIs/json/001G/PB6A/9XPE/001GPB6A9XPE8XJICC14.json", "utf-8");
		assertTrue(text.startsWith("{\"LEIRecord\":"));
	}
}
