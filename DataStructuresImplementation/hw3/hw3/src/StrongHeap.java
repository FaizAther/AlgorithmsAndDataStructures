public class StrongHeap {
    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    private static final int D1 = 0;
    private static final int D2 = 1;
    private static final int LE = 2;
    /**
     * Time Complexity O(n) n being the number of nodes
     * Space Complexity O(n) each n at level 2 returns an 2D array of 2 by 3 ints
     *
     *
     * Determines whether the binary tree with the given root node is
     * a "strong binary heap", as described in the assignment task sheet.
     *
     * A strong binary heap is a binary tree which is:
     *  - a complete binary tree, AND
     *  - its values satisfy the strong heap property.
     *
     * @param root root of a binary tree, cannot be null.
     * @return true if the tree is a strong heap, otherwise false.
     */
    public static boolean isStrongHeap(BinaryTree<Integer> root) {
        if(root.getRight() == null && root.getLeft() == null)
            return true;
        else if (root.getLeft() == null && root.getRight() != null)
            return false;
        else if (root.getRight() == null && root.getLeft().getLeft() == null) {
            return root.getLeft().getValue() < root.getValue();
        } else if (root.getRight().getRight() == null && root.getLeft().getLeft() == null) {
            if (root.getRight().getLeft() == null && root.getLeft().getRight() == null) {
                return root.getRight().getValue() < root.getValue() && root.getLeft().getValue() < root.getValue();
            }
        }
            return isStrongHeap_(root) != null;

    }
    /**
     * Time Complexity O(n) n being the number of nodes
     * Space Complexity O(n) each n at level 2 returns an 2D array of 2 by 3 ints
     * */
    private static int[][] isStrongHeap_(BinaryTree<Integer> node) {

        if (node == null) {
            return new int[][]{{0, 0, 0}, {0,0,0}};
        }else if (node.getLeft() == null && node.getRight() != null)
            return null;

        int[][] resL, resR;
        resL = isStrongHeap_(node.getLeft());
        resR = isStrongHeap_(node.getRight());
        if (resL == null || resR == null) {
            return null;
        }
        if (resL[LEFT][LE] <= -2 && resL[LEFT][D1] + resL[LEFT][D2] >= node.getValue())
            return null;
        else if (resR[RIGHT][LE] <= -2 && resR[RIGHT][D1] + resR[RIGHT][D2] >= node.getValue())
            return null;
        else if (resL[RIGHT][LE] <= -2
                && resL[LEFT][D1] + resL[RIGHT][D2] >= node.getValue())
            return null;
        else if (resR[LEFT][LE] <= -2
                && resR[RIGHT][D1] + resR[LEFT][D2] >= node.getValue())
            return null;

        int[][] res = new int[][] {{0,0,0},{0,0,0}};
        res[LEFT][D2] = resL[LEFT][D1];
        res[LEFT][D1] = node.getValue();
        res[LEFT][LE] = resL[LEFT][LE] - 1;
        res[RIGHT][D2] = resR[RIGHT][D1];
        res[RIGHT][D1] = node.getValue();
        res[RIGHT][LE] = resR[RIGHT][LE] - 1;

        return res;
    }
}
