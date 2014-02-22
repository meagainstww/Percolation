Percolation
===========
Coursera: Algorithms, Part I, Princeton University---Programming Assignments, Week 1
------------------------------------------------------------------------------------

### 1. classpath stdlib.jar and algs4.jar
### 2. Percolation.java 
    It is used to model a percolation system, create a data type Percolation with the following API:
    public class Percolation {
        public Percolation(int N)              // create N-by-N grid, with all sites blocked
        public void open(int i, int j)         // open site (row i, column j) if it is not already
        public boolean isOpen(int i, int j)    // is site (row i, column j) open?
        public boolean isFull(int i, int j)    // is site (row i, column j) full?
        public boolean percolates()            // does the system percolate?
    }
    By convention, the indices i and j are integers between 1 and N. A java.lang.IndexOutOfBoundsException if either i or
    j is outside this range.
### 3. PercolationStats 
    It is used to perform Monte Carlo simulation:
    (1) Initialize all sites to be blocked.
    (2) Repeat the following until the system percolates:
            a. Choose a site (row i, column j) uniformly at random among all blocked sites.
            b. Open the site (row i, column j).
    (3) The fraction of sites that are opened when the system percolates provides an estimate of the percolation
        threshold.
        
### 4. PercolationStats 
    It is also used to calculate the sample mean of percolation threshold, sample standard deviation
    of percolation threshold and also lower and upper bound of the 95% confidence interval.
    To perform a series of computational experiments, create a data type PercolationStats with the following API:
    public class PercolationStats {
        public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
        public double mean()                     // sample mean of percolation threshold
        public double stddev()                   // sample standard deviation of percolation threshold
        public double confidenceLo()             // returns lower bound of the 95% confidence interval
        public double confidenceHi()             // returns upper bound of the 95% confidence interval
           public static void main(String[] args)   // test client, described below
    }
    The constructor throws a java.lang.IllegalArgumentException if either N ≤ 0 or T ≤ 0.
    a main() method that takes two command-line arguments N and T, performs T independent computational experiments 
    (discussed above) on an N-by-N grid, and prints out the mean, standard deviation, and the 95% confidence interval 
    for the percolation threshold.
    
### 5. [More specific details](http://blog.sina.com.cn/s/blog_73b055450101j3ap.html)
