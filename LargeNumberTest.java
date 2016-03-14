
public class LargeNumberTest {

	public static void main(String[] args) {
		
		UnboundedInt testInt1 = new UnboundedInt(10);
		UnboundedInt testInt2 = new UnboundedInt(1,000);
		
		UnboundedInt testIntSum = testInt1.multiply(testInt2);
		
		System.out.println(testIntSum.toString());
		System.out.println("1,000,000,000,000,999,999,999");
		
		
		


	}

}
