package bridges.base;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;


public class BenchMarking {
	private Plot plot;
	
	public BenchMarking(Plot p) {
		this.plot = p;
	}
	
	public void run(String title, int iter, int maxRun, Consumer<int[]> runnable) {
		Random r = new Random();
		long[] time = new long[iter];
		int[] xData = new int[iter];
		int size = maxRun/iter; //determines the size of each iteration
		int n = 0;
		
		for(int j = 1; j <= iter; j++) {
			n += size;
			System.out.println(n);
			int[] arr = new int[n];
			for(int i = 0; i < arr.length; i++) {
				arr[i] = r.nextInt(10000);
			}
			long start = System.currentTimeMillis();
			runnable.accept(arr);
			long end = System.currentTimeMillis();
			long runTime = end - start;
			time[j-1] = runTime;
			xData[j-1] = n;
		}
		plot.setXData(title, xData);
		plot.setYData(title, time);
	}

}
