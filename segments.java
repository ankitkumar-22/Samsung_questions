/*
The original problem description is a bit disjointed and poorly formatted, which is common in competitive programming platforms!

Here is a clear, plain-English breakdown of exactly what the question is asking you to do, without touching on how to solve it.

The Core Premise
Imagine an infinite 2D graph (like graph paper). On this graph, you have two things:

A drawn path made of straight lines.

A scatter of random dots (points).

Your singular goal is to figure out how many of those random dots lie exactly on the drawn path.

The Components
1. The Path (The M points)

You are given a sequence of coordinates that represent the "corners" or "turning points" of a continuous path.

The rules state that the path can only move horizontally (parallel to the X-axis) or vertically (parallel to the Y-axis). There are no diagonal lines.

Example: If the path points are (1,1) -> (1,5) -> (2,5), it means the path goes straight up from 1,1 to 1,5, makes a 90-degree right turn, and goes horizontally to 2,5.

2. The Query Points (The N points)

You are given a completely separate set of coordinates. These are just individual points scattered in space.

How the Input is Given
The way the inputs are provided is a bit unusual. Instead of giving you a list of (x, y) pairs, the problem separates the X and Y coordinates into parallel lists (arrays).

N and M: The number of query points and the number of path turning points, respectively.

Query Points Arrays:

You get an array of N numbers representing the X-coordinates.

You get another array of N numbers representing the corresponding Y-coordinates.

Path Points Arrays:

You get an array of M numbers representing the X-coordinates of the path's turning points.

You get another array of M numbers representing the corresponding Y-coordinates of the path.

Summary of Your Task
You must read in the path's corners, understand the horizontal and vertical line segments that connect them, and then check each of the N query points to see if it lands anywhere on those line segments. Finally, you just return the total count of points that touched the path.

3
5 4
1 3 2 4 5
3 5 2 1 5
1 1 4 4
1 5 5 1
3 3
1 3 6
0 0 0
0 5 2
0 0 0
2 5
2 5
3 6
0 0 4 4 0
0 4 4 0 0

answer
3
2
0

 */
import java.util.*;
class Pair{
    int x;
    int y;
    Pair(){}
    Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        int[] res = new int[tc];
        for(int t = 0;t<tc;t++){
            int numQuery = sc.nextInt();
            int numLines = sc.nextInt();
            Pair[] query = new Pair[numQuery];
            for(int i = 0;i<numQuery;i++){
                query[i] = new Pair();
                query[i].x = sc.nextInt();
            }
            for(int i = 0;i<numQuery;i++){
                //query[i] = new Pair();
                query[i].y = sc.nextInt();
            }
            
            Pair[] lines = new Pair[numLines];
            for(int i = 0;i<numLines;i++){
                lines[i] = new Pair();
                lines[i].x = sc.nextInt();
            }
            for(int i = 0;i<numLines;i++){
                //lines[i] = new Pair();
                lines[i].y = sc.nextInt();
            }
            //HashMap construction
            HashMap<Integer,List<Pair>> xc = new HashMap<>();
            HashMap<Integer,List<Pair>> yc = new HashMap<>();
            for(int i = 0;i<numLines-1;i++){
                if(lines[i].x==lines[i+1].x){
                    if(!xc.containsKey(lines[i].x))
                        xc.put(lines[i].x,new ArrayList<>());
                    List<Pair> intervals = xc.get(lines[i].x);
                    intervals.add(new Pair(Math.min(lines[i].y,lines[i+1].y),
                            Math.max(lines[i].y,lines[i+1].y)));
                }
                if(lines[i].y==lines[i+1].y){
                    if(!yc.containsKey(lines[i].y))
                        yc.put(lines[i].y,new ArrayList<>());
                    List<Pair> intervals = yc.get(lines[i].y);
                    intervals.add(new Pair(Math.min(lines[i].x,lines[i+1].x),
                            Math.max(lines[i].x,lines[i+1].x)));
                }
            }
            sort(xc);
            sort(yc);
            //print(xc);
            // System.out.println();
            // print(yc);
            int count = 0;
            for(int i = 0;i<numQuery;i++){
                boolean inLine = false;
                if(xc.containsKey(query[i].x)){
                    inLine = binarySearchIntervals(xc.get(query[i].x),query[i].y);
                }
                if(!inLine && yc.containsKey(query[i].y)){
                    inLine = binarySearchIntervals(yc.get(query[i].y),query[i].x);
                }
                if(inLine)
                    count++;
            }
            res[t] = count;
        }
        for(int i = 0;i<tc;i++){
            System.out.println(res[i]);
        // System.out.println();
        // System.out.println();
        // System.out.println();
        }
        sc.close();
    }
    public static void sort(HashMap<Integer,List<Pair>> map){
        for(int key : map.keySet()){
            List<Pair> curr = map.get(key);
            Collections.sort(curr,((a,b)->(a.x-b.x)));
            List<Pair> next = new ArrayList<>();
            int idx = 0;
            Pair current = curr.get(idx);
            while(idx<curr.size()){
                current = curr.get(idx);
                idx++;
                while(idx<curr.size() && current.y>=curr.get(idx).x){
                    int newStart = Math.min(current.x,curr.get(idx).x);
                    int newEnd = Math.max(current.y,curr.get(idx).y);
                    current = new Pair(newStart,newEnd);
                    idx++;
                }
                next.add(current);
                
            }
            map.put(key,next);
        }
    }
    public static void print(HashMap<Integer,List<Pair>> map){
        for(int key: map.keySet()){
            System.out.println(key);
            for(Pair intervals : map.get(key)){
                System.out.println(intervals.x +" "+intervals.y);
            }
        }
    }
    // O(log K) Binary Search for checking if a value falls inside disjoint, sorted intervals
    public static boolean binarySearchIntervals(List<Pair> intervals, int target) {
        int left = 0;
        int right = intervals.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Pair midInterval = intervals.get(mid);

            if (target >= midInterval.x && target <= midInterval.y) {
                return true; // Target is inside this interval
            } else if (target < midInterval.x) {
                right = mid - 1; // Target is to the left of this interval
            } else {
                left = mid + 1; // Target is to the right of this interval
            }
        }
        return false;
    }
}
