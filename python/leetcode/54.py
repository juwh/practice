from typing import List

class Solution:
    def spiralOrder(self, matrix: List[List[int]]) -> List[int]:
        #return self.spiral_order_init(matrix)
        #return self.spiral_order_subproblem_bu(matrix)
        return self.spiral_order_visited(matrix)
        
    def spiral_order_init(self, matrix):
        """Recurse by defining each sub-problem as the sub-matrix box one layer within the current matrix.

        For standard cases, we are adding elements by traversing each edge by the clockwise direction. There are four loops executing from each matrix corner going in the right, down, left, up directions.

        Keep track of whether we have reached the center such that there are no four distinct corners to point from. This is done by passing the length and width of the sub-matrix which is reduced by 2 per recursive call. Once a length of less than 2 is found, base cases apply. If the length is 0, we have encountered all traversable elements. If the length is 1, we have a row or column to traverse before recursion termination.
        """
        def spiral_order_init_recurse(ans, matrix, x, y, x_len, y_len):
            if x_len == 1:
                for down in range(y_len):
                    ans.append(matrix[y + down][x])
                return ans
            if y_len == 1:
                for right in range(x_len):
                    ans.append(matrix[y][x + right])
                return ans
            if x_len == 0 or y_len == 0:
                return ans
            for right in range(x_len - 1):
                ans.append(matrix[y][x + right])
            for down in range(y_len - 1):
                ans.append(matrix[y + down][x + x_len - 1])
            for left in range(x_len - 1):
                ans.append(matrix[y + y_len - 1][x + x_len - 1 - left])
            for up in range(y_len - 1):
                ans.append(matrix[y + y_len - 1 - up][x])
            return spiral_order_init_recurse(ans, matrix, x + 1, y + 1, x_len - 2, y_len - 2)

        ans = []
        return spiral_order_init_recurse(ans, matrix, 0, 0, len(matrix[0]), len(matrix))

    def spiral_order_subproblem_bu(self, matrix):
        """Iterate with each sub-problem as the sub-matrix box one layer within the current matrix.

        Apply traversal with the termination occurring when we have added the expected number of elements in the matrix (all elements once). To handle single row or column cases (no four corners), have an initial check for edge lengths at 1.
        """
        m = len(matrix)
        n = len(matrix[0])
        expected = m * n
        ans = []
        x, y = 0, 0
        x_len, y_len = n, m
        while len(ans) < expected:
            if x_len == 1:
                for down in range(y_len):
                    ans.append(matrix[y + down][x])
                return ans
            if y_len == 1:
                for right in range(x_len):
                    ans.append(matrix[y][x + right])
                return ans

            for right in range(x_len - 1):
                ans.append(matrix[y][x + right])
            for down in range(y_len - 1):
                ans.append(matrix[y + down][x + x_len - 1])
            for left in range(x_len - 1):
                ans.append(matrix[y + y_len - 1][x + x_len - 1 - left])
            for up in range(y_len - 1):
                ans.append(matrix[y + y_len - 1 - up][x])

            x += 1
            y += 1
            x_len -= 2
            y_len -= 2 
        return ans

    def spiral_order_visited(self, matrix):
        """Traverse in a direction, turning when we encounter a boundary or visited node.
        """

        def in_bounds(m, n, x, y):
            return x >= 0 and x < n and y >= 0 and y < m

        ans = []
        m, n = len(matrix), len(matrix[0])
        expected = m * n
        visited = [[0 for _ in range(n)] for _ in range(m)]
        directions = [[1, 0], [0, 1], [-1, 0], [0, -1]]
        cur, x, y = 0, 0, 0
        while len(ans) < expected:
            if in_bounds(m, n, x, y) and not visited[y][x]:
                ans.append(matrix[y][x])
                visited[y][x] = 1
            else:
                x = x - directions[cur][0]
                y = y - directions[cur][1]
                cur = (cur + 1) % 4
            x = x + directions[cur][0]
            y = y + directions[cur][1]
        return ans