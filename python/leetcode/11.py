from typing import List

class Solution:
    def maxArea(self, height: List[int]) -> int:
        return self.max_area_two_pointer(height)

    def max_area_two_pointer(self, height):
        """Move the "shorter" side pointer inward while calculating the water contained until the pointers meet.

        The maximum horizontal width is one axis which determines the amount of water contained. The other axis is the minimum wall height at each side. Moving the walls "inwards" will reduce the width, but more water may be able to be stored by finding a "taller" wall even with the shorter width. The side with the "shorter" wall should move as finding a "taller" wall from the current taller side would not result in any water amount increase since the limiting factor is still the shorter wall.
        """
        area_max, left, right = 0, 0, len(height)-1
        while left < right:
            area_max = max(area_max, (right - left) * min(height[left], height[right]))
            if height[left] > height[right]:
                right -= 1
            else:
                left += 1
        return area_max