from typing import List

class Solution:
    def rotate(self, matrix: List[List[int]]) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        #self.rotate_corners(matrix)
        self.rotate_transform(matrix)

    def rotate_corners(self, matrix):
        """Rotate values in place by identifying the four corners indices that will be moved.

        As the matrix is a square, the corners would follow this sequence.

        0,0 | 0,n-1 | n-1,n-1 | n-1,0
        0,1 | 1,n-1 | n-1,n-2 | n-2,0
        0,2 | 2,n-1 | n-1,n-3 | n-3,0
        ...
        1,1 | 1,n-2 | n-2,n-2 | n-2,1

        Each block coordinate adjustment is from an "offset" while each coordiante adjustment between blocks is from the "layer" iteration. The next "layer" begins after n-1 iterations, then n-3, n-5 until n <= 0.
        """
        n = len(matrix)
        for layer in range(n//2):
            for offset in range(n-2*layer-1):
                matrix[layer][layer+offset], matrix[n-1-layer-offset][layer], matrix[n-1-layer][n-1-layer-offset], matrix[layer+offset][n-1-layer] = matrix[n-1-layer-offset][layer], matrix[n-1-layer][n-1-layer-offset], matrix[layer+offset][n-1-layer], matrix[layer][layer+offset]

    def rotate_transform(self, matrix):
        """Adjust the matrix using linear algebra concepts of transpose and reflect.

        Think about a vector on a coordinate grid. A transpose operation on a vector essentially is a reflection on the quadrant diagonal where the vector exists. This "flips" the angles between the vector and the two "surrounding" axes. A 90 degree clockwise rotation of a vector means that the x and y coordinates move to the previous quadrant (with wraparound) and must have create an angle between the axis adjacent to the "previous position" quadrant that is 90 - (angle between axis and "previous position" vector). A reflection moves the vector to an adjacent quadrant and the transpose flips the angle so it is A + B rather than A + A (from the reflection). We know A + B = 90 as coordinate axes are perpendicular. A clockwise rotation of 90 degrees can be created from a sequence of a x-axis reflection then transpose, or a transpose then y-axis reflection. Extend this vector scale to this matrix by saying each x, y coordinate is a vector in the fourth quadrant. We can recreate an axis reflection by splitting the matrix in half one way (reflecting the axis we are reflecting on) then flipping the values for that axis.
        """
        n = len(matrix)
        for i in range(n):
            for j in range(i + 1, n):
                matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]

        for i in range(n):
            for offset in range(n // 2):
                matrix[i][offset], matrix[i][n-1-offset] = matrix[i][n-1-offset], matrix[i][offset]
