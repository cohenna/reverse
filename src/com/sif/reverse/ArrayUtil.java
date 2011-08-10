package com.sif.reverse;

public class ArrayUtil {
	public static void reverse(byte[] array, int n) {
		int middle = n/2;
		for(int i = 0; i < middle; i++) {
			byte tmp = array[i];
			int n_minus_i = n-i-1;
			array[i] = array[n_minus_i];
			array[n_minus_i] = tmp;
		}
	}
}

