import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class ZlibUtil {

	public static void main(String[] args) {
		System.out.println(decodeZlib(decodeBase64("eJxVUktOw0AMvQqysgCpKfOfTHZQYAkLloCqNJmWqiGpkkkRRT0CR+AMrLkPC46BPaUSRPl47Ge/ZzuvMPQd5K9QLSvIgV2cqcw6nU74+VWqLvlV6oTJUq7M5FywySWbSNiNIGwhF1nG6BpB+9x4LALbBYyg8rNhATm6ixVVtM54VxbeVlbNWZWVtqjMbD7zirFMMszo6waBy9Cgva7JbHs0h4CmYEKmjKfMHHGTC5VrRxxFKCC/Q9WE8ZtAmbGN5Knx5LtuG4/OpCRdx81Q1yf7Nzl97LX3RVc+zoYQ2mZa1styhbHp0PtuGuNca8OZktJxLYS2IlInGxIrxnLMBR1RKgq+uaXclX95bjvKpUhBwO+3j6/Pd7wjdhPLjtWYR23DgUdnUjnjhJIs08KaGKX0s/X66Da0Xexl35kipr32KHoalk/+d1L3p4zj829WSV9CbulLbNxY4RyycWUVOv8vMinDHwhi+GEiTRFJFvPtC+x2D7i0akXdSGxGaIjn+AektLzdD3v7oiQ=")));
	}
	
	public static String decodeAll(String origin) {
		try {
			byte[] middle = decodeBase64(origin);
			return decodeZlib(middle);
		} catch (Exception e) {
			System.out.println("wrong:" + origin);
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] decodeBase64(String origin) {
		return Base64.getDecoder().decode(origin);
	}

	public static String decodeZlib(byte[] input) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
		Inflater decompresser = new Inflater();
		decompresser.setInput(input, 0, input.length);
		int resultLength;
		byte[] result = new byte[1024];

		try {
			while ((resultLength = decompresser.inflate(result)) != 0) {
				bos.write(result, 0, resultLength);
			}
		} catch (DataFormatException e) {
			e.printStackTrace();
		}

		return new String(bos.toByteArray(), StandardCharsets.UTF_8);
	}
}
