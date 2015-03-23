/**
 * 
 */

/**
 * @author boris
 *
 */
public final class Board {

	/**
	 * Allows unit testing with assert key word.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Constructs a board from an N-by-N array of blocks.
	 * @param blocks
	 */
	public Board(int[][] blocks) {
		
	}
	
	/**
	 * Returns the board dimension N.
	 * @return the board dimension.
	 */
	public int dimension() {
		return 0;
	}
	
	/**
	 * Returns the number of blocks out of place.
	 * @return the number of blocks out of place.
	 */
	public int hamming() {
		return 0;
	}
	
	/**
	 * Returns the sum of Manhattan distances between blocks and goal.
	 * @return the sum of Manhattan distances between blocks and goal.
	 */
	public int manhattan() {
		return 0;
	}
	
	/**
	 * Is this board the goal board?
	 * @return true if it is the goal board; otherwise, false.
	 */
	public boolean isGoal() {
		return false;
	}
	
	/**
	 * Generates the board resulting from exchanging two adjacent blocks in the 
	 * same row.
	 * @return the resulting board.
	 */
	public Board twin() {
		return null;
	}
	
	/**
	 * Does this board equals the given one y?
	 * @return true if both objects are equal; otherwise, false.
	 */
	@Override
	public boolean equals(Object y) {
		return false;
	}
	
	/**
	 * Returns all neighboring boards.
	 * @return an iterable collection of neighboring boards.
	 */
	public Iterable<Board> neighbors() {
		return null;
	}
	
	/**
	 * Returns the following string representation:
	 * 3
	 * 1 3 4
	 * 0 8 7
	 * 6 2 5
	 */
	public String toString() {
		return null;
	}

}
