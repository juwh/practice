class Solution:
    def romanToInt(self, s: str) -> int:
        """Build the value sum by assuming smaller symbols are always encountered first to add (then later after larger symbols to subtract) when reading right to left.
        """
        mapping = {
            'I': 1,
            'V': 5,
            'X': 10,
            'L': 50,
            'C': 100,
            'D': 500,
            'M': 1000
        }
        
        largest = 0
        sum = 0
        for symbol in reversed(s):
            val = mapping[symbol]
            largest = max(largest, val)
            if val >= largest:
                sum += val
            else:
                sum -= val
        return sum