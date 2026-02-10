import math
from typing import List

class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        #return self.max_subarray_linear(nums)
        return self.max_subarray_dac(nums)

    def max_subarray_linear(self, nums):
        """If you reach zero, you can start the subarray here as the rest of the array before is just a "cycle".
        """
        sum_max = sum_cur = nums[0]
        for num in nums[1:]:
            sum_cur += num
            sum_cur = max(num, sum_cur)
            sum_max = max(sum_max, sum_cur)
        return sum_max

    def max_subarray_dac(self, nums):
        """Divide the problem into sub-problems, drawing the largest subarray from three possibilities: subarray from the left half, subarray from the right half, subarray crossing the median.

        For problem division, just choose the median index to split on until the size is trivial (1 or 0). Determine the greatest crossing subarray per split level O(logn), leading to a time complexity of O(nlogn).
        """

        def max_subarray_recurse(nums, low, high):
            if high == low:
                return (low, high, nums[low])
            else:
                mid = (low + high) // 2
                left_low, left_high, left_sum = max_subarray_recurse(nums, low, mid)
                right_low, right_high, right_sum = max_subarray_recurse(nums, mid+1, high)
                cross_low, cross_high, cross_sum = crossing(nums, low, mid, high)
                if left_sum >= right_sum and left_sum >= cross_sum:
                    return (left_low, left_high, left_sum)
                elif right_sum >= left_sum and right_sum >= cross_sum:
                    return (right_low, right_high, right_sum)
                else:
                    return (cross_low, cross_high, cross_sum)
        
        def crossing(nums, low, mid, high):
            left_sum = right_sum = -math.inf
            sum = 0
            for i in range(mid, low-1, -1):
                sum += nums[i]
                if sum > left_sum:
                    left_sum = sum
                    max_left = i

            sum = 0
            for j in range(mid+1, high+1):
                sum += nums[j]
                if sum > right_sum:
                    right_sum = sum
                    max_right = j

            return (max_left, max_right, left_sum + right_sum)

        max_left, max_right, max_sum = max_subarray_recurse(nums, 0, len(nums)-1)
        return max_sum