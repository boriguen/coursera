import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 */

/**
 * @author boris
 *
 */
public class Fast {

	private static final Set<String> lineSegmentKeys = new HashSet<String>();
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Only the path of a file is expected");
		}
		
		Scanner scanner = new Scanner(new File(args[0]));
		if (scanner.hasNextInt()) {
			lineSegmentKeys.clear();
			
			// Rescale coordinate system.
			StdDraw.setXscale(0, 32768);
			StdDraw.setYscale(0, 32768);
			
			// Retrieve the points from the file.
			Point[] points = new Point[scanner.nextInt()];
			int i = 0;
			while(scanner.hasNextInt()) {
				Point p = new Point(scanner.nextInt(), scanner.nextInt());
				p.draw();
				points[i++] = p;
			}
			scanner.close();
			
			if (points.length > 3) {
				// Examine M points of which slope is equal to current index point.
				for (i = 0; i < points.length; i++) {
					// Sort by coordinate.
					Arrays.sort(points);
					
					// Retrieve current point p.
					Point p = points[i];
					
					// Sort by slope with respect to p.
					Arrays.sort(points, p.SLOPE_ORDER);
					//StdOut.println(Arrays.toString(points));
					// Count the number of adjacent points of equal slope
					// with respect to p.
					boolean sameSlope = true;
					int count = 2;
					for (int j = 1, k = j + 1; k < points.length; j++, k++) {
						sameSlope = p.slopeTo(points[j]) == p.slopeTo(points[k]);
						if (sameSlope) {
							count++;
						} else {
							// If 3 points or more are collinear.
							if (count > 3) {
								processPointsSubset(p, points, count, k);
							}
							// Restart count.
							count = 2;
						}
					}
					
					// If 3 points or more are collinear.
					if (count > 3) {
						processPointsSubset(p, points, count, points.length);
					}
				}
			}
		}
	}
	
	/**
	 * Creates points subset from the given current reference point,
	 * the given array of all points, the given subset count and the given
	 * max index.
	 * @param refPoint
	 * @param allPoints
	 * @param count
	 * @param maxIndex
	 */
	private static void processPointsSubset(Point refPoint, Point[] allPoints, int count, int maxIndex) {
		// Store them in a subarray.
		Point[] pointsSubset = new Point[count];
		pointsSubset[0] = refPoint;
		for (int l = maxIndex - count + 1, m = 1; m < count; l++, m++) {
			pointsSubset[m] = allPoints[l];
		}
		
		// And print them.
		printDrawOrderedLineSegment(pointsSubset);
	}
	
	/**
	 * Orders the array according to the position in the slope first and then prints
	 * the line segment as a string:
	 * e.g. (10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000)
	 * @param points
	 */
	private static void printDrawOrderedLineSegment(Point[] points) {
		Arrays.sort(points);
		String key = Arrays.toString(points);
		if (!lineSegmentKeys.contains(key)) {
			lineSegmentKeys.add(key);
			StringBuilder sb = new StringBuilder(points[0].toString());
			for (int i = 1; i < points.length; i++) {
				sb.append(" -> ").append(points[i]);
			}
			points[0].drawTo(points[points.length - 1]);
			StdOut.println(sb.toString());
		}
	}

}
