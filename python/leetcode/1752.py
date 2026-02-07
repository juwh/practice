from typing import List

class Solution:
    def check(self, nums: List[int]) -> bool:
        #return self.check_init(nums)
        return self.check_smallest(nums)

    def check_init(self, nums):
        """Find and flag up to one "fold" point in the array, continuously checking non-decreasing sequence (which resets at the fold).

        Start with a conventional sorted check. This allows us to set aside the edge case where no rotation on the array has been applied. Otherwise a rotation has occurred, a "fold" exist, and for a valid rotation to have occurred, the first element must be greater than the last.

        If there is a "fold", we can iterate through the array until it is found (element value decreases), mark it as the singular allowed "strike" against the sorted rule, then continue to check for sorted status.
        """

        def check_sorted(nums, start, end):
            last = nums[start]
            for i in range(start + 1, end + 1):
                if last > nums[i]:
                    return False
                last = nums[i]
            return True

        n = len(nums)
        if n == 1:
            return True
        
        sorted_check = check_sorted(nums, 0, n - 1)
        if sorted_check:
            return True

        if nums[0] < nums[n - 1]:
            return False

        last = nums[0]
        j = 1
        while nums[j] >= last:
            last = nums[j]
            j += 1
        return check_sorted(nums, j, n - 1)

    def check_smallest(self, nums):
        """Find the smallest element in the array as the "fold" point, then check sorted status with a cyclic loop.

        The smallest element should be the first in the cycle, so once we know the smallest element we can find the "first" index of it by iterating "left" until we encounter the fold boundary.
        """

        def check_sorted_cyclic(nums, start):
            n = len(nums)
            last = nums[start]
            for i in range(1, n):
                if last > nums[(start + i) % n]:
                    return False
                last = nums[(start + i) % n]
            return True

        n = len(nums)
        if n == 1:
            return True
        smallest = min(nums)
        idx_min = nums.index(smallest)
        for _ in range(1, n):
            if nums[(n + idx_min - 1) % n] != smallest:
                break
            idx_min = (n + idx_min - 1) % n
        return check_sorted_cyclic(nums, idx_min)
