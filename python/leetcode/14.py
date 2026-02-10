from typing import List

class Solution:
    def longestCommonPrefix(self, strs: List[str]) -> str:
        #return self.longest_common_prefix_vert(strs)
        #return self.longest_common_prefix_dac(strs)
        return self.longest_common_prefix_trie(strs)

    def longest_common_prefix_vert(self, strs):
        """Iterates through vertical character slices while comparing against the first word.

        The first word sets some initial constraints on the longest common prefix. As characters can match up to the end of the word, the longest possible common prefix based on the first word is the word itself. Thus, we can use it as the reference against which all other words must match.
        """
        m = len(strs)
        for i in range(len(strs[0])):
            c = strs[0][i]
            for j in range(1, m):
                if i == len(strs[j]) or strs[j][i] != c:
                    return strs[0][0:i]
        return strs[0]

    def longest_common_prefix_dac(self, strs):
        """Divides the word list to find longest common prefixes from each subset then combine by minimum length.
        """

        def longest_common_prefix_recurse(strs, left, right):

            if left == right:
                return strs[left]
            else:
                mid = left + (right - left) // 2
                lcp_left = longest_common_prefix_recurse(strs, left, mid)
                lcp_right = longest_common_prefix_recurse(strs, mid + 1, right)
                return longest_common_prefix_recurse_merge(lcp_left, lcp_right)

        def longest_common_prefix_recurse_merge(lcp_left, lcp_right):
            m = len(lcp_left)
            n = len(lcp_right)
            if m > n:
                return longest_common_prefix_recurse_merge(lcp_right, lcp_left)

            i = 0
            while i < m:
                if lcp_left[i] != lcp_right[i]:
                    return lcp_left[0:i]
                i += 1
            return lcp_left

        n = len(strs)
        return longest_common_prefix_recurse(strs, 0, n - 1)

    def longest_common_prefix_trie(self, strs):
        """Builds a trie with each node representing a character, then finds the longest common prefix by identifying the first end of word within the non-diverging chain.
        """
        trie = Trie()
        for word in strs:
            trie.addWord(word)
        return trie.longestPrefix()
        
class TrieNode:
    def __init__(self):
        self.children = {}
        self.word = False

    def add(self, c):
        self.children[c] = TrieNode()

class Trie:
    def __init__(self):
        self.root = TrieNode()

    def addWord(self, word):
        cur = self.root
        for c in word:
            if c not in cur.children:
                cur.add(c)
            cur = cur.children[c]
        cur.word = True

    def longestPrefix(self):
        chars = []
        cur = self.root
        while len(cur.children) == 1 and not cur.word:
            c = next(iter(cur.children))
            chars.append(c)
            cur = cur.children[c]
        return "".join(chars)