package com.cwtcn.kmlib.util;

import android.os.Environment;
import android.text.TextUtils;

import com.cwtcn.kmlib.api.KMWearerManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;

public class FileUtils {

	/** 定义SD卡根目录 */
	public static final String SD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String PATH_ROOT = SD_ROOT + "/km/";

	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] file2Bytes(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 保存文件到指定路径
	 * 
	 * @param buf
	 * @param filePath
	 * @param fileName
	 */
	public static void byte2File(byte[] buf, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file = new File(filePath + "/" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除该文件目录中的所有图片
	 * 
	 * @param imageUrl
	 */
	public static void delAllImage(String imageUrl) {
		String dir = imageUrl.substring(0, imageUrl.lastIndexOf("/"));
		File filedir = new File(dir);
		if (!filedir.exists()) {
			filedir.mkdirs();
		} else {
			if (filedir != null && filedir.listFiles() != null) {
				for (File f : filedir.listFiles()) {
					if (f.exists()) {
						f.delete();
					}
				}
			}
		}
	}

	/**
	 * 删除指定文件
	 * 
	 * @param fileName
	 */
	public static void delFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s
	 *            源文件
	 * 
	 * @param t
	 *            复制到的新文件
	 */

	public static void fileChannelCopy(File s, File t) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void getImgFileFromNet(final String url, final String fileName, final String version) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				URL imgUrl = null;
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
					imgUrl = new URL(url);
					HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
					conn.setDoInput(true);
					conn.setConnectTimeout(50000);
					conn.setReadTimeout(50000);
					conn.connect();
					bis = new BufferedInputStream(conn.getInputStream());
					bos = new BufferedOutputStream(new FileOutputStream(fileName));
					byte[] temp = new byte[1024];
					int size;
					while (((size = bis.read(temp, 0, temp.length)) != -1)) {
						bos.write(temp, 0, size);
					}
					bos.flush();
					bos.close();
					bos = null;
					bis = null;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {

				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {

						}
						bos = null;
					}
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {

						}
						bis = null;
					}
				}
			}
		}).start();
	}

	/**
	 * 复制一个目录及其子目录、文件到另外一个目录
	 * 
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFolder(File src, File dest) {
		try {
			if (src.isDirectory()) {
				if (!dest.exists()) {
					dest.mkdirs();
				}
				String files[] = src.list();
				for (String file : files) {
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					// 递归复制
					copyFolder(srcFile, destFile);
					if (srcFile.exists()) {
						srcFile.delete();
					}
				}
			} else {
				InputStream in = new FileInputStream(src);
				FileOutputStream out = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				out.flush();
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public static void recursionDeleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				recursionDeleteFile(f);
			}
			file.delete();
		}
	}

	// =================从kt02过来==================================
	/*public static File getMyPath(String user) {
		File f = null;
		if (checkSDCard()) {
			f = new File(PATH_ROOT + user + "/voice");
			if (!f.exists()) {
				f.mkdirs();
			}
		}
		return f;
	}*/

	public static File getMyPath4Voice(String user, String imei) {
		File f = null;
		if (checkSDCard()) {
			f = new File(PATH_ROOT + user + "/" + KMWearerManager.getInstance().getWearerId(imei) + "/voice");
			if (!f.exists()) {
				f.mkdirs();
			}
		}
		return f;
	}

	public static File getMyPath4Image(String user, String imei) {
		File f = null;
		if (checkSDCard()) {
			f = new File(PATH_ROOT + user + "/" + KMWearerManager.getInstance().getWearerId(imei) + "/image");
			if (!f.exists()) {
				f.mkdirs();
			}
		}
		return f;
	}

	private static boolean checkSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 创建音频文件
	 * @param user
	 * @param name
	 * @return
	 */
	public static File getRecorderFile(String user, String name, String imei) {
		File dir = getMyPath4Voice(user, imei);
		if (dir == null) {
			return null;
		} else {
			return new File(dir, name + ".amr");
		}
	}
	/**
	 * 创建视频文件
	 * @param user
	 * @param name
	 * @return
	 */
	public static File getVideoFile(String user, String name, String imei) {
		File dir = getMyPath4Voice(user, imei);
		if (dir == null) {
			return null;
		} else {
			return new File(dir, name + ".3gp");
		}
	}

	/***
	 * 创建拍照图片的目录
	 * @param user
	 * @param name
	 * @return
	 */
	public static File getImageFilePath(String user, String name, String imei) {
		File dir = getMyPath4Image(user, imei);
		if (dir == null) {
			return null;
		} else {
//			return new File(dir, name + ".jpg");
			return new File(dir, name );
		}
	}
}
