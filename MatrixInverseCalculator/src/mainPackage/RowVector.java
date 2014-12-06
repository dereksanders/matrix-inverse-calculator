package mainPackage;

/**
 * The Class RowVector.
 * 
 * @author Derek Sanders
 * @version 1.0
 * @since December 5th, 2014
 */
public class RowVector {

	double[] coordinates;

	/**
	 * Instantiates a new row vector.
	 *
	 * @param coordinates
	 *            the coordinates
	 */
	public RowVector(double[] coordinates) {

		setCoordinates(coordinates);
	}

	/**
	 * Gets the coordinates.
	 *
	 * @return the coordinates
	 */
	public double[] getCoordinates() {

		double[] coordinatesCopy = new double[this.coordinates.length];

		int i = 0;

		while (i < this.coordinates.length) {

			coordinatesCopy[i] = this.coordinates[i];
			i++;
		}

		return coordinatesCopy;
	}

	/**
	 * Sets the coordinates.
	 *
	 * @param coordinates
	 *            the new coordinates
	 */
	public void setCoordinates(double[] coordinates) {

		this.coordinates = coordinates.clone();
	}

	/**
	 * Sets a coordinate.
	 *
	 * @param entry
	 *            the entry
	 * @param coordinate
	 *            the coordinate
	 */
	public void setCoordinate(int entry, double coordinate) {

		this.coordinates[entry] = coordinate;
	}

	/**
	 * Multiplies the row vector by a scalar.
	 *
	 * @param scalar
	 *            the scalar
	 */
	public void multiplyRow(double scalar) {

		int i = 0;

		while (i < this.coordinates.length) {

			setCoordinate(i, scalar * this.getCoordinates()[i]);
			i++;
		}
	}

	/**
	 * Adds a row vector to the row vector.
	 *
	 * @param rowBeingAdded
	 *            the row being added
	 */
	public void addRow(RowVector rowBeingAdded) {

		int i = 0;

		while (i < this.coordinates.length) {

			this.setCoordinate(i, this.coordinates[i]
					+ rowBeingAdded.coordinates[i]);

			i++;
		}
	}

	/**
	 * Subtracts a row vector from the row vector.
	 *
	 * @param rowBeingSubtracted
	 *            the row being subtracted
	 */
	public void subtractRow(RowVector rowBeingSubtracted) {

		int i = 0;

		while (i < this.coordinates.length) {

			this.setCoordinate(i, this.coordinates[i]
					- rowBeingSubtracted.coordinates[i]);

			i++;
		}
	}

	/**
	 * Swaps the row vector with a row vector.
	 *
	 * @param rowBeingSwappedWith
	 *            the row being swapped with
	 */
	public void swapRow(RowVector rowBeingSwappedWith) {

		RowVector rowCopy = this.clone();

		this.setCoordinates(rowBeingSwappedWith.coordinates);

		rowBeingSwappedWith.setCoordinates(rowCopy.coordinates);
	}

	/**
	 * Creates the zero vector.
	 *
	 * @param dimension
	 *            the dimension
	 * @return the row vector
	 */
	public static RowVector createZeroVector(int dimension) {

		double[] zeroCoordinates = new double[dimension];

		int i = 0;

		while (i < dimension) {

			zeroCoordinates[i] = 0;
			i++;
		}

		RowVector zeroVector = new RowVector(zeroCoordinates);

		return zeroVector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {

		if (object instanceof RowVector) {

			RowVector vector = (RowVector) object;

			if (this.coordinates.length != vector.coordinates.length) {

				return false;
			}

			int i = 0;

			while (i < this.coordinates.length) {

				if (this.coordinates[i] != vector.coordinates[i]) {

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
	public RowVector clone() {

		RowVector vectorCopy = new RowVector(this.getCoordinates());

		return vectorCopy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String vectorInfo = "";

		int i = 0;

		while (i < this.coordinates.length) {

			vectorInfo += this.coordinates[i];

			if (i < this.coordinates.length - 1) {

				vectorInfo += ", ";
			}

			i++;
		}

		return vectorInfo;
	}
}
