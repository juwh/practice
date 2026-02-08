from typing import Optional

class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution:
    def getIntersectionNode(self, headA: ListNode, headB: ListNode) -> Optional[ListNode]:
        #return self.get_intersection_node_init(headA, headB)
        #return self.get_intersection_node_flip(headA, headB)
        #return self.get_intersection_node_count(headA, headB)
        return self.get_intersection_node_set(headA, headB)

    def get_intersection_node_init(self, head_a, head_b):
        """Initially check that the chains converge, then equalize the lengths traversed by "flipping" the chains when we first encounter the ends.

        Assuming that an intersection exists, observe that if we have chains a and b, the total number of nodes we would traverse is the same if the lists were concatenated as a + b and b + a. This also means we are reaching the final node of the concatenated chains at the same time. So if an intersection exists, this chain "flip" aligns our iteration to eventually line up when we reach the intersection point.
        """
        cur_a = head_a
        cur_b = head_b
        while cur_a.next:
            cur_a = cur_a.next
        while cur_b.next:
            cur_b = cur_b.next
        if cur_a != cur_b:
            return

        cur_a = head_a
        cur_b = head_b
        while cur_a != cur_b:
            if not cur_a.next:
                cur_a = head_b
            else:
                cur_a = cur_a.next
            if not cur_b.next:
                cur_b = head_a
            else:
                cur_b = cur_b.next
        return cur_a

    def get_intersection_node_flip(self, head_a, head_b):
        """Equalize the lengths traversed by "flipping" the chains when we first encounter the ends.

        Assuming that an intersection exists, observe that if we have chains a and b, the total number of nodes we would traverse is the same if the lists were concatenated as a + b and b + a. This also means we are reaching the final node of the concatenated chains at the same time. So if an intersection exists, this chain "flip" aligns our iteration to eventually line up when we reach the intersection point.

        For non-intersecting scenarios, since the implementation allows traversal into None, after flipping the chains the iteration will eventually meet at None for equality and return.
        """
        cur_a = head_a
        cur_b = head_b
        while cur_a != cur_b:
            cur_a = head_b if not cur_a else cur_a.next
            cur_b = head_a if not cur_b else cur_b.next
        return cur_a

    def get_intersection_node_count(self, head_a, head_b):
        """Initially check that the chains converge while also keeping track of chain lengths, then traverse to the intersection aligning iteration with the length offset.
        """
        cur_a = head_a
        len_a = 1
        cur_b = head_b
        len_b = 1
        while cur_a.next:
            cur_a = cur_a.next
            len_a += 1
        while cur_b.next:
            cur_b = cur_b.next
            len_b += 1
        if cur_a != cur_b:
            return

        cur_long = head_a if len_a > len_b else head_b
        cur_short = head_a if len_a <= len_b else head_b
        for _ in range(abs(len_a - len_b)):
            cur_long = cur_long.next
        while cur_long != cur_short:
            cur_long = cur_long.next
            cur_short = cur_short.next
        return cur_long

    def get_intersection_node_set(self, head_a, head_b):
        """Store all nodes of one chain to a set and check for equality (intersection) while traversing the other chain.
        """
        nodes_a = set()
        cur_a = head_a
        while cur_a:
            nodes_a.add(cur_a)
            cur_a = cur_a.next
        
        cur_b = head_b
        while cur_b:
            if cur_b in nodes_a:
                return cur_b
            cur_b = cur_b.next

        return None