public class UnionFind {
    private int[] parents;
    private int[] size;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parents = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i += 1) {
            parents[i] = i;
            size[i] = 1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex > parents.length || vertex < 0) {
            throw new RuntimeException("Out of index.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        int root = parent(v1);
        return size[root];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return parents[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        int parentOfv1 = find(v1);
        int parentOfv2 = find(v2);

        if (sizeOf(parentOfv1) > sizeOf(parentOfv2)) {
            parents[parentOfv2] = parentOfv1;
            size[parentOfv1] += sizeOf(parentOfv2);
        } else {
            parents[parentOfv1] = parentOfv2;
            size[parentOfv2] += sizeOf(parentOfv1);
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        int dad = vertex;
        while(parent(dad) != dad) {
            dad = parent(dad);
        }

        while (vertex != dad) {
            int temp = parent(vertex);
            parents[vertex] = dad;
            vertex = temp;
        }

        return dad;
    }

}
