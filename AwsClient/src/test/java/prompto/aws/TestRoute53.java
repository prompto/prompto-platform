package prompto.aws;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import software.amazon.awssdk.services.route53.model.Change;
import software.amazon.awssdk.services.route53.model.ChangeAction;
import software.amazon.awssdk.services.route53.model.ChangeBatch;
import software.amazon.awssdk.services.route53.model.ChangeResourceRecordSetsRequest;
import software.amazon.awssdk.services.route53.model.ChangeResourceRecordSetsResponse;
import software.amazon.awssdk.services.route53.model.ChangeStatus;
import software.amazon.awssdk.services.route53.model.HostedZone;
import software.amazon.awssdk.services.route53.model.ListHostedZonesResponse;
import software.amazon.awssdk.services.route53.model.ListResourceRecordSetsRequest;
import software.amazon.awssdk.services.route53.model.ListResourceRecordSetsResponse;
import software.amazon.awssdk.services.route53.model.RRType;
import software.amazon.awssdk.services.route53.model.ResourceRecord;
import software.amazon.awssdk.services.route53.model.ResourceRecordSet;

@Category(AwsTest.class)
public class TestRoute53 extends AWSTestBase {

	@Test
	public void canListHostedZones() {
		ListHostedZonesResponse zones = route53.listHostedZones();
		assertEquals(2, zones.hostedZones().size());
	}
	
	@Test
	public void canReadHostedZoneId() {
		String zoneId = getZoneId("prompto.org.");
		assertEquals("Z2NKEUI11Q0ZVF", zoneId);
		ListHostedZonesResponse zones = route53.listHostedZones();
		assertEquals(2, zones.hostedZones().size());
	}

	private String getZoneId(String zoneName) {
		ListHostedZonesResponse zones = route53.listHostedZones();
		HostedZone zone = zones.hostedZones().stream()
				.filter(z->z.name().equals(zoneName))
				.findFirst()
				.orElse(null);
		return zone.id();
	}

	@Test
	public void canCreateReadAndDropARecord() {
		String zoneId = getZoneId("prompto.org.");
		ChangeResourceRecordSetsRequest request = ChangeResourceRecordSetsRequest.builder()
		.hostedZoneId(zoneId)
		.changeBatch(ChangeBatch.builder()
				.changes(Collections.singletonList(Change.builder()
					.action(ChangeAction.CREATE)
					.resourceRecordSet(ResourceRecordSet.builder()
						.name("test-user.prompto.org")
						.type(RRType.A)
						.ttl(300L)
						.resourceRecords(ResourceRecord.builder().value("222.222.222.222").build())
						.build()
					).build()))
				.build())
			.build();
		ChangeResourceRecordSetsResponse result = route53.changeResourceRecordSets(request);
		ChangeStatus status = result.changeInfo().status();
		assertTrue(status==ChangeStatus.PENDING || status==ChangeStatus.INSYNC);
		ListResourceRecordSetsRequest listRequest = ListResourceRecordSetsRequest.builder()
			.hostedZoneId(zoneId)
			.startRecordType(RRType.A)
			.startRecordName("test-user.prompto.org.")
			.build();
		ListResourceRecordSetsResponse listResult = route53.listResourceRecordSets(listRequest);
		List<ResourceRecordSet> recordSets = listResult.resourceRecordSets();
		// list will contain all records from "test-user.prompto.org.", not just that one
		ResourceRecordSet recordSet = recordSets.get(0);
		assertEquals("222.222.222.222", recordSet.resourceRecords().get(0).value());
		request = ChangeResourceRecordSetsRequest.builder()
			.hostedZoneId(zoneId)
			.changeBatch(ChangeBatch.builder()
				.changes(Collections.singletonList(Change.builder()
					.action(ChangeAction.DELETE)
					.resourceRecordSet(ResourceRecordSet.builder()
						.name("test-user.prompto.org")
						.type(RRType.A)
						.ttl(300L)
						.resourceRecords(ResourceRecord.builder().value("222.222.222.222").build())
						.build()
					).build())
				).build())
			.build();
		result = route53.changeResourceRecordSets(request);
		status = result.changeInfo().status();
		assertTrue(status==ChangeStatus.PENDING || status==ChangeStatus.INSYNC);
	}
	
}
