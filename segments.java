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
import java.io.*;
public class Samsung {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder res;
    public static void main(String[] args) throws IOException{
        br= new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        res = new StringBuilder();
        while(t-->0){
            fn();
        }
        System.out.println(res);
    }
    public static void fn() throws IOException {
        st = new StringTokenizer(br.readLine());
        int qLen = Integer.parseInt(st.nextToken());
        int pLen = Integer.parseInt(st.nextToken());
        int[][] query = new int[qLen][2];
        st = new StringTokenizer(br.readLine());
        for(int i = 0;i<qLen;i++)
            query[i][0] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for(int i = 0;i<qLen;i++)
            query[i][1] = Integer.parseInt(st.nextToken());

        int[][] points = new int[pLen][2];
        st = new StringTokenizer(br.readLine());
        for(int i = 0;i<pLen;i++)
            points[i][0]= Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for(int i = 0;i<pLen;i++)
            points[i][1] = Integer.parseInt(st.nextToken());

        HashMap<Integer,int[]> xRange = new HashMap<>();
        HashMap<Integer,int[]> yRange = new HashMap<>();

        for(int i = 1;i<pLen;i++){
            if(points[i][0]==points[i-1][0]){
                if(!xRange.containsKey(points[i][0]))
                    xRange.put(points[i][0],new int[]{Integer.MAX_VALUE,Integer.MIN_VALUE});
                int[] arr = xRange.get(points[i][0]);
                arr[0] = Math.min(arr[0],Math.min(points[i][1],points[i-1][1]));
                arr[1] = Math.max(arr[1],Math.max(points[i][1],points[i-1][1]));
                xRange.put(points[i][0],arr);
            }
            else{
                //we are promised a continuous path thus one co-ord is bound to match
                if(!yRange.containsKey(points[i][1]))
                    yRange.put(points[i][1],new int[]{Integer.MAX_VALUE,Integer.MIN_VALUE});
                int[] arr = yRange.get(points[i][1]);
                arr[0] = Math.min(arr[0],Math.min(points[i][0],points[i-1][0]));
                arr[1] = Math.max(arr[1],Math.max(points[i][0],points[i-1][0]));
                yRange.put(points[i][1],arr);
            }
        }

        int count = 0;
        for(int i = 0; i<qLen;i++){
            if(xRange.containsKey(query[i][0])){
                int[] range = xRange.get(query[i][0]);
                if(query[i][1]>=range[0] && query[i][1]<=range[1]) {
                    count++;
                    continue;
                }
            }
            if(yRange.containsKey(query[i][1])){
                int[] range = yRange.get(query[i][1]);
                if(query[i][0]>=range[0] && query[i][0]<=range[1]){
                    count++;
                }
            }
        }
        res.append(count).append('\n');
    }
}
