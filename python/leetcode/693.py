class Solution:
    def hasAlternatingBits(self, n: int) -> bool:
        #return self.has_alternating_bits_init(n)
        #return self.has_alternating_bits_str(n)
        return self.has_alternating_bits_mod(n)

    def has_alternating_bits_init(self, n):
        """Use an XOR action to attempt to convert all bits into 1s by applying a 1 offset mask then verifying the bits.

        If the number is alternating bits, a 1 offset mask should result in a number of all 1 bits as the presence of consecutive 1 or 0 bits will result in at least one 0 bit through XOR when applied with a 1 offset mask. 
        """
        offset = n >> 1
        xor = offset ^ n
        return xor.bit_count() == xor.bit_length()

    def has_alternating_bits_str(self, n):
        """Convert the input integer into a binary string, then iterate and compare the current character to the previous one.
        """
        s = "{:b}".format(n)
        for i in range(1, len(s)):
            if s[i] == s[i - 1]:
                return False
        return True

    def has_alternating_bits_mod(self, n):
        """Check for consecutive bits as we iterate through each binary digit by shifting right.
        """
        cur = n % 2
        n >>= 1
        while n > 0:
            if cur == n % 2:
                return False
            cur = n % 2
            n >>= 1
        return True