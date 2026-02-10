from typing import List

class Solution:
    def removeDuplicates(self, nums: List[int]) -> int:
        """Keep track of the last seen element along with the index to place the next unique element while iterating through the list, placing it when a new unique value is detected.

        As i acts as a scout pointer which is equal to or ahead of left, the value at left has already been evaluated via i (or will be immediately evaluated if left == i). This means that placing the next unique value at left will not cause the value at left to be lost. The value at left is either a duplicate or the next unique value (when left == i).
        """
        n = len(nums)
        last = nums[0]
        left = 1
        k = 1
        for i in range(1, n):
            if nums[i] != last:
                nums[left] = nums[i]
                last = nums[i]
                left += 1
                k += 1
        return k