package prompto.aws;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.amazonaws.services.route53.model.Change;
import com.amazonaws.services.route53.model.ChangeAction;
import com.amazonaws.services.route53.model.ChangeBatch;
import com.amazonaws.services.route53.model.ChangeResourceRecordSetsRequest;
import com.amazonaws.services.route53.model.ChangeResourceRecordSetsResult;
import com.amazonaws.services.route53.model.HostedZone;
import com.amazonaws.services.route53.model.ListHostedZonesResult;
import com.amazonaws.services.route53.model.ListResourceRecordSetsRequest;
import com.amazonaws.services.route53.model.ListResourceRecordSetsResult;
import com.amazonaws.services.route53.model.RRType;
import com.amazonaws.services.route53.model.ResourceRecord;
import com.amazonaws.services.route53.model.ResourceRecordSet;

@Category(AwsTest.class)
public class TestRoute53 extends AWSTestBase {

	@Test
	public void canListHostedZones() {
		ListHostedZonesResult zones = route53.listHostedZones();
		assertEquals(2, zones.getHostedZones().size());
	}
	
	@Test
	public void canReadHostedZoneId() {
		String zoneId = getZoneId("prompto.org.");
		assertEquals("Z2NKEUI11Q0ZVF", zoneId);
		ListHostedZonesResult zones = route53.listHostedZones();
		assertEquals(2, zones.getHostedZones().size());
	}

	private String getZoneId(String zoneName) {
		ListHostedZonesResult zones = route53.listHostedZones();
		HostedZone zone = zones.getHostedZones().stream()
				.filter(z->z.getName().equals(zoneName))
				.findFirst()
				.orElse(null);
		return zone.getId();
	}

	@Test
	public void canCreateReadAndDropARecord() {
		String zoneId = getZoneId("prompto.org.");
		ChangeResourceRecordSetsRequest request = new ChangeResourceRecordSetsRequest()
			.withHostedZoneId(zoneId)
			.withChangeBatch(new ChangeBatch(Collections.singletonList(new Change()
				.withAction(ChangeAction.CREATE)
				.withResourceRecordSet(new ResourceRecordSet()
					.withName("test-user.prompto.org")
					.withType(RRType.A)
					.withTTL(300L)
					.withResourceRecords(new ResourceRecord("222.222.222.222"))
				))));
		ChangeResourceRecordSetsResult result = route53.changeResourceRecordSets(request);
		String status = result.getChangeInfo().getStatus();
		assertTrue("PENDING".equals(status) || "INSYNC".equals(status));
		ListResourceRecordSetsRequest listRequest = new ListResourceRecordSetsRequest()
			.withHostedZoneId(zoneId)
			.withStartRecordType(RRType.A)
			.withStartRecordName("test-user.prompto.org.");
		ListResourceRecordSetsResult listResult = route53.listResourceRecordSets(listRequest);
		List<ResourceRecordSet> recordSets = listResult.getResourceRecordSets();
		// list will contain all records from "test-user.prompto.org.", not just that one
		ResourceRecordSet recordSet = recordSets.get(0);
		assertEquals("222.222.222.222", recordSet.getResourceRecords().get(0).getValue());
		request = new ChangeResourceRecordSetsRequest()
			.withHostedZoneId(zoneId)
			.withChangeBatch(new ChangeBatch(Collections.singletonList(new Change()
				.withAction(ChangeAction.DELETE)
				.withResourceRecordSet(new ResourceRecordSet()
					.withName("test-user.prompto.org")
					.withType(RRType.A)
					.withTTL(300L)
					.withResourceRecords(new ResourceRecord("222.222.222.222"))
				))));
		result = route53.changeResourceRecordSets(request);
		status = result.getChangeInfo().getStatus();
		assertTrue("PENDING".equals(status) || "INSYNC".equals(status));
	}
	
}
