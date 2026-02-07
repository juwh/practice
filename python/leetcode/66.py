from typing import List

class Solution:
    def plusOne(self, digits: List[int]) -> List[int]:
        return self.plus_one_init(digits)

    def plus_one_init(self, digits):
        """Reverse the digits to iterate from the lowest place, start with a carry of 1 for the addition of 1.
        """
        n = len(digits)
        digits.reverse()
        ans = []
        carry = 1
        for digit in digits:
            added = digit + carry
            rem = added % 10
            carry = added // 10
            ans.append(rem)
        if carry:
            ans.append(carry)
        ans.reverse()
        return ans
