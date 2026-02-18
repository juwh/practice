import math
from typing import Optional

class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def balanceBST(self, root: Optional[TreeNode]) -> Optional[TreeNode]:
        return self.balance_bst_init(root)
        #return self.balance_bst_rebuild(root)

    def balance_bst_init(self, root):
        """Utilizes the DSW algorithm to rebalance the binary search tree by creating a chain of nodes, then progressively applying repetitions of clustering to create completed subtrees which eventually combine into a single perfect tree which may also have "extra" nodes at the bottom level of the tree to be filled from left to right.

        m represents the number of nodes in the maximum perfect tree that can be created from the number of nodes in the input tree. Considering m, we can think of the extra nodes that we have in our input tree (n - m) as nodes attached left to right at the bottom layer while not filling it completely (complete versus perfect). Thus, we calculate the number of nodes that exist at the bottom row of the tree then prime those nodes with the initial left rotations.

        The left rotation action builds subtrees by combining subtrees that we build layer by layer with each iteration of a left rotation loop set.

        First, we bring down one node as the new left subtree as we pull up the new parent node. Since the parent we bring up does not have a left subtree, nothing is attached as the right subtree to the node brought down as the new left subtree. Now we have clusters of a parent node with a single node left subtree in a chain after one set of m // 2 iterations. The final subtree in the chain is complete and will serve as the "cap" (or sum carry) for other subtree merges in subsequent iterations. This subtree is "combined" with the last subtree created from left rotation in the set, creating a singular "higher-level" subtree which will eventually combine into another perfect subtree when the previous two clusters are combined in the next left rotation set.

        Moving to the next set of left rotations with m // 2, we are now creating clusters of complete 3 node subtrees which are attached to the chain (total of 4 node clusters with the chain connector). This occurs by the left rotation involving 2 adjacent 2-node clusters (created from the previous rotation set) with the third node in the order sequence becoming the overall parent of the cluster subtree, the second becoming parent node of the subtree attached to the chain.

        We utilize division by 2 on m as a representation of the number of subtrees we are creating with each repetition when the existing subtrees are combined. When we reach m // 2 = 1, the single left rotation shapes the overall left subtree along with the root. The right subtree has already been formed from the previous rotation set and no action is needed as it attaches as the right subtree.

        Straggler nodes which are added as part of the first left rotation set are separated from the rest of the chain, allowing the chain to be transformed into a perfect tree. The number of straggler nodes will always be less than the total number of nodes possible on the bottom row of a non-perfect tree. To fill from left to right, the rotation of every other node in the chain (up to the number of straggler nodes) allows the node in between to act as the parent when merging into subtrees. We are only concerned with creating 3 node subtrees for the bottom layer, thus applying only one set of left rotations up to the number of straggler nodes expected in the bottom layer of the balanced tree.
        """

        def num_nodes(node):
            if not node:
                return 0
            return 1 + num_nodes(node.left) + num_nodes(node.right)

        def rotate_left(node):
            if not node or not node.right:
                return node
            new_root = node.right
            new_left = node
            new_left_right = new_root.left
            new_root.left = None
            new_left.right = new_left_right
            new_root.left = new_left
            return new_root

        def rotate_right(node):
            if not node or not node.left:
                return node
            new_root = node.left
            new_right = node
            new_right_left = new_root.right
            new_root.right = None
            new_right.left = new_right_left
            new_root.right = new_right
            return new_root

        if not root:
            return
        dummy = TreeNode()
        dummy.right = root
        parent = dummy
        cur = root
        while cur:
            while cur.left:
                cur = rotate_right(cur)
            parent.right = cur
            parent = parent.right
            cur = cur.right

        n = num_nodes(dummy.right)
        n_perfect = 2 ** math.floor(math.log2(n + 1)) - 1
        stragglers = n - n_perfect
        m = n_perfect // 2

        parent = dummy
        cur = dummy.right
        for _ in range(stragglers):
            cur = rotate_left(cur)
            parent.right = cur
            parent = parent.right
            cur = cur.right
        
        while m >= 1:
            parent = dummy
            cur = dummy.right
            for _ in range(m):
                cur = rotate_left(cur)
                parent.right = cur
                parent = parent.right
                cur = cur.right
            m //= 2
        return dummy.right

    def balance_bst_rebuild(self, root):
        """Apply inorder traversal to obtain the sequence of nodes from which to build the binary tree, then recursively build the balanced binary search tree by selecting the middle element as the root and building subtrees from the elements to the left and right.
        """

        def inorder(node):
            traversal = []
            if not node:
                return traversal
            traversal += inorder(node.left)
            traversal.append(node)
            traversal += inorder(node.right)
            return traversal

        def build_bst(traversal):
            n = len(traversal)
            if n == 0:
                return None
            if n == 1:
                node_mid = traversal[0]
                node_mid.left = None
                node_mid.right = None
                return node_mid
            mid = n // 2
            node_mid = traversal[mid]
            node_left = build_bst(traversal[0:mid])
            node_right = build_bst(traversal[mid + 1:n])
            node_mid.left = node_left
            node_mid.right = node_right
            return node_mid

        traversal = inorder(root)
        return build_bst(traversal)