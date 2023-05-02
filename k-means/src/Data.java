import java.util.Arrays;


public class Data {

    private double [] vector;
    private String label;

    public Data(double[] vector, String label) {
        this.vector = vector;
        this.label = label;
    }

    public double getDistance(double [] vector2)
    {
        double distance = 0;
        for (int i = 0; i <vector.length ; i++)
        {
            double sum = vector[i]-vector2[i];
            distance += Math.pow(sum,2);
        }
        return Math.sqrt(distance);
    }


    public double[] getVector() {
        return vector;
    }

    public String getLabel() {
        return label;
    }

    public boolean isEqual(Data d)
    {
        for (int i = 0; i <d.getVector().length ; i++) {
            if(d.getVector()[i]!=vector[i])
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Data{" +
                "vector=" + Arrays.toString(vector) +
                ", label='" + label + '\'' +
                '}';
    }
}
