package prompto.aws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;

@Category(AwsTest.class)
public class TestElasticIP extends AWSTestBase {

	@Test
	public void getsAddressIdFromIpAddress() throws Exception {
		EC2 awsEc2 = new EC2(ec2);
		String addressId = awsEc2.getAddressIdForIpAddress("34.234.147.231");
		assertNotNull(addressId);
	}
	
	@Test
	public void createsAssignsAndDropsElasticIP() throws Exception {
		EC2 awsEc2 = new EC2(ec2);
		PromptoDocument<String, Object> ipAddress = awsEc2.createIpAddress();
		String addressId = (String)ipAddress.get("allocationId");
		assertNotNull(addressId);
		String publicIp = (String)ipAddress.get("publicIp");
		assertNotNull(publicIp);
		String instanceId = awsEc2.runInstance("ami-08a28a73", "t2.micro", "prompto-admin", "", new PromptoList<String>(Collections.singletonList("default"), false), null);
		assertNotNull(instanceId);
		awsEc2.waitForInstanceState(instanceId, "running", 180);
		String associationId = awsEc2.associateIPAddress(instanceId, addressId);
		assertNotNull(associationId);
		awsEc2.dissociateIPAddress(associationId);
		awsEc2.dropInstance(instanceId);
		awsEc2.dropIPAddress(addressId);
		assertNull(awsEc2.getAddressIdForIpAddress(publicIp));
		
	}
	

}
