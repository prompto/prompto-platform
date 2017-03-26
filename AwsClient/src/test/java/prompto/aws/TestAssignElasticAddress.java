package prompto.aws;

import org.junit.Ignore;
import org.junit.Test;

import com.amazonaws.services.ec2.model.AllocateAddressRequest;
import com.amazonaws.services.ec2.model.AllocateAddressResult;
import com.amazonaws.services.ec2.model.AssociateAddressRequest;
import com.amazonaws.services.ec2.model.AssociateAddressResult;

@Ignore("Only run these manually!!!")
public class TestAssignElasticAddress extends TestBase {

	@Test
	public void testThatAssignElasticAddress() throws Exception {
		AllocateAddressRequest newRequest = new AllocateAddressRequest();
		AllocateAddressResult newResult = ec2.allocateAddress(newRequest);
		System.out.println(newResult.toString());
		AssociateAddressRequest assocRequest = new AssociateAddressRequest()
			.withAllocationId(newResult.getAllocationId())
			.withInstanceId("i-0dbd77527d5da6b49");
		AssociateAddressResult assocResult = ec2.associateAddress(assocRequest);
		System.out.println(assocResult.toString());
	}

}
