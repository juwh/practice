from typing import List

class Solution:
    def validWordSquare(self, words: List[str]) -> bool:
        #return self.valid_word_square_init(words)
        return self.valid_word_square_direct(words)

    def valid_word_square_init(self, words):
        """Break down words into a square matrix with each cell containing a single character, then comparing characters at each coordinate with the transpose.

        The maximum dimension of the square can be found as the longest word in words (horizontal direction) or the length of words itself (vertical direction).
        """
        n = max(max(map(lambda x: len(x), words)), len(words))

        square = [[None for _ in range(n)] for _ in range(n)]
        for i, word in enumerate(words):
            for j, c in enumerate(word):
                square[i][j] = c

        for row in range(n):
            for col in range(row + 1, n):
                if square[row][col] != square[col][row]:
                    return False
        return True

    def valid_word_square_direct(self, words):
        """Process each character in all input words, identifying instances where a character exists but not in the transpose or comparing character values with the transpose.

        The loops ensure that all words and their characters will be processed, so the task is to check that the transpose exists and matches in value. For the tranpose letter to exist, the row and column must not extend beyond certain boundaries. The column offset of a valid letter must not go beyond the row limit (number of words). In addition, the row offset of the letter must land within the column limit (length of word) of the word in the letter columnth row. Alternatively, you can think of the prospective process of querying the transpose as flipping row and column, so the first index is checked by bounding the col value within the number of rows then, pulling the row as the word, bounding the row to the maximum col of the word. Once we know we can query the transpose location, we can compare values.

        We check all rows/columns instead of just above the transpose diagonal as there may be a scenario of a character below that exists below the diagonal but not in the transpose position (above the diagonal). All character mismatches must be considered but our only direction for this check is from an existing character due to the loops iterating through only letters rather than blank spaces (from the hypothetical word square matrix) as well.
        """
        rows = len(words)
        for row in range(rows):
            for col in range(len(words[row])):
                if col + 1 > rows or row + 1 > len(words[col]) or words[row][col] != words[col][row]:
                    return False
        return True