package mainPackage;

/**
 * The Class SquareMatrix.
 * 
 * @author Derek Sanders
 * @version 2.0
 * @since June 30th, 2015
 */
public class SquareMatrix {

	private static int MAX_ITERATIONS = 200000; // Arbitrarily large value used
												// to cease row reduction of a
												// matrix when finding its
												// inverse if the matrix is not
												// invertible.

	private RowVector[] rows;
	private int dimension;

	/**
	 * Instantiates a new square matrix.
	 *
	 * @param rows
	 *            the rows
	 */
	public SquareMatrix(RowVector[] rows) {

		try {

			this.setRows(rows);
			this.setDimension(this.rows.length);

		} catch (IllegalMatrixException e) {

			System.out.println("Square matrix could not be created.");
			e.printStackTrace();
		}
	}

	/**
	 * Sets the rows.
	 *
	 * @param rows
	 *            the new rows
	 * @throws IllegalMatrixException
	 *             the illegal matrix exception
	 */
	public void setRows(RowVector[] rows) throws IllegalMatrixException {

		int i = 0;

		while (i < rows.length) {

			if (rows.length != rows[i].coordinates.length) {

				throw new IllegalMatrixException(
						"Must specify a square matrix.");
			}

			i++;
		}

		this.rows = rows.clone();
	}

	/**
	 * Creates the zero matrix.
	 *
	 * @param dimension
	 *            the dimension
	 * @return the square matrix
	 */
	public static SquareMatrix createZeroMatrix(int dimension) {

		RowVector[] zeroEntries = new RowVector[dimension];

		int i = 0;

		while (i < dimension) {

			zeroEntries[i] = RowVector.createZeroVector(dimension);
			i++;
		}

		SquareMatrix zeroMatrix = new SquareMatrix(zeroEntries);

		return zeroMatrix;
	}

	/**
	 * Creates the identity matrix.
	 *
	 * @param dimension
	 *            the dimension
	 * @return the square matrix
	 */
	public static SquareMatrix createIdentityMatrix(int dimension) {

		SquareMatrix identityMatrix = SquareMatrix.createZeroMatrix(dimension);

		int i = 0;

		while (i < dimension) {

			identityMatrix.rows[i].setCoordinate(i, 1);
			i++;
		}

		return identityMatrix;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public RowVector[] getRows() {

		RowVector[] rowsCopy = new RowVector[this.rows.length];

		int i = 0;

		while (i < this.rows.length) {

			rowsCopy[i] = this.rows[i].clone();
			i++;
		}

		return rowsCopy;
	}

	/**
	 * L decomposition.
	 *
	 * @return the square matrix
	 */
	public SquareMatrix lDecomposition() {

		return null;
	}

	/**
	 * Performs row operations until the matrix is in Reduced Row Echelon Form
	 * (RREF), and prints out each step. Deprecated method, not currently used
	 * in program.
	 *
	 * @return the rref
	 */
	public SquareMatrix getRREF() {

		SquareMatrix matrixCopy = this.clone();
		int n = matrixCopy.rows.length;

		int steps = 1;

		SquareMatrix identity = SquareMatrix.createIdentityMatrix(n);

		while (!matrixCopy.equals(identity)) {

			int i = 0;

			int p = 1;

			while (i < matrixCopy.rows.length) {

				while (matrixCopy.rows[i].getCoordinates()[i] == 0
						&& steps < MAX_ITERATIONS) {

					try {

						matrixCopy.rows[i].swapRow(matrixCopy.rows[i + p]);
						System.out.println("Step " + steps + ": Swapped rows "
								+ (i + 1) + " and " + (i + p + 1));
						steps++;

					} catch (IndexOutOfBoundsException indexTooLarge) {

						try {
							matrixCopy.rows[i].swapRow(matrixCopy.rows[i - p]);
							System.out.println("Step " + steps
									+ ": Swapped rows " + (i + 1) + " and "
									+ (i - p + 1));
							steps++;

						} catch (IndexOutOfBoundsException negativeIndex) {

							return matrixCopy;
						}
					}

					p++;

					if (p == n) {

						p = 1;
					}
				}

				if (matrixCopy.rows[i].getCoordinates()[i] != 0
						&& matrixCopy.rows[i].getCoordinates()[i] != 1) {

					System.out.println("Step " + steps + ": Multiplied row "
							+ (i + 1) + " by "
							+ (1.0 / matrixCopy.rows[i].getCoordinates()[i]));
					steps++;
					matrixCopy.rows[i].multiplyRow(1.0 / matrixCopy.rows[i]
							.getCoordinates()[i]);
				}

				if (matrixCopy.equals(identity)) {

					return matrixCopy;
				}

				int j = 0;

				while (j < matrixCopy.rows.length) {

					if (j == i) {

						j++;
					}

					if (j == n) {

						return matrixCopy;
					}

					while (matrixCopy.rows[j].getCoordinates()[i] != 0) {

						if (matrixCopy.rows[j].getCoordinates()[i] >= 1) {

							System.out.println("Step " + steps
									+ ": Subtracted row " + (i + 1) + " * "
									+ matrixCopy.rows[j].coordinates[i]
									+ " from row " + (j + 1));
							steps++;
							matrixCopy.rows[j].subtractRow(matrixCopy.rows[i],
									matrixCopy.rows[j].coordinates[i]);

						} else if (matrixCopy.rows[j].getCoordinates()[i] < 1) {

							System.out
									.println("Step "
											+ steps
											+ ": Added row "
											+ (i + 1)
											+ " * "
											+ Math.abs(matrixCopy.rows[j].coordinates[i])
											+ " to row " + (j + 1));
							steps++;
							matrixCopy.rows[j]
									.addRow(matrixCopy.rows[i],
											Math.abs(matrixCopy.rows[j].coordinates[i]));
						}
					}

					j++;
				}

				i++;
			}
		}
		return matrixCopy;
	}

	/**
	 * Performs row operations on a clone of the original matrix until it is in
	 * Reduced Row Echelon Form (RREF). Simultaneously performs these operations
	 * on the identity matrix in order to find the inverse of the matrix.
	 *
	 * @return the inverse
	 */
	public SquareMatrix getInverse() {

		SquareMatrix matrixCopy = this.clone();
		int n = matrixCopy.rows.length;
		SquareMatrix identity = SquareMatrix.createIdentityMatrix(n);
		SquareMatrix inverse = SquareMatrix.createIdentityMatrix(n);

		while (!matrixCopy.equals(identity)) {

			int i = 0;

			int p = 1;

			while (i < matrixCopy.rows.length) {

				while (matrixCopy.rows[i].getCoordinates()[i] == 0) {

					try {

						inverse.rows[i].swapRow(inverse.rows[i + p]);
						matrixCopy.rows[i].swapRow(matrixCopy.rows[i + p]);

					} catch (IndexOutOfBoundsException e) {

						inverse.rows[i].swapRow(inverse.rows[i - p]);
						matrixCopy.rows[i].swapRow(matrixCopy.rows[i - p]);
					}

					p++;

					if (p == n) {

						p = 1;
					}
				}

				if (matrixCopy.rows[i].getCoordinates()[i] != 0
						&& matrixCopy.rows[i].getCoordinates()[i] != 1) {

					inverse.rows[i].multiplyRow(1.0 / matrixCopy.rows[i]
							.getCoordinates()[i]);

					matrixCopy.rows[i].multiplyRow(1.0 / matrixCopy.rows[i]
							.getCoordinates()[i]);
				}

				if (matrixCopy.equals(identity)) {

					return inverse;
				}

				int j = 0;

				while (j < matrixCopy.rows.length) {

					if (j == i) {

						j++;
					}

					if (j == n) {

						return inverse;
					}

					while (matrixCopy.rows[j].getCoordinates()[i] != 0) {

						if (matrixCopy.rows[j].getCoordinates()[i] >= 1) {

							inverse.rows[j].subtractRow(inverse.rows[i],
									matrixCopy.rows[j].coordinates[i]);

							matrixCopy.rows[j].subtractRow(matrixCopy.rows[i],
									matrixCopy.rows[j].coordinates[i]);

						} else if (matrixCopy.rows[j].getCoordinates()[i] < 1) {

							inverse.rows[j]
									.addRow(inverse.rows[i],
											Math.abs(matrixCopy.rows[j].coordinates[i]));

							matrixCopy.rows[j]
									.addRow(matrixCopy.rows[i],
											Math.abs(matrixCopy.rows[j].coordinates[i]));
						}
					}

					j++;
				}

				i++;
			}
		}

		return inverse;
	}

	// public SquareMatrix getChangeOfBasis() {
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {

		if (object instanceof SquareMatrix) {

			SquareMatrix matrix = (SquareMatrix) object;

			int i = 0;

			if (this.rows.length != matrix.rows.length) {

				return false;
			}

			while (i < this.rows.length) {

				if (!this.rows[i].equals(matrix.rows[i])) {

					return false;
				}

				i++;
			}

			return true;

		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public SquareMatrix clone() {

		SquareMatrix matrixCopy = new SquareMatrix(this.getRows());

		return matrixCopy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String matrixInfo = "";

		int i = 0;

		while (i < this.rows.length) {

			matrixInfo += rows[i].toString();

			if (i < this.rows.length - 1) {

				matrixInfo += "\n";
			}

			i++;
		}

		return matrixInfo;
	}

	/**
	 * Gets the dimension.
	 *
	 * @return the dimension
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * Sets the dimension.
	 *
	 * @param dimension the new dimension
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
}
