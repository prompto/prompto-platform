package prompto.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.intrinsic.PromptoDocument;
import software.amazon.awssdk.services.route53.model.ChangeStatus;

@Category(AwsTest.class)
public class TestRoute53 extends AWSTestBase {

	@Test
	public void listsHostedZones() {
		Route53 awsRoute53 = new Route53(route53);
		List<PromptoDocument<String, Object>> zones = awsRoute53.listHostedZones();
		assertEquals(2, zones.size());
	}
	
	@Test
	public void createsReadsAndDropsARecord() {
		Route53 awsRoute53 = new Route53(route53);
		String status = awsRoute53.createARecord("prompto.org", "test-user", "222.222.222.222", 300L);
		assertTrue(status==ChangeStatus.PENDING.name() || status==ChangeStatus.INSYNC.name());
		PromptoDocument<String, Object> record = awsRoute53.readARecord("prompto.org", "test-user");
		assertEquals("222.222.222.222", record.get("ipAddress"));
		status = awsRoute53.deleteARecord("prompto.org", "test-user");
		assertTrue(status==ChangeStatus.PENDING.name() || status==ChangeStatus.INSYNC.name());
	}
	
}
