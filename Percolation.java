/****************************************************************************
 *  Author:       Muruo Liu
 *  Written:      02/10/2014
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation 
 *  Dependencies: WeightedQuickUnionUF.java
 *
 *  This program describe a class that can test whether a system is percolated 
 *  and whether a certain site is full and open
 ****************************************************************************/

public class Percolation 
{
    private int row               =  0; //initialize the bound
    private boolean[] openFlag; //indicate the open status
    private WeightedQuickUnionUF conne; 
    //construct a variable to implement WeightedQuickUnionUF
    private WeightedQuickUnionUF conneBackwash; 
    //construct a variable to implement 
    //WeightedQuickUnionUF and solve backwash bug
    private int numTop             = 1; // number of top virtual node
    private int numBottom          = 1; //number of bottom virtual node
    /**This constructor creates N-by-N grid, with all sites blocked
      * initialize the open status of each node
      * union virtual top to top row
      * union virtual bottom to bottom row
      * @param N
      * determine the size of the grid, which is N
      */  
    public Percolation(int N)  
    {
        row      = N; 
        int len  = xyTo1D(N, N); //count the length of 1D index
        openFlag = new boolean[len];  
        conne    = new WeightedQuickUnionUF(N * N + numTop + numBottom); 
        //initialize the N * N matrix with virtual top and virtual bottom
        conneBackwash = new WeightedQuickUnionUF(N * N + numTop);
        //initialize the N * N matrix with only virtual top
        for (int i = 0; i < len; i++) //initialize the open status
        {
            openFlag[i] = false;
        }        
        for (int i = 0; i < row; i++) //union virtual top to top row
        {
            conne.union(row * row, i);
            conneBackwash.union(row * row, i);
        }
        for (int i = row * (row - 1); i < row * row; i++) 
        //union virtual bottom to bottom row
        {
            conne.union(row * row + 1, i);
        }
    }
    
     /**This method converts 2D index to 1D index
     * @param x
     * number of rows
     * @param y
     * number of columns
     * @return x * y
     * 1D index
     */
    private int xyTo1D (int  x, int y)
    {
        CheckBound(x, row);
        CheckBound(y, row);
        return x * y;
    }
    
    /**This method is to check the validation of input
      * if the input is not valid, throw the exception
      * @param i
      * input
      * @param row
      * size of the grid
      * @ throws IndexOutOfBoundsException
      * if i <= 0 or i > row occurred
      */
    private void CheckBound(int i, int row)
    {
        if ( i <= 0 || i > row ) // bound of input is 1<= i <= N
        {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
    }
    /**This method first validates the indices of the site that it receives. 
      * Second, it somehow marks the site as open. 
      * Third, it performes some sequence of WeightedQuickUnionUF operations 
      * that links the site in question to its open neighbors
      * @param i
      * No.i row
      * @param j
      * No.j column
      */
    public void open(int i, int j)
    { 
        CheckBound(i, row);
        CheckBound(j, row);
        int current = (i - 1) * row + j -1;  //current site 
        int left = (i - 1) * row + j -2;        //left site
        int right = (i - 1) * row + j;    //right site
        int up = (i - 1) * row + j -1 - row; //up site
        int down = (i - 1) * row + j -1 + row;//down site
        openFlag[current] = true; //change the open status
        if (row == 1)          //special condition for size 1
        {
            conne.union(current, row * row);
            conneBackwash.union(current, row * row);
        }
        else
        {
            if ((j -1) > 0 && isOpen(i, j -1))       //check whether the left site 
            {                                        // is open and exclude the
                 conne.union(current, left);         //situation that the left of 
                 conneBackwash.union(current, left);//current site exceed the range
            }                                 
            if ((j + 1) <= row && isOpen(i, j + 1))
            {
                conne.union(current, right);
                conneBackwash.union(current, right);
            }
            if ((i + 1) <= row && isOpen(i + 1, j))
            {
                conne.union(current, down);
                conneBackwash.union(current, down);
            }
            if ((i -1) > 0 && isOpen(i - 1, j))
            {
                conne.union(current, up);
                conneBackwash.union(current, up);
            }
        }
    }
    
    /**check if site (row i, column j) open
     * @param i
     * No.i row
     * @param j
     * No.j column
     * @return open status
     * whether site (row i, column j) is open
     */
    public boolean isOpen(int i, int j)
    {
        CheckBound(i, row);
        CheckBound(j, row);
        int current = (i - 1) * row + j -1;
        if (openFlag[current] == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**check if site (row i, column j) full
     * @param i
     * No.i row
     * @param j
     * No.j column
     * @return full status
     * whether site (row i, column j) is full
     */
    public boolean isFull(int i, int j)
    {
        CheckBound(i, row);
        CheckBound(j, row);
        int current = (i - 1) * row + j -1;
        if (conneBackwash.connected(current, row * row) && isOpen(i, j)) // if current site is connected to the virtual top and is open,then it must be full
        {                                                                 // Attention: In this place, we use conneBackwash which has only virtual top 
            return true;                                                  // instead of conne just to solve backwash bug.
        }                                                                 
        else
        {
            return false;
        }
    }
    
    /**check if the system percolates
     * @return percolation status
     * whether the system percolates
     */
    public boolean percolates()  
    {
        if (row == 1)
        {
            if (isOpen(1, 1))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if (conne.connected(row * row + 1, row * row)) //if virtual top is connected to virtual
            {                                              //bottom, then the system percolates
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}    