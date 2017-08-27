import java.util.Random;

/**
 * Created by tgit on 24/08/17.
 */
public abstract class animal {
    protected int timestamp;
    protected int health;
    protected float xCoordinate;
    protected float yCoordinate;
    static int herbiCount = 2;
    static int carniCount = 2;
    protected boolean dead;

    public abstract void takeTurn(grassland g1, grassland g2, animal a, animal b);

    public double getHealth() {
        return health;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(int x) {
        this.timestamp = x;
    }

    public void print() {
    }

    public int inGrassland(grassland g1, grassland g2) {
        double d1 = Math.pow(Math.pow(this.xCoordinate - g1.getXCenter(), 2) + Math.pow(this.yCoordinate - g1.getyCenter(), 2), 0.5);
        if (d1 <= g1.getRadius())
            return 1;//if in grasssland 1
        double d2 = Math.pow(Math.pow(this.xCoordinate - g2.getXCenter(), 2) + Math.pow(this.yCoordinate - g2.getyCenter(), 2), 0.5);
        if (d2 <= g2.getRadius())
            return 2;//if in grassland 2
        return 0;//if not in grassland

    }

    public double distfrom(animal a) {
        // implement it
        double dist;
        double x = Math.pow(a.xCoordinate - this.xCoordinate, 2) + Math.pow(a.yCoordinate - this.yCoordinate, 2);
        dist = Math.pow(x, 0.5);
        return dist;
    }

    public double distfrom(grassland g1, grassland g2) {
        // return distance from nearest grassland
        double d1, d2;
        double x1 = Math.pow(g1.getXCenter() - this.xCoordinate, 2) + Math.pow(g1.getyCenter() - this.xCoordinate, 2);
        d1 = Math.pow(x1, 0.5);
        double x2 = Math.pow(g2.getXCenter() - this.xCoordinate, 2) + Math.pow(g2.getyCenter() - this.xCoordinate, 2);
        d2 = Math.pow(x2, 0.5);
        if (d1 > d2) {
            return d2;
        } else return d1;

    }

    public void moveTowards(float displacement, float x, float y) {
        double mid_x = 0, mid_y = 0;
        double dist = distanceFormula(this.xCoordinate, this.yCoordinate, x, y);
        mid_x = (x * (dist - displacement) + (displacement * this.xCoordinate)) / (dist);
        mid_y = (y * (dist - displacement) + (displacement * this.yCoordinate)) / (dist);
        this.xCoordinate = (float) mid_x;
        this.yCoordinate = (float) mid_y;
    }

    public void moveAway(float displacement, float x, float y) {
        double dist = distanceFormula(this.xCoordinate, this.yCoordinate, x, y);
        float new_x, new_y;
        new_x = (float) ((this.xCoordinate * (displacement + dist)) - (dist * x)) / displacement;
        new_y = (float) ((this.yCoordinate * (displacement + dist)) - (dist * y)) / displacement;
        this.yCoordinate = new_y;
        this.xCoordinate = new_x;

    }

    public double distanceFormula(float x1, float y1, float x2, float y2) {
        // implement distance formula
        double d;
        double x = Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
        d = Math.pow(x, 0.5);
        return d;
    }

    public animal getNearest(animal a, animal b) {
        if (a.distfrom(this) < b.distfrom(this)) {
            return a;
        } else return b;
    }

    public grassland getNearest(grassland a, grassland b) {
        if (a.distfrom(this) < b.distfrom(this)) {
            return a;
        } else return b;
    }

}

class carnivore extends animal {
    private double carniHealth;
    private int awayFromHerbi;

    public carnivore(float x, float y, int time, int carniH) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.timestamp = time;
        this.carniHealth = carniH;
    }

    @Override
    public double getHealth() {
        return this.carniHealth;
    }

    @Override
    public void print() {
        System.out.println("x: " + this.xCoordinate + " y: " + this.yCoordinate + " time: " + this.getTimestamp() + " health: " + this.carniHealth + "type: " + this.getClass());
    }

    public carnivore() {

    }

//    public void eatHerbi(herbivore h) {
//        this.carniHealth = this.carniHealth + (h.herbiHealth * (2 / 3));
//        System.out.println("LOG: killing the herbi who's health is "+h.health);
//        // kill herbivores, i.e. it dies.
//        h.dead = true;
//    }


    @Override
    public void takeTurn(grassland g1, grassland g2, animal a, animal b) {
        Random r = new Random();
        int x = r.nextInt(100);
        herbivore h1 = (herbivore) a;
        herbivore h2 = (herbivore) b;
        double temp1 = Math.pow(h1.xCoordinate - this.xCoordinate, 2) + Math.pow(h1.yCoordinate - this.yCoordinate, 2);
        double distH1 = Math.pow(temp1, 0.5);
        double temp2 = Math.pow(h2.xCoordinate - this.xCoordinate, 2) + Math.pow(h2.yCoordinate - this.yCoordinate, 2);
        double distH2 = Math.pow(temp2, 0.5);
        System.out.println("LOG: distance from herb1 is: "+distH1);
        System.out.println("LOG: distance from herb2 is: "+distH2);

        if (herbiCount != 0) {
            System.out.println("LOG: herbicount!=0");
//todo eat it only if it's not already dead
            if ((distH1 <= 1) && (distH2 <= 1)) {
                // kill and eat herbi, if both in 1 unit-kill nearest one
                System.out.println("LOG: eats herbi");

                if (distH1 > distH2) {
                    h2.dead=true;
                    this.carniHealth=this.carniHealth+0.67*h2.herbiHealth;
                } else {
                    h1.dead=true;
                    this.carniHealth=this.carniHealth+0.67*h1.herbiHealth;

                }
            } else if (distH1 <= 1) {
                System.out.print("LOG: eats herbi");
                h1.dead=true;
                this.carniHealth=this.carniHealth+0.67*h1.herbiHealth;
            } else if(distH2 <= 1) {
                System.out.print("LOG: eats herbi");
                h2.dead=true;
                this.carniHealth=this.carniHealth+0.67*h2.herbiHealth;
            }
            else if (this.inGrassland(g1, g2) == 0) {
                System.out.println("LOG: not in grassland");
                if (x < 92) {

                    System.out.println("LOG: 4 units to herbi");
                    // move 4 units to nearest herbivore

                    animal nearest = getNearest(h1, h2);
                    this.moveTowards(4, nearest.xCoordinate, nearest.yCoordinate);
                } else {
                    System.out.println("LOG: stay at current pos");
                    this.carniHealth = this.carniHealth - 60;
                    //stay at current position
                }
            } else {
                System.out.println("LOG: is in grassland");
                // in grassland
                if (x < 25) {
                    System.out.println("LOG: stay in grassland");
                    this.carniHealth = this.carniHealth - 30;
                    //stay in grassland
                } else {
                    System.out.println("LOG: 2 units towards from herbi");
                    // move 2 units towards herbivore
                    animal nearest = getNearest(h1, h2);
                    this.moveTowards(2, nearest.xCoordinate, nearest.yCoordinate);

                }
            }
        }
        if ((distH1 > 5) && (distH2 > 5)) {
            awayFromHerbi++;
        } else {
            awayFromHerbi = 0;
        }
        if (awayFromHerbi > 7) {
            this.carniHealth = this.carniHealth - 6;
        }
    }
}

class herbivore extends animal {

    private int grassCapacity;
    protected double herbiHealth;
    private int outsideGrassLandCount;

    public herbivore(float x, float y, int time, int grassC, int herbH) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.timestamp = time;
        this.grassCapacity = grassC;
        this.herbiHealth = herbH;
    }

    @Override
    public double getHealth() {
        return this.herbiHealth;
    }

    public herbivore() {

    }

    @Override
    public void print() {
        System.out.println("x: " + this.xCoordinate + " y: " + this.yCoordinate + " time: " + this.getTimestamp() + " health: " + this.herbiHealth + " type: " + this.getClass());
    }

    @Override
    public void takeTurn(grassland g1, grassland g2, animal a, animal b) {

        carnivore c1 = (carnivore) a;
        carnivore c2 = (carnivore) b;
        Random r = new Random();
        int x = r.nextInt(100);
        if (carniCount == 0) {
            // go to grassland or stay-50%
            System.out.println("LOG:no carnivores");
            if (this.inGrassland(g1, g2) == 0) {
                //if not in grassland
                System.out.println("LOG: outside grassland");
                if (x < 50) {
                    System.out.println("LOG: move 5 units to nearest grassland");
                    grassland nearest = getNearest(g1, g2);
                    this.moveTowards(5, nearest.getXCenter(), nearest.getyCenter());
                }
                outsideGrassLandCount++;
            } else {
                if (getNearest(g1, g2).equals(g1))
                    moveTowards(5, g2.getXCenter(), g2.getyCenter());
                else moveTowards(5, g1.getXCenter(), g2.getyCenter());
                System.out.println("LOG: go to mnext nearest grassland");
                this.outsideGrassLandCount = 0;
            }
        }
        //if herbivore is not in a grassland
        System.out.println("LOG: carni count!=0");
        if (this.inGrassland(g1, g2) == 0) {
            //if herbivore is not in a grassland
            System.out.println("LOG: herbi not in grassland");
            this.outsideGrassLandCount++;
            if (this.outsideGrassLandCount > 7) {
                // log statement
                this.herbiHealth = this.herbiHealth - 5;
            }
            if (x < 5) {
                // herbivore will stay
                System.out.println("LOG: stays at curr pos");
            } else {
                if (x < 65) {
                    System.out.println("LOG: move 5 units to grassland");
                    // move 5 units towards grassland
                    grassland nearest = getNearest(g1, g2);
                    this.moveTowards(4, nearest.getXCenter(), nearest.getyCenter());
                } else {
                    System.out.println("LOG: move 4 units away from nearest carnivore");
                    // move away from nearest carnivore, 4 units
                    animal nearest = getNearest(c1, c2);
                    this.moveAway(4, nearest.xCoordinate, nearest.yCoordinate);

                }
            }

        } else {
            //if herbivore is in a grassland
            System.out.println("LOG: in grassland");
            this.outsideGrassLandCount = 0;
            int grasslandID = this.inGrassland(g1, g2);


            //if animal in grassland 1
            if (grasslandID == 1) {
                System.out.println("LOG: in grassland 1");
// difference bwteeb herbigealth and just health
                if (g1.getGrassAvailable() >= this.grassCapacity) {
                    //if grass available is enough
                    System.out.println("LOG: enough grass available");
                    if (x < 90) {
                        this.herbiHealth = this.herbiHealth * 1.5;
                        // stay and eat the grass
                        System.out.println("LOG: eats enough grass");
                    } else {
                        //doesn't stay in grassland
                        System.out.println("LOG: doesn't stay in grassland");
                        this.herbiHealth = this.herbiHealth - 25;
                        if (x < 50) {
                            System.out.println("LOG: moves 2 units away from carni");
                            // move 2 units away from the nearest carnivore
                            animal nearest = getNearest(c1, c2);
                            this.moveAway(2, nearest.xCoordinate, nearest.yCoordinate);
                        } else {
                            System.out.println("LOG: moves 3 units to next grassland");
                            // move 3 units in direction of next nearest grassland
                            this.moveTowards(3, g2.getXCenter(), g2.getyCenter());
                        }
                    }
                } else {
                    //if grass available is not enough
                    System.out.println("LOG: not enough grassland");
                    if (x < 20) {
                        System.out.println("LOG: eats insufficient grass");
                        // herbivore stays and eats the grass-whatever is left
                        this.herbiHealth = this.herbiHealth * 1.2;
                    } else {
                        if (x < 70) {
                            System.out.println("LOG: herbi moves 4 units away from carni");
                            // herbivore will move 4 units from carnivore
                            animal nearest = getNearest(c1, c2);
                            this.moveAway(4, nearest.xCoordinate, nearest.yCoordinate);
                            this.herbiHealth = this.herbiHealth - 25;

                        } else {
                            System.out.println("LOG: moves 2 units towards next nearest grassland");
                            // herbivore moves 2 units towards next nearest grassland
                            grassland nearest = getNearest(g1, g2);
                            this.moveTowards(2, nearest.getXCenter(), nearest.getyCenter());
                            this.herbiHealth = this.herbiHealth - 25;

                        }
                    }
                }

            }


            //if animal in grassland 2

            if (grasslandID == 2) {
                System.out.println("LOG: in grassland 2");
                if (g2.getGrassAvailable() >= this.grassCapacity) {
                    System.out.println("LOG: enough grass available");
                    if (x < 90) {
                        System.out.println("LOG: eats enough grass");
                        // stay and eat the grass
                        this.herbiHealth = this.herbiHealth * 1.5;
                    } else {
                        System.out.println("LOG: doesn't stay in grassland");
                        this.herbiHealth = this.herbiHealth - 25;
                        if (x < 50) {
                            System.out.println("LOG: moves 2 units away from carni");
                            // move 2 units away from the nearest carnivore
                            animal nearest = getNearest(c1, c2);
                            this.moveAway(2, nearest.xCoordinate, nearest.yCoordinate);
                        } else {
                            System.out.println("LOG: moves 3 units to next grassland");
                            //  move 3 units in direction of next nearest grassland
                            this.moveTowards(3, g1.getXCenter(), g1.getyCenter());
                        }
                    }
                } else {
                    //if grass available is not enough
                    System.out.println("LOG: not enough grassland");
                    if (x < 20) {
                        System.out.println("LOG: eats insufficient grass");
                        this.herbiHealth = this.herbiHealth * 1.2;
                        // herbivore stays and eats the grass-whatever is left
                    } else {
                        if (x < 70) {
                            System.out.println("LOG: herbi moves 4 units away from carni");
                            this.herbiHealth = this.herbiHealth - 25;
                            // herbivore will move 4 units from carnivore
                            animal nearest = getNearest(c1, c2);
                            this.moveAway(4, nearest.xCoordinate, nearest.yCoordinate);
                        } else {
                            System.out.println("LOG: moves 2 units towards next nearest grassland");
                            this.herbiHealth = this.herbiHealth - 25;
                            // "check" herbivore moves 2 units towards next nearest grassland
                            grassland nearest = getNearest(g1, g2);
                            this.moveTowards(2, nearest.getXCenter(), nearest.getyCenter());
                        }
                    }
                }
            }


        }
    }


}


