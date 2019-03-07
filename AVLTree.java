package optimalsearchtreeapp;

import java.util.Stack;

public class AVLTree {

    public NodeAVL rootAVLtree;

    public int sizeAVL = 0; // количество элементов в дереве
    BArray<NodeAVL> out; // массив узлов дерева

    public AVLTree() {
        rootAVLtree = null;
        out = new BArray<NodeAVL>();

    }

    public NodeAVL find(String k) { // поиск
        NodeAVL curr = rootAVLtree;
        while (k.compareToIgnoreCase(curr.key) != 0) {
            if (k.compareToIgnoreCase(curr.key) < 0) {
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
        return curr;
    }

/////////////// ребалансировка
    private int height(NodeAVL n) {
        if (n == null) {
            return 0;
        }
        return n.height;
    }

    private void reHeight(NodeAVL node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    private void setBalance(NodeAVL node) {
        if (node != null) {
            reHeight(node);
            node.balance = height(node.right) - height(node.left);
        }
    }

    private void reBalance(NodeAVL node) {
        setBalance(node);
        if (node.balance == -2) { // выбор правых поворотов
            if (height(node.left.left) >= height(node.left.right)) {
                node = rightRotate(node);
            } else {
                node = rotateLeft_Right(node);
            }
        } else if (node.balance == 2) { // выбор левых поворотов
            if (height(node.right.right) >= height(node.right.left)) {
                node = leftRotate(node);
            } else {
                node = rotateRight_Left(node);
            }
        }
        if (node.parent != null) {
            reBalance(node.parent);
        } else {
            rootAVLtree = node;
        }
    }

////////////// малые повороты
    private NodeAVL leftRotate(NodeAVL x) { // RR     x
        NodeAVL y = x.right; //                   / \ 
        y.parent = x.parent; //              T1   y
        x.right = y.left; //                     / \
        if (x.right != null) { //               T2  T3
            x.right.parent = x;
        }
        y.left = x;
        x.parent = y;
        if (y.parent != null) {
            if (y.parent.right == x) {
                y.parent.right = y;
            } else {
                y.parent.left = y;
            }
        }
        setBalance(x);
        setBalance(y);
        return y;
    }

    private NodeAVL rightRotate(NodeAVL y) { // LL    y
        NodeAVL x = y.left;  //                   / \
        x.parent = y.parent; //              x   T3
        y.left = x.right;//                / \
        if (y.left != null) {//          T1  T2
            y.left.parent = y;
        }
        x.right = y;
        y.parent = x;
        if (x.parent != null) {
            if (x.parent.right == y) {
                x.parent.right = x;
            } else {
                x.parent.left = x;
            }
        }
        setBalance(y);
        setBalance(x);

        return x;
    }

////////////// большие повороты
    private NodeAVL rotateLeft_Right(NodeAVL node) { // LR
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    private NodeAVL rotateRight_Left(NodeAVL node) { // RL
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

////////////////// вставка
    public boolean insert(String key) {
        if (rootAVLtree == null) {
            rootAVLtree = new NodeAVL(key, null);
            return true;
        }
        NodeAVL n = rootAVLtree; // начинаем с корня
        while (true) { // выходим при таком же уже ключе в дереве
            if (key.compareToIgnoreCase(n.key) == 0) {
//                System.out.println("repeat " + n.key);
                n.count++; // счетчик повторов ключа
                return false;
            }
            NodeAVL parent = n; // сохранение узла для вставки если дальше не будет потомка

            int isLeft = key.compareToIgnoreCase(n.key); // в какую ветвь идет вставка
            if (isLeft < 0) {
                n = n.left;
            } else {
                n = n.right;
            }

            if (n == null) { // прогоняем до низа дерева
                if (isLeft < 0) {
                    parent.left = new NodeAVL(key, parent);
                } else {
                    parent.right = new NodeAVL(key, parent);
                }
                reBalance(parent);
                break;
            }
        }
        return true;
    }

////////////////////// удаление    
    private void delNode(NodeAVL node) {
        if (node.left == null && node.right == null) { //если нет потомков - убираем связи у родителя на этот узел
            if (node.parent == null) {
                rootAVLtree = null;
            } else {
                NodeAVL parent = node.parent;
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                reBalance(parent);
            }
            return;
        }

        if (node.left != null) { // ищем замену вместо удаляемого узла
            NodeAVL fndNxt = node.left; // налево самый максимальный
            while (fndNxt.right != null) {
                fndNxt = fndNxt.right;
            }
            node.key = fndNxt.key;
            delNode(fndNxt);
        } else {
            NodeAVL fndNxt = node.right; // направо самый минимальный
            while (fndNxt.left != null) {
                fndNxt = fndNxt.left;
            }
            node.key = fndNxt.key;
            delNode(fndNxt);
        }
    }

    public void delete(String key) {
        if (rootAVLtree == null) {
            return;
        }
        NodeAVL fnd = rootAVLtree;
        while (fnd != null) {
            NodeAVL node = fnd;
            if (key.compareToIgnoreCase(node.key) > 0) {
                fnd = node.right;
            } else {
                fnd = node.left;
            }
            if (key.compareToIgnoreCase(node.key) == 0) { // узел найден
                delNode(node);
                return;
            }
        }
    }

// вывод узла
    public NodeAVL getNodeAvl(int k) {
        return out.get(k);
    }

// вывод дерева
    public BArray preOrderArr(NodeAVL localRoot) {

        if (localRoot != null) {
            out.add(localRoot);
            sizeAVL++;
            preOrderArr(localRoot.left);
            preOrderArr(localRoot.right);
        }
        return out;
    }

//    private void preOrder (Node localRoot){
//        if (localRoot != null){
//            System.out.print(localRoot.key + " ");
//            preOrder (localRoot.left);
//            preOrder (localRoot.right);
//        }
//    }
//    private void inOrder (Node localRoot){
//        if (localRoot != null){
//            inOrder (localRoot.left);
//            System.out.print(localRoot.key + " ");
//            inOrder (localRoot.right);
//        }
//    }
//    
//    private void postOrder (Node localRoot){
//        if (localRoot != null){
//            postOrder (localRoot.left);
//            postOrder (localRoot.right);
//            System.out.print(localRoot.key + " ");
//        }
//    }
    public void Out() {
        preOrderArr(rootAVLtree);
        for (int i = 0; i < sizeAVL; i++) {
            System.out.print(out.get(i).count + " ");
        }
    }

    public void displayAVLTree() {
        Stack globalStack = new Stack();
        globalStack.push(rootAVLtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("......................................................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                NodeAVL tmp = (NodeAVL) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.key);
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

    public void displayHeight() { // вывод высот
        Stack globalStack = new Stack();
        globalStack.push(rootAVLtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("...............................Height.................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                NodeAVL tmp = (NodeAVL) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.height);
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

    public void displayBalance() { // вывод баланса
        Stack globalStack = new Stack();
        globalStack.push(rootAVLtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("...............................Balance................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                NodeAVL tmp = (NodeAVL) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.balance);
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

    public void displayCount() { // вывод баланса
        Stack globalStack = new Stack();
        globalStack.push(rootAVLtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("................................Count.................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                NodeAVL tmp = (NodeAVL) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.count);
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
