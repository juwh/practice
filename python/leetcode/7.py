import math

class Solution:
    def reverse(self, x: int) -> int:
        """Reverses the integer by extracting digits and attaching while shifting the currently built reversed value and applying limit checks.

        Because Python applies the mathematical approach to modulus (instead of Java's C-based approach), we manually calculate the remainder by utilizing truncate. The C remainder operator is offered via math.fmod.

        Limit checks should continuously apply to one order of magnitude below the actual integer limit.
        """
        rev = 0
        while x != 0:
            #digit = x - math.trunc(x / 10) * 10
            digit = math.fmod(x, 10)
            if (rev > (2**31 - 1) // 10) or (rev == (2**31 - 1) // 10 and digit > 7) or (rev < math.trunc(-2**31 / 10)) or (rev == math.trunc(-2**31 / 10) and digit < -8):
                return 0
            rev = rev * 10 + digit
            x = math.trunc(x / 10)
        return int(rev)