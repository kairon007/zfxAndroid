package com.zifei.corebeau.my.qiniu.up;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.net.Uri;

import com.zifei.corebeau.my.qiniu.config.Config;
import com.zifei.corebeau.my.qiniu.up.UpParam.FileUpParam;
import com.zifei.corebeau.my.qiniu.up.UpParam.StreamUpParam;
import com.zifei.corebeau.my.qiniu.up.auth.Authorizer;
import com.zifei.corebeau.my.qiniu.up.rs.PutExtra;
import com.zifei.corebeau.my.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.my.qiniu.up.simple.FileSimpleUpload;
import com.zifei.corebeau.my.qiniu.up.simple.StreamSimpleUpload;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
import com.zifei.corebeau.my.qiniu.up.slice.FileSliceUpload;
import com.zifei.corebeau.my.qiniu.up.slice.SliceUpload;
import com.zifei.corebeau.my.qiniu.up.slice.StreamSliceUpload;

public class UpApi {
	/**
	 * 资源大小 大于此值，使用分片上传，否则使用简单直传
	 */
	public static int SLICE = 1024 * 1024 * 4;
	/**
	 * 上传线程池
	 */
	public static ExecutorService UPLOAD_THREAD_POOL;

	public static Upload build(Authorizer authorizer, String key, FileUpParam p, PutExtra extra, Object passParam,
			UploadHandler handler) {
		Upload up = null;
		if (p.getSize() <= SLICE) {
			up = new FileSimpleUpload(authorizer, key, p, extra, passParam, handler);
		} else {
			up = new FileSliceUpload(authorizer, key, p, extra, passParam, handler);
		}
		return up;
	}

	public static Upload build(Authorizer authorizer, String key, File file, PutExtra extra, Object passParam,
			UploadHandler handler) {
		FileUpParam p = UpParam.fileUpParam(file);
		return build(authorizer, key, p, extra, passParam, handler);
	}

	/**
	 * 若不能获得流的长度（小于 0），则将流保存为临时文件，作为文件上传
	 */
	public static Upload build(Authorizer authorizer, String key, StreamUpParam p, PutExtra extra, Object passParam,
			UploadHandler handler) {
		if (p.getSize() < 0) {
			return build(authorizer, key, p.getIs(), extra, passParam, handler);
		}
		Upload up = null;
		if (p.getSize() <= SLICE) {
			up = new StreamSimpleUpload(authorizer, key, p, extra, passParam, handler);
		} else {
			up = new StreamSliceUpload(authorizer, key, p, extra, passParam, handler);
		}
		return up;
	}

	/**
	 * 若不能获得流的长度（小于 0），则将流保存为临时文件，作为文件上传
	 */
	public static Upload build(Authorizer authorizer, String key, InputStream is, PutExtra extra, Object passParam,
			UploadHandler handler) {
		StreamUpParam p = UpParam.streamUpParam(is);
		if (p.getSize() < 0) {
			return toBuildFile(authorizer, key, is, extra, passParam, handler);
		} else {
			return build(authorizer, key, p, extra, passParam, handler);
		}
	}

	private static Upload toBuildFile(Authorizer authorizer, String key, InputStream is, PutExtra extra, Object passParam,
			UploadHandler handler) {
		final File file = copyToTmpFile(is);
		Upload up = build(authorizer, key, file, extra, passParam, handler);
		up.addCleanCallback(new Clean() {

			public void clean() {
				if (file != null) {
					try {
						file.delete();
					} catch (Exception e) {
					}
				}
			}

		});
		return up;
	}

	private static File copyToTmpFile(InputStream from) {
		FileOutputStream os = null;
		try {
			File to = File.createTempFile("qiniu_", ".tmp");
			os = new FileOutputStream(to);
			byte[] b = new byte[64 * 1024];
			int l;
			while ((l = from.read(b)) != -1) {
				os.write(b, 0, l);
			}
			os.flush();
			return to;
		} catch (Exception e) {
			throw new RuntimeException("create tmp file failed.", e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
				}
			}
			if (from != null) {
				try {
					from.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static Upload build(Authorizer authorizer, String key, Uri uri, Context context, PutExtra extra, Object passParam,
			UploadHandler handler) {
		StreamUpParam p = UpParam.streamUpParam(uri, context);
		return build(authorizer, key, p, extra, passParam, handler);
	}

	public static Executor put(Authorizer authorizer, String key, FileUpParam p, PutExtra extra, Object passParam,
			UploadHandler handler) {
		Upload up = build(authorizer, key, p, extra, passParam, handler);
		return execute(up);
	}

	public static Executor put(Authorizer authorizer, String key, File file, PutExtra extra, Object passParam,
			UploadHandler handler) {
		Upload up = build(authorizer, key, file, extra, passParam, handler);
		return execute(up);
	}

	public static Executor put(Authorizer authorizer, String key, StreamUpParam p, PutExtra extra, Object passParam,
			UploadHandler handler) {
		Upload up = build(authorizer, key, p, extra, passParam, handler);
		return execute(up);
	}

	public static Executor put(Authorizer authorizer, String key, InputStream is, PutExtra extra, Object passParam,
			UploadHandler handler) {
		Upload up = build(authorizer, key, is, extra, passParam, handler);
		return execute(up);
	}

	public static Executor put(Authorizer authorizer, String key, Uri uri, Context context, PutExtra extra, Object passParam,
			UploadHandler handler) {
		Upload up = build(authorizer, key, uri, context, extra, passParam, handler);
		return execute(up);
	}

	public static Executor execute(Upload up, List<Block> blocks) {
		up.setLastUploadBlocks(blocks);
		return execute(up);
	}

	public static Executor execute(final Upload up) {
		initThreadPool();
		Executor exec = new Executor(up, UPLOAD_THREAD_POOL);
		exec.execute();
		return exec;
	}

	private static void initThreadPool() {
		if (UPLOAD_THREAD_POOL == null) {
			UPLOAD_THREAD_POOL = new ThreadPoolExecutor(0, 3, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
					new DaemonThreadFactory());
		}
	}

	public static boolean isSliceUpload(Upload up) {
		return up instanceof SliceUpload;
	}

	public static class DaemonThreadFactory implements ThreadFactory {

		public Thread newThread(Runnable r) {
			Thread th = new Thread(r);
			th.setDaemon(true);
			return th;
		}

	}

	public static class Executor {
		private Future<UploadResultCallRet> ret;
		private final Upload up;
		private final ExecutorService THREAD_POOL;

		public Executor(Upload up, ExecutorService pool) {
			this.up = up;
			THREAD_POOL = pool;
		}

		/**
		 * 此方法只执行一次
		 */
		public void execute() {
			if (ret != null) {
				return;
			}
			Callable<UploadResultCallRet> th = new Callable<UploadResultCallRet>() {
				public UploadResultCallRet call() throws Exception {
					return up.execute();
				}
			};
			ret = THREAD_POOL.submit(th);
		}

		public Future<UploadResultCallRet> getFuture() {
			return ret;
		}

		/**
		 * Waits if necessary for the computation to complete, and then
		 * retrieves its result.
		 * 
		 * @return
		 */
		public UploadResultCallRet get() {
			if (ret.isCancelled()) {
				return new UploadResultCallRet(Config.ERROR_CODE, new Exception(Config.PROCESS_MSG));
			} else {
				try {
					return ret.get();
				} catch (Exception e) {
					return new UploadResultCallRet(Config.CANCEL_CODE, e);
				}
			}
		}

		public void cancel() {
			ret.cancel(true);
			up.cancel();
		}
	}

}
