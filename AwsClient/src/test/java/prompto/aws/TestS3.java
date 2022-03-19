package prompto.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
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
		var response = awsS3.listObjects("prompto-test", null, null, null, 1L);
		var contents = response.get("contents");
		assertTrue(contents instanceof List);
		assertEquals(1, ((List<?>)contents).size());
		var continuation = response.get("nextContinuationToken");
		assertTrue(continuation instanceof String);
		var response2 = awsS3.listObjects("prompto-test", null, null, continuation.toString(), 1L);
		var contents2 = response2.get("contents");
		assertTrue(contents2 instanceof List);
		var object1 = ((List<?>)contents).get(0);
		var object2 = ((List<?>)contents2).get(0);
		assertNotEquals(object1, object2);
	}
	
	@Test
	public void fetchesObjectText() {
		var awsS3 = new S3(s3);
		var text = awsS3.fetchObjectText("prompto-test", "0A6MFVCO7B244F0UGO76.json", "utf-8");
		assertTrue(text.startsWith("{\"LEIRecord\":"));
	}
	
	@Test
	public void storesAndDeletesObjectText() {
		var awsS3 = new S3(s3);
		var text = "Hi there!";
		var key = "sample.txt";
		try {
			awsS3.storeObjectText("prompto-test", key, text, "utf-8");
			var text2 = awsS3.fetchObjectText("prompto-test", key, "utf-8");
			assertEquals(text, text2);
		} finally {
			awsS3.deleteObject("prompto-test", key);
			var text3 = awsS3.fetchObjectText("prompto-test", key, "utf-8");
			assertNull(text3);
		}
	}

}
