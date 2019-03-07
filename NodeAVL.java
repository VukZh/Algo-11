package optimalsearchtreeapp;

public class NodeAVL {

    String key;
    int height;
    int balance;
    int count; // счетчик повтора ключа при вставке в дерево
    NodeAVL left;
    NodeAVL right;
    NodeAVL parent;

    NodeAVL(String k, NodeAVL prnt) {
        key = k;
        parent = prnt;
        height = 1;
        count = 1;
    }
}
