package utils;

public class Matrix3x3 {

	private Vector3[] matrix;

	public Matrix3x3() {
		matrix = new Vector3[3];
	}

	public Matrix3x3(Vector3 r1, Vector3 r2, Vector3 r3) {
		matrix = new Vector3[3];
		matrix[0] = r1;
		matrix[1] = r2;
		matrix[2] = r3;
	}

	public void setRow(int index, Vector3 value) {
		matrix[index] = value;
	}

	public Vector3 getRow(int index) {
		return matrix[index];
	}

	public Vector3 getCol(int index) {
		return new Vector3(matrix[0].getComponent(index), matrix[1].getComponent(index), matrix[2].getComponent(index));
	}

	public double getValue(int i, int j) {
		return matrix[i].getComponent(j);
	}

	public Vector3 matVecMult(Vector3 vec){
		double x = vec.dot(matrix[0]);
		double y = vec.dot(matrix[1]);
		double z = vec.dot(matrix[2]);

		return new Vector3(x, y, z);
	}

	public Matrix3x3 matrixMult(Matrix3x3 m) {
		Matrix3x3 result = new Matrix3x3();
		for(int i=0; i<matrix.length; i++) {
			Vector3 row = new Vector3();
			for(int j=0; j<3; j++) {
				row.setComponent(j, getRow(i).dot(m.getCol(j)));
			}
			result.setRow(i, row);
		}
		return result;
	}

	public static Matrix3x3 getRotationMatrix(double alpha, double beta, double gamma) {
		// alpha, beta, gamma are the rotation angle from x,y,z
		Matrix3x3 yaw = new Matrix3x3(new Vector3(Math.cos(gamma), -Math.sin(gamma), 0),
				new Vector3(Math.sin(gamma), Math.cos(gamma), 0),
				Vector3.UNIT_VECTOR_Z);

		Matrix3x3 pitch = new Matrix3x3(new Vector3(Math.cos(beta), 0, Math.sin(beta)),
				Vector3.UNIT_VECTOR_Y,
				new Vector3(-Math.sin(beta), 0, Math.cos(beta)));

		Matrix3x3 roll = new Matrix3x3(Vector3.UNIT_VECTOR_X,
				new Vector3(0, Math.cos(alpha), -Math.sin(alpha)),
				new Vector3(0, Math.sin(alpha), Math.cos(alpha)));

		Matrix3x3 rotationMatrix = yaw.matrixMult(pitch).matrixMult(roll);
		return rotationMatrix;
	}

}

