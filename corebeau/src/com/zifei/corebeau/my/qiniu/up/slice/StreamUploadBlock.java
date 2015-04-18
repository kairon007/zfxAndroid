package com.zifei.corebeau.my.qiniu.up.slice;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ByteArrayEntity;

import com.zifei.corebeau.my.qiniu.up.auth.Authorizer;
import com.zifei.corebeau.my.qiniu.up.slice.StreamSliceUpload.ByteRef;
import com.zifei.corebeau.my.qiniu.util.Util;

public class StreamUploadBlock extends UploadBlock {
	protected ByteRef buffer;

	public StreamUploadBlock(SliceUpload upload, Authorizer authorizer, HttpClient httpClient, String host, int blockIdx,
			long offset, int len, int chunkSize, int firstChunk, ByteRef bref) {
		super(upload, authorizer, httpClient, host, blockIdx, offset, len, chunkSize, firstChunk);
		this.buffer = bref;
	}

	@Override
	protected HttpEntity buildHttpEntity(int start, int len) {
		byte[] data = copy2New(start, len);
		ByteArrayEntity bae = new ByteArrayEntity(data);
		bae.setContentType("application/octet-stream");
		return bae;
	}

	@Override
	protected long buildCrc32(int start, int len) {
		byte[] data = copy2New(start, len);
		return Util.crc32(data);
	}

	@Override
	protected void clean() {
		super.clean();
		buffer.clean();
		buffer = null;
	}

	private byte[] copy2New(int start, int len) {
		byte[] b = new byte[len];
		System.arraycopy(buffer.getBuf(), start, b, 0, len);
		return b;
	}

}
