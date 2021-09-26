import java.util.Comparator;

/**
 * A comparator for Binary Trees.
 */
public class BinaryTreeComparator<E extends Comparable<E>> implements Comparator<BinaryTree<E>> {

    /**
     * Time Complexity O(n) n being the number of nodes
     * Space Complexity O(n)
     *
     * Compares two binary trees with the given root nodes.
     *
     * Two nodes are compared by their left childs, their values, then their right childs,
     * in that order. A null is less than a non-null, and equal to another null.
     *
     * @param tree1 root of the first binary tree, may be null.
     * @param tree2 root of the second binary tree, may be null.
     * @return -1, 0, +1 if tree1 is less than, equal to, or greater than tree2, respectively.
     */
    @Override
    public int compare(BinaryTree<E> tree1, BinaryTree<E> tree2) {

        if (tree1 == null && tree2 == null) {
            return 0;
        } else if (tree1 == null) {
            return -2;
        } else if (tree2 == null) {
            return 2;
        }

        int curr = tree1.getValue().compareTo(tree2.getValue());
        int resL, resR;

        resL = compare(tree1.getLeft(), tree2.getLeft());
        resR = compare(tree1.getRight(), tree2.getRight());

        if (resL == 0 && resR == 0)
            return curr;
        else if (resL != 0 || resR != 0) {
            if (resL == -2 || resL == 2) {
                return resL/2;
            } else if (resR == -2 || resR == 2) {
                return resR/2;
            } else if (curr == 0) {
                if (resL != 0) {
                    return resL;
                } else {
                    return resR;
                }
            }
        }

        return curr;
    }
}
