package lerrain.tool;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Zip
{
	private static final int BUFFER = 1024 * 16;
	
	List<Item> list = new ArrayList<Item>();
	
	File zip = null;
	
	public Zip()
	{
	}
	
	public Zip(File zip)
	{
		this.zip = zip;
	}
	
	public Zip(String zipFilePath)
	{
		this.zip = new File(zipFilePath);
	}

	public void decompress(String outputDirectory) throws IOException
	{
		if (outputDirectory != null && !"".equals(outputDirectory))
			new File(outputDirectory).mkdirs();
		
		try (ZipFile zipFile = new ZipFile(zip.getAbsolutePath()))
		{
			java.util.Enumeration<? extends ZipEntry> e = zipFile.entries();
			while (e.hasMoreElements())
			{
				ZipEntry zipEntry = e.nextElement();
				//判断是否为一个文件夹
				if (zipEntry.isDirectory())
				{
					String name = zipEntry.getName().trim();
					File dir = new File(Disk.pathOf(outputDirectory, name));
					if (!dir.exists() || !dir.isDirectory())
						dir.mkdirs();
				} 
				else
				{
					String fileName = zipEntry.getName();
					fileName = Disk.pathOf(fileName);
	
					//判断子文件是否带有目录，有目录需要创建
					String parentDir = Disk.parentPathOf(fileName);
					if (parentDir != null)
					{
						File dir = new File(Disk.pathOf(outputDirectory, parentDir));
						if (!dir.exists() || !dir.isDirectory())
							dir.mkdirs();
					}
	
					File f = new File(Disk.pathOf(outputDirectory, fileName));
					InputStream is = zipFile.getInputStream(zipEntry);
					Disk.saveToDisk(is, f);
					is.close();
				}
			}
		}
	}
	
	/**
	 * 添加一个文件到zip包
	 * @param file 文件或目录
	 * @param zipDir 该文件或目录在zip中的目录。
	 * 比如add(new File("org", "org"))，那么在zip包中会有一个org文件夹，里面会有一个org文件/目录。
	 */
	public void add(File file, String zipDir)
	{
		if (!file.exists())
			return;
		
		if (file.isDirectory())
		{
			addDir(file, zipDir);
		}
		else if (file.isFile())
		{
			list.add(new Item(file, zipDir));
		}
	}
	
	private void addDir(File dir, String zipDir)
	{
		File[] f = dir.listFiles();
		if (f == null)
			return;
		
		for (int i=0;i<f.length;i++)
		{
			if (zipDir == null || "".equals(zipDir.trim()))
				add(f[i], dir.getName());
			else
				add(f[i], zipDir + File.separator + dir.getName());
		}
	}
	
	public void export(File zip) throws IOException
	{
		ZipOutputStream zos = null;
		
		try
		{
			zos = new ZipOutputStream(new FileOutputStream(zip));
			
			Iterator<Item> iter = list.iterator();
			while (iter.hasNext())
			{
				Item item = (Item)iter.next();
				compress(zos, item);
			}
		}
		finally
		{
			if (zos != null)
				zos.close();
		}
	}
	
    private void compress(ZipOutputStream out, Item item)
    {  
        if (!item.exists())  
            return;  

        try 
        {  
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(item.file));  
            ZipEntry entry = new ZipEntry(item.getZipPath());  
            out.putNextEntry(entry);
            
            int count;  
            byte data[] = new byte[BUFFER];  
            while ((count = bis.read(data, 0, BUFFER)) != -1)
            {  
                out.write(data, 0, count);  
            }
            
//            System.out.println("zipping: " + item.file + " to " + item.getZipPath());
            
            bis.close();  
        }
        catch (Exception e)
        {  
            throw new RuntimeException(e);  
        }  
    }
    
    class Item
    {
    	public File file;
    	public String zipDir;

    	public Item(File file, String zipDir)
    	{
    		this.file = file;
    		this.zipDir = zipDir == null || "".equals(zipDir.trim()) ? zipDir : (zipDir + File.separator);
    	}
    	
    	public boolean exists()
    	{
    		if (file == null)
    			return false;
    		
    		return file.exists();
    	}
    	
    	public String getZipPath()
    	{
    		return zipDir + file.getName();
    	}
    }
}
