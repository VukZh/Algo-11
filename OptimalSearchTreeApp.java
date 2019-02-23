package optimalsearchtreeapp;

public class OptimalSearchTreeApp {

    public static void main(String[] args) {
        ost a1;
        a1 = new ost(26);
        a1.insert(817, 'a');
        a1.insert(149, 'b');
        a1.insert(278, 'c');
        a1.insert(425, 'd');
        a1.insert(1270, 'e');
        a1.insert(223, 'f');
        a1.insert(202, 'g');
        a1.insert(609, 'h');
        a1.insert(697, 'i');
        a1.insert(15, 'j');
        a1.insert(77, 'k');
        a1.insert(403, 'l');
        a1.insert(241, 'm');
        a1.insert(675, 'n');
        a1.insert(751, 'o');
        a1.insert(193, 'p');
//        a1.insert(10, 'q');
//        a1.insert(599, 'r');
//        a1.insert(633, 's');
//        a1.insert(906, 't');
//        a1.insert(276, 'u');
//        a1.insert(98, 'v');
//        a1.insert(236, 'w');
//        a1.insert(15, 'x');
//        a1.insert(197, 'y');
//        a1.insert(5, 'z');
//        

        a1.Algo2(); // Algo1 OR Algo2
        a1.displayOST(); // дерево
        System.out.println("Средневзвешенная высота  " + a1.HVA());

    }

}
