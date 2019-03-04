package optimalsearchtreeapp;

import java.util.Stack;

public class ost { // optimalSearchTree

    private class Node { // класс объекта

        int wNode; // вероятность
        int hNode; // высота - для вычисления средневзвешенной высоты дерева
        String sNode; // ключ

        Node left;
        Node right;

        private Node(String s) {
            wNode = 1;
            sNode = s;
            hNode = 1;
        }
    }

    Node tree;

    BArray<Node> arr;
    private int size = 0; // текущий размер

    public ost() {
//        arr = new Node[maxSize];        
        arr = new BArray<Node>();
        tree = null;
    }

    public void insert(String s) {
        int f = DuplicateFind(s);
        if (f == -1) {
            arr.add(new Node(s));
            size++;
        } else {
            Node tmp = arr.get(f);
            tmp.wNode++;
            arr.set(f, tmp);
        }
    }

    public int DuplicateFind(String s) { // поиск повторений для вставки в массив
        for (int i = 0; i < size; i++) {
            if (arr.get(i).sNode.compareToIgnoreCase(s) == 0) {
                return i;
            }
        }
        return -1;
    }

    public String find(String s) { // поиск
        Node curr = tree;
        while (s.compareToIgnoreCase(curr.sNode) != 0) {
            if (s.compareToIgnoreCase(curr.sNode) < 0) { //compareToIgnoreCase
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            if (curr == null) {
                System.out.println("not find");
                return null;
            }
        }
        System.out.println("find");
        return curr.sNode;
    }

    public int getsize() {
        System.out.println("size -> " + size);
        return size;
    }

    public void getArr(int i) {
        System.out.println("Arr-> " + arr.get(i).sNode + " dup-> " + arr.get(i).wNode + " H->" + arr.get(i).hNode);
    }

    private void insertionSortA1() { // предварительная сортировка массива объектов по полю вероятности (для алгоритма 1)
        int in, out;
        for (out = 1; out < size; out++) {
            Node temp = arr.get(out);
            in = out;
            while (in > 0 && arr.get(in - 1).wNode < temp.wNode) {
                arr.set(in, arr.get(in - 1));
                --in;
            }
            arr.set(in, temp);
        }
    }

    private void insertionSortA2() { // предварительная сортировка массива объектов по полю ключа (для алгоритма 2)
        int in, out;
        for (out = 1; out < size; out++) {
            Node temp = arr.get(out);
            in = out;
            while (in > 0 && arr.get(in - 1).sNode.compareToIgnoreCase(temp.sNode) > 0) { // compareToIgnoreCase
                arr.set(in, arr.get(in - 1));
                --in;
            }
            arr.set(in, temp);
        }
    }

    public float HVA() { // средневзвешанная высота
        int W = 0;
        int h = 0;
        for (int i = 0; i < size; i++) {
            h += arr.get(i).wNode * arr.get(i).hNode;
            W += arr.get(i).wNode;
//                System.out.println("s-" + arr[i].sNode + "    w-" + arr[i].wNode + "    h-" + arr[i].hNode);                
        }
        return (float) h / W;
    }

    public void insert(int k) { // вставка в двоичное дерево
        Node newNode = new Node(arr.get(k).sNode);
        if (tree == null) {
            tree = newNode;
        } else {

            Node current = tree;
            Node parent;
            while (true) {
                parent = current;
                arr.get(k).hNode = arr.get(k).hNode + 1;  // считаем высоту объекта вставляемого дерева
                if (arr.get(k).sNode.compareToIgnoreCase(current.sNode) < 0) {
                    current = current.left;

                    if (current == null) {
                        parent.left = newNode;
                        return;
                    }
                } else if (arr.get(k).sNode.compareToIgnoreCase(current.sNode) > 0) {
                    current = current.right;
                    if (current == null) {

                        parent.right = newNode;
                        return;
                    }
                } else {
                    current.wNode++;
                    return;
                }
            }
        }
    }

    public void Algo1() { // приближенный алгоритм 1 - вершины с бОльшим весом - выше
        insertionSortA1(); // сортировка по вероятности
        for (int i = 0; i < size; i++) {
            insert(i);
        }
    }

    public void Algo2() { // приближенный алгоритм 2 - рекурсивное нахождение центров тяжести
        insertionSortA2(); // сортировка по ключу
        Algo2req(0, size - 1); // рек. поиск "центра тяжести"
    }

    private void Algo2req(int left, int right) {
        float weight = 0; // общий вес
        float sum = 0; // вес диапазона
        int find = -1; // 
        if (left == right) {
            insert(left);
        } else if (left < right) {
            for (int i = left; i < right; i++) {
                weight = weight + arr.get(i).wNode;
            }
            for (int i = left; i < right; i++) {
                if (sum < weight / 2 && sum + arr.get(i).wNode >= weight / 2) {
                    find = i;
                } else {
                    sum = sum + arr.get(i).wNode;
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
