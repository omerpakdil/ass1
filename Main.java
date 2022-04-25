import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.*;
import java.util.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;

public class Main{
    
    public static void insertionSort(int arr[]) {
        int length = arr.length;
        for (int j = 1; j < length; j++) {
            int value = arr[j];
            int i = j-1;  
            while ( (i > -1) && ( arr [i] > value ) ) {
                arr [i+1] = arr [i];  
                i--;  
            }  
            arr[i+1] = value;
        }
    }

    public static void merge(int[] left_arr,int[] right_arr, int[] arr,int left_size, int right_size){

        int i=0,l=0,r = 0;

        while(l<left_size && r<right_size){

            if(left_arr[l]<right_arr[r]){
                arr[i++] = left_arr[l++];
            }
            else{
                arr[i++] = right_arr[r++];
            }
        }
        while(l<left_size){
            arr[i++] = left_arr[l++];
        }
        while(r<right_size){
            arr[i++] = right_arr[r++];
        }
    }

    public static void mergeSort(int [] array, int length){

        if (length < 2){
            return;
        }

        int mid = length / 2;
        int [] leftArray = new int[mid];
        int [] rightArray = new int[length-mid];

        int k = 0;
        for(int i = 0;i<length;++i){
            if(i<mid){
                leftArray[i] = array[i];
            }
            else{
                rightArray[k] = array[i];
                k = k+1;
            }
        }

        mergeSort(leftArray,mid);
        mergeSort(rightArray,length-mid);
        merge(leftArray,rightArray,array,mid,length-mid);
    }

    public static void pigeonhole_sorting(int array[], int n) {

        int minimum = array[0];
        int maximum = array[0];
        int range, i, j, index;

        for (int a = 0; a < n; a++) {
            if (array[a] > maximum)
                maximum = array[a];
            if (array[a] < minimum)
                minimum = array[a];
        }

        range = maximum - minimum + 1;
        int[] pigeon = new int[range];
        Arrays.fill(pigeon, 0);

        for (i = 0; i < n; i++)
            pigeon[array[i] - minimum]++;

        index = 0;

        for (j = 0; j < range; j++)
            while (pigeon[j]-- > 0)
                array[index++] = j + minimum;
     }

    public static void countSort(int array[], int size) {

        int[] output = new int[size + 1];

        int max = array[0];
        for (int i = 1; i < size; i++) {
            if (array[i] > max)
                max = array[i];
        }
        int[] count = new int[max + 1];


        for (int i = 0; i < max; ++i) {
            count[i] = 0;
        }

        for (int i = 0; i < size; i++) {
            count[array[i]]++;
        }

        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        for (int i = size - 1; i >= 0; i--) {
            output[count[array[i]] - 1] = array[i];
            count[array[i]]--;
        }

        for (int i = 0; i < size; i++) {
            array[i] = output[i];
        }
    }

    public static int[] createSub(ArrayList<String> durations,int num){
        int[] elements = new int[num];
        for(int i=0;i<num;i++){
            elements[i] = Integer.parseInt(durations.get(i));
        }
        return elements;
    }

    public static int[] reverse_array(int char_array[], int n)
    {
        int[] dest_array = new int[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            dest_array[j - 1] = char_array[i];
            j = j - 1;
        }
        return dest_array;
    }

    public static void main(String[] args) throws IOException {


        ArrayList<String> durations = new ArrayList<String>();
        String line = "";
        String splitBy = ",";
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("TrafficFlowDataset.csv"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] employee = line.split(splitBy);    // use comma as separator
                durations.add(employee[7]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        durations.remove(0);

        int[] elements512 = createSub(durations,512);
        int[] elements1024 = createSub(durations,1024);
        int[] elements2048 = createSub(durations,2048);
        int[] elements4096 = createSub(durations,4096);
        int[] elements8192 = createSub(durations,8192);
        int[] elements16384 = createSub(durations,16384);
        int[] elements32768 = createSub(durations,32768);
        int[] elements65536 = createSub(durations,65536);
        int[] elements131072 = createSub(durations,131072);
        int[] allElements = createSub(durations,durations.size());

        int[][] arrayElements = new int[][]{elements512, elements1024, elements2048, elements4096, elements8192, elements16384, elements32768, elements65536, elements131072, allElements};
        int[][] arrayElementsOrdered = new int[10][];
        int[][] arrayElementsReversed = new int[10][];

        long[] timesInsertion = new long[10];
        long[] timesMerge = new long[10];
        long[] timesPigeonhole = new long[10];
        long[] timesCounting = new long[10];

        long[] timesInsertionOrdered = new long[10];
        long[] timesMergeOrdered = new long[10];
        long[] timesPigeonholeOrdered = new long[10];
        long[] timesCountingOrdered = new long[10];

        long[] timesInsertionReversed = new long[10];
        long[] timesMergeReversed = new long[10];
        long[] timesPigeonholeReversed = new long[10];
        long[] timesCountingReversed = new long[10];

        System.out.println("Insertion");
        for(int i=0;i<arrayElements.length;i++){
            long start = System.currentTimeMillis();
            insertionSort(arrayElements[i]);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesInsertion[i] = elapsedTime;
            arrayElementsOrdered[i] = arrayElements[i];
            arrayElementsReversed[i] = reverse_array(arrayElementsOrdered[i],arrayElementsOrdered[i].length);
        }

        System.out.println("Merge");
        for(int i=0;i<arrayElements.length;i++){
            long start = System.currentTimeMillis();
            mergeSort(arrayElements[i],arrayElements[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesMerge[i] = elapsedTime;

        }

        System.out.println("Pigeonhole");
        for(int i=0;i<arrayElements.length;i++){
            long start = System.currentTimeMillis();
            pigeonhole_sorting(arrayElements[i],arrayElements[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesPigeonhole[i] = elapsedTime;
        }

        System.out.println("Counting");
        for(int i=0;i<arrayElements.length;i++){
            long start = System.currentTimeMillis();
            countSort(arrayElements[i],arrayElements[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesCounting[i] = elapsedTime;
        }


        System.out.println("Insertion Ordered");
        for(int i=0;i<arrayElementsOrdered.length;i++){
            long start = System.currentTimeMillis();
            insertionSort(arrayElementsOrdered[i]);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesInsertionOrdered[i] = elapsedTime;
        }

        System.out.println("Merge Ordered");
        for(int i=0;i<arrayElementsOrdered.length;i++){
            long start = System.currentTimeMillis();
            mergeSort(arrayElementsOrdered[i],arrayElementsOrdered[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesMergeOrdered[i] = elapsedTime;

        }

        System.out.println("Pigeonhole Ordered");
        for(int i=0;i<arrayElementsOrdered.length;i++){
            long start = System.currentTimeMillis();
            pigeonhole_sorting(arrayElementsOrdered[i],arrayElementsOrdered[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesPigeonholeOrdered[i] = elapsedTime;
        }

        System.out.println("Counting Ordered");
        for(int i=0;i<arrayElementsOrdered.length;i++){
            long start = System.currentTimeMillis();
            countSort(arrayElementsOrdered[i],arrayElementsOrdered[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesCountingOrdered[i] = elapsedTime;
        }


        System.out.println("Insertion Reversed");
        for(int i=0;i<arrayElementsReversed.length;i++){
            long start = System.currentTimeMillis();
            insertionSort(arrayElementsReversed[i]);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesInsertionReversed[i] = elapsedTime;
        }

        System.out.println("Merge Reversed");
        for(int i=0;i<arrayElementsReversed.length;i++){
            long start = System.currentTimeMillis();
            mergeSort(arrayElementsReversed[i],arrayElementsReversed[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesMergeReversed[i] = elapsedTime;

        }

        System.out.println("Pigeonhole Reversed");
        for(int i=0;i<arrayElementsReversed.length;i++){
            long start = System.currentTimeMillis();
            pigeonhole_sorting(arrayElementsReversed[i],arrayElementsReversed[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesPigeonholeReversed[i] = elapsedTime;
        }

        System.out.println("Counting Reversed");
        for(int i=0;i<arrayElementsReversed.length;i++){
            long start = System.currentTimeMillis();
            countSort(arrayElementsReversed[i],arrayElementsReversed[i].length);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println(elapsedTime);
            timesCountingReversed[i] = elapsedTime;
        }



    // X axis data
        int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        // Create sample data for linear runtime
        double[][] yAxis = new double[4][10];
        yAxis[0] = new double[]{timesMergeReversed[0], timesMergeReversed[1], timesMergeReversed[2], timesMergeReversed[3], timesMergeReversed[4], timesMergeReversed[5], timesMergeReversed[6], timesMergeReversed[7], timesMergeReversed[8], timesMergeReversed[9]};
        //yAxis[1] = new double[]{timesInsertionReversed[0], timesInsertionReversed[1], timesInsertionReversed[2], timesInsertionReversed[3], timesInsertionReversed[4], timesInsertionReversed[5], timesInsertionReversed[6], timesInsertionReversed[7], timesInsertionReversed[8], timesInsertionReversed[9]};
        yAxis[1] = new double[]{timesPigeonholeReversed[0],timesPigeonholeReversed[1],timesPigeonholeReversed[2],timesPigeonholeReversed[3],timesPigeonholeReversed[4],timesPigeonholeReversed[5],timesPigeonholeReversed[6],timesPigeonholeReversed[7],timesPigeonholeReversed[8],timesPigeonholeReversed[9]};
        yAxis[2] = new double[]{timesCountingReversed[0],timesCountingReversed[1],timesCountingReversed[2],timesCountingReversed[3],timesCountingReversed[4],timesCountingReversed[5],timesCountingReversed[6],timesCountingReversed[7],timesCountingReversed[8],timesCountingReversed[9]};

        // Save the char as .png and show it
        showAndSaveChart("Tests on Reversed Data", inputAxis, yAxis);

    }

    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Merge Sort", doubleX, yAxis[0]);
        //chart.addSeries("Insertion Sort", doubleX, yAxis[1]);
        chart.addSeries("Pigeonhole Sort", doubleX, yAxis[1]);
        chart.addSeries("Counting Sort", doubleX, yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }



}