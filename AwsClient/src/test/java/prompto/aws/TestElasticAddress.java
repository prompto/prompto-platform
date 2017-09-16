package prompto.aws;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.amazonaws.services.ec2.model.AllocateAddressRequest;
import com.amazonaws.services.ec2.model.AllocateAddressResult;
import com.amazonaws.services.ec2.model.AssociateAddressRequest;
import com.amazonaws.services.ec2.model.AssociateAddressResult;
import com.amazonaws.services.ec2.model.DescribeAddressesRequest;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import com.amazonaws.services.ec2.model.DisassociateAddressRequest;
import com.amazonaws.services.ec2.model.DisassociateAddressResult;
import com.amazonaws.services.ec2.model.ReleaseAddressRequest;
import com.amazonaws.services.ec2.model.ReleaseAddressResult;

@Category(AwsTest.class)
public class TestElasticAddress extends AWSTestBase {

	@Test
	public void testDescribeAddresses() throws Exception {
		DescribeAddressesRequest req = new DescribeAddressesRequest()
			.withPublicIps("35.166.112.187");
		DescribeAddressesResult res = ec2.describeAddresses(req);
		System.out.println(res.toString());
	}
	
	@Test
	public void testCreateAssignAndDropElasticAddress() throws Exception {
		AllocateAddressRequest newRequest = new AllocateAddressRequest();
		AllocateAddressResult newResult = ec2.allocateAddress(newRequest);
		System.out.println(newResult.toString());
		AssociateAddressRequest assocRequest = new AssociateAddressRequest()
			.withAllocationId(newResult.getAllocationId())
			.withInstanceId("i-0dbd77527d5da6b49");
		AssociateAddressResult assocResult = ec2.associateAddress(assocRequest);
		System.out.println(assocResult.toString());
		DisassociateAddressRequest cancelRequest = new DisassociateAddressRequest()
			.withAssociationId(assocResult.getAssociationId());
		DisassociateAddressResult cancelResult = ec2.disassociateAddress(cancelRequest);
		System.out.println(cancelResult.toString());
		ReleaseAddressRequest dropRequest = new ReleaseAddressRequest()
			.withAllocationId(newResult.getAllocationId());
		ReleaseAddressResult dropResult = ec2.releaseAddress(dropRequest);
		System.out.println(dropResult.toString());
	}
	
	@Test
	public void testAssociateElasticAddress() throws Exception {
		AssociateAddressRequest assocRequest = new AssociateAddressRequest()
		.withAllocationId("eipalloc-dc98c1bb")
		.withInstanceId("i-0acb2373bb25ce373");
		AssociateAddressResult assocResult = ec2.associateAddress(assocRequest);
		System.out.println(assocResult.toString());
	}

}
