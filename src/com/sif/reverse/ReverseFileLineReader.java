package com.sif.reverse;

import java.io.*;

// probably could extend BufferedReader
public class ReverseFileLineReader extends Reader {
	public static final int BUFFER_SIZE = 4096;
	private ReverseFileInputStream in = null;
	private byte[] readLineBuffer;
	
	public ReverseFileLineReader(File file) throws IOException {
		in = new ReverseFileInputStream(file);
		this.readLineBuffer = new byte[BUFFER_SIZE];
	}
	
	private void growReadLineBuffer() {
		int len = this.readLineBuffer.length<<1;
		byte[] tmp = new byte[len];
		System.arraycopy(this.readLineBuffer, 0, tmp, 0, this.readLineBuffer.length);
		this.readLineBuffer = tmp;
	}
	
	public String readLine() throws IOException {
		int i = 0;
		int b;
		boolean valid = false;
		while((b = this.read()) != -1 && b != 10) {
			if(b == 13) {
				// windows
				continue;
			}
			if(!valid) {
				valid = true;
			}
			if(i >= this.readLineBuffer.length) {
				this.growReadLineBuffer();
			}
			this.readLineBuffer[i] = (byte)b;
			i++;
		}
		
		//debug("b="+b);
		//debug("pos=" + this.in.getFilePointer());
		if(!valid) {
			return null;
		}
		
		// reverse
		ArrayUtil.reverse(this.readLineBuffer, i);
		
		if(i >= this.readLineBuffer.length) {
			growReadLineBuffer();
		}
		
		return new String(this.readLineBuffer, 0, i);
	}
	
	public int read() throws IOException {
		return in.read();
	}
	
	public int read(char[] cbuf, int off, int len) {
		// punt for now
		return -1;
	}
	
	public void close() throws IOException {
		if(in != null) {
			in.close();
			in = null;
		}
	}
	
}


