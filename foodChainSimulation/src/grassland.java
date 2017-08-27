/**
 * Created by tgit on 24/08/17.
 */
public class grassland {
    private float xCenter;
    private float yCenter;
    private float radius;
    private int grassAvailable;

    public grassland(float x, float y, float r, int available) {
        this.xCenter = x;
        this.yCenter = y;
        this.radius = r;
        this.grassAvailable = available;
    }
    public float getXCenter(){
        return this.xCenter;
    }

    public float getyCenter(){
        return this.yCenter;
    }

    public float getRadius(){
        return this.radius;
    }

    public int getGrassAvailable(){
        return this.grassAvailable;
    }

    public float distfrom(animal a){
        return (float)a.distanceFormula(this.xCenter,this.yCenter,a.xCoordinate,a.yCoordinate);

    }
}
