import math
from typing import List

class Solution:
    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        """In general, binary is preferred as it can be used to find any k value. However, partition does maintain a better time complexity (O(log(min(m, n))) vs. O(log(m) + log(n)) = O(log(m * n))). These algorithms are O(log) as we are reducing our search space from n to 1 with "binary" discards.
        """
        #return self.find_median_sorted_arrays_binary(nums1, nums2)
        return self.find_median_sorted_arrays_partition(nums1, nums2)

    def find_median_sorted_arrays_partition(self, nums1, nums2):
        """Searches for the overall midpoint of the combined array by "trying" partitions and checking if the cross-combined subarrays maintain the sorted state between combined halves.

        We are trying to divide the combined arrays exactly in half such that all the elements in the "left" half are less than the elements in the "right" half. We can then take the element(s) at this division point to find the median. The elements within these halves do not have to be fully sorted however. So we can attempt to create these two halves by combining the partitioned left subarrays together (and also with the right side). The combined left half will contain (m + n + 1) // 2 elements while the right half will contain (m + n) // 2 elements, thus in cases of an odd number of elements, the median will be contained as the maximum right ends of the left partitions.

        Take arrays A and B and partition them into a_left, a_right, b_left, and b_right. In order for us to state that no elements in the a left partition should exist in the right half, that partition's maximum element must be less than (or equal to) both minimums of the right partitions. Let's think about this for the a_left partition. Since A and B were sorted, we already know a_left's maximum element is less than (or equal to) a_right's minimum. We just need to check against b_right's minimum as well. In order for us to say that all the elements in a_left and b_left are less than (or equal to) all the elements in a_right and b_right, we also have to do the same check for b_left. Once the partition point that achieves this is found, we can then also find the median.

        We apply binary search to identify the correct partition point, discarding a "half" of the current search space with each iteration. Continuing with our A and B partitions, as we move the partition point of A "up" (thus increasing the maximum of a_left and minimum of a_right), we move the partition point of B "down" (thus decreasing the maximum of b_left and minimum of b_right) to maintain our constant element count for the combined left and right halves. Remember, we want to partition such that a_left max <= b_right min and b_left max <= a_right min to find the median. This action helps towards fulfilling b_left max <= a_right min at the cost of potentially breaking a_left max <= b_right min. Moving the A partition "down" applies the opposite action. We continue to move the partition point "up" and "down" until both of these conditions are met.
        """
        m = len(nums1)
        n = len(nums2)
        if m > n:
            return self.find_median_sorted_arrays_partition(nums2, nums1)

        left = 0
        right = m
        while left <= right:
            # partition indices are not inclusive to the left subarrays
            one_part = left + (right - left) // 2 # 1
            two_part = (m + n + 1) // 2 - one_part # 3 - 1 = 2

            # partition of 0 means no elements in nums1 are in the proposed left half
            # there are no nums1 elements to potentially violate (a_left max <= b_right min) and must be framed to always be true
            one_left = nums1[one_part - 1] if one_part > 0 else -math.inf

            # partition of m means no elements in nums1 are in the proposed right half
            # there are no nums1 elements to potentially violate (b_left max <= a_right min) and must be framed to always be true
            one_right = math.inf if one_part == m else nums1[one_part]

            # partition of 0 means no elements in nums2 are in the proposed left half
            # there are no nums2 elements to potentially violate (b_left max <= a_right min) and must be framed to always be true
            two_left = nums2[two_part - 1] if two_part > 0 else -math.inf

            # partition of n means no elements in nums2 are in the proposed right half
            # there are no nums2 elements to potentially violate (a_left max <= b_right min) and must be framed to always be true
            two_right = math.inf if two_part == n else nums2[two_part]

            if one_left <= two_right and two_left <= one_right:
                return max(one_left, two_left) if (m + n) % 2 else (max(one_left, two_left) + min(one_right, two_right)) / 2
            elif one_left > two_right:
                # we just tried one_part in the search space so we can exclude it for the next
                right = one_part - 1
            else:
                # we just tried one_part in the search space so we can exclude it for the next
                left = one_part + 1

    def find_median_sorted_arrays_binary(self, nums1, nums2):
        """Applies binary search by discarding either the high or low "half" from one of the input arrays.

        The base case occurs when one of the input arrays has been discarded fully such that only elements in the other array remain. This also means all elements in the discarded array are less than the kth element. At that point, since the arrays are sorted, we can find the kth element (initially passed as the overall median index) by accessing the index beyond the elements accounted for from the discarded array (k - number of elements from discarded array = number of elements from other array needed to reach k).

        Otherwise, we divide the input arrays by the midpoint indices. We can "isolate" two ends of these split subarrays. Let's say we have two arrays A and B. Splitting by the midpoints of these arrays, we get a_left, a_right, b_left, and b_right. By checking which midpoint value is higher, we can claim either left subarray of the smaller midpoint array or right subarray of the larger midpoint array do not intersect with each other. Along with the fact that these subarrays are not intersecting with their opposite side (per intuition of the splitting of a sorted array), we designate half of the combined array (1/2A + 1/2B = 1/2(A + B)) as non-intersecting with a subarray end.

        By identifying whether k (median index) exists in either the left or right half of the combined array, we choose whether to discard the "smallest" subarray or the "largest" subarray as we know by non-intersection that the discarded subarray will not contain the kth element. The kth element might be mixed somewhere in the other three subarrays however.
        """

        def _find_median_sorted_arrays_binary_recurse(nums1, nums2, k, oneStart, oneEnd, twoStart, twoEnd):

            if oneStart > oneEnd:
                return nums2[k - oneStart]
            elif twoStart > twoEnd:
                return nums1[k - twoStart]

            oneMid = oneStart + (oneEnd - oneStart) // 2
            twoMid = twoStart + (twoEnd - twoStart) // 2
            oneVal = nums1[oneMid]
            twoVal = nums2[twoMid]

            if oneMid + twoMid < k:
                if oneVal > twoVal:
                    return _find_median_sorted_arrays_binary_recurse(nums1, nums2, k, oneStart, oneEnd, twoMid + 1, twoEnd)
                else:
                    return _find_median_sorted_arrays_binary_recurse(nums1, nums2, k, oneMid + 1, oneEnd, twoStart, twoEnd)
            else:
                if oneVal > twoVal:
                    return _find_median_sorted_arrays_binary_recurse(nums1, nums2, k, oneStart, oneMid - 1, twoStart, twoEnd)
                else:
                    return _find_median_sorted_arrays_binary_recurse(nums1, nums2, k, oneStart, oneEnd, twoStart, twoMid - 1)
        
        oneN, twoN = len(nums1), len(nums2)
        n = oneN + twoN
        k = n // 2

        if n % 2:
            return _find_median_sorted_arrays_binary_recurse(nums1, nums2, k, 0, oneN - 1, 0, twoN - 1)
        else:
            return (_find_median_sorted_arrays_binary_recurse(nums1, nums2, k, 0, oneN - 1, 0, twoN - 1) + _find_median_sorted_arrays_binary_recurse(nums1, nums2, k - 1, 0, oneN - 1, 0, twoN - 1)) / 2