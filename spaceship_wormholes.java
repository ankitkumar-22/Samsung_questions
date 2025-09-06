/*
There is one spaceship. X and Y co-odinate of source of spaceship and destination spaceship is given.
There are N number of warmholes; each warmhole has 5 values. First 2 values are starting co-ordinate
of warmhole and after that value no. 3 and 4 represents ending co-ordinate of warmhole and last 5th 
value is represents cost to pass through this warmhole. Now these warmholes are bi-directional. Now 
the to go from (x1,y1) to (x2,y2) is abs(x1-x2)+abs(y1-y2). The main problem here is to find minimum 
distance to reach spaceship from source to destination co-ordinate using any number of warm-hole. 
It is ok if you wont use any warmhole.
*/

/*
The approach is very very simple, we try out each and every combination of wormholes (both ways entering from 1 to 2 and 2 to 1)
Then keep a track of the cost of each of the traversal and make sure that we reach a minimum
*/
import java.util.*;

public class WormholeSolverDijkstra {
    private static final long INF = Long.MAX_VALUE / 4;

    public long findMinDistance(int sx, int sy, int tx, int ty, int[][] wh) {
        if (wh == null) wh = new int[0][];
        int n = wh.length;
        int m = 2 * n + 2;
        int tgt = m - 1;

        int[][] pt = new int[m][2];
        pt[0][0] = sx; pt[0][1] = sy;
        for (int i = 0; i < n; i++) {
            pt[1 + 2 * i][0] = wh[i][0];
            pt[1 + 2 * i][1] = wh[i][1];
            pt[1 + 2 * i + 1][0] = wh[i][2];
            pt[1 + 2 * i + 1][1] = wh[i][3];
        }
        pt[tgt][0] = tx; pt[tgt][1] = ty;

        List<List<E>> g = new ArrayList<>(m);
        for (int i = 0; i < m; i++) g.add(new ArrayList<>());

        // all-pairs manhattan edges
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (i == j) continue;
                long w = man(pt[i][0], pt[i][1], pt[j][0], pt[j][1]);
                g.get(i).add(new E(j, w));
            }
        }

        // enforce min(walk, wormhole cost) for each wormhole pair
        for (int i = 0; i < n; i++) {
            int a = 1 + 2 * i, b = a + 1;
            long c = wh[i][4];
            long w = man(pt[a][0], pt[a][1], pt[b][0], pt[b][1]);
            long best = Math.min(w, c);
            rem(g.get(a), b);
            rem(g.get(b), a);
            g.get(a).add(new E(b, best));
            g.get(b).add(new E(a, best));
        }

        long[] dist = new long[m];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        PriorityQueue<N> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o.d));
        pq.add(new N(0, 0));

        while (!pq.isEmpty()) {
            N cur = pq.poll();
            if (cur.d != dist[cur.i]) continue;
            if (cur.i == tgt) break;
            for (E e : g.get(cur.i)) {
                long nd = cur.d + e.w;
                if (nd < dist[e.to]) {
                    dist[e.to] = nd;
                    pq.add(new N(e.to, nd));
                }
            }
        }

        return dist[tgt];
    }

    private static void rem(List<E> lst, int to) {
        for (int k = lst.size() - 1; k >= 0; k--) {
            if (lst.get(k).to == to) lst.remove(k);
        }
    }

    private static long man(int x1, int y1, int x2, int y2) {
        return (long) Math.abs(x1 - x2) + (long) Math.abs(y1 - y2);
    }

    private static final class E {
        final int to; final long w;
        E(int to, long w) { this.to = to; this.w = w; }
    }

    private static final class N {
        final int i; final long d;
        N(int i, long d) { this.i = i; this.d = d; }
    }
}
