class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        return self.lengthOfLongestSubstringTwoPointer(s)

    def lengthOfLongestSubstringTwoPointer(self, s):
        """Utilize two pointers tracking the start of a valid substring and the current processing character.

        Keep track of the last occurrence of each character and the left most index without a repeat. If a "repeat" is found that is greater than the current left-most index, that becomes the new left-most index and we take the maximum of the current longest length and the currently built length. Sometimes when left is shifted right, the new substring start point may go past some previously seen characters. As they no longer contribute to the length of the current substring, so even if they are seen again, this does not flag the "no duplicate character" requirement.
        """
        left, right, longest = 0, 0, 0
        last_seen = {}
        n = len(s)
        while right < n:
            cur = s[right]
            if cur in last_seen and last_seen[cur] >= left:
                left = last_seen[cur] + 1
            last_seen[cur] = right
            longest = max(longest, right - left + 1)
            right += 1
        return longest