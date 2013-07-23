package com.yihaodian.architecture.remote.example.service;

import java.util.Random;

public class DataCreator {
	static byte[] sample;
	static {
		sample = new byte[128];
		for (byte i = 0; i < 127; i++) {
			sample[i] = i;
		}
	}
	/**
	 * @param size
	 *            unit is 'k'
	 * @return
	 */
	public static String createObject(int size) {
		byte[] ba = null;
		Random r = new Random();
		if (size > 0) {
			int realSize = size << 10;
			ba = new byte[realSize];
			for (int i = 0; i < realSize; i++) {
				int p = r.nextInt(127);
				ba[i] = sample[p];
			}
		}
		return new String(ba);
	}

}
