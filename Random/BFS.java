import java.awt.Point;
import java.util.*;
public class BFS{
    public static void main(String[] args){
        int[][] lot = new int[][]{ 
        {1, 1, 1, 0, 0},
        {1, 1, 1, 1, 1},
        {1, 0, 1, 0, 1},
        {1, 1, 1, 0, 1},
        {0, 1, 1, 0, 9}
        };

        System.out.println(removeObstacle(3,3,lot));
    }

    static int removeObstacle(int numRows, int numColumns, int[][] lot){
        // remembers visited spots && distances
        Map<Point, Integer> distances = new HashMap<Point, Integer>();
        // Queue for BFS
        Deque<Point> queue = new ArrayDeque<Point>(); 

        // start at top-left corner 
        Point coord = new Point(0,0);   // I used a point because it holds x and y
        queue.offer(coord);
        distances.put(coord, 0);

        while(!queue.isEmpty()){
            coord = queue.poll();
            int x = (int)coord.getX();
            int y = (int)coord.getY();
            int distance = distances.get(coord);
            int currPos = lot[x][y];

            if(currPos == 9){
                return distance;
            }

            // Move up
            if(x > 0 && lot[x-1][y] != 0){
                Point up = new Point(x-1,y);
                if(!distances.containsKey(up)){ // if the point hasn't been visited  yet
                    queue.offer(up);
                    distances.put(up, distance+1);
                }
            }
            // Move down 
            if(x < lot.length-1 && lot[x+1][y] != 0){
                Point down = new Point(x+1,y);
                if(!distances.containsKey(down)){
                    queue.offer(down);
                    distances.put(down, distance+1);
                }
            }
            // Move left 
            if(y > 0 && lot[x][y-1] != 0){
                Point left = new Point(x, y-1);
                if(!distances.containsKey(left)){
                    queue.offer(left);
                    distances.put(left, distance+1);
                }
            }
            // Move right 
            if(y < lot[0].length-1 && lot[x][y+1] != 0){
                Point right = new Point(x, y+1);
                if(!distances.containsKey(right)){
                    queue.offer(right);
                    distances.put(right, distance+1);
                }
            }
       }

       return -1;
    }
}
