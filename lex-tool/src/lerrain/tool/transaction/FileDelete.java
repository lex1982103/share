package lerrain.tool.transaction;

import java.io.File;

import lerrain.tool.Disk;

public class FileDelete implements Behavior
{
	File f;
	File temp;
	
	public FileDelete(File f, File temp)
	{
		this.f = f;
		this.temp = temp;
	}

	public boolean perform()
	{
		if (!f.exists())
			return true;
		
		if (temp != null)
		{
			return f.renameTo(temp);
		}
		else
		{
			Disk.delete(f);
			return true;
		}
	}

	public void rollback()
	{
		temp.renameTo(f);
	}

	public void commit()
	{
		Disk.delete(temp);
	}
	
	public String toString()
	{
		return "if " + f + " exists, delete it.";
	}
}