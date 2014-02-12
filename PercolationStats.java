/****************************************************************************
 *  Author:       Muruo Liu
 *  Written:      02/11/2014
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation 
 *  Dependencies: Percolation.java
 *
 *  This program performes T independent computational experiments on an N-by-N grid
 *  and calculates the mean, standard deviation, confidence interval of percolation
 *  probability
 ****************************************************************************/



public class PercolationStats {
   private int times = 0; // times independent experiment
   private int size  = 0;  // grid size
   private double[] p;    // vector of probability of percolation 
   /**This constructor assign the times of independent experiment
    * and the grid size
    * @param N, T
    * N indicates the size of the grid
    * T indicates the times of independent experiment
    */
   public PercolationStats(int N, int T)   
   {
       if ( N <= 0 || T <= 0 ) 
        {
            throw new IllegalArgumentException("parameters must be positive");
        }
       times = T;
       size = N;
       p = new double[times];
       for (int i = 0; i < times; i ++)
       {
           double count = 0.0; // number of opened site
           Percolation conne = new Percolation(size); // create the grid ready for percolation
           while (!conne.percolates())
           {
               int x = StdRandom.uniform(size) + 1;  // assign xth row
               int y = StdRandom.uniform(size) + 1;  // assign yth column
               if (!conne.isOpen(x, y))
               {
                   conne.open(x, y);
                   count ++;    
               }
           }
           p[i] = count / (size * size); 
       } 
   }
   
   /**This method provides mean probability of percolation
    * @return mean
    * mean probability of percolation
    */
   public double mean()                     
   {
       return StdStats.mean(p);
   }
   
   /**This method provides standard deviation for probability of percolation
    * @return stddev
    * standard deviation for probability of percolation
    */
   public double stddev()    
   {
       if (times == 1) //if times is 1, the sample standard deviation is undefined
       {
           return Double.NaN;
       }
       else
       {
           return StdStats.stddev(p);
       }
   }
   
    /**This method provides lower bound of the 95% confidence interval
    * @return confidenceLo
    * lower bound of the 95% confidence interval
    */
   public double confidenceLo()  
   {
       return mean() - 1.96 * stddev() / Math.sqrt(times);
   }
   
   /**This method provides higher bound of the 95% confidence interval
    * @return confidenceHi
    * higher bound of the 95% confidence interval
    */
   public double confidenceHi()   
   {
       return mean() + 1.96 * stddev() / Math.sqrt(times);
   }
   
   public static void main(String[] args)  // test client, perform Monte Carlo simulation described below
   {
       int N = 0;
       int T = 0;
       N = Integer.parseInt(args[0]);
       T = Integer.parseInt(args[1]);
       PercolationStats test = new PercolationStats(N, T);
       StdOut.println("mean                    = " + test.mean());
       StdOut.println("stddev                  = " + test.stddev());
       StdOut.println("95% confidence interval = " + test.confidenceLo() + ", " + test.confidenceHi());
   }
}