package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private WorldState w;
        private int moves;   // The number of steps required from the initial node.
        private int eDist;
        private SearchNode parent;

        public SearchNode(WorldState w, int moves, SearchNode parent) {
            this.w = w;
            this.moves = moves;
            this.parent = parent;
            eDist = w.estimatedDistanceToGoal();
        }

        private int value() {
            return moves + eDist;
        }

        @Override
        public int compareTo(SearchNode other) {
            return this.value() - other.value();
        }

        private class AStarComparator implements Comparator<SearchNode> {
            public int compare(SearchNode n1, SearchNode n2) {
                return n1.compareTo(n2);
            }
        }

        private boolean isParent(WorldState o) {
            return parent.w.equals(o);
        }
    }

    private SearchNode target;
    private SearchNode start;
    private int moves;
    private ArrayList<WorldState> sol;

    public Solver(WorldState initial) {
        start = new SearchNode(initial, 0, null);
        moves = 0;
        solve();
        sol = getSolution();
    }

    private void solve() {
        MinPQ<SearchNode> pq = new MinPQ<>(SearchNode::compareTo);
        pq.insert(start);

        while (true) {
            SearchNode x = pq.delMin();
            WorldState v = x.w;
            if (v.isGoal()) {
                target = x;
                moves = x.moves;
                return;
            }
            for (WorldState w : v.neighbors()) {
                if (x.parent == null || !x.isParent(w)) {
                    SearchNode newNode = new SearchNode(w, x.moves + 1, x);
                    pq.insert(newNode);
                }
            }
        }
    }

    private ArrayList<WorldState> getSolution() {
        SearchNode ptr = target;
        ArrayList<WorldState> res = new ArrayList<>();
        while (ptr != null) {
            res.add(ptr.w);
            ptr = ptr.parent;
        }
        return res;
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return sol;
    }
}
