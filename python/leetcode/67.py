class Solution:
    def addBinary(self, a: str, b: str) -> str:
        #return self.add_binary_str(a, b)
        #return self.add_binary_bit_add(a, b)
        #return self.add_binary_bit_man(a, b)
        return self.add_binary_init(a, b)

    def add_binary_str(self, a, b):
        """Converts binary strings to integers for direct addition before formatting back to binary string.
        """
        return '{:b}'.format(int(a, 2) + int(b, 2))

    def add_binary_bit_add(self, a, b):
        """Iterates through each digit of the binary strings, adding and keeping track of the carry up to the final sum.
        """
        n = max(len(a), len(b))
        a, b = a.zfill(n), b.zfill(n)

        carry = 0
        ans = []
        for ad, bd in zip(reversed(a), reversed(b)):
            if ad == '1':
                carry += 1
            if bd == '1':
                carry += 1
            if carry % 2:
                ans.append('1')
            else:
                ans.append('0')
            carry //= 2
        if carry:
            ans.append('1')
        return ''.join(reversed(ans))

    def add_binary_bit_man(self, a, b):
        """Apply XOR with an addition of the carry as a loop until all carries have been added to the sum.

        XOR determines all the initial places where the ones digit pass through to the sum. All zeros from this operation may be coming from the addition of two ones or two zeros. To account for the "lost" bits from two ones sums, we utilize a second operation of AND then shift bits left. These two operations reframe the sum by isolating the carry bits out from the addition to be added separately.

        We know that continuing this loop of XOR then extracting the carries to then add together will eventually terminate. Carry bits will continue to shift left by one bit until either the carry falls into a zero bucket (thus not generating another carry) or the left end of the current sum (which is always zero).
        """
        added, carries = int(a, 2), int(b, 2)
        while carries:
            added, carries = added ^ carries, (added & carries) << 1
        return '{:b}'.format(added)

    def add_binary_init(self, a, b):
        """Applies an XOR action to establish 1s coming either from a or b, extracting out carry 1s (from 1s in both a and b at the same digit) as a separate addend using an AND operation to XOR back onto the rolling sum until no carry value exists (all carries have been cascaded into open 0 slots).

        AND isolates carries by evaluating combined 1s from both a and b and shifting up one place to represent the greater order.

        XOR identifies the additions without carries. If a place had 1s in both a and b, that is extracted into the carry via the AND operation. If a place only had a single 1 from either a or b, that 1 is applied to the place in the number. If the carry is evaluated to have a 1 also in that place, the next iteration will combine and get extracted into the next carry. If neither a nor b have a 1 in the same place, that place should stay 0 similar to a standard addition.

        Eventually all carries will fall off as they are pushed up the left end of the binary number (or fall into 0 slots without additional carries following behind it).
        """
        base = int(a, 2)
        carry = int(b, 2)
        while carry:
            base, carry = base ^ carry, ((base & carry) << 1)
        return "{:b}".format(base)