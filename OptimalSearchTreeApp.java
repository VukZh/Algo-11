package optimalsearchtreeapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class OptimalSearchTreeApp {

    public static String[] parseFile(String strpath) {
        Path path = Paths.get(strpath);
        try (Stream<String> lines = Files.lines(path)) {
            ArrayList<String> result = new ArrayList<String>();
            lines.forEach(s -> {
                StringTokenizer token = new StringTokenizer(s, " .,<>@-=():_';\"");
                while (token.hasMoreTokens()) {
                    result.add(token.nextToken());
                }
            });
            return (String[]) result.toArray(new String[result.size()]);
        } catch (IOException ex) {
        }
        return null;
    }

    public static void main(String[] args) {

        String[] words = parseFile("src\\optimalsearchtreeapp\\wiki.test.train.tokens"); //wiki.train.tokens > 3 минут

        ost a1,a2;
        a1 = new ost();
        a2 = new ost();

        for (int i = 0; i < words.length; i++) {
            a1.insert(words[i]);
            a2.insert(words[i]);
        }

        long timeStartA1 = System.currentTimeMillis();
        a1.Algo1(); // Algo1 OR Algo2
        long timeStopA1 = System.currentTimeMillis() - timeStartA1;
        System.out.println("время построения дерева А1 " + timeStopA1 + " миллисекунд");
        
        long timeStartF1 = System.currentTimeMillis();
        a1.find("dominic"); // Algo1 OR Algo2
        long timeStopF1 = System.currentTimeMillis() - timeStartF1;
        System.out.println("время поиска А1 " + timeStopF1 + " миллисекунд");  
        
        System.out.println("Средневзвешенная высота A1 " + a1.HVA());

        System.out.println("---");

        long timeStartA2 = System.currentTimeMillis();
        a2.Algo2(); // Algo1 OR Algo2
        long timeStopA2 = System.currentTimeMillis() - timeStartA2;
        System.out.println("время построения дерева А2 " + timeStopA2 + " миллисекунд");
        
        long timeStartF2 = System.currentTimeMillis();
        a2.find("dominic"); // Algo1 OR Algo2
        long timeStopF2 = System.currentTimeMillis() - timeStartF2;
        System.out.println("время поиска А2 " + timeStopF2 + " миллисекунд");
        
        System.out.println("Средневзвешенная высота A2 " + a2.HVA());

    }

}
