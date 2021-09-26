
/**
 * A 2D cartesian plane implemented as with an array. Each (x,y) coordinate can
 * hold a single item of type <T>.
 *
 * @param <T> The type of element held in the data structure
 */
public class ArrayCartesianPlane<T> implements CartesianPlane<T> {

    private T[][] grid;
    private int minimumX;
    private int maximumX;
    private int minimumY;
    private int maximumY;
    /**
     * Constructs a new ArrayCartesianPlane object with given minimum and
     * maximum bounds.
     *
     * Note that these bounds are allowed to be negative.
     *
     * @param minimumX A new minimum bound for the x values of
     *         elements.
     * @param maximumX A new maximum bound for the x values of
     *         elements.
     * @param minimumY A new minimum bound for the y values of
     *         elements.
     * @param maximumY A new maximum bound for the y values of
     *         elements.
     * @throws IllegalArgumentException if the x minimum is greater
     *         than the x maximum (and resp. with y min/max)
     */
    public ArrayCartesianPlane(int minimumX, int maximumX, int minimumY,
            int maximumY) throws IllegalArgumentException {
        // TODO: implement the constructor
        if (outOfBound( minimumX, maximumX, minimumY, maximumY ))
            throw new IllegalArgumentException("Minimum greater than maximum!");
        this.minimumX = minimumX;
        this.minimumY = minimumY;
        this.maximumX = maximumX;
        this.maximumY = maximumY;
        this.grid = (T[][]) new Object [calculateAxisSize(minimumX, maximumX)][calculateAxisSize(minimumY, maximumY)];
        clear();
    }

    // TODO: you are to implement all of ArrayCartesianPlanes's methods here

    @Override
    public void add(int x, int y, T element) throws IllegalArgumentException {
        if (!checkInside(x,y))
            throw new IllegalArgumentException("Out of Bounds");
        setElement(x, y, element);
    }
    @Override
    public T get(int x, int y) throws IndexOutOfBoundsException {
        if (!checkInside(x,y))
            throw new IndexOutOfBoundsException("Out of bound");
        return getElement(x, y);
    }

    @Override
    public boolean remove(int x, int y) throws IndexOutOfBoundsException {
        if (!checkInside(x,y))
            throw new IndexOutOfBoundsException("Out of bound");
        if (!isPresent(getElement(x, y))) {
            return false;
        }
        setElement(x, y, null);
        return true;
    }

    @Override
    public void clear() {
        clearH(this.grid, this.minimumX, this.maximumX, this.minimumY, this.maximumY);
    }

    @Override
    public void resize(int newMinimumX, int newMaximumX, int newMinimumY, int newMaximumY) throws IllegalArgumentException {
        if (outOfBound(newMinimumX, newMaximumX, newMinimumY, newMaximumY)) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        //Checks if a value is being lost, short circuits if the value is contained inside the new grid
        for (int x = this.minimumX; x <= this.maximumX; x++) {
            for (int y = this.minimumY; y <= this.maximumY; y++) {
                if (!checkInsideH(x,y, newMinimumX, newMaximumX, newMinimumY, newMaximumY) && isPresent(getElement(x, y)))
                    throw new IllegalArgumentException("Loss of data");
            }
        }
        // new grid
        T[][] tmpGrid = (T[][]) new Object [calculateAxisSize(newMinimumX, newMaximumX)][calculateAxisSize(newMinimumY, newMaximumY)];
        //clear the grid i.e. fill with nulls, have to clear as copying from other size grid may not fill whole grid
        clearH(tmpGrid, newMinimumX, newMaximumX, newMinimumY, newMaximumY);

        // copy the elements
        for (int x = minimumX; x <= maximumX; x++) {
            for (int y = minimumY; y <= maximumY; y++){
                setGridElement(tmpGrid, x, y, getElement(x,y));
            }
        }
        this.minimumX = newMinimumX;
        this.minimumY = newMinimumY;
        this.maximumX = newMaximumX;
        this.maximumY = newMaximumY;

        this.grid = tmpGrid;
    }

    // Helper Functions
    //for both x and y if value is even negative provided
    private int calculateAxisSize (int min, int max) {
        return max - min + 1;
    }

    //outside the grid?
    private boolean outOfBound (int minX, int maxX, int minY, int maxY) {
        return maxX < minX || maxY < minY;
    }

    // is not null
    private boolean isPresent ( T obj) {
        return !(obj == null);
    }

    // fix the coordiantes for the array as a mins are the actuall 0's in the array array starts at 0,0 not -n,-m
    private int[] resolveCoordinates ( int x, int y ) {
        int[] xy = new int[2];
        xy[0] = resolveCoordinate(x,this.minimumX);
        xy[1] = resolveCoordinate(y, this.minimumY);
        return xy;
    }

    // fix a single coordinate with respect to minimum
    private int resolveCoordinate(int c, int min) {
        return c-min;
    }

    // give it
    private T getElement (int x, int y) {
        int[] xy = resolveCoordinates(x, y);
        return this.grid[xy[0]][xy[1]];
    }

    // set it
    private void setElement (int x, int y, T e) {
        setGridElement(this.grid, x, y, e);
    }

    // generic setter for code reuse in resize tmpGrid
    private void setGridElement ( T[][] g, int x, int y, T e) {
        int[] xy = resolveCoordinates(x, y);
        g[xy[0]][xy[1]] = e;
    }

    //check inside
    private boolean checkInside(int x, int y) {
        return checkInsideH(x, y, minimumX ,maximumX ,minimumY ,maximumY);
    }

    // generic checker code resuse
    private boolean checkInsideH (int x, int y, int miX, int maX, int miY, int maY) {
        return x >= miX && x <= maX && y >= miY && y <= maY;
    }

    // generic grid clear code reuse
    public void clearH (T[][] g, int minX, int maxX, int minY, int maxY){
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY;y <= maxY;y++) {
                setGridElement(g, x, y, null);
            }
        }
    }

}

