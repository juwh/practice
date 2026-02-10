from typing import List

class Solution:
    def groupAnagrams(self, strs: List[str]) -> List[List[str]]:
        return self.groupAnagramsCharIntList(strs)

    def groupAnagramsCharIntList(self, strs):
        """Build an ordered list to store counts of a fixed set of characters to then compare against matching "footprints" and group.

        Ordered lists can be converted into a tuple key for dictionaries to map and append strings with matching character count footprints. The dictionary is then converted into a list of lists by casting the sequence of values into a list.
        """
        ans = {}
        for str_ in strs:
            counts = [0] * 26
            for char_ in list(str_):
                counts[ord(char_) - ord("a")] += 1
            ans.setdefault(tuple(counts), []).append(str_)
        return list(ans.values())
