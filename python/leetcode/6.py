class Solution:
    def convert(self, s: str, numRows: int) -> str:
        #return self.convert_init(s, numRows)
        #return self.convert_matrix(s, numRows)
        return self.convert_period(s, numRows)

    def convert_init(self, s, num_rows):
        """Calculate the indices of the characters for each row by identifying the cadence from the direction (up or down) and the row position.

        The number of indices to pass before returning to the same row is constant to the number of rows above for the up direction and the number of rows below for the down direction. The number of indices to the next letter in the same row when going in the down direction is (rows_num - row - 1) * 2. The number of indices to the next letter in the same row when going in the up direction is row * 2.

        An edge case to consider is for the top and bottom rows which have a 0 index cadence for up and down directions respectively. However, to avoid counting the same index twice, we do not change the "direction" for those rows.
        """
        if num_rows == 1:
            return s
        n = len(s)
        ans = []
        for row in range(num_rows):
            idx = row
            down = (row != (num_rows - 1))
            while idx < n:
                ans.append(s[idx])
                if down:
                    idx += (num_rows - row - 1) * 2
                if not down:
                    idx += row * 2
                if row != 0 and row != num_rows - 1:
                    down = not down
        return "".join(ans)

    def convert_matrix(self, s, num_rows):
        """Create a matrix to contain the string characters placed in zigzag pattern, set characters in their appropriate locations, then combine rows by joining.
        """
        if num_rows == 1:
            return s
        n = len(s)
        matrix = [["" for _ in range(n)] for _ in range(num_rows)]
        chars_placed = 0
        row = 0
        col = 0
        while chars_placed < n:
            while chars_placed < n and row < num_rows:
                matrix[row][col] = s[chars_placed]
                chars_placed += 1
                row += 1
            row -= 2
            col += 1
            while chars_placed < n and row >= 0:
                matrix[row][col] = s[chars_placed]
                chars_placed += 1
                row -= 1
                col += 1
            row += 2
            col -= 1
        
        ans = []
        for r in matrix:
            ans.append("".join(r))
        return "".join(ans)

    def convert_period(self, s, num_rows):
        """Calculate indices to pull characters from for each row by isolating "periods" bounded by the end of the diagonal.

        Observe that within each period, all rows except for the first and last have two characters. The number of characters between the two in each row can be calculated directly from the row number as (rows - row - 1) * 2.

        We can calculate the index offset from one period to the next in the same "position" in the zigzag as (rows - 1) * 2.
        """
        if num_rows == 1:
            return s
        n = len(s)
        ans = []
        for row in range(num_rows):
            idx = row
            while idx < n:
                ans.append(s[idx])
                second = (num_rows - row - 1) * 2
                if row != 0 and row != num_rows - 1 and idx + second < n:
                    ans.append(s[idx + second])
                idx += (num_rows - 1) * 2
        return "".join(ans)