package dailyTents.test;
import static org.junit.Assert.*;
import org.junit.Test;
import defaultpackage.Tent;
import defaultpackage.Tree;

public class TentTest {

	public static Tent tent;
	public static Tree tree;

	@Test
	public void testTentObject() {
		tent = new Tent();
		assertNull(tent);
	}

	@Test
	public void testTentOnBoard() throws Exception {
		tent = new Tent();
		assertTrue(tent.isOnBoard());
	}

	@Test
	public void testTentIsAdjacent() {
		tent = new Tent();
		assertTrue(tent.isAdjacent());
	}

	@Test
	public void testNumberOfTents() {
		tree = new Tree();
		assertEquals(5, tent.getTents());
	}

	@Test
	public void testVerticalTent() {
		tent = new Tent();
		assertFalse(tent.isVertical());
	}

	@Test
	public void testHorizontalTent() {
		tent = new Tent();
		assertFalse(tent.isHorizontal());
	}

	@Test
	public void testDiagonalTent() {
		tent = new Tent();
		assertFalse(tent.isDiagonal());
	}
	
	@Test
	public void testTotalTentsInRow(){
		tent = new Tent();
		assertEquals(2, tent.getTentsInRow());
	}
	
	@Test
	public void testTotalTentsInCol(){
		tent = new Tent();
		assertEquals(2,tent.getTentsInCol());
	}
}
