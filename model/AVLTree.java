package model;

public class AVLTree {
    private AVLNode root;
    

    public AVLTree() {
        this.root = null;
    }

    public boolean insert(AVLNode node) {
        if (this.root == null) {
            this.root = node;
            return true;
        } else {
            AVLNode activeNode = this.root;
            
            while (true) {
                if (node.compareTo(activeNode) == 0) {
                    return false;
                } else if (node.compareTo(activeNode) >= 1) {
                    if (activeNode.getRight() == null) {
                        activeNode.setRight(node);
                        node.setParent(activeNode);
                        this.resetHeightDetectRotation(node);
                        return true;
                    } else {
                        activeNode = activeNode.getRight();
                    }
                } else {
                    if (activeNode.getLeft() == null) {
                        activeNode.setLeft(node);
                        node.setParent(activeNode);
                        this.resetHeightDetectRotation(node);
                        return true;
                    } else {
                        activeNode = activeNode.getLeft();
                    }
                }
            }
        }
    }
    
    public boolean delete(AVLNode node) {
        AVLNode nodeToRemove = this.search(node); 
        AVLNode nodeToRemoveParent;
        
        if (nodeToRemove != null) {
            if (nodeToRemove.getLeft() == null && nodeToRemove.getRight() == null) {
                if (nodeToRemove != this.root) {
                    nodeToRemoveParent = nodeToRemove.getParent();
                    if (nodeToRemoveParent.getLeft() == nodeToRemove) {
                        nodeToRemoveParent.setLeft(null);
                    } else {
                        nodeToRemoveParent.setRight(null);
                    }
                    nodeToRemove.setParent(null);
                    this.resetHeightDetectRotation(nodeToRemoveParent);
                } else {
                    this.root = null;
                }
            } else if(nodeToRemove.getLeftHeight() == 1 && nodeToRemove.getRightHeight() == 0) {
                AVLNode nodeToRemoveLeftChild = nodeToRemove.getLeft();
                nodeToRemoveParent = nodeToRemove.getParent();
                if (nodeToRemove != this.root) {
                    if (nodeToRemoveParent.getLeft() == nodeToRemove) {
                        nodeToRemoveParent.setLeft(nodeToRemoveLeftChild);
                    } else {
                        nodeToRemoveParent.setRight(nodeToRemoveLeftChild);
                    }
                    nodeToRemoveLeftChild.setParent(nodeToRemoveParent);
                    this.resetHeightDetectRotation(nodeToRemove.getLeft());
                } else {
                    nodeToRemoveLeftChild.setParent(nodeToRemoveParent);
                    this.root = nodeToRemove.getLeft();
                }
                nodeToRemove.setLeft(null); 
                nodeToRemove.setParent(null);
            } else if (nodeToRemove.getLeftHeight() == 0 && nodeToRemove.getRightHeight() == 1) {
                AVLNode nodeToRemoveRightChild = nodeToRemove.getRight();
                nodeToRemoveParent = nodeToRemove.getParent();
                if (nodeToRemove != this.root) {
                    if (nodeToRemoveParent.getLeft() == nodeToRemove) {
                        nodeToRemoveParent.setLeft(nodeToRemoveRightChild);
                    } else {
                        nodeToRemoveParent.setRight(nodeToRemoveRightChild);
                    }
                    nodeToRemoveRightChild.setParent(nodeToRemoveParent);
                    this.resetHeightDetectRotation(nodeToRemove.getRight());
                } else {
                    nodeToRemoveRightChild.setParent(nodeToRemoveParent);
                    this.root = nodeToRemoveRightChild;
                }
                nodeToRemove.setRight(null); 
                nodeToRemove.setParent(null); 
            } else {
                nodeToRemoveParent = nodeToRemove.getParent();
                AVLNode substitute = this.getSubstitute(nodeToRemove);
                AVLNode tempNode;

                if (nodeToRemove == substitute.getParent()) {
                    tempNode = substitute;
                } else {
                    tempNode = substitute.getParent();
                }
                if (substitute.getParent().getLeft() == substitute) {
                    if (substitute.getRight() != null) {
                        substitute.getParent().setLeft(substitute.getRight()); 
                        substitute.getRight().setParent(substitute.getParent());
                    } else {
                        substitute.getParent().setLeft(null);
                    }
                    substitute.setParent(null);
                } else {
                    if (substitute.getLeft() != null) {
                        substitute.getParent().setRight(substitute.getLeft());
                        substitute.getLeft().setParent(substitute.getParent());
                    } else {
                        substitute.getParent().setRight(null);
                    }
                    substitute.setParent(null);
                }
            
                if (nodeToRemoveParent != null) {
                    substitute.setParent(nodeToRemoveParent); 
                    if (nodeToRemoveParent.getLeft() == nodeToRemove) {
                        nodeToRemoveParent.setLeft(substitute);
                    } else {
                        nodeToRemoveParent.setRight(substitute);
                    }
                } else {
                    this.root = substitute;
                }

                if (nodeToRemove.getLeft() != null) {
                    substitute.setLeft(nodeToRemove.getLeft());
                    nodeToRemove.getLeft().setParent(substitute);
                }
                if (nodeToRemove.getRight() != null) {
                    substitute.setRight(nodeToRemove.getRight());
                    nodeToRemove.getRight().setParent(substitute);
                }
                this.resetHeightDetectRotation(tempNode);
 
                nodeToRemove.setParent(null);
                nodeToRemove.setLeft(null);
                nodeToRemove.setRight(null);
            }
        } else {
            return false;
        }
        return true;
    }
    
    public AVLNode search(AVLNode node) {
        AVLNode activeNode = this.root;
        
        if (this.root == null) {
            return null;
        }
        
        while (activeNode != null) {
            if (node.compareTo(activeNode) == 0) {
                return activeNode;
            } else if (activeNode.compareTo(node) <= -1) {
                activeNode = activeNode.getRight();
            } else {
                activeNode = activeNode.getLeft();
            }
        }
        return null;
    }
 
    private void detectRotation(AVLNode node) {

        if (node.nodeBalanceFactor() >= 2) {
            if (node.getRight().getLeftHeight() > node.getRight().getRightHeight()) {
                this.rightLeftRotation(node);
            } else {
                this.leftRotation(node);
            }
        }
        if (node.nodeBalanceFactor() <= -2) {
            if (node.getLeft().getRightHeight() > node.getLeft().getLeftHeight()) {
                this.leftRightRotation(node);
            } else {
                this.rightRotation(node);
            }
        }
    }

    private void resetHeightDetectRotation(AVLNode node) {
        while (node != null) {
            node.setNodeHeights();
            this.detectRotation(node);
            node = node.getParent();
        }
    }

    private void leftRotation(AVLNode node) {
        AVLNode rightNode = node.getRight(); 
        AVLNode parentNode = node.getParent();

        if (parentNode == null) {
            rightNode.setParent(null);
            this.root = rightNode;
        } else if (parentNode.getRight() == node) {   
            parentNode.setRight(rightNode);
        } else {                          
            parentNode.setLeft(rightNode);
        }
        rightNode.setParent(node.getParent());
        node.setParent(rightNode);
        
        if (rightNode.getLeft() == null) {
            node.setRight(null); 
            rightNode.setLeft(node);
        } else {
            rightNode.getLeft().setParent(node);
            node.setRight(rightNode.getLeft());
            rightNode.setLeft(node);
        }
        node.setNodeHeights();
        node.getParent().setNodeHeights();
    }
    
    private void rightRotation(AVLNode node) 
    {
        AVLNode leftNode = node.getLeft(); 
        AVLNode parentNode = node.getParent(); 
        if (parentNode == null) {
            leftNode.setParent(null);
            this.root = leftNode;
        } else if (parentNode.getLeft() == node) {   
            parentNode.setLeft(leftNode);
        } else {                          
            parentNode.setRight(leftNode);
        }
        leftNode.setParent(node.getParent());
        node.setParent(leftNode);
        

        if (leftNode.getRight() == null) {
            node.setLeft(null);
            leftNode.setRight(node);
        } else {
            leftNode.getRight().setParent(node);
            node.setLeft(leftNode.getRight());
            leftNode.setRight(node);
        }
        node.setNodeHeights();
        node.getParent().setNodeHeights();
    }
    
    private void leftRightRotation(AVLNode node) 
    {
        this.leftRotation(node.getLeft());
        this.rightRotation(node);
    }
 
    private void rightLeftRotation(AVLNode node) 
    {
        this.rightRotation(node.getRight());
        this.leftRotation(node);
    }
 
    private AVLNode getSubstitute(AVLNode node) {
        AVLNode tempNode;
        if (node.getRight() == null) {
            tempNode = node.getLeft();
            while (true) {
                if (tempNode.getRight() != null) {
                    tempNode = tempNode.getRight();
                } else {
                    break;
                }
            }
            return tempNode;
        } else {
            tempNode = node.getRight();
            while (true) {
                if (tempNode.getLeft() != null) {
                    tempNode = tempNode.getLeft();
                } else {
                    break;
                }
            }
            return tempNode;
        }
    }

    public int getNumberOfNodes(AVLNode root) {
        if(root == null) {
            return 0;
        } else {
            return 1 + this.getNumberOfNodes(root.getLeft()) + this.getNumberOfNodes(root.getRight());
        }
    }
   
    public AVLNode getRoot() {
        return this.root;
    }

 }