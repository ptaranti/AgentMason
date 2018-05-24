package agentMason.simulationClock;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatisticCollectorTest {

	@Test
	public void testGetInstance() {
		
		//the class is correct
		StatisticCollector stc1 = StatisticCollector.getInstance(); 
		assertTrue(StatisticCollector.getInstance().getClass() == StatisticCollector.class);
		
		//the object is singleton
		StatisticCollector stc2 = StatisticCollector.getInstance(); 
		assertEquals(stc1, stc2);
	}

	@Test
	public void testSendTardiness() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertDataLongDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertDataDoubleBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testRecoverData() {
		fail("Not yet implemented");
	}

	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

}
