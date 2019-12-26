import java.util.*;
import java.awt.Point;

public class Parcels{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int numOfTestCases = in.nextInt();

		for(int t = 0; t < numOfTestCases; t++){
			int numOfRows = in.nextInt();
			int numOfCols = in.nextInt();
			int[][] matrix = new int[numOfRows][numOfCols];
			List<Point> offices = new ArrayList<Point>();

			in.nextLine();
			for(int r = 0; r < numOfRows; r++){
				char[] line = in.nextLine().toCharArray();
				for(int c = 0; c < numOfCols; c++){
					matrix[r][c] = line[c] - '0';
					if(matrix[r][c] == 1)
						offices.add(new Point(r,c));
				}
			}

			int[][] distances = new int[numOfRows][numOfCols];
			calculateDistances(distances, matrix, offices);

			Point farthest = findFarthestPoint(distances);
			offices.add(farthest);
			matrix[farthest.x][farthest.y] = 1;

			int maxDist = calculateDistances(distances, matrix, offices);

			System.out.println("Case #"+(t+1)+": " + maxDist);
		}
	}

	public static int calculateDistances(int[][] distances, int[][] matrix, List<Point> offices){
		int maxDist = 0;
		for(int r = 0; r < matrix.length; r++){
			for(int c = 0; c < matrix[0].length; c++){
				if(matrix[r][c] == 1)
					continue;
				distances[r][c] = calculateMinDistToOffice(matrix, r, c, offices);
				maxDist = Math.max(distances[r][c], maxDist);
			}
		}

		return maxDist;
	}

	public static int calculateMinDistToOffice(int[][] matrix, int r, int c, List<Point> offices){
		int minDist = Integer.MAX_VALUE;
		for(Point office : offices){
			minDist = Math.min(minDist, calculateManhattanDistance(r, c, office.x, office.y));	
		}

		return minDist;
	}

	public static int calculateManhattanDistance(int r1, int c1, int r2, int c2){
		return Math.abs(r1-r2) + Math.abs(c1-c2);
	}

	public static Point findFarthestPoint(int[][] distances){
		int maxDist = Integer.MIN_VALUE;
		Point farthestPoint = new Point(0,0);
		for(int r = 0; r < distances.length; r++){
			for(int c = 0; c < distances[0].length; c++){
				if(maxDist < distances[r][c]){
					maxDist = distances[r][c];
					farthestPoint = new Point(r,c);
				}
			}
		}

		return farthestPoint;
	}
}
