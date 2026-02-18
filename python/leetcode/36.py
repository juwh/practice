from typing import List

class Solution:
    def isValidSudoku(self, board: List[List[str]]) -> bool:
        return self.is_valid_sudoku_init(board)
        #return self.is_valid_sudoku_bit(board)

    def is_valid_sudoku_init(self, board):
        """Utilize lists to track encountered cell values on the board and classify the row, column, and box as "used", short-circuit returning an invalid indicator if any attribute has already been filled.

        Boxes map such that rows 0 - 2 will be the first row of boxes, 3 - 5 to the second row, 6 - 8 to the third row. The column value will map to the specific box within the row.
        """
        
        DIM = 9
        rows = [[0 for _ in range(DIM)] for _ in range(DIM)]
        cols = [[0 for _ in range(DIM)] for _ in range(DIM)]
        boxes = [[0 for _ in range(DIM)] for _ in range(DIM)]

        for row in range(DIM):
            for col in range(DIM):
                if board[row][col] == ".":
                    continue
                val = int(board[row][col]) - 1
                if rows[row][val]:
                    return False
                rows[row][val] += 1
                if cols[col][val]:
                    return False
                cols[col][val] += 1
                flattened = (row // 3) * 3 + (col // 3)
                if boxes[flattened][val]:
                    return False
                boxes[flattened][val] += 1
        return True
        
    def is_valid_sudoku_bit(self, board):
        """Applies bit operations for checking for duplicate numbers within arrays of rows, columns, and boxes.

        Arrays of length N store a single number which represents a bit sequence with each place representing a value being checked for duplicate. Starting with 1 and bitshifting using << creates a mask which when combined with & will zero out all values except the place representing the value being checked for duplicate. This place will not be zeroed out if it is already set to 1, meaning we already encountered it for that group. Once the check has verified that the value has not been previously encountered, we mark the number in the array by an | operation using the same bitmask. A 2D array is flattened into a 1D array by row * n + col for a matrix of m by n dimensions. For boxes, we treat the board like a 3 by 3 matrix by using integer division to reduce the row and column from 9 possible indices to 3.
        """
        N = 9

        rows = [0] * N
        cols = [0] * N
        boxes = [0] * N

        for row in range(N):
            for col in range(N):
                if board[row][col] == ".":
                    continue
                offset = int(board[row][col]) - 1
                if rows[row] & (1 << offset):
                    return False
                rows[row] |= (1 << offset)
                if cols[col] & (1 << offset):
                    return False
                cols[col] |= (1 << offset)
                box_index = (row // 3) * 3 + (col // 3)
                if boxes[box_index] & (1 << offset):
                    return False
                boxes[box_index] |= (1 << offset)
        
        return True
