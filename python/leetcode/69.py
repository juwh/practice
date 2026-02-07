class Solution:
    def mySqrt(self, x: int) -> int:
        return self.my_sqrt_init(x)
    
    def my_sqrt_init(self, x):
        """Apply binary search to reduce the search space of potential square root factors to the closest single element.

        Base cases include values 0 and 1. The square root of 0 is 0 while the square root of 1 is 1.

        For integer values >= 2, start by determining the initial potential range. A conventional square root cannot be found from a negative number so the factors, rounded down, may include 0 up to x/2. We know from when x is greater than or equal to 4, the growth of the square outpaces the division by 4 in (x/2)^2 = (x^2)/4, so we can at least restrict our search space up to x/2. For when x is equal to 2 or 3, the square root, when rounded down, is 1. This could be added as a base case or computed as part of the non-base flow as x//2 does not exclude the answer in the search space. x equal to 2 is the absolute boundary in which x//2 maintains the search space starting at 1.

        We're looking for the maximum value of the square root that does not exceed x when squared. As such, if we find that mid squared exceeds x, we can definitely remove mid from our search pool. mid - 1 however, might be the answer.
        
        Even if the mid square value is less than x, it might still be the answer (as the maximum square root that when squared does not exceed x), however to proceed with the loop, we must exclude it from our search space (otherwise we might be in an infinite loop, like where left = 2, right = 3, x = 8 where mid = 2 which when squared is 4 which is less than 8 but we assign mid back to left which is the same value of 2). If mid was the answer, us moving the search space range to starting at mid + 1 means that the square always exceeds x, thus the right boundary is reduced until it is less than mid + 1, which is mid and the correct answer.
        """
        if x < 2:
            return x

        left, right = 1, x // 2
        while left <= right:
            mid = (left + right) // 2
            potential = mid * mid
            if potential > x:
                right = mid - 1
            elif potential < x:
                left = mid + 1
            else:
                return mid
        return right