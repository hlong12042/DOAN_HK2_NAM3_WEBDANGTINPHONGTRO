package ptithcm.Random;


public class Random {
	public static final char[] lower_char = new String("abcdefghijklmnopqrstuvwxyz").toCharArray();
	public static final char[] upper_char = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	public static final char[] number_char = new String("0123456789").toCharArray();
	public static final char[] alphabet_char = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();	
	public static final char[] character_char = new String("abcdefghijklmnopqrstuvwxyz"
			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~`!@#$%^&*-_?/").toCharArray();
	public static final int lower = 1;
	public static final int upper = 2;
	public static final int number = 3;
	public static final int alphabet = 4;
	public static final int character = 5;
	
	public static void swap(char cs[], int a, int b) {
		char c = cs[a];
		cs[a] = cs[b];
		cs[b] = c;
	}
	
	public static int randomInt(int min, int max){ 
	    if(min >= max)
	        return 0;
	    int d = max - min;
	    return Math.abs((new java.util.Random().nextInt() % (d+1) + min));
	}
	
	public static String RandomString(int length, int gioihan) {
		String str="";
		char[] cs = new char[100];
		switch (gioihan) {
		case lower -> cs=lower_char;
		case upper -> cs=upper_char;
		case number -> cs=number_char;
		case alphabet -> cs=alphabet_char;
		case character -> cs=character_char;
		}
		for (int i=0; i<length; i++) {
			int ranint = randomInt(i, cs.length-1);
			str+=cs[ranint];
			swap(cs,i,ranint);
		}
		return str;
	}
	
	public static String[] RandomStringArray(int n, int length, int gioihan) {
		String str = "";
		for (int i=0; i<n-1; i++) 
			str+=RandomString(length, gioihan)+" ";
		str+=RandomString(length, gioihan);
		return str.split(" ");		
	}
		
	public static void main(String[] agvs) {
		String[] list = RandomStringArray(5, 40, character);
		for (int i=0; i<5; i++)
			System.out.println(list[i]);
	}
}
