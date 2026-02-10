from typing import List

class Solution:
    def trap(self, height: List[int]) -> int:
        #return self.trap_two_pointer(height)
        return self.trap_stack(height)

    def trap_two_pointer(self, height):
        """Move left and right pointers inward, moving the "shorter" pointer and adding any valid water underneath.

        As the pointers encounter taller "walls", the maximum height of the captured water is limited by the minimum of the two walls encountered so far. Moving the "taller" wall pointer would not give any insight as to whether more water can be captured as we are not sure if we are about to meet the "shorter" wall, meaning the water captured must not be based on the "taller" wall height. In accumulating water using the "shorter" wall, we are absolutely sure that we can count the water underneath up to the "shorter" wall height as the other side is at least as tall as the "taller" wall. When determining which index to add water from, we can compare the heights currently pointed at since one side will always be the currently encountered "tallest" wall index (either the taller wall from a previous iteration which doesn't move or we just encountered a taller wall on the other side).
        """
        n = len(height)
        left, right, height_left, height_right = 0, n - 1, 0, 0
        water = 0
        while left < right:
            if height[left] < height[right]:
                height_left = max(height_left, height[left])
                water += height_left - height[left]
                left += 1
            else:
                height_right = max(height_right, height[right])
                water += height_right - height[right]
                right -= 1
        return water

    def trap_stack(self, heights):
        """Store the heights as a stack such that when we encounter a "taller" wall that can potentially create a "bowl" shape, we capture the water to the left of it in horizontal layers.

        After encountering a taller right wall, we pop the stack using the minimum height of the left (current top of stack after popping) and right walls (current height index) to determine how much water is added as a "layer". As we continue to pop the stack, the left wall might continue to increase, thus requiring more wide layers to be added on (water between the taller left wall and the unmoved right wall). The left wall after pop will always be greater than the height of what was popped (or the stack will be empty) as only a downward slope of heights will accumulate in the stack (otherwise they would be cleared by the inner loop which checks against the right wall and pops entries until the right wall is lesser and added to the top of the stack).
        """
        stack = []
        water = 0
        for i, height in enumerate(heights):
            while len(stack) > 0 and height > heights[stack[-1]]:
                lesser = stack.pop()
                if len(stack) == 0:
                    break
                width = i - stack[-1] - 1
                limit = min(height, heights[stack[-1]])
                water += width * (limit - heights[lesser])
            stack.append(i)
        return water