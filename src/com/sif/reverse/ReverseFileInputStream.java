package com.sif.reverse;

import java.io.*;

public class ReverseFileInputStream extends InputStream {
	public static final int BUFFER_SIZE = 4096;
    private RandomAccessFile in;
    private byte[] buffer;
    private int bufferIndex, bufferLength;
	
    public ReverseFileInputStream(File file) throws IOException {
        this.in = new RandomAccessFile(file, "r");
        this.buffer = new byte[BUFFER_SIZE];
        this.bufferIndex = this.buffer.length;
		this.bufferLength = this.buffer.length;
        this.in.seek(file.length());
    }
	
    public void populateBuffer() throws IOException {
		debug("populateBuffer start bufferlength=" + this.bufferLength);
        // record the old position
		long oldPos = this.in.getFilePointer();
		if(oldPos == 0) {
			this.bufferIndex = 0;
			this.bufferLength = 0;
			return;
		}
		
        // seek to a new, previous position
		long newPos = oldPos - BUFFER_SIZE;
		if(newPos < 0) {
			newPos = 0;
		}
		debug("oldPos=" + oldPos);
		debug("newPos=" + newPos);
		this.in.seek(newPos);
		debug("newPosCheck=" + this.in.getFilePointer());
		int n = (int)(oldPos-newPos);
		debug("n=" + n);
        // read from the new position to the old position into the buffer
		this.in.read(this.buffer, 0, n);
		
        // reverse the buffer in place
		ArrayUtil.reverse(this.buffer, n);
		
		this.in.seek(newPos);
		debug("populateBuffer end bufferlength=" + this.bufferLength);
		this.bufferIndex = 0;
		this.bufferLength = n;
    }

    public int read() throws IOException {
        if (this.bufferIndex == this.bufferLength) {
			debug("before bidx="+this.bufferIndex + " blen=" + this.bufferLength);
            populateBuffer();
			debug("after bidx="+this.bufferIndex + " blen=" + this.bufferLength);
            if (this.bufferIndex == this.bufferLength) {
                return -1;
            }
        }
        return this.buffer[this.bufferIndex++];
    }
	
	public void debug(Object o) {
		//System.out.println(o);
	}
}