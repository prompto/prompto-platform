package prompto.aws;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import software.amazon.awssdk.services.ec2.model.AllocateAddressRequest;
import software.amazon.awssdk.services.ec2.model.AllocateAddressResponse;
import software.amazon.awssdk.services.ec2.model.AssociateAddressRequest;
import software.amazon.awssdk.services.ec2.model.AssociateAddressResponse;
import software.amazon.awssdk.services.ec2.model.DescribeAddressesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeAddressesResponse;
import software.amazon.awssdk.services.ec2.model.DisassociateAddressRequest;
import software.amazon.awssdk.services.ec2.model.DisassociateAddressResponse;
import software.amazon.awssdk.services.ec2.model.ReleaseAddressRequest;
import software.amazon.awssdk.services.ec2.model.ReleaseAddressResponse;

@Category(AwsTest.class)
public class TestElasticIP extends AWSTestBase {

	@Test
	public void describeAddressesReturnsData() throws Exception {
		DescribeAddressesRequest req = DescribeAddressesRequest.builder()
			.publicIps("34.234.147.231")
			.build();
		DescribeAddressesResponse res = ec2.describeAddresses(req);
		System.out.println(res.toString());
	}
	
	@Test
	public void canCreateAssignAndDropElasticAddress() throws Exception {
		AllocateAddressRequest newRequest = AllocateAddressRequest.builder().build();
		AllocateAddressResponse newResult = ec2.allocateAddress(newRequest);
		System.out.println(newResult.toString());
		AssociateAddressRequest assocRequest = AssociateAddressRequest.builder()
			.allocationId(newResult.allocationId())
			.instanceId("i-0dbd77527d5da6b49")
			.build();
		AssociateAddressResponse assocResult = ec2.associateAddress(assocRequest);
		System.out.println(assocResult.toString());
		DisassociateAddressRequest cancelRequest = DisassociateAddressRequest.builder()
			.associationId(assocResult.associationId())
			.build();
		DisassociateAddressResponse cancelResult = ec2.disassociateAddress(cancelRequest);
		System.out.println(cancelResult.toString());
		ReleaseAddressRequest dropRequest = ReleaseAddressRequest.builder()
			.allocationId(newResult.allocationId())
			.build();
		ReleaseAddressResponse dropResult = ec2.releaseAddress(dropRequest);
		System.out.println(dropResult.toString());
	}
	
	@Test
	public void canAssociateElasticAddress() throws Exception {
		AssociateAddressRequest assocRequest = AssociateAddressRequest.builder()
			.allocationId("eipalloc-dc98c1bb")
			.instanceId("i-0acb2373bb25ce373")
			.build();
		AssociateAddressResponse assocResult = ec2.associateAddress(assocRequest);
		System.out.println(assocResult.toString());
	}

}
