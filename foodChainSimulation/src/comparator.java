import java.util.Comparator;

/**
 * Created by tgit on 26/08/17.
 */
public class comparator implements Comparator<animal> {
    @Override
    public int compare(animal o1, animal o2) {
        if (o1.getTimestamp()>o2.getTimestamp()){
            return 1;
        }
        else if (o1.getTimestamp()<o2.getTimestamp()){
            return -1;
        }
        if (o1.getHealth()>o2.getHealth()){
            return -1;
        }
        else if (o1.getHealth()<o2.getHealth()){
            return 1;
        }
        if (o1.getClass()!=o2.getClass()){
            if (o1 instanceof herbivore){
                return -1;
            }
            else if (o2 instanceof herbivore){
                return 1;
            }
        }
        else {
            if (o1.distanceFormula(0,0,o1.xCoordinate,o2.yCoordinate)>o2.distanceFormula(0,0,o2.xCoordinate,o2.yCoordinate)){
                return 1;
            }
            else return -1;
        }
        return 1;

    }
}
