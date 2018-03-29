package odev;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Odev {

    public static void main(String[] args) {
//        LinkedList<Integer> sayilar = new LinkedList<>();
//        Random r1 = new Random();
//        Node node = new Node(null);
//        while (sayilar.size() < 15) {
//            int sayi2 = r1.nextInt(100);
//            node.data = sayi2;
//            sayilar.addLast(node);
//            node = new Node(null);
//        }
//        sayilar.print();
        Set<Integer> numbers = new HashSet<>();
        Random r = new Random();
        while (numbers.size() < 15) {
            int sayi1 = r.nextInt(100);
            numbers.add(sayi1);
        }
        Object dizi[] = numbers.toArray();
        System.out.println(numbers);

        ArrayList<Integer> sayilar1 = new ArrayList();
        int randomy = r.nextInt(99) + 1;
        sayilar1.add(randomy);
        while (sayilar1.size() < 99) {
            Random random = new Random();
            int need = random.nextInt(99) + 1;
            if (!sayilar1.contains(need)) {
                sayilar1.add(need);
            }

        }
        for (int i = 0; i < sayilar1.size(); i++) {
            System.out.println(sayilar1.get(i));
        }
        System.out.println("size = " + sayilar1.size());

    }

}
