package prompto.aws;

import java.util.Collections;
import java.util.List;

import prompto.intrinsic.PromptoDocument;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.route53.Route53ClientBuilder;
import software.amazon.awssdk.services.route53.model.Change;
import software.amazon.awssdk.services.route53.model.ChangeAction;
import software.amazon.awssdk.services.route53.model.ChangeBatch;
import software.amazon.awssdk.services.route53.model.ChangeResourceRecordSetsRequest;
import software.amazon.awssdk.services.route53.model.ChangeResourceRecordSetsResponse;
import software.amazon.awssdk.services.route53.model.HostedZone;
import software.amazon.awssdk.services.route53.model.ListHostedZonesResponse;
import software.amazon.awssdk.services.route53.model.ListResourceRecordSetsRequest;
import software.amazon.awssdk.services.route53.model.ListResourceRecordSetsResponse;
import software.amazon.awssdk.services.route53.model.RRType;
import software.amazon.awssdk.services.route53.model.ResourceRecord;
import software.amazon.awssdk.services.route53.model.ResourceRecordSet;

public class Route53 {

	public static Route53 newInstance(String login, String password) {
		Route53ClientBuilder builder = Route53Client.builder()
				.region(Region.AWS_GLOBAL);
		if(login!=null && password!=null) {
			AwsCredentials credentials = AwsBasicCredentials.create(login, password);
			builder = builder.credentialsProvider(StaticCredentialsProvider.create(credentials));
		}
		return new Route53(builder.build());
	}
	
	Route53Client route53;

	public Route53(Route53Client route53) {
		this.route53 = route53;
	}
	
	public String createARecord(String domainName, String domainPrefix, String ipAddress, Long ttl) {
		if(!domainName.endsWith("."))
			return createARecord(domainName + ".", domainPrefix, ipAddress, ttl);
		if(!domainPrefix.endsWith("."))
			return createARecord(domainName, domainPrefix + ".", ipAddress, ttl);
		String zoneId = getZoneId(domainName);
		ChangeResourceRecordSetsRequest request = ChangeResourceRecordSetsRequest.builder()
			.hostedZoneId(zoneId)
			.changeBatch(ChangeBatch.builder()
					.changes(Collections.singletonList(Change.builder()
							.action(ChangeAction.CREATE)
							.resourceRecordSet(ResourceRecordSet.builder()
									.name(domainPrefix + domainName)
									.type(RRType.A)
									.ttl(300L)
									.resourceRecords(ResourceRecord.builder().value(ipAddress).build())
									.build())
							.build()))
					.build())
			.build();
		ChangeResourceRecordSetsResponse result = route53.changeResourceRecordSets(request);
		return result.changeInfo().status().name();
	}
	
	public PromptoDocument<String, Object> readARecord(String domainName, String domainPrefix) {
		if(!domainName.endsWith("."))
			return readARecord(domainName + ".", domainPrefix);
		if(!domainPrefix.endsWith("."))
			return readARecord(domainName, domainPrefix + ".");
		String zoneId = getZoneId(domainName);
		ListResourceRecordSetsRequest listRequest = ListResourceRecordSetsRequest.builder()
			.hostedZoneId(zoneId)
			.startRecordType(RRType.A)
			.startRecordName(domainPrefix + domainName)
			.build();
		ListResourceRecordSetsResponse listResult = route53.listResourceRecordSets(listRequest);
		List<ResourceRecordSet> recordSets = listResult.resourceRecordSets();
		if(recordSets.isEmpty())
			return null;
		ResourceRecordSet recordSet = recordSets.stream()
				.filter(rs->rs.name().startsWith(domainPrefix))
				.findFirst()
				.orElse(null);
		if(recordSet==null)
			return null;
		PromptoDocument<String, Object> result = new PromptoDocument<>();
		result.put("name", recordSet.name());
		result.put("ttl", recordSet.ttl());
		result.put("ipAddress", recordSet.resourceRecords().get(0).value());
		return result;
	}
	
	public String deleteARecord(String domainName, String domainPrefix) {
		if(!domainName.endsWith("."))
			return deleteARecord(domainName + ".", domainPrefix);
		if(!domainPrefix.endsWith("."))
			return deleteARecord(domainName, domainPrefix + ".");
		String zoneId = getZoneId(domainName);
		ChangeResourceRecordSetsRequest request = ChangeResourceRecordSetsRequest.builder()
			.hostedZoneId(zoneId)
			.changeBatch(ChangeBatch.builder()
				.changes( Collections.singletonList( Change.builder()
					.action(ChangeAction.DELETE)
					.resourceRecordSet( ResourceRecordSet.builder()
						.name("test-user.prompto.org")
						.type(RRType.A)
						.ttl(300L)
						.resourceRecords(ResourceRecord.builder().value("222.222.222.222").build())
						.build()
					).build()
				)).build())
			.build();
		ChangeResourceRecordSetsResponse result = route53.changeResourceRecordSets(request);
		return result.changeInfo().status().name();
	}

	private String getZoneId(String domainName) {
		if(!domainName.endsWith("."))
			return getZoneId(domainName + ".");
		ListHostedZonesResponse zones = route53.listHostedZones();
		HostedZone zone = zones.hostedZones().stream()
				.filter(z->z.name().equals(domainName))
				.findFirst()
				.orElse(null);
		return zone==null ? null : zone.id();
	}

}
