package prompto.aws;

import java.util.Collections;
import java.util.List;

import prompto.intrinsic.PromptoDocument;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.route53.AmazonRoute53;
import com.amazonaws.services.route53.AmazonRoute53ClientBuilder;
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

public class Route53 {

	public static Route53 newInstance(String awsRegion, String login, String password) {
		AmazonRoute53ClientBuilder builder = AmazonRoute53ClientBuilder.standard()
				.withRegion(awsRegion);
		if(login!=null && password!=null) {
			AWSCredentials credentials = new BasicAWSCredentials(login, password);
			builder = builder.withCredentials(new AWSStaticCredentialsProvider(credentials));
		}
		return new Route53(builder.build());
	}
	
	AmazonRoute53 route53;

	public Route53(AmazonRoute53 route53) {
		this.route53 = route53;
	}
	
	public String createARecord(String domainName, String domainPrefix, String ipAddress, Long ttl) {
		if(!domainName.endsWith("."))
			return createARecord(domainName + ".", domainPrefix, ipAddress, ttl);
		if(!domainPrefix.endsWith("."))
			return createARecord(domainName, domainPrefix + ".", ipAddress, ttl);
		String zoneId = getZoneId(domainName);
		ChangeResourceRecordSetsRequest request = new ChangeResourceRecordSetsRequest()
			.withHostedZoneId(zoneId)
			.withChangeBatch(new ChangeBatch(Collections.singletonList(new Change()
				.withAction(ChangeAction.CREATE)
				.withResourceRecordSet(new ResourceRecordSet()
					.withName(domainPrefix + domainName)
					.withType(RRType.A)
					.withTTL(300L)
					.withResourceRecords(new ResourceRecord(ipAddress))
				))));
		ChangeResourceRecordSetsResult result = route53.changeResourceRecordSets(request);
		return result.getChangeInfo().getStatus();
	}
	
	public PromptoDocument<String, Object> readARecord(String domainName, String domainPrefix) {
		if(!domainName.endsWith("."))
			return readARecord(domainName + ".", domainPrefix);
		if(!domainPrefix.endsWith("."))
			return readARecord(domainName, domainPrefix + ".");
		String zoneId = getZoneId(domainName);
		ListResourceRecordSetsRequest listRequest = new ListResourceRecordSetsRequest()
			.withHostedZoneId(zoneId)
			.withStartRecordType(RRType.A)
			.withStartRecordName(domainPrefix + domainName);
		ListResourceRecordSetsResult listResult = route53.listResourceRecordSets(listRequest);
		List<ResourceRecordSet> recordSets = listResult.getResourceRecordSets();
		if(recordSets.isEmpty())
			return null;
		ResourceRecordSet recordSet = recordSets.stream()
				.filter(rs->rs.getName().startsWith(domainPrefix))
				.findFirst()
				.orElse(null);
		if(recordSet==null)
			return null;
		PromptoDocument<String, Object> result = new PromptoDocument<>();
		result.put("name", recordSet.getName());
		result.put("ttl", recordSet.getTTL());
		result.put("ipAddress", recordSet.getResourceRecords().get(0).getValue());
		return result;
	}
	
	public String deleteARecord(String domainName, String domainPrefix) {
		if(!domainName.endsWith("."))
			return deleteARecord(domainName + ".", domainPrefix);
		if(!domainPrefix.endsWith("."))
			return deleteARecord(domainName, domainPrefix + ".");
		String zoneId = getZoneId(domainName);
		ChangeResourceRecordSetsRequest request = new ChangeResourceRecordSetsRequest()
			.withHostedZoneId(zoneId)
			.withChangeBatch(new ChangeBatch(Collections.singletonList(new Change()
				.withAction(ChangeAction.DELETE)
				.withResourceRecordSet(new ResourceRecordSet()
					.withName("test-user.prompto.org")
					.withType(RRType.A)
					.withTTL(300L)
					.withResourceRecords(new ResourceRecord("222.222.222.222"))
				))));
		ChangeResourceRecordSetsResult result = route53.changeResourceRecordSets(request);
		return result.getChangeInfo().getStatus();
	}

	private String getZoneId(String domainName) {
		if(!domainName.endsWith("."))
			return getZoneId(domainName + ".");
		ListHostedZonesResult zones = route53.listHostedZones();
		HostedZone zone = zones.getHostedZones().stream()
				.filter(z->z.getName().equals(domainName))
				.findFirst()
				.orElse(null);
		return zone==null ? null : zone.getId();
	}

}
