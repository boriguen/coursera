/**
 * 
 */

/**
 * @author boris
 *
 */
public final class Solver {

	/**
	 * Solves a slider puzzle.
	 * @param args
	 */
	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
	
	/**
	 * Constructs an instance to find a solution for the given initial board
	 * (using A* algorithm).
	 * @param board
	 */
	public Solver(Board board) {
		
	}
	
	/**
	 * Is the initial board solvable?
	 * @return true if solvable; otherwise, false.
	 */
	public boolean isSolvable() {
		return false;
	}
	
	/**
	 * Calculates the minimum number of moves to solve the initial board.
	 * @return the resulting min number if relevant; otherwise, -1 if unsolvable.
	 */
	public int moves() {
		return 0;
	}
	
	/**
	 * Returns the sequence of boards in a shortest solution.
	 * @return the resulting sequence of boards if relevant; otherwise, null if unsolvable.
	 */
	public Iterable<Board> solution() {
		return null;
	}

}
