package effectPackage;

public class Clamper {
	public static float clamp(float value, float min, float max) {
		if(value >= max) value = max;
		else if(value <= min) value = min;
		
		return value;
	}
}