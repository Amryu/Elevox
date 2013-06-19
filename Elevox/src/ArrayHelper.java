
public class ArrayHelper {
	public static float[] addArrays(float[] array1, float[] array2) {
		float[] newArray = new float[array1.length + array2.length];
		
		for(int i = 0; i < array1.length; i++) {
			newArray[i] = array1[i];
		}
		
		for(int i = array1.length; i < array1.length + array2.length; i++) {
			newArray[i] = array2[i - array1.length];
		}
		
		return newArray;
	}
}
