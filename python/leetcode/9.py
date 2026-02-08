class Solution:
    def isPalindrome(self, x: int) -> bool:
        #return self.is_palindrome_string(x)
        return self.is_palindrome_integer(x)

    def is_palindrome_string(self, x):
        """Convert the number to a string and use list slicing to reverse and compare.
        """
        return str(x)[::-1] == str(x)

    def is_palindrome_integer(self, x):
        """Reverse the number by taking mod 10 and adding onto a new label then compare.
        """
        if x < 0 or (x % 10 == 0 and x != 0):
            return False
        cur = x
        rev = 0
        while cur > 0:
            rev = rev * 10 + cur % 10
            cur //= 10
        return x == rev
        