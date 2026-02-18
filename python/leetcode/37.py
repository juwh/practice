from typing import List

class Solution:
    def solveSudoku(self, board: List[List[str]]) -> None:
        """
        Do not return anything, modify board in-place instead.
        """
        self.solve_sudoku_init(board)
        #self.solve_sudoku_iter(board)

    def solve_sudoku_init(self, board):
        """Apply backtracking by attempting to fill any empty cell with a valid value (based on constraint checks) either until we reach the "end" of all cells to consider or we run out of options and try a different branch by "undoing" one step.

        The general backtracking structure followed includes the following: a base case to indicate recursive completion, a prospective action (surrounded by a check if conditional) which leads to the next recursive call, a completion check (if we want to short circuit on finding the first solution), then an undo action of the prospective action.

        For cases where we have extended beyond the column boundary or have encountered a space with an existing value, we continue on by recursively calling the next iteration (with potentially a "carriage return").
        """

        def placeable(row, col, val):
            return valid_row(row, val) and valid_col(col, val) and valid_box(row, col, val)

        def valid_row(row, val):
            return not rows[row][val - 1]

        def valid_col(col, val):
            return not cols[col][val - 1]

        def valid_box(row, col, val):
            flattened = (row // 3) * 3 + (col // 3)
            return not boxes[flattened][val - 1]

        def update_checks(row, col, val, flag):
            rows[row][val - 1] = flag
            cols[col][val - 1] = flag
            flattened = (row // 3) * 3 + (col // 3)
            boxes[flattened][val - 1] = flag
        
        def backtrack(row, col):
            if row == DIM:
                return True
            if col == DIM:
                return backtrack(row + 1, 0)
            if board[row][col] != ".":
                return backtrack(row, col + 1)
            for val in range(1, 10):
                if placeable(row, col, val):
                    board[row][col] = str(val)
                    update_checks(row, col, val, 1)
                    if backtrack(row, col + 1):
                        return True
                    update_checks(row, col, val, 0)
            board[row][col] = "."
            return False

        n = 3
        DIM = n * n
        cols = [[0 for _ in range(DIM)] for _ in range(DIM)]
        rows = [[0 for _ in range(DIM)] for _ in range(DIM)]
        boxes = [[0 for _ in range(DIM)] for _ in range(DIM)]
        for row in range(DIM):
            for col in range(DIM):
                if board[row][col] != ".":
                    update_checks(row, col, int(board[row][col]), 1)
        backtrack(0, 0)
            
    def solve_sudoku_iter(self, board):
        """Utilize a stack to keep track of the current solve path for sudoku, such that the top of the stack reflects the next coordinate and value to try until we 
        """

        def placeable(row, col, val):
            return valid_row(row, val) and valid_col(col, val) and valid_box(row, col, val)

        def valid_row(row, val):
            return not rows[row][val - 1]

        def valid_col(col, val):
            return not cols[col][val - 1]

        def valid_box(row, col, val):
            flattened = (row // 3) * 3 + (col // 3)
            return not boxes[flattened][val - 1]

        def update_checks(row, col, val, flag):
            rows[row][val - 1] = flag
            cols[col][val - 1] = flag
            flattened = (row // 3) * 3 + (col // 3)
            boxes[flattened][val - 1] = flag

        def next_place(board, row, col, val):
            while row < DIM:
                if val > DIM:
                    return (-1, -1, -1)
                if col >= DIM:
                    row += 1
                    col = 0
                    val = 1
                    continue
                if board[row][col] != ".":
                    col += 1
                    val = 1
                    continue
                if not placeable(row, col, val):
                    val += 1
                    continue
                return (row, col, val)
            return (-1, -1, -1)

        n = 3
        DIM = n * n
        cols = [[0 for _ in range(DIM)] for _ in range(DIM)]
        rows = [[0 for _ in range(DIM)] for _ in range(DIM)]
        boxes = [[0 for _ in range(DIM)] for _ in range(DIM)]
        for row in range(DIM):
            for col in range(DIM):
                if board[row][col] != ".":
                    update_checks(row, col, int(board[row][col]), 1)
        
        s = [next_place(board, 0, 0, 1)]
        while len(s) > 0:
            row, col, val = s[-1]
            if row == DIM:
                return
            else:
                if row >= 0:
                    board[row][col] = str(val)
                    update_checks(row, col, val, 1)
                    s.append(next_place(board, row, col + 1, 1))
                else:
                    s.pop()
                    if len(s) > 0:
                        row, col, val = s.pop()
                        board[row][col] = "."
                        update_checks(row, col, val, 0)
                        s.append(next_place(board, row, col, val + 1))
