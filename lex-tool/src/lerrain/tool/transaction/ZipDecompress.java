package lerrain.tool.transaction;

import java.io.File;
import java.io.IOException;

import lerrain.tool.Disk;
import lerrain.tool.Zip;

public class ZipDecompress implements Behavior
{
	File zipFile;
	String outputDir;
	
	/**
	 * 这个解压的回滚写的比较简单
	 * 回滚就是简单删除了解压目录，如果原来这个目录就有内容，那么会全部被删除掉(务必注意)
	 * @param zipFile
	 * @param dir
	 */
	public ZipDecompress(File zipFile, String dir)
	{
		this.zipFile = zipFile;
		this.outputDir = dir;
	}
	
	public boolean perform()
	{
		Zip zip = new Zip(zipFile);
		
		try
		{
			zip.decompress(outputDir);
		}
		catch (IOException e)
		{
			return false;
		}
		
		return true;
	}

	public void rollback()
	{
		File dir = new File(outputDir);
		Disk.delete(dir);
	}
	
	public void commit()
	{
	}
}
