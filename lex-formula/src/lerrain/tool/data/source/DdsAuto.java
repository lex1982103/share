package lerrain.tool.data.source;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import lerrain.tool.data.DataParseException;
import lerrain.tool.data.DataParser;
import lerrain.tool.data.DataSource;

public class DdsAuto implements DataParser
{
	private static final long serialVersionUID = 1L;

	private static FileReader fileReader		= null;

	public DdsAuto()
	{
	}

	public DataSource newSource(Map value)
	{
		File file = (File)value.get("file");
		
		if (file == null)
			return null;
		
		DataInputStream dis = null;
		try
		{
			dis = new DataInputStream (new FileInputStream(file));
			
			dis.readByte();
			dis.readByte();
			dis.readByte();
			
			int version = dis.readByte();

			if (version == 8)
				return new DdsV8(file);
			if (version == 7)
				return new DdsV7(file);
			if (version == 6)
				return new DdsV6(file);
			if (version == 4)
				return new DdsV4(file);
			if (version == 3 || version == 5)
				return new DdsV3V5(file);
			if (version == 1 || version == 2)
				return new DdsV1V2(file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (dis != null)
					dis.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	public static interface FileReader
	{
		public InputStream open(File file);
	}
	
	public static class FileReaderDefault implements FileReader
	{
		public InputStream open(File datFile)
		{
			if (!datFile.exists() || !datFile.isFile())
				throw new DataParseException("file<" + datFile.getAbsolutePath() + "> is not found.");

			try
			{
				return new FileInputStream(datFile);
			}
			catch (FileNotFoundException e)
			{
				throw new DataParseException("file<" + datFile.getAbsolutePath() + "> open failed.", e);
			}
		}
	}
	
	public static void setFileReader(FileReader reader)
	{
		DdsAuto.fileReader = reader;
	}
	
	public static FileReader getFileReader()
	{
		if (DdsAuto.fileReader == null)
			DdsAuto.fileReader = new FileReaderDefault();
		return DdsAuto.fileReader;
	}
}
