from typing import Optional

class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution:
    def mergeTwoLists(self, list1: Optional[ListNode], list2: Optional[ListNode]) -> Optional[ListNode]:
        """Build the list connecting the lower nodes from a dummy head then returning just past the dummy node.
        """
        head = ListNode()
        cur_one = list1
        cur_two = list2
        cur = head
        while cur_one and cur_two:
            if cur_one.val > cur_two.val:
                cur.next = cur_two
                cur_two = cur_two.next
            else:
                cur.next = cur_one
                cur_one = cur_one.next
            cur = cur.next
        
        if cur_one:
            cur.next = cur_one

        if cur_two:
            cur.next = cur_two

        return head.next