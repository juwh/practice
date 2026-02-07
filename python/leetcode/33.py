from typing import List

class Solution:
    def search(self, nums: List[int], target: int) -> int:
        #return self.search_init(nums, target)
        #return self.search_subarrays(nums, target)
        #return self.search_shifted(nums, target)
        return self.search_single(nums, target)

    def search_init(self, nums, target):
        """Find the pivot point via binary search, unrotate the array, then search for target within the fixed array via binary search.

        For all arrays, the last element is expected to always be greater than or equal to the minimum element of the array. If the array is rotated by one element, it is equal to the minimum. Any additional offsets push the greater elements into that index.

        Using this assumption, we can search for the pivot point (alternatively the minimum of the array) with the following statements. If the midpoint element is less than or equal to the rightmost element in the subarray, then smaller values must be found somewhere left of the midpoint (or the minimum value is the midpoint) so restrict the search space to the left half of the midpoint. If the midpoint value is greater than the rightmost value of the subarray, we are in the "rotated" portion of the array and the pivot point must be somewhere right of it, so restrict the search space to the right (non-inclusive of the midpoint as the value is greater than the midpoint element and cannot be a minimum). Continue the loop until we have isolated a single element as the pivot.

        Once we have isolated the pivot point, rearrange the array back to a standard array to apply standard binary search for the target.
        """
        n = len(nums)
        left = 0
        right = n - 1
        comp = nums[-1]
        while left != right:
            mid = (left + right) // 2
            if nums[mid] <= comp:
                right = mid
                comp = nums[mid]
            else:
                left = mid + 1
        offset = left
        unrotated = nums[offset:] + nums[:offset]
        left = 0
        right = n - 1
        while left <= right:
            mid = (left + right) // 2
            if unrotated[mid] < target:
                left = mid + 1
            elif unrotated[mid] > target:
                right = mid - 1
            else:
                return (mid + offset) % n
        return -1

    def search_subarrays(self, nums, target):
        """Find the pivot point via binary search, then search the subarrays split by the pivot point for the target value.
        """

        def search_binary(nums, left, right, target):
            while left <= right:
                mid = (left + right) // 2
                if nums[mid] < target:
                    left = mid + 1
                elif nums[mid] > target:
                    right = mid - 1
                else:
                    return mid
            return -1

        n = len(nums)
        left = 0
        right = n - 1
        comp = nums[-1]
        while left != right:
            mid = (left + right) // 2
            if nums[mid] <= comp:
                right = mid
                comp = nums[mid]
            else:
                left = mid + 1
        offset = left
        if (ans := search_binary(nums, offset, n - 1, target)) != -1:
            return ans
        return search_binary(nums, 0, offset - 1, target)

    def search_shifted(self, nums, target):
        """Find the pivot point via binary search, then search for target via binary search by shifting the index when comparing values.
        """
        n = len(nums)
        left = 0
        right = n - 1
        comp = nums[-1]
        while left != right:
            mid = (left + right) // 2
            if nums[mid] <= comp:
                right = mid
                comp = nums[mid]
            else:
                left = mid + 1
        offset = left
        left = 0
        right = n - 1
        while left <= right:
            mid = (left + right) // 2
            shifted = (mid + offset) % n
            if nums[shifted] < target:
                left = mid + 1
            elif nums[shifted] > target:
                right = mid - 1
            else:
                return shifted
        return -1

    def search_single(self, nums, target):
        """Find target in single binary search pass by considering first if we are looking for a greater or lesser value compared to the value at the midpoint index, then determining if the midpoint element is part of the rotated "lower" sequence (in the fold) or not, combined with considerations for whether the target is in the rotated sequence.

        If the target is less than the middle element, and the middle element is in the fold, then the target must be in the left half as all elements in the right half will be larger. If the middle element is not in the fold, then it is dependent on whether target is in the fold or not. We know the fold starts to the right of the middle element, so if the target is in the fold, we search the right half. Otherwise, the left half is searched.

        Apply similar bounding when the target is greater than the middle element. If the middle element is in the fold, then the half to search depends on whether the target is also in the fold (search right half with the initial sequence of values greater than the middle element) or not (search left half with the rest of the values greater than the middle element).

        Recall that we use the rightmost element of the subarray to represent an element that is always greater than or equal to the minimum of the subarray. The rightmost element is usually the largest in a regular sorted array, so it can help us locate whether we are in the fold or not. Finding a larger element compared to the rightmost element in a rotated array indicates that the minimum element is somewhere between the two as there is a point where we cycle from the largest element to the smallest then increase up to the rightmost element value.
        """
        n = len(nums)
        left = 0
        right = n - 1
        comp = nums[-1]
        while left <= right:
            mid = (left + right) // 2
            if nums[mid] > target:
                if nums[mid] <= comp or target > comp:
                    right = mid - 1
                    comp = nums[mid - 1]
                else:
                    left = mid + 1
            elif nums[mid] < target:
                if nums[mid] > comp or target <= comp:
                    left = mid + 1
                else:
                    right = mid - 1
                    comp = nums[mid - 1]
            else:
                return mid
        return -1