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
    def addTwoNumbers(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        """Adds each place by creating a dummy node in case of different list lengths.

        Addition continues until both aligned digits as well as the carry from the previous addition are "empty".
        """
        one, two, carry = l1, l2, 0
        res = cur = ListNode()
        cur.next = ListNode(0)
        while one is not None or two is not None or carry != 0:
            one = one or ListNode(0)
            two = two or ListNode(0)
            addition = one.val + two.val + carry
            cur.next = ListNode(addition % 10)
            cur = cur.next
            carry = addition // 10
            one = one.next
            two = two.next
        return res.next
        