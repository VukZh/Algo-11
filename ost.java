package optimalsearchtreeapp;

import java.util.Stack;

public class ost { // optimalSearchTree

    private class Node { // класс объекта

        int wNode; // вероятность
        int hNode; // высота - для вычисления средневзвешенной высоты дерева
        char sNode; // ключ

        Node left;
        Node right;

        private Node(int w, char s) {
            wNode = w;
            sNode = s;
            hNode = 1;
        }
    }

    Node tree;

    private final Node[] arr;
    private int size = 0; // текущий размер

    public ost(int maxSize) {
        arr = new Node[maxSize];
        tree = null;
    }

    public void insert(int w, char s) {
        arr[size] = new Node(w, s);
        size++;
    }

    private void insertionSortA1() { // сортировка массива объектов по полю вероятности (алгоритм 1)
        int in, out;
        for (out = 1; out < size; out++) {
            Node temp = arr[out];
            in = out;
            while (in > 0 && arr[in - 1].wNode < temp.wNode) {
                arr[in] = arr[in - 1];
                --in;
            }
            arr[in] = temp;
        }
    }

    private void insertionSortA2() { // сортировка массива объектов по полю ключа (алгоритм 2)
        int in, out;
        for (out = 1; out < size; out++) {
            Node temp = arr[out];
            in = out;
            while (in > 0 && arr[in - 1].sNode > temp.sNode) {
                arr[in] = arr[in - 1];
                --in;
            }
            arr[in] = temp;
        }
    }

    public float HVA() { // средневзвешанная высота
        int W = 0;
        int h = 0;
        for (int i = 0; i < size; i++) {
            h += arr[i].wNode * arr[i].hNode;
            W += arr[i].wNode;
//                System.out.println("s-" + arr[i].sNode + "    w-" + arr[i].wNode + "    h-" + arr[i].hNode);                
        }
        return (float) h / W;
    }

    public void insert(int k) { // вставка в двоичное дерево
        Node newNode = new Node(arr[k].wNode, arr[k].sNode);
        if (tree == null) {
            tree = newNode;
        } else {

            Node current = tree;
            Node parent;
            while (true) {
                parent = current;
                arr[k].hNode = arr[k].hNode + 1;  // считаем высоту объекта вставляемого дерева
                if (arr[k].wNode < current.wNode) {
                    current = current.left;

                    if (current == null) {
                        parent.left = newNode;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {

                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    public void Algo1() { // приближенный алгоритм 1 - вершины с бОльшим весом - выше
        insertionSortA1();
        for (int i = 0; i < size; i++) {
            insert(i);
        }
    }

    public void Algo2() { // приближенный алгоритм 2 - рекурсивное нахождение центров тяжести
        insertionSortA2();
        Algo2req(0, size - 1);
    }

    private void Algo2req(int left, int right) {
        float weight = 0; // общий вес
        float sum = 0; // вес диапазона
        int find = -1; // 
        if (left == right) {
            insert(left);
        } else if (left < right) {
            for (int i = left; i < right; i++) {
                weight = weight + arr[i].wNode;
            }
            for (int i = left; i < right; i++) {
                if (sum < weight / 2 && sum + arr[i].wNode >= weight / 2) {
                    find = i;
                } else {
                    sum = sum + arr[i].wNode;
                }
            }
            if (find != -1) {
                insert(find);
            }
            Algo2req(left, find - 1);
            Algo2req(find + 1, right);
        }
    }

    public void displayOST() {
        Stack globalStack = new Stack();
        globalStack.push(tree);
        int nBlanks = 48;
        boolean isRowEmpty = false;

        System.out.println("......................................................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                Node tmp = (Node) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.sNode);
                    localStack.push(tmp.left);
                    localStack.push(tmp.right);
                    if (tmp.left != null || tmp.right != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("  ");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int i = 0; i < nBlanks * 2 - 2; i++) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            nBlanks = nBlanks / 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        }
        System.out.println("......................................................................");
    }
}
