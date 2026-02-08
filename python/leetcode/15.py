from typing import List

class Solution:
    def threeSum(self, nums: List[int]) -> List[List[int]]:
        """Use two_sum as a sub-routine to find the complement to a third element in the current list iteration.

        Notable differences from standard two_sum include the fact that all unique answers are required and that the answers include the values themselves (not their indices). This simplifies index maintenance allowing easy usage of sorting and a hash set instead of a hash map. Sorting would take O(nlgn) which would be absorbed in an overall time complexity of O(n^2).

        As we select our "first" element for the sum triple, our two_sum search space includes all elements after the selected index. This is because including the entire list would open possibilities to duplicate triplets.
        """
        nums.sort()
        i = 0
        n = len(nums)
        ans = []
        while i < n and nums[i] <= 0:
            if i == 0 or nums[i] != nums[i - 1]:
                #subanss = self.two_sum_pointer(nums, -nums[i], i + 1)
                subanss = self.two_sum_set(nums, -nums[i], i + 1)
                for subans in subanss:
                    ans.append([nums[i]] + subans)
            i += 1
        return ans
            
    def two_sum_pointer(self, nums, target, start):
        n = len(nums)
        left = start
        right = n - 1
        ans = []
        while left < right:
            if nums[left] + nums[right] == target:
                ans.append([nums[left], nums[right]])
                left += 1
                right -= 1
                while left < right and nums[left] == nums[left - 1]:
                    left += 1
            elif nums[left] + nums[right] < target:
                left += 1
            else:
                right -= 1
        return ans

    def two_sum_set(self, nums, target, start):
        n = len(nums)
        j = start
        seen = set()
        ans = []
        while j < n:
            complement = target - nums[j]
            if complement in seen:
                ans.append([complement, nums[j]])
                while j + 1 < n and nums[j] == nums[j + 1]:
                    j += 1
            seen.add(nums[j])
            j += 1
            
        return ans