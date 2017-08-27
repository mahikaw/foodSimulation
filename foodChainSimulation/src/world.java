import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by tgit on 24/08/17.
 */
public class world {
    int totalFinalTime;
    animal[] list;
    PriorityQueue<animal> pq;

    public world() {
        comparator c = new comparator();
        pq = new PriorityQueue<animal>(4, c);
    }

    public boolean endGame() {
        Iterator it = pq.iterator();
        while (it.hasNext()) {
            animal a = (animal) it.next();
            if (a.dead == false) {
                return false;
            }
        }
        return true;
    }

    //priority queue implementation
    // provide comparator to priority queue
    public static void main(String str[]) {
        Scanner read = new Scanner(System.in);
        world w = new world();
        w.list = new animal[4];
        System.out.println("Enter    Total   Final   Time   for   Simulation:");
        w.totalFinalTime = Integer.parseInt(read.nextLine());

        System.out.println("Enter    x,   y   centre,   radius   and   Grass   Available   for   First   Grassland:");
        String s[] = read.nextLine().split(" ");
        grassland g1 = new grassland(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));

        System.out.println("Enter    x,   y   centre,   radius   and   Grass   Available   for   Second   Grassland:");
        s = read.nextLine().split(" ");
        grassland g2 = new grassland(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));

        System.out.println("Enter   Health   and   Grass   Capacity   for   Herbivores:");
        s = read.nextLine().split(" ");
        int herbiHealth = Integer.parseInt(s[0]);
        int grassCapacity = Integer.parseInt(s[1]);

        System.out.println("Enter   x,   y   position   and   timestamp   for   First   Herbivore:");
        s = read.nextLine().split(" ");
        herbivore h1 = new herbivore(Float.parseFloat(s[0]), Float.parseFloat(s[1]), Integer.parseInt(s[2]), grassCapacity, herbiHealth);
        w.pq.add(h1);
        w.list[0] = new herbivore();
        w.list[0] = h1;

        System.out.println("Enter   x,   y   position   and   timestamp   for   Second   Herbivore:");
        s = read.nextLine().split(" ");
        herbivore h2 = new herbivore(Float.parseFloat(s[0]), Float.parseFloat(s[1]), Integer.parseInt(s[2]), grassCapacity, herbiHealth);
        w.pq.add(h2);
        w.list[1] = new herbivore();
        w.list[1] = h2;

        System.out.println("Enter   Health   for   Carnivores:");
        int carniHealth = Integer.parseInt(read.nextLine());

        System.out.println("Enter   x,   y   position   and   timestamp   for   First   Carnivore:");
        s = read.nextLine().split(" ");
        carnivore c1 = new carnivore(Float.parseFloat(s[0]), Float.parseFloat(s[1]), Integer.parseInt(s[2]), carniHealth);
        w.pq.add(c1);
        w.list[2] = new carnivore();
        w.list[2] = c1;

        System.out.println("Enter   x,   y   position   and   timestamp   for   Second   Carnivore:");
        s = read.nextLine().split(" ");
        carnivore c2 = new carnivore(Float.parseFloat(s[0]), Float.parseFloat(s[1]), Integer.parseInt(s[2]), carniHealth);
        w.pq.add(c2);
        w.list[3] = new carnivore();
        w.list[3] = c2;

        System.out.println("The   Simulation   Begins   -");

        int noOfTurns = 0;
        int maxTimeStamp = 0;
//        System.out.println("intial size: "+w.pq.size());
        while ((noOfTurns < w.totalFinalTime) && (w.endGame() == false)) {
            noOfTurns++;
            System.out.println("Turn number: " + noOfTurns);
            animal current = w.pq.poll();
            System.out.print("popped: ");
            current.print();
//            System.out.println("size after popping: "+w.pq.size());
//            if (w.pq.size()==3)
//            break;
            System.out.println(w.pq.size());
            if (current.getTimestamp() > maxTimeStamp) {
                maxTimeStamp = current.getTimestamp();
            }
            if (current instanceof carnivore) {
                System.out.println("PASSED TO CARNI: ");
                w.list[0].print();
                w.list[1].print();
                current.takeTurn(g1, g2, w.list[0], w.list[1]);
            } else {
                System.out.println("PASSED TO HERBI: ");
                w.list[2].print();
                w.list[3].print();
                current.takeTurn(g1, g2, w.list[2], w.list[3]);
            }
            if (current.getHealth() <= 0) {
                current.dead = true;
                System.out.println("it is dead");
                System.out.println("");
                System.out.println("turn over-----------------------------------------------------------------");
                System.out.println("");
                continue;
            } else {
                System.out.println("the health is " + current.getHealth());
                System.out.println("the coordinates are "+current.xCoordinate+","+current.yCoordinate);
                Random r = new Random();
                int y = r.nextInt(w.totalFinalTime - maxTimeStamp) + maxTimeStamp;
                current.setTimestamp(y);
                w.pq.add(current);
//                System.out.println("add");
//                current.print();
            }
            System.out.println("");
            System.out.println("turn over-----------------------------------------------------------------");
            System.out.println("");
        }
//        while (w.pq.isEmpty()==false) {
//            w.pq.poll().print();
//        }
    }

}
