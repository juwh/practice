class Solution:
    def countBinarySubstrings(self, s: str) -> int:
        return self.count_binary_substrings_init(s)

    def count_binary_substrings_init(self, s):
        """Track the length of the current consecutive sequence, comparing with the last alternate sequence to increment the count of equal 1s and 0s.
        
        With only two bits, sequences can only alternate, allowing us to utilize only two variables to keep track of the length of the last sequence as well as the length of the current sequence. Once we encounter the alternate value, we assign down the current length to act as the last sequence length.

        If both sequences have at least n characters, we know we can also create a substring of n - 1 characters at each side of the adjacent boundary, all the way down to one character each. Thus, as we increment our current sequence length, as long as there is a matching character in the last sequence, we can say that there is another instance of a substring with equal 1s and 0s.
        """
        n = len(s)
        ans = 0
        last = 0
        cur = 1
        for i in range(1, n):
            if s[i] == s[i - 1]:
                cur += 1
                if last >= cur:
                    ans += 1
            else:
                last = cur
                cur = 1
                ans += 1
        return ans