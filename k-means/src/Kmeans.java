import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Kmeans {

    private int k;

    private String file;
    private BufferedReader bufferedReader;

    private List<Data> data;
    private List<Centroid> centroids;
    private int vecSize;

    public Kmeans(int k, String file) {
        this.k = k;
        this.file = file;
        this.data = new ArrayList<>();
        this.centroids = new ArrayList<>();
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.vecSize = data.get(0).getVector().length;
        randCentroids();
        doKmeans();
        show();
    }

    private void randCentroids() {
        for (int i = 0; i < this.k; i++) {
            centroids.add(new Centroid(vecSize,i));
        }
    }

    private void doKmeans() {
        boolean runKmeans = true;
        int iteration = 1;
        int noChangeLoopCount = 0;
        while (runKmeans)
        {
            double allDistance = 0;
            List<List<Data>> dataList = new ArrayList<>();
            for (int i = 0; i <centroids.size() ; i++) {
                dataList.add(new ArrayList<>());
            }
            for (Data d : this.data) {
                double minDistance = Double.MAX_VALUE;
                Centroid closestCentroid = null;
                for(Centroid c : centroids)
                {
                    double distance = d.getDistance(c.getVector());
                    if(distance<minDistance)
                    {
                        minDistance=distance;
                        closestCentroid=c;
                    }
                }
                dataList.get(closestCentroid.getCentroidNumber()).add(d);
            }
            int somethingChanged = 0;
            for (int i = 0; i <centroids.size() ; i++)
            {
                Centroid c=centroids.get(i);
                if(c.replaceAllData(dataList.get(i)))
                    somethingChanged++;
                allDistance+=c.getAllDistances();
                c.correctVectorValues();
            }
            if(somethingChanged==0)
                noChangeLoopCount++;
            else
                noChangeLoopCount=0;
            if(noChangeLoopCount>1)
                runKmeans=false;
            System.out.println("Iteration " + iteration + ": " + allDistance);
//            show();
            iteration++;
        }

    }



    private void readData() throws IOException {
        FileReader fileReader = new FileReader(this.file);
        bufferedReader = new BufferedReader(fileReader);

        while (bufferedReader.ready()) {
            String[] args = bufferedReader.readLine().split(",");
            double[] vector = new double[args.length - 1];
            String label = args[args.length - 1];

            for (int i = 0; i < args.length - 1; i++) {
                vector[i] = Double.parseDouble(args[i]);
            }

            data.add(new Data(vector, label));
        }
    }

    private void show() {
        for(Centroid c : centroids)
        {
            if(c.getGroup().size()>0)
            {
                System.out.println("===");
            System.out.println("Centroid number: "+c.getCentroidNumber()+" Group:");
            for (int i = 0; i <c.getGroup().size() ; i++) {
                System.out.print(c.getGroup().get(i).getLabel()+", ");
                if(i%10==0 && i!=0)
                    System.out.println();
            }
                System.out.println();
                System.out.println("===");
            }
        }
    }
}
