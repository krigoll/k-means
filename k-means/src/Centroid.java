import java.util.ArrayList;
import java.util.List;

public class Centroid
{
    private double [] vector;
    private int centroidNumber;
    private List<Data> group;

    public Centroid(int vecSize,int centroidNumber)
    {
        this.centroidNumber=centroidNumber;
        this.vector=new double[vecSize];
        this.group=new ArrayList<>();
        randVec();
    }
    public boolean correctVectorValues()
    {
        if(group.size()==0)
            return false;
        double[] vecTmp = vector;
        for (int i = 0; i <vector.length ; i++)
        {
            double correctVal = 0;
            for (int j = 0; j <group.size() ; j++)
            {
                correctVal+=group.get(j).getVector()[i];
            }
            vector[i]=correctVal/(double)(group.size());
        }
        for (int i = 0; i <vector.length ; i++) {
            if(vecTmp[i]!=vector[i])
                return true;
        }
        return false;
    }

    public boolean replaceAllData(List<Data> data)
    {
        boolean result = false;
        if(data.size()!=group.size())
            result=true;
        else {
            for (int i = 0; i < group.size(); i++) {
                if (!group.get(i).isEqual(data.get(i)))
                    result = true;
            }
        }
        group.clear();
        group.addAll(data);
        return result;
    }

    private void randVec()
    {
        for (int i = 0; i <vector.length ; i++) {
            double add = Math.random()*10;
            int opt = Math.random()>0.5? 1:-1;
            vector[i]=(Math.random()+add)*opt;}
        System.out.println();
    }

    public double getAllDistances()
    {
        double distance = 0;
        for(Data d:group)
        {
            distance+=d.getDistance(vector);
        }
        return distance;
    }

    public double[] getVector()
    {
        return vector;
    }

    public List<Data> getGroup()
    {
        return group;
    }
    public int getCentroidNumber()
    {
        return centroidNumber;
    }


}
