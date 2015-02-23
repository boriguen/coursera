import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author boris
 *
 */
public class Brute {

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
			
			if (points.length > 1) {
				if (points.length  == 2) {
					printDrawOrderedLineSegment(points);
				} else if (points.length == 3) {
					if (areSameLineSegmentPoints(points)) {
						printDrawOrderedLineSegment(points);
					}
				} else {
					// Examine 4 points at a time.
					for (i = 0; i < points.length - 3; i++) {
						Point p1 = points[i];
						for (int j = i + 1; j < points.length - 2; j++) {
							Point p2 = points[j];
							for (int k = j + 1; k < points.length - 1; k++) {
								Point p3 = points[k];
								for (int l = k + 1; l < points.length; l++) {
									Point p4 = points[l];
									Point[] pointsSubset = new Point[]{p1, p2, p3, p4};
									
									// If all slopes are equal relative to P1.
									if (areSameLineSegmentPoints(pointsSubset)) {
										printDrawOrderedLineSegment(pointsSubset);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Orders the array according to the position in the slope first and then prints
	 * the line segment as a string:
	 * e.g. (10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000)
	 * @param points
	 */
	private static void printDrawOrderedLineSegment(Point[] points) {
		Arrays.sort(points);
		StringBuilder sb = new StringBuilder(points[0].toString());
		for (int i = 1; i < points.length; i++) {
			sb.append(" -> ").append(points[i]);
			points[i-1].drawTo(points[i]);
		}
		StdOut.println(sb.toString());
	}
	
	/**
	 * Checks whether the points in the given array belong to the same line segment.
	 * @param points
	 * @return
	 */
	private static boolean areSameLineSegmentPoints(Point[] points) {
		// Calculate the slope from P1 to the 3 other points.
		double slopeP1P2 = points[0].slopeTo(points[1]);
		double slopeP1P3 = points[0].slopeTo(points[2]);
		double slopeP1P4 = points[0].slopeTo(points[3]);
		
		return slopeP1P2 == slopeP1P3 && slopeP1P2 == slopeP1P4;
	}

}
