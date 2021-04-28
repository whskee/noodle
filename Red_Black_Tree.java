import java.util.LinkedList;
import java.util.Queue;

/*
    1.  Every node is either red or black
    2.  The root is black
    3.  Every leaf (nil) is black
    4.  If a node is red, then both its children are black
    5.  For each node, all simple paths from the node to descendant
        leaves contains the same # of black nodes
*/

// example from HW 5
public class Red_Black_Tree {
    public static void main(String[] args) {

        /*  
        IMPORTANT -->
            1. make sure all the arrays are of the same size!
            2. insert all the nodes and its corresponding color and letter in order 
        */
        char[] idArr = new char[] { 'f', 'c', 'l', 'a', 'e', 'h', 'n', 'b', 'd', 'g', 'j', 'm', 'o', 'i', 'k' };
        int[] arr = new int[] { 40, 20, 100, 10, 30, 60, 120, 15, 25, 50, 80, 110, 130, 70, 90 };
        char[] colors = new char[] { 'B', 'B', 'R', 'B', 'B', 'B', 'B', 'R', 'R', 'B', 'B', 'B', 'B', 'R', 'R' };

        RedBlackTree tree = new RedBlackTree(idArr, arr, colors);
        System.out.println();

        /*
            1. to insert a node ---> tree.insert('p', 12);
            2. to delete a node ---> tree.delete(40);
            3. to print successor ---> tree.successor('g');
            4. to print predecessor ---> tree.predecessor('g')
            5. to print a single node ---> tree.printNode('g');
            6. to print all nodes ---> tree.printAllNodes();
            7. to print tree ---> tree.printTree();
        */

        // write commands here

        tree.insert('p', 12);

        tree.delete(40);
        tree.printNode('g');
        tree.printNode('l');
        tree.printNode('j');
        tree.printNode('h');

        // tree.printTree();
    }
}

class RedBlackTree {

    class Node {
        char id;
        int key;
        char color;
        Node left;
        Node right;
        Node p;

        // default constructor
        Node() {
            color = 'B';
        }

        // parameterized constructor
        Node(char id, int key, char color) {
            this.id = id;
            this.key = key;
            this.color = color;
        }
    }

    private Node root;
    private Node nil;
    private String violations = "";

    // constructor to insert nodes in RB-Tree
    public RedBlackTree(char[] idArr, int[] arr) {
        nil = new Node();
        root = nil;
        root.p = nil;

        for (int x = 0; x < arr.length; x++) {
            insert(new Node(idArr[x], arr[x], 'R'));
        }
    }

    // constructor to insert nodes in BST with predefined colors
    public RedBlackTree(char[] idArr, int[] arr, char[] colors) {
        nil = new Node();
        root = nil;
        root.p = nil;

        for (int x = 0; x < arr.length; x++) {
            bstInsert(new Node(idArr[x], arr[x], colors[x]), false);
        }
    }

    public void insert(char id, int key) {
        violations = "";

        Node z = new Node();
        z.id = id;
        z.key = key;
        insert(z);

        System.out.print("After rbt insertion: ");
        printViolations();
        printNode(id);
    }

    private void insert(Node z) {
        bstInsert(z, true);
        z.color = 'R';

        // return if z's parent or grandparent is null
        if (z.p == nil) {
            z.color = 'B';
            violations += "Property 2, ";
            return;
        } else if (z.p.p == null) {
            return;
        }

        insertFixup(z);
    }

    private void insertFixup(Node z) {
        Node y;
        while (z.p.color == 'R') {
            violations += "Property 4, ";
            if (z.p == z.p.p.left) {
                y = z.p.p.right;
                if (y.color == 'R') {
                    // case 1
                    violations += "Case 1 ";
                    z.p.color = 'B';
                    y.color = 'B';
                    z.p.p.color = 'R';
                    z = z.p.p;
                } else {
                    if (z == z.p.right) {
                        // case 2
                        violations += "Case 2 ";
                        z = z.p;
                        leftRotate(z);
                    }
                    // case 3
                    violations += "Case 3 ";
                    z.p.color = 'B';
                    z.p.p.color = 'R';
                    rightRotate(z.p.p);
                }

            } else {
                y = z.p.p.left; // uncle
                if (y.color == 'R') {
                    // case 1
                    violations += "Case 1 ";
                    z.p.color = 'B';
                    y.color = 'B';
                    z.p.p.color = 'R';
                    z = z.p.p;

                } else {
                    if (z == z.p.left) {
                        // case 2
                        violations += "Case 2 ";
                        z = z.p;
                        rightRotate(z);
                    }
                    // case 3
                    violations += "Case 3 ";
                    z.p.color = 'B';
                    z.p.p.color = 'R';
                    leftRotate(z.p.p);
                }
            }
        }
        root.color = 'B';
    }

    private void bstInsert(Node z, boolean flag) {
        Node y = nil;
        Node x = root;
        while (x != nil) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == nil) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = nil;
        z.right = nil;

        if (flag == true) {
            System.out.println("After bst insertion:");
            printNode(z.id);
        }
    }

    public void delete(int key) {
        violations = "";
        Node x = search(root, key);
        if (x == nil) {
            System.out.println("Key does not exist in the tree\n");
            return;
        }
        delete(x);
        // need to fix
        //printViolations();
    }

    private void delete(Node z) {
        //successor(z.id);

        if (z == root) {
            violations += "Property 2 ";
        }

        Node x;
        Node y = z;
        char yOriginalColor = y.color;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.p == z) {
                x.p = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }

        if (yOriginalColor == 'B') {
            deleteFixup(x);
        }
    }

    private void deleteFixup(Node x) {
        Node w;
        while (x != root && x.color == 'B') {
            if (x == x.p.left) {
                w = x.p.right;
                if (w.color == 'R') {
                    // case 1
                    violations += "Case 1 ";
                    w.color = 'B';
                    x.p.color = 'R';
                    leftRotate(x.p);
                    w = x.p.right;
                }

                if (w.left.color == 'B' && w.right.color == 'B') {
                    // case 2
                    violations += "Case 2 ";
                    w.color = 'R';
                    x = x.p;
                } else {
                    if (w.right.color == 'B') {
                        // case 3
                        violations += "Case 3 ";
                        w.left.color = 'B';
                        w.color = 'R';
                        rightRotate(w);
                        w = x.p.right;
                    }
                    // case 4
                    violations += "Case 4 ";
                    w.color = x.p.color;
                    x.p.color = 'B';
                    w.right.color = 'B';
                    leftRotate(x.p);
                    x = root;
                }
            } else {
                w = x.p.left;
                if (w.color == 'R') {
                    // case 1
                    violations += "Case 1 ";
                    w.color = 'B';
                    x.p.color = 'R';
                    rightRotate(x.p);
                    w = x.p.left;
                }
                if (w.right.color == 'B' && w.left.color == 'B') {
                    // case 2
                    violations += "Case 2 ";
                    w.color = 'R';
                    x = x.p;
                } else {
                    if (w.left.color == 'B') {
                        // case 3
                        violations += "Case 3 ";
                        w.right.color = 'B';
                        w.color = 'R';
                        leftRotate(w);
                        w = x.p.left;
                    }
                    // case 4
                    violations += "Case 4 ";
                    w.color = x.p.color;
                    x.p.color = 'B';
                    w.left.color = 'B';
                    rightRotate(x.p);
                    x = root;
                }
            }
        }
        x.color = 'B';
    }

    private void transplant(Node u, Node v) {
        if (u.p == nil) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        v.p = u.p;
    }

    private Node search(Node x, int key) {
        if (x == nil || key == x.key) {
            return x;
        }
        if (key < x.key) {
            return search(x.left, key);
        } else {
            return search(x.right, key);
        }
    }

    private void leftRotate(Node x) {
        Node y = x.right; // set y
        x.right = y.left; // turn y’s left subtree into x’s right subtree
        if (y.left != nil) {
            y.left.p = x;
        }
        y.p = x.p; // link x’s parent to y
        if (x.p == nil) {
            root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.left = x; // put x on y's left
        x.p = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left; // set y
        x.left = y.right; // turn y’s right subtree into x’s left subtree
        if (y.right != nil) {
            y.right.p = x;
        }
        y.p = x.p; // link y’s parent to x
        if (x.p == nil) {
            root = y;
        } else if (x == x.p.right) {
            x.p.right = y;
        } else {
            x.p.left = y;
        }
        y.right = x; // put x on y's left
        x.p = y;
    }

    public void minimum() {
        System.out.println(minimum(root).key);
    }

    private Node minimum(Node x) {
        while (x.left != nil) {
            x = x.left;
        }
        return x;
    }

    public void maximum() {
        System.out.println(maximum(root).key);
    }

    private Node maximum(Node x) {
        while (x.right != nil) {
            x = x.right;
        }
        return x;
    }

    public void predecessor(char id) {
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

        System.out.println("Predecessor --->");

        if (node != null) {
            printNode(predecessor(node).id);
        } else {
            System.out.println("id not found\n");
        }
    }

    private Node predecessor(Node x) {
        // predecessor is the rightmost node in the left subtree
        // if left subtree isn't null
        if (x.left != nil) {
            return maximum(x.left);
        }

        Node y = x.p;
        while (y != nil && x == y.left) {
            x = y;
            y = y.p;
        }

        // return largest node of x's left subtree
        return y;
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

        if (node != null) {
            printNode(successor(node).id);
        } else {
            System.out.println("id not found\n");
        }

    }

    private Node successor(Node x) {
        if (x.right != nil) {
            return minimum(x.right);
        }

        Node y = x.p;
        while (y != nil && x == y.right) {
            x = y;
            y = y.p;
        }
        // return smallest node of x's right subtree
        return y;
    }

    private boolean isEmpty() {
        if (root == nil) {
            return true;
        }
        return false;
    }

    public void printRoot() {
        printNode(root.id);
    }

    private void printViolations() {
        if (violations.equals("")) {
            System.out.println("No violations");
        } else {
            System.out.println("Violations ---> " + violations + "");
        }
    }

    public void printAllNodes() {
        if (isEmpty()) {
            System.out.println("Tree is empty\n");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        Node node = queue.poll();

        while (node != null) {

            if (node.p != null) {
                if (node.key < node.p.key) {
                    System.out.print(node.id + " ----> key: " + node.key + "\n\tcolor: " + node.color
                            + "\n\tparent: left child of " + node.p.id + " (" + node.p.id + ".key=" + node.p.key
                            + ")\n\tleft child: ");
                } else {
                    System.out.print(node.id + " ----> key: " + node.key + "\n\tcolor: " + node.color
                            + "\n\tparent: right child of " + node.p.id + " (" + node.p.id + ".key=" + node.p.key
                            + ")\n\tleft child: ");
                }

            } else {
                System.out.print(node.id + " ----> key: " + node.key + "\n\tcolor: " + node.color + "\n\tparent: "
                        + node.p + "\n\tleft child: ");
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

            node = queue.poll();
        }
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
                System.out.print(
                        node.id + " ----> key: " + node.key + "\n\tcolor: " + node.color + "\n\tparent: left child of "
                                + node.p.id + " (" + node.p.id + ".key=" + node.p.key + ")\n\tleft child: ");
            } else {
                System.out.print(
                        node.id + " ----> key: " + node.key + "\n\tcolor: " + node.color + "\n\tparent: right child of "
                                + node.p.id + " (" + node.p.id + ".key=" + node.p.key + ")\n\tleft child: ");
            }

        } else {
            System.out.print(node.id + " ----> key: " + node.key + "\n\tcolor: " + node.color + "\n\tparent: " + node.p
                    + "\n\tleft child: ");
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
        if (root != nil) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|\t";
            }

            String sColor = root.color == 'R' ? "RED" : "BLACK";
            System.out.println(root.key + " " + root.id + " (" + sColor + ")");
            //System.out.println(root.key + "(" + sColor + ")");
            printTree(root.left, indent, false);
            printTree(root.right, indent, true);
        }
    }
}