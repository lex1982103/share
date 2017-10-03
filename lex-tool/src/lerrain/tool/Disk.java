package lerrain.tool;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Disk
{
	public static boolean delete(File fileOrDir)
	{
		if (!fileOrDir.exists())
			return false;
		
		if (!fileOrDir.isDirectory())
			return fileOrDir.delete();
		
		File[] f = fileOrDir.listFiles();
		for (int i=0;i<f.length;i++)
		{
			if (f[i].isDirectory())
			{
				if (!delete(f[i]))
					return false;
			}
			else if (f[i].isFile())
			{
				if (!f[i].delete())
					return false;
			}
		}
		
		return fileOrDir.delete();
	}
	
	public static void saveToDisk(InputStream is, File file) throws IOException
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(file);
			
			byte[] b = new byte[4096];
			int c = 0;
			while (c >= 0)
			{
				c = is.read(b);
				if (c > 0)
					fos.write(b, 0, c);
			}
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			if (fos != null)
			{
				try
				{
					fos.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean copy(File source, File destination)
	{
		try
		{
			copyEx(source, destination);
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean copy(File source, File destination, boolean overwrite)
	{
		try
		{
			copyEx(source, destination, overwrite);
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	public static void copyEx(File source, File destination) throws IOException
	{
		copyEx(source, destination, false);
	}
	
	public static void copyEx(File source, File destination, boolean overwrite) throws IOException
	{
		if (!source.exists() || source.isDirectory())
			throw new FileNotFoundException(source.getAbsolutePath() + " not existed.");
		
		if (destination.exists() && destination.isFile())
		{
			if (overwrite)
				destination.delete();
			else
				throw new IOException(destination.getAbsolutePath() + " is existed.");
		}
		
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(source);
			saveToDisk(fis, destination);
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * <p>获取满足通配符条件的文件列表<p>
	 * <p>
	 * 传入的是文件名（带有或没有通配符），比如：<br>
	 * dir1/file*.xml<br>
	 * dir1/file???s.r??<br>
	 * </p>
	 * <p>
	 * 如果传入"dir1/dir2"，那么dir2会被当做一个文件名而不是目录名
	 * </p>
	 * 
	 * @param filePathWithWildcard
	 * @return
	 */
	public static List<String> listFilesByWildcard(String filePathWithWildcard)
	{
		int l = filePathWithWildcard.lastIndexOf("/");
		String dir = l >= 0 ? filePathWithWildcard.substring(0, l) : "";
		
		File file = new File(dir);
		if (!file.exists() || !file.isDirectory())
			return null;
		
		String[] flist = file.list();
		
		List<String> r = filterFilesWithWildcard(flist, filePathWithWildcard.substring(l + 1));
		if (r != null)
		{
			int len = r.size();
			for (int i=0;i<len;i++)
			{
				r.set(i, dir + File.separator + r.get(i));
			}
		}
		
		return r;
	}
	
	/**
	 * 不可以带有路径
	 * @param fileNameArray
	 * @param fileNameWithWildcard
	 * @return
	 */
	public static List<String> filterFilesWithWildcard(String[] fileNameArray, String fileNameWithWildcard)
	{
		if (fileNameArray == null)
			return null;
		
		List<String> r = new ArrayList<String>();
		
		if (fileNameWithWildcard.indexOf("?") >= 0 || fileNameWithWildcard.indexOf("*") >= 0)
		{
			Pattern p = matcher(fileNameWithWildcard);
			for(int i=0;i<fileNameArray.length;i++)
			{
				if (p.matcher(fileNameArray[i]).matches())
				{
					r.add(fileNameArray[i]);
				}
			}
		}
		else
		{
			for(int i=0;i<fileNameArray.length;i++)
			{
				if (fileNameWithWildcard.equals(fileNameArray[i]))
				{
					r.add(fileNameArray[i]);
					break;
				}
			}
		}
		
		return r;
	}
	
	/**
	 * 不可以带有路径
	 * @param fileNameWithWildcard
	 * @return
	 */
	public static Pattern matcher(String fileNameWithWildcard)
	{
		fileNameWithWildcard = fileNameWithWildcard.replace('.', '#');
		fileNameWithWildcard = fileNameWithWildcard.replaceAll("#", "\\\\.");
		fileNameWithWildcard = fileNameWithWildcard.replace('*', '#');
		fileNameWithWildcard = fileNameWithWildcard.replaceAll("#", ".*");
		fileNameWithWildcard = fileNameWithWildcard.replace('?', '#');
		fileNameWithWildcard = fileNameWithWildcard.replaceAll("#", "?");
		fileNameWithWildcard = "^" + fileNameWithWildcard + "$";
			
		return Pattern.compile(fileNameWithWildcard);
	}
	
	public static String parentPathOf(String path)
	{
		path = pathOf(path);
		
		int pos = path.lastIndexOf(File.separator);
		if (pos < 0)
			return null;
		
		return path.substring(0, pos);
	}
	
	public static File fileOf(String path, String fileName)
	{
		return new File(path, fileName);
	}
	
	public static String pathOf(String path)
	{
		if (path == null)
			return null;
		
		return path.trim().replace("/", File.separator).replace("\\", File.separator);
	}
	
	public static String pathOf(String path1, String path2)
	{
		if (path1 == null && path2 == null)
			return null;
		
		String res = null;
		
		if (path1 != null)
			path1 = pathOf(path1);
		if (path2 != null)
			path2 = pathOf(path2);
		
		if (path1 == null)
		{
			res = path2;
		}
		else if (path2 == null)
		{
			res = path1;
		}
		else
		{
			if (!path1.endsWith(File.separator))
				path1 = path1 + File.separator;
			
			if (path2.startsWith(File.separator))
				path2 = path2.substring(1);
			
			res = path1 + path2;
		}
		
		if (res.endsWith(File.separator))
			res = res.substring(0, res.length() - 1);
		
		return res;
	}

	public static String load(File f, String charset)
	{
		try
		{
			return new String(load(f), charset);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static byte[] load(File f)
	{
		try (InputStream is = new FileInputStream(f); ByteArrayOutputStream out = new ByteArrayOutputStream())
		{
			byte[] b = new byte[2048];
			int c;
			while ((c = is.read(b)) >= 0)
				out.write(b, 0, c);

			return out.toByteArray();
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
