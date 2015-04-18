package com.zifei.corebeau.my.qiniu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.zifei.corebeau.my.qiniu.up.UpApi;
import com.zifei.corebeau.my.qiniu.up.UpApi.Executor;
import com.zifei.corebeau.my.qiniu.up.UpParam;
import com.zifei.corebeau.my.qiniu.up.Upload;
import com.zifei.corebeau.my.qiniu.up.UploadHandler;
import com.zifei.corebeau.my.qiniu.up.auth.Authorizer;
import com.zifei.corebeau.my.qiniu.up.rs.PutExtra;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
import com.zifei.corebeau.my.qiniu.util.Util;

public class QiniuTask {
	
	Context context;
	
	public QiniuTask(Context context) {
		this.context = context;
	}
	
	private List<Upload> ups = new LinkedList<Upload>();

	public void preUpload(Uri uri, UploadHandler uploadHandler) {
		// 此参数会传递到回调
		String passObject = "test: " + uri.getEncodedPath() + "passObject";

		String qiniuKey = UUID.randomUUID().toString();
		PutExtra extra = null;

		Upload up = UpApi.build(getAuthorizer(), qiniuKey, uri, context, extra, passObject, uploadHandler);
		addUp(up);
	}
	

	private synchronized void addUp(Upload up) {
		if (!contains(up)) {
			ups.add(up);
		}
	}

	private synchronized boolean contains(Upload up) {
		for (Upload u : ups) {
			if (up.getPassParam() != null && up.getPassParam().equals(u.getPassParam())) {
				return true;
			}
		}
		return false;
	}

	private List<Executor> executors = new ArrayList<Executor>();

	public synchronized void doUpload() {
		for (Upload up : ups) {
			if (UpApi.isSliceUpload(up)) {
				String sourceId = generateSourceId(up.getUpParam(), up.getPassParam());
				List<Block> bls = null;
				try {
					bls = load(sourceId);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 设置以前上传的断点记录。 直传会忽略此参数
				up.setLastUploadBlocks(bls);
			}
			// UpApi.execute(up, bls);
			Executor executor = UpApi.execute(up);
			executors.add(executor);
		}
		start = System.currentTimeMillis();
	}

	// 取消上传 **************************
	private void cancel() {
		try {
			for (Executor executor : executors) {
				executor.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cancel0();
	}

	// 上传成功、失败、取消后等，也应将对应的UpLoad、Executor 取消，避免一直被引用，不能回收
	private synchronized void cancel0() {
		try {
			for (int l = ups.size(); l > 0; l--) {
				ups.remove(ups.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 断点记录 记录到文件示例 ******************************
	public static String RESUME_DIR;
	private File getDir() throws IOException {
		String dir = RESUME_DIR;
		String qinuDir = ".qiniu_up";
		if (dir == null) {
			// dir = System.getProperties().getProperty("user.home");
			File exdir = Environment.getExternalStorageDirectory();
			dir = exdir.getCanonicalPath();
			return new File(exdir, qinuDir);
		} else {
			return new File(dir, qinuDir);
		}
	}

	private File initFile(File dir, String sourceId) throws IOException {
		dir.mkdirs();
		File file = new File(dir, sourceId);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public String generateSourceId(UpParam p, Object passParam) {
		String s = p.getName() + "-" + p.getSize() + "-" + passParam;
		String ss = Util.urlsafeBase64(s);
		return ss;
	}

	private List<Block> load(String sourceId) throws IOException {
		File file = new File(getDir(), sourceId);
		if (!file.exists()) {
			return null;
		}
		List<Block> bls = null;
		FileReader freader = null;
		BufferedReader reader = null;
		try {
			bls = new ArrayList<Block>();
			freader = new FileReader(file);
			reader = new BufferedReader(freader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				Block b = analyLine(line);
				if (b != null) {
					bls.add(b);
				}
			}
			Collections.reverse(bls);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (freader != null) {
				try {
					freader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bls;
	}

	public void addBlock(String sourceId, Block block) throws IOException {
		File file = initFile(getDir(), sourceId);
		String l = sync(block);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.newLine();
			writer.write(l);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void clean(String sourceId) throws IOException {
		File file = new File(getDir(), sourceId);
		file.delete();
	}

	private Block analyLine(String line) {
		String[] s = line.split(",");
		if (s.length >= 4) {
			int idx = Integer.parseInt(s[0]);
			String ctx = s[1];
			long length = Long.parseLong(s[2]);
			String host = s[3];
			Block block = new Block(idx, ctx, length, host);
			return block;
		} else {
			return null;
		}
	}

	private String sync(Block b) {
		return b.getIdx() + "," + b.getCtx() + "," + b.getLength() + "," + b.getHost();
	}
	
	
	public long start = 0;
	private static Authorizer authorizer = new Authorizer();

	private static Date buildTokenDate;
	private static ScheduledExecutorService replenishTimer = Executors.newScheduledThreadPool(1, new UpApi.DaemonThreadFactory());
	private static ReadWriteLock rw = new ReentrantReadWriteLock();
	private void initBuildToken() {
		replenishTimer.scheduleAtFixedRate(new Runnable() {
			private long gap = 1000 * 60 * 40; // 40分钟
			public void run() {
				if (getBuildTokenDate() == null || (new Date().getTime() - getBuildTokenDate().getTime() > gap)) {
					buildToken();
				}
			}

		}, 0, 10, TimeUnit.MINUTES);

		authorizer.setUploadToken("iDq6OVCngjLsVz_9960jV1UaU6NT9dndQVKdhYE5:UO1tVL9jfXMkokPjc5mxfU3rOYg=:eyJzY29wZSI6InpmeHBpY3R1cmUiLCJkZWFkbGluZSI6MTQyOTM0OTE0MX0=");
		buildTokenDate = new Date();
	}

	private Random r = new Random();
	private void buildToken() {
		try {
			rw.writeLock().lock();
			if (r.nextBoolean()) {// 模拟
				throw new RuntimeException("  获取token失败。  ");
			}
			authorizer.setUploadToken("iDq6OVCngjLsVz_9960jV1UaU6NT9dndQVKdhYE5:UO1tVL9jfXMkokPjc5mxfU3rOYg=:eyJzY29wZSI6InpmeHBpY3R1cmUiLCJkZWFkbGluZSI6MTQyOTM0OTE0MX0=");
			buildTokenDate = new Date();
		} catch (Exception e) {

		} finally {
			rw.writeLock().unlock();
		}
	}

	private Date getBuildTokenDate() {
		try {
			rw.readLock().lock();
			return buildTokenDate;
		} finally {
			rw.readLock().unlock();
		}
	}

	public Authorizer getAuthorizer() {
		try {
			rw.readLock().lock();
			return authorizer;
		} finally {
			rw.readLock().unlock();
		}
	}

}
