package Test;

import model.AVLNode;
import model.AVLTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ui.Int;

public class TestAvlTree {
    private AVLTree avlTree;

    @Before
    public void setUp() {
        this.avlTree = new AVLTree();
    }

    @Test
    public void testInsertNode() {
        Int node1 = new Int(9);
        this.avlTree.insert(node1);
        assert !(node1.nodeBalanceFactor() >= 2) || !(node1.nodeBalanceFactor() <= -2);
        assert this.avlTree.getNumberOfNodes(this.avlTree.getRoot()) == 1;

        Int node2 = new Int(5);
        this.avlTree.insert(node2);
        assert !(node2.nodeBalanceFactor() >= 2) || !(node2.nodeBalanceFactor() <= -2);
        assert this.avlTree.getNumberOfNodes(this.avlTree.getRoot()) == 2;

        Int node3 = new Int(3);
        this.avlTree.insert(node3);
        assert !(node3.nodeBalanceFactor() >= 2) || !(node3.nodeBalanceFactor() <= -2);
        assert this.avlTree.getNumberOfNodes(this.avlTree.getRoot()) == 3;
    }

    @Test
    public void testDelete() {
        boolean insertState, deleteState;

        for (int i = 0; i < 100; i++) {
            Int nodeToInsert = new Int(i);
            insertState = avlTree.insert(nodeToInsert);
            assert insertState == true;
        }

        Assert.assertTrue(this.avlTree.delete(new Int(99)));
        Assert.assertTrue(this.avlTree.delete(new Int(3)));
        Assert.assertTrue(this.avlTree.delete(new Int(37)));
        Assert.assertTrue(this.avlTree.delete(new Int(42)));
        Assert.assertTrue(this.avlTree.delete(new Int(17)));

        assert this.avlTree.getNumberOfNodes(this.avlTree.getRoot()) == 95;
    }

    @Test
    public void testSearchFound() {
        Int node1 = new Int(1);
        this.avlTree.insert(node1);
        AVLNode foundNode = this.avlTree.search(node1);
        Assert.assertNotNull(foundNode);

        Int node2 = new Int(97);
        this.avlTree.insert(node2);
        foundNode = this.avlTree.search(node2);
        Assert.assertNotNull(foundNode);

        Int node3 = new Int(54);
        this.avlTree.insert(node3);
        foundNode = this.avlTree.search(node3);
        Assert.assertNotNull(foundNode);
    }

}
