package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import launcher.STAProccess;

public class LaunchSTATest {
	private STAProccess process;
	@Before
	public void setUp() throws Exception {
		process= new STAProccess();
		process.waitForLaunch();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Launch ended");
		process.destroy();
		
	}

	@Test
	public void test() throws InterruptedException {
		System.out.println("Launch Complete");
		Thread.sleep(10000);
	}

}
