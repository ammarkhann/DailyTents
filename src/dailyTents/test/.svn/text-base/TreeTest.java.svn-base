package dailyTents.test;
import static org.junit.Assert.*;

import org.junit.Test;

import defaultpackage.Tent;
import defaultpackage.Tree;

public class TreeTest {
	public static Tree tree;
	public static Tent tent;

	@Test
	public void testTreeObject() {
		tree = new Tree();
		assertNotNull(null);
	}

	@Test
	public void testTreeOnBoard() throws Exception {
		tree = new Tree();
		assertTrue(tree.isOnBoard());
	}

	@Test
	public void testNumberOfTrees() {
		tree = new Tree();
		tent = new Tent();
		assertEquals(tent.getTents(), tree.getTrees());
	}
	
	@Test
	public void testIsTreeAdjancent(){
		tree = new Tree();
		assertTrue(tree.isAdjacent());
	}

}
