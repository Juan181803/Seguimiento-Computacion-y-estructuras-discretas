package model;

public abstract class AVLNode<T> {
    private AVLNode left, right, parent; 
    private int leftHeight, rightHeight; 

    public AVLNode() {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.leftHeight = 0;
        this.rightHeight = 0;
    }
    

    public int nodeBalanceFactor() {
        return (this.rightHeight - this.leftHeight);
    }
    

    public int nodeHeight() {
        return (this.rightHeight > this.leftHeight) ? this.rightHeight : this.leftHeight;
    }
  
    public void setNodeHeights() {
        if (this.right == null) {
            this.rightHeight = 0;
        } else {
            this.rightHeight = this.right.nodeHeight() + 1;
        }
        
        if (this.left == null) {
            this.leftHeight = 0;
        } else {
            this.leftHeight = this.left.nodeHeight() + 1;
        }
    }
    
  
    public abstract int compareTo(T o);
    
    @Override
    public abstract String toString();
 
    public abstract int getValue();

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public AVLNode getParent() {
        return parent;
    }

    public void setParent(AVLNode parent) {
        this.parent = parent;
    }

    public int getLeftHeight() {
        return leftHeight;
    }

    public void setLeftHeight(int leftHeight) {
        this.leftHeight = leftHeight;
    }

    public int getRightHeight() {
        return rightHeight;
    }

    public void setRightHeight(int rightHeight) {
        this.rightHeight = rightHeight;
    }

}
