import java.util.LinkedList;
import java.util.Queue;

public class Binary_Search_Tree {
    public static void main(String[] args) {
        // char[] id = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
        //         'r', 's', 't' };
        //int[] arr = new int[] { 40, 20, 100, 10, 30, 60, 120, 15, 25, 50, 80, 110, 130, 70, 90 };
        int[] arr = new int[] { 15, 6, 18, 3, 7, 17, 20, 2, 4, 13, 19 };
        //BinarySearchTree bst = new BinarySearchTree(arr, id);
        BinarySearchTree bst = new BinarySearchTree(arr);

        bst.printTree();
        bst.successor(13);
    }
}

class BinarySearchTree {

    class Node {
        char id;
        int key;
        Node left;
        Node right;
        Node p; // parent

        public Node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.p = null;
        }

        public Node(int key, char id) {
            this.id = id;
            this.key = key;
            this.left = null;
            this.right = null;
            this.p = null;
        }
    }

    private int treeHeight, numOfNodes;
    Node root;

    public BinarySearchTree() {

    }

    public BinarySearchTree(int[] arr) {
        numOfNodes = arr.length;
        updateTreeHeight();

        for (int i = 0; i < arr.length; i++) {
            insert(new Node(arr[i]));
        }
    }

    public BinarySearchTree(int[] arr, char[] id) {
        numOfNodes = arr.length;
        updateTreeHeight();

        for (int i = 0; i < arr.length; i++) {
            insert(new Node(arr[i], id[i]));
        }
    }

    private void updateTreeHeight() {
        treeHeight = (int) Math.ceil(Math.log(numOfNodes + 1) / Math.log(2));
    }

    public void insert(Node z) {
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == null) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        numOfNodes++;
        updateTreeHeight();
    }

    public void delete(Node z) {
        if (z.left == null) {
            transplant(z, z.right);
        } else if (z.right == null) {
            transplant(z, z.left);
        } else {
            Node y = minimum(z.right);
            if (y.p != z) {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
        }
        numOfNodes--;
        updateTreeHeight();
    }

    public void transplant(Node u, Node v) {
        if (u.p == null) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }

        if (v != null) {
            v.p = u.p;
        }
    }

    public void preOrder(Node x) {
        if (x != null) {
            System.out.print(" " + x.key);
            preOrder(x.left);
            preOrder(x.right);
        }
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node x) {
        if (x != null) {
            inOrder(x.left);
            System.out.print(" " + x.key);
            inOrder(x.right);
        }
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node x) {
        if (x != null) {
            preOrder(x.left);
            preOrder(x.right);
            System.out.print(" " + x.key);
        }
    }

    public Node search(Node x, int key) {
        if (x == null || key == x.key) {
            return x;
        }
        if (key < x.key) {
            return search(x.left, key);
        } else {
            return search(x.right, key);
        }
    }

    public Node iterativeSearch(Node x, int key) {
        while (x != null && key != x.key) {
            if (key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    public void minimum() {
        System.out.println(minimum(root));
    }

    private Node minimum(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    public void maximum() {
        System.out.println(maximum(root));
    }

    private Node maximum(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    public void successor(char id) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        Node node = queue.poll();

        // search for the node with the id
        while (node != null && node.id != id) {
            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
            node = queue.poll();
        }

        System.out.println("Successor --->");
        printNode(successor(node).id);
    }

    public void successor(int key) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        Node node = queue.poll();

        // search for the node with the id
        while (node != null && node.key != key) {
            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
            node = queue.poll();
        }

        System.out.println("Successor --->");
        //printNode(successor(node).id);
        System.out.println(successor(node).key);
    }

    private Node successor(Node x) {
        if (x.right != null) {
            return minimum(x.right);
        }

        Node y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        // return smallest node of x's right subtree
        return y;
    }

    public void printTraversals() {
        System.out.print("Preorder:");
        preOrder(root);
        System.out.print("\nInorder:");
        inOrder(root);
        System.out.print("\nPostorder:");
        postOrder(root);
        System.out.println();
    }

    public int getTreeHeight() {
        return treeHeight;
    }

    private boolean isEmpty() {
        if (root == null) {
            return true;
        }
        return false;
    }

    public void printNode(char id) {
        if (isEmpty()) {
            System.out.println("Tree is empty\n");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        Node node = queue.poll();

        // search for the node with the id
        while (node != null && node.id != id) {
            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
            node = queue.poll();
        }

        // return if node not found
        if (node == null) {
            System.out.println(id + " does not exist in the tree\n");
            return;
        }

        if (node.p != null) {
            if (node.key < node.p.key) {
                System.out.print(node.id + " ----> key: " + node.key + "\n\tparent: left child of " + node.p.id + " ("
                        + node.p.id + ".key=" + node.p.key + ")\n\tleft child: ");
            } else {
                System.out.print(node.id + " ----> key: " + node.key + "\n\tparent: right child of " + node.p.id + " ("
                        + node.p.id + ".key=" + node.p.key + ")\n\tleft child: ");
            }

        } else {
            System.out.print(node.id + " ----> key: " + node.key + "\n\tparent: " + node.p + "\n\tleft child: ");
        }

        if (node.left != null) {
            System.out.print(node.left.id + "\n\tright child: ");
            queue.add(node.left);
        } else {
            System.out.print(node.left + "\n\tright child: ");
        }

        if (node.right != null) {
            System.out.print(node.right.id + "\n\n");
            queue.add(node.right);
        } else {
            System.out.print(node.right + "\n\n");
        }
    }

    public void printTree() {
        printTree(this.root, "", true);
        System.out.println();
    }

    private void printTree(Node root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|\t";
            }

            System.out.println(root.key);
            //System.out.println(root.key + "(" + sColor + ")");
            printTree(root.left, indent, false);
            printTree(root.right, indent, true);
        }
    }

    // public void printTree() {
    //     if (isEmpty()) {
    //         return;
    //     }

    //     Queue<Node> queue = new LinkedList<>();
    //     queue.add(root);

    //     Node node = queue.poll();
    //     System.out.println(node.key);

    //     int treeLevel = 0;

    //     while (node != null) {
    //         int x = (int) Math.pow(2, treeLevel);

    //         while (x > 0) {
    //             if (node.left != null) {
    //                 System.out.print(node.left.key + " ");
    //                 queue.add(node.left);
    //             }

    //             if (node.right != null) {
    //                 System.out.print(node.right.key + " ");
    //                 queue.add(node.right);
    //             }

    //             node = queue.poll();

    //             if (node == null) {
    //                 break;
    //             }
    //             x--;
    //         }

    //         if (x == 0) {
    //             treeLevel++;
    //             System.out.println();
    //         }
    //     }
    //     System.out.println();
    // }

    // private boolean isEmpty() {
    //     if (root == null) {
    //         return true;
    //     }
    //     return false;
    // }
}