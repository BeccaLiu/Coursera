/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hotpad.TrinaryTree;

/**
 * @author rliu
 */
public class TrinaryTree {

    public static Node root;

//    public static void main(String[] args) throws Exception {
//        TrinaryTree t = new TrinaryTree();
//        int[] input = {5, 4, 9, 5, 7, 2, 2, 6, 10};
//        for (int i : input) {
//            t.insert(i);
//        }
//        display(root);
//        System.out.println("");
//        t.delete(9);
//        display(root);
//    }

    public TrinaryTree() {
        root = null;
    }

    public void delete(int deleteVal) throws Exception {
        Node parent = new Node(root.val);
        Node curr = root;
        parent.middle = curr;
        boolean isLeft = false;
        boolean isRight = false;

        while (curr.val != deleteVal) {
            parent = curr;
            if (deleteVal < curr.val) {
                isLeft = true;
                curr = curr.left;
            } else if (deleteVal > curr.val) {
                isRight = true;
                curr = curr.right;
            }
            if (curr == null) {
                throw new Exception("the node you want to delete is not exist");
            }
        }

        //the delete node has no children except middle
        if (curr.left == null && curr.right == null) {
            if (curr == root) {
                root = root.middle;
            } else if (isLeft) {
                parent.left = curr.middle;
            } else if (isRight) {
                parent.right = curr.middle;
            }
        } //if node nas only one child except middle
        else if (curr.right == null) {
            if (curr == root) {
                if (curr.middle != null) {
                    curr.middle.left = curr.left;
                    root = curr.middle;
                } else {
                    root = curr.left;
                }
            } else if (isLeft) {
                if (curr.middle != null) {
                    curr.middle.left = curr.left;
                    parent.left = curr.middle;
                } else {
                    parent.left = curr.left;
                }

            } else if (isRight) {
                if (curr.middle != null) {
                    curr.middle.left = curr.left;
                    parent.right = curr.middle;
                } else {
                    parent.right = curr.left;

                }
            }
        } else if (curr.left == null) {
            if (curr == root) {
                if (root.middle != null) {
                    root.middle.right = curr.right;
                    root = root.middle;
                } else {
                    root = root.right;
                }
            } else if (isLeft) {
                if (curr.middle != null) {
                    curr.middle.right = curr.right;
                    parent.left = curr.middle;
                } else {
                    parent.left = curr.right;
                }
            } else if (isRight) {
                if (curr.middle != null) {
                    curr.middle.right = curr.right;
                    parent.right = curr.middle;
                } else {
                    parent.right = curr.right;
                }

            }

        } //if node has left child and right child 
        else {

            if (curr == root) {
                if (curr.middle != null) {
                    curr.middle.left = curr.left;
                    curr.middle.right = curr.right;
                    root = curr.middle;
                } else {
                    Node successor = findSuccessor(curr);
                    successor.left = curr.left;
                    root = successor;
                }
            } else if (isLeft) {
                if (curr.middle != null) {
                    curr.middle.left = curr.left;
                    curr.middle.right = curr.right;
                    parent.left = curr.middle;
                } else {
                    Node successor = findSuccessor(curr);
                    successor.left = curr.left;
                    parent.left = successor;

                }

            } else if (isRight) {
                if (curr.middle != null) {
                    curr.middle.left = curr.left;
                    curr.middle.right = curr.right;
                    parent.right = curr.middle;
                } else {
                    Node successor = findSuccessor(curr);
                    successor.left = curr.left;
                    parent.right = successor;

                }

            }
        }

    }

    public static Node findSuccessor(Node deleteNode) {
        Node successor = null;
        Node successorParent = null;
        Node curr = deleteNode.right;
        while (curr != null) {
            successorParent = successor;
            successor = curr;
            curr = curr.left;
        }
        if (successor != deleteNode.right) {
            successorParent.left = successor.right;
            successor.right = deleteNode.right;

        }

        return successor;
    }

    public void insert(int newNodeVal) {
        Node newNode = new Node(newNodeVal);
        if (root == null) {
            root = newNode;
            return;
        }
        Node curr = root;
        Node parent = null;
        while (true) {
            parent = curr;
            if (newNodeVal < curr.val) {
                curr = parent.left;
                if (curr == null) {
                    parent.left = newNode;
                    return;
                }

            } else if (newNodeVal == curr.val) {
                curr = parent.middle;
                if (curr == null) {
                    parent.middle = newNode;
                    return;
                }
            } else if (newNodeVal > curr.val) {
                curr = parent.right;
                if (curr == null) {
                    parent.right = newNode;
                    return;
                }
            }
        }
    }

    public static void display(Node root) {
        if (root != null) {
            display(root.left);
            System.out.print("/" + root.val + "\\");
            display(root.middle);
            display(root.right);
        }
    }

}
