/*You’ll be given a grid as below:

    0 1 0 2 0
    0 2 2 2 1
    0 2 1 1 1
    1 0 1 0 0
    0 0 1 2 2
    1 1 0 0 1
    x x S x x

In the grid above,
  1: This cell has a coin.
  2: This cell has an enemy.
  0: It contains nothing.

  The highlighted(yellow) zone is the control zone. S is a spaceship that we need to control so that we can get 
  maximum coins.
  Now, S’s initial position will be at the center and we can only move it right or left by one cell or do not move.
  At each time, the non-highlighted part of the grid will move down by one unit.
  We can also use a bomb but only once. If we use that, all the enemies in the 5×5 region above the control zone 
  will be killed.
  If we use a bomb at the very beginning, the grid will look like this:

    0 1 0 2 0
    0 0 0 0 1
    0 0 1 1 1
    1 0 1 0 0
    0 0 1 0 0
    1 1 0 0 1
    x x S x x

  As soon as, the spaceship encounters an enemy or the entire grid has come down, the game ends.
  For example,
  At the very first instance, if we want to collect a coin we should move left( coins=1). This is because when the 
  grid comes down by 1 unit we have a coin on the second position and by moving left we can collect that coin. 
  Next, we should move right to collect another coin (coins=2).
  After this, remain at the same position (coins=4).
  This is the current situation after collecting 4 coins.

    0 1 0 2 0 0 1 0 0 0
    0 2 2 2 1 -->after using 0 0 0 0 1
    x x S x x -->bomb x x S x x

   Now, we can use the bomb to get out of this situation. After this, we can collect at most 1 coin. So maximum coins=5.
*/


public class SpaceCollectorSolver {
    /**
     * Entry method: returns maximum coins collectable.
     * grid: rows x 5 char[][] (rows >= 1). Characters: '0' empty, '1' coin, '2' enemy.
     */
    public int findMaxCoins(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int rows = grid.length;
        // Start at virtual row == rows (means we look at row-1 as first incoming row).
        return dfs(rows, 2, 1, grid);
    }

    /**
     * Recursive DFS:
     * row  : how many rows are still above the control zone (we inspect row-1).
     * col  : spaceship column (0..4).
     * bombs: bombs left (0 or 1).
     * grid : current grid state (rows x 5).
     */
    private int dfs(int row, int col, int bombs, char[][] grid) {
        // If no more rows to process or column out-of-bounds -> game ends, no more coins.
        if (row <= 0 || col < 0 || col >= 5) return 0;

        int best = 0;

        // Look at the next incoming cell (row-1, col) because grid moves down by 1.
        // If there's an enemy in that incoming cell, that move is invalid (you die).
        // Otherwise we can collect coin if it's a '1'.
        int rNext = row - 1;

        // Stay in same column, if safe
        if (grid[rNext][col] != '2') {
            int gain = (grid[rNext][col] == '1') ? 1 : 0;
            best = Math.max(best, gain + dfs(row - 1, col, bombs, grid));
        }

        // Move left then grid moves down
        if (col - 1 >= 0 && grid[rNext][col - 1] != '2') {
            int gain = (grid[rNext][col - 1] == '1') ? 1 : 0;
            best = Math.max(best, gain + dfs(row - 1, col - 1, bombs, grid));
        }

        // Move right then grid moves down
        if (col + 1 < 5 && grid[rNext][col + 1] != '2') {
            int gain = (grid[rNext][col + 1] == '1') ? 1 : 0;
            best = Math.max(best, gain + dfs(row - 1, col + 1, bombs, grid));
        }

        // If no safe move (best == 0) and we still have a bomb, try using bomb now.
        // Using bomb clears '2's in the 5×5 region above the control zone ending at row-1.
        if (best == 0 && bombs > 0) {
            // make a deep copy so other branches are unaffected
            char[][] copy = deepCopy(grid);
            applyBomb(copy, rNext);            // rNext == row-1
            best = Math.max(best, dfs(row, col, bombs - 1, copy));
        }

        return best;
    }

    // Apply bomb: clear '2' -> '0' for rows from r down to max(0, r-4), all columns 0..4.
    private void applyBomb(char[][] grid, int r) {
        if (r < 0) return;
        int top = Math.max(0, r - 4);
        for (int i = r; i >= top; i--) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] == '2') grid[i][j] = '0';
            }
        }
    }

    // Utility to deep-copy a 2D char array
    private char[][] deepCopy(char[][] src) {
        int n = src.length;
        char[][] dst = new char[n][5];
        for (int i = 0; i < n; i++) {
            System.arraycopy(src[i], 0, dst[i], 0, 5);
        }
        return dst;
    }
}
