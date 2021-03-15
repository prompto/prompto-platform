package prompto.aws;

public abstract class Utils {

	public static void unsafeSleep(long millis) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
