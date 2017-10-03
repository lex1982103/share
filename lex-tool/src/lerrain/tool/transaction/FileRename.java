package lerrain.tool.transaction;

import java.io.File;

public class FileRename implements Behavior
{
	File f1, f2;
	
	public FileRename(File f1, File f2)
	{
		this.f1 = f1;
		this.f2 = f2;
	}

	public boolean perform()
	{
		return f1.renameTo(f2);
	}

	public void rollback()
	{
		f2.renameTo(f1);
	}
	
	public void commit()
	{
	}

}