from typing import List

class Solution:
    def longestConsecutive(self, nums: List[int]) -> int:
        #return self.longest_consecutive_set(nums)
        return self.longest_consecutive_dp(nums)

    def longest_consecutive_set(self, nums):
        """Count sequences by checking presence with a hash set while incrementing and only starting a sequence if it is the first element of the sequence (no value immediately before).
        """
        nums_set = set(nums)
        longest = 0
        for num in nums:
            if num - 1 not in nums_set:
                streak = 1
                cur = num + 1
                while cur in nums_set:
                    streak += 1
                    cur += 1
                longest = max(longest, streak)
        return longest

    def longest_consecutive_dp(self, nums):
        """Utilize dynamic programming to avoid recomputing sequence lengths by keeping the length of the chain from that number in memory.

        This solution proactively calculates and saves the chain length starting from any point to the end. When a number is encountered in the middle of this precalculated range, the chain length is directly pulled from the memo. Otherwise, if the number is before the range, but will eventually sequence to the range, we calculate lengths until we reach the start of that range in which we pull the memoized value directly.
        """

        def longest_consecutive_inner(nums_set, num, dp):
            if num not in nums_set:
                return 0
            
            if num in dp:
                return dp[num]
            
            dp[num] = 1 + longest_consecutive_inner(nums_set, num + 1, dp)
            return dp[num]

        nums_set = set(nums)
        dp = {}
        longest = 0
        for num in nums:
            longest = max(longest, longest_consecutive_inner(nums_set, num, dp))
        return longest