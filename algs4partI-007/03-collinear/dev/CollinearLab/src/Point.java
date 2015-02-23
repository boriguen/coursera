import java.util.Arrays;
import java.util.Comparator;

/**
 * @author boris
 *
 */
public class Point implements Comparable<Point> {
	
	public final Comparator<Point> SLOPE_ORDER;
	
	private final int x;
	private final int y;
	
	public Point(int x, int y) {
		this.SLOPE_ORDER = new BySlope();
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Runs unit tests.
	 * @param args
	 */
	public static void main(String[] args) {
		Point p1 = new Point(3, 7);
		Point p2 = new Point(-4, -8);
		Point p3 = new Point(3, -8);
		Point p4 = new Point(3, 7);
		
		// Test Comparable.
		assert p1.compareTo(p2) == 1;
		assert p2.compareTo(p3) == -1;
		assert p1.compareTo(p4) == 0;
		
		// Test Comparator.
		Point[] points = new Point[]{p1, p2, p3, p4};
		StdOut.println(Arrays.toString(points));
		Arrays.sort(points, p1.SLOPE_ORDER);
		StdOut.println(Arrays.toString(points));
		assert points[0] == p1;
		assert points[1] == p4;
		assert points[2] == p2;
		assert points[3] == p3;
	}
	
	/**
	 * Draws this point.
	 */
	public void draw() {
		StdDraw.point(this.x, this.y);
	}
	
	/**
	 * Draws the line segment from this point to that point. 
	 * @param that
	 */
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}
	
	/**
	 * Returns the slope between this point and that point.
	 * @param that
	 * @return
	 */
	public double slopeTo(Point that) {
		double slopeTo = 0;
		
		if (this.toString().equals(that.toString())) {
			slopeTo = Double.NEGATIVE_INFINITY;
		} else {
			if (this.x == that.x) {
				slopeTo = Double.POSITIVE_INFINITY;
			} else if (this.y != that.y){
				slopeTo = (double) (that.y - this.y) / (double) (that.x - this.x);
			}
		}
		
		return slopeTo;
	}
	
	/**
	 * Returns the string representation.
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	/**
	 * Is this point lexicographically smaller than that point?
	 */
	public int compareTo(Point that) {
		int compareTo = 0;
		
		if (this.y < that.y || this.y == that.y && this.x < that.x) {
			compareTo = -1;
		} else if (this.x > that.x){
			compareTo = 1;
		}
		
		return compareTo;
	}
	
	private class BySlope implements Comparator<Point> {

		@Override
		public int compare(Point that, Point other) {
			double slopeThisThat = Point.this.slopeTo(that);
			double slopeThisOther = Point.this.slopeTo(other);
			return Double.compare(slopeThisThat, slopeThisOther);
		}
		
	}

}
