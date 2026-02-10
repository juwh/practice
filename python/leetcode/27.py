from typing import List

class Solution:
    def removeElement(self, nums: List[int], val: int) -> int:
        #return self.remove_element_swap_min(nums, val)
        return self.remove_element_remove_min(nums, val)

    def remove_element_swap_min(self, nums, val):
        """Identifies values to remove from the left and valid replacement values from the right before swapping.

        This implementation avoids unnecessary swapping or shifting of values, bounded by the number of invalid elements in the first k elements of the list.

        In scenarios where the left and right indices end by equaling each other, we must evaluate the element at that location to see if it should be a part of the first k elements or not (to be removed), thus determining the boundary of the list. Left and right indices can only equal each other by being adjusted after a swap as the other while loops ensure that left > right.
        """
        n = len(nums)
        left = 0
        right = n - 1
        count = 0
        while left < right:
            while left <= right and nums[left] != val:
                left += 1
            while left <= right and nums[right] == val:
                right -= 1
                count += 1
            if left < right:
                nums[left], nums[right] = nums[right], nums[left]
                left += 1
                right -= 1
                count += 1
        count += 1 if left == right and nums[left] == val else 0
        return n - count

    def remove_element_remove_min(self, nums, val):
        """Swaps in values from the right end of the list to replace removed values from the iterated left.

        This swap could potentially replace a removable element with another one as we are not considering the value of the element we are pulling from the right. As such, the iterated index is not incremented after a swap in order to re-evaluate the element. The maximum number of swaps is equal to the number of removable elements. With each swap action, we finalize the placement of one removable element on the right, even if we add in another removable element in place of it.

        Once all removable elements are placed on the right side, we know n points to the index of the "first" removable element on the left end of that sequence. As such the count of valid elements is equal to n due to zero-based indexing.
        """
        n = len(nums)
        i = 0
        while i < n:
            if nums[i] == val:
                nums[i] = nums[n - 1]
                n -= 1
            else:
                i += 1
        return n
