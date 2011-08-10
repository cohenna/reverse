package com.sif.reverse;

public class ArrayUtil {	
	public static void reverse(byte[] array, int n) {
		int middle = n >> 1;
		int n_minus_i = n-1;
		for(int i = 0; i < middle; i++) {
			byte tmp = array[i];
			array[i] = array[n_minus_i];
			array[n_minus_i] = tmp;
			n_minus_i--;
		}
	}
}

