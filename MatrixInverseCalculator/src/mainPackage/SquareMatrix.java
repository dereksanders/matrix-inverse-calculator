package mainPackage;

/**
 * The Class SquareMatrix.
 * 
 * @author Derek Sanders
 * @version 1.0
 * @since December 5th, 2014
 */
public class SquareMatrix {

	private RowVector[] rows;

	/**
	 * Instantiates a new square matrix.
	 *
	 * @param rows
	 *            the rows
	 */
	public SquareMatrix(RowVector[] rows) {

		try {

			this.setRows(rows);

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
	 * Performs row operations until the matrix is in Reduced Row Echelon Form
	 * (RREF).
	 *
	 */
	public void getRREF() {

		int n = this.rows.length;

		SquareMatrix identity = SquareMatrix.createIdentityMatrix(n);

		while (!this.equals(identity)) {

			int i = 0;

			int p = 1;

			while (i < this.rows.length) {

				while (this.rows[i].getCoordinates()[i] == 0) {

					try {

						this.rows[i].swapRow(this.rows[i + p]);

					} catch (IndexOutOfBoundsException e) {

						this.rows[i].swapRow(this.rows[i - p]);
					}

					p++;

					if (p == n) {

						p = 1;
					}
				}

				if (this.rows[i].getCoordinates()[i] != 0) {

					this.rows[i].multiplyRow(1.0 / this.rows[i]
							.getCoordinates()[i]);

				}

				if (this.equals(identity)) {

					return;
				}

				int j = 0;

				while (j < this.rows.length) {

					if (j == i) {

						j++;
					}

					if (j == 3) {

						return;
					}

					while (this.rows[j].getCoordinates()[i] != 0) {

						if (this.rows[j].getCoordinates()[i] >= 1) {

							this.rows[j].subtractRow(this.rows[i]);

						} else if (this.rows[j].getCoordinates()[i] < 1) {

							this.rows[j].addRow(this.rows[i]);
						}
					}

					j++;
				}

				i++;
			}
		}
	}

	/**
	 * Performs row operations until the matrix is in Reduced Row Echelon Form
	 * (RREF). Simultaneously performs these operations on the identity matrix
	 * in order to find the inverse of the matrix.
	 *
	 * @return the inverse
	 */
	public SquareMatrix getInverse() {

		int n = this.rows.length;

		SquareMatrix identity = SquareMatrix.createIdentityMatrix(n);

		SquareMatrix inverse = SquareMatrix.createIdentityMatrix(n);

		while (!this.equals(identity)) {

			int i = 0;

			int p = 1;

			while (i < this.rows.length) {

				while (this.rows[i].getCoordinates()[i] == 0) {

					try {

						inverse.rows[i].swapRow(inverse.rows[i + p]);
						this.rows[i].swapRow(this.rows[i + p]);

					} catch (IndexOutOfBoundsException e) {

						inverse.rows[i].swapRow(inverse.rows[i - p]);
						this.rows[i].swapRow(this.rows[i - p]);
					}

					p++;

					if (p == n) {

						p = 1;
					}
				}

				if (this.rows[i].getCoordinates()[i] != 0) {

					inverse.rows[i].multiplyRow(1.0 / this.rows[i]
							.getCoordinates()[i]);

					this.rows[i].multiplyRow(1.0 / this.rows[i]
							.getCoordinates()[i]);

				}

				if (this.equals(identity)) {

					return inverse;
				}

				int j = 0;

				while (j < this.rows.length) {

					if (j == i) {

						j++;
					}

					if (j == n) {

						return inverse;
					}

					while (this.rows[j].getCoordinates()[i] != 0) {

						if (this.rows[j].getCoordinates()[i] >= 1) {

							inverse.rows[j].subtractRow(inverse.rows[i]);

							this.rows[j].subtractRow(this.rows[i]);

						} else if (this.rows[j].getCoordinates()[i] < 1) {

							inverse.rows[j].addRow(inverse.rows[i]);

							this.rows[j].addRow(this.rows[i]);
						}
					}

					j++;
				}

				i++;
			}
		}

		return inverse;
	}

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
}
