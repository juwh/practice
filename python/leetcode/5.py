class Solution:
    def longestPalindrome(self, s: str) -> str:
        #return self.longestPalindromeDp(s)
        return self.longestPalindromeExpand(s)

    def longestPalindromeDp(self, s):
        """Bottom up dynamic programming keeps track of whether a valid palindrome is contained within x, y.

        Initially all x == y (single character) slots are true. In addition, all length two strings are true. Once initialized, iterate through the "length" of the substring by moving "up" the diagonal of the 2D array and combining the character comparison between x and y with the palindrome status of the substring with x and y removed (x + 1, y - 1). The diagonal represents substrings of the same length as we build palindrome status from the bottom up.
        """
        n = len(s)
        dp = [[False] * n for _ in range(n)]
        ans = [0, 0]

        for i in range(n):
            dp[i][i] = True

        for j in range(n - 1):
            if s[j] == s[j + 1]:
                dp[j][j + 1] = True
                ans = [j, j + 1]

        for len_ in range(2, n):
            for i in range(n - len_):
                j = i + len_
                if s[i] == s[j] and dp[i + 1][j - 1]:
                    dp[i][j] = True
                    ans = [i, j]

        i, j = ans
        return s[i : j + 1]

    def longestPalindromeExpand(self, s):
        """Check from each potential palindrome center the longest valid palindrome substring.

        Two cases are checked, one for odd length centers and another for even length centers. The upper bound of centers checked in linear to the length of the string (generally 2N). Each expanded check takes linear time, leading to a combined time complexity of O(n^2).
        """

        def expand(s, left, right):
            """Return the maximum length of the largest palindrome substring created from expanding out from i and j (assuming the substring between i and j non-inclusive is already a palindrome). The minimum length is always 1 as there is always a single character palindrome (as long as right >= left).
            """
            n = len(s)
            while left >= 0 and right < n and s[left] == s[right]:
                left -= 1
                right += 1
            longest = max(1, right - left - 1)
            return longest

        n = len(s)
        ans = [0, 0]
        longest_cur = 1

        for i in range(n):
            longest_odd = expand(s, i, i)
            if longest_odd > longest_cur:
                longest_cur = longest_odd
                dist = longest_odd // 2
                ans = [i - dist, i + dist]

            longest_even = expand(s, i, i + 1)
            if longest_even > longest_cur:
                longest_cur = longest_even
                dist = longest_even // 2
                ans = [i - dist + 1, i + dist]

        i, j = ans
        return s[i : j + 1]
