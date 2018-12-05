package lerrain.tool.script.warlock.analyse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lerrain.tool.script.SyntaxException;

/**
 * 代码的文本级处理
 * @author lerrain
 */
public class Text
{
	/**
	 * 去除代码中的注释
	 */
	public static String cutComment(String text)
	{
		StringBuffer buf = new StringBuffer();

		int len = text.length();
		int begin = 0;
		
		for (int i = 0; i < len; i++)
		{
			char c = text.charAt(i);
			
			if (c == '/')
			{
				char c1 = text.charAt(i + 1);
				if (c1 == '/') //如果是//的注释
				{
					buf.append(text.substring(begin, i));

					int m = i;
					i = find(text, i + 2, '\n');

					if (i < 0) //没找到换行，后面全都是注释
						i = len;

					for (int j = m; j < i; j++) //填空格，保持文本中每个字符的位置不变
						buf.append(' ');

					begin = i; //单行注释结尾的回车不需要跳过，保留，否则很多跟在代码后的注释会导致正常的回车也没有了
				}
				else if (c1 == '*') //如果是/*的注释
				{
					buf.append(text.substring(begin, i));

					int m = i;
					i = find(text, i + 2, "*/");

					if (i < 0)
						i = len;
					else
						i += 2; //注释的结尾符号是两个字节，全部都要跳过，只靠循环的i++还差一个，这里要补一下。

					for (int j = m; j < i; j++) //填空格，保持文本中每个字符的位置不变
						buf.append(' ');

					begin = i;
					i--; //注释结尾符后面可能直接跟着有效字符，退一位，而行注释结尾符是回车，跳过无所谓
				}
			}
			else if (c == '\"' || c == '\'') //如果是字符串，或单引号圈起来的
			{
				i = findInString(text, i + 1, c);
			}
		}

		if (begin >= 0 && begin < len)
			buf.append(text.substring(begin));
		
		return buf.toString();
	}
	
	public static int find(String text, int begin, char f)
	{
		int len = text.length();
		for (int i = begin; i < len; i++)
		{
			if (text.charAt(i) == f)
				return i;
		}
		
		return -1;
	}
	
	public static int find(String text, int begin, String f)
	{
		int s = f.length();
		int len = text.length() - s + 1;
		char c = f.charAt(0);
		for (int i = begin; i < len; i++)
		{
			if (text.charAt(i) == c)
			{
				if (f.equals(text.substring(i, i + s)))
					return i;
			}
		}
		
		return -1;
	}
	
	public static int findInString(String text, int begin, char quote)
	{
		int len = text.length();
		for (int i = begin; i < len; i++)
		{
			char c = text.charAt(i);
			
			if (c == '\\')
				i++;
			else if (c == quote)
				return i;
		}

		throw new SyntaxException(text, begin, "无法找到对应的右侧引号");
	}

/*
	public static String read2(File file)
	{
		FileInputStream fis = null;
		OutputStream os = null;
		try
		{
			fis = new FileInputStream(file);
			
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			os = sr.getOutputStream();
			
	        byte[] data = new byte[1024];  
	        int count = -1;  
	        while ((count = fis.read(data)) >= 0)
	        	os.write(data, 0, count);
			
			sw.close();
			
			return sw.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			return null;
		}
		finally
		{
			try
			{
				if (fis != null)
					fis.close();
			}
			catch (IOException e)
			{
			}
			
			try
			{
				if (os != null)
					os.close();
			}
			catch (IOException e)
			{
			}
		}
		
	}
*/	
	public static String read(File file)
	{
		String r = null;
		
		FileInputStream is = null;
		ByteArrayOutputStream os = null;
		try
		{
			is = new FileInputStream(file);
	        os = new ByteArrayOutputStream();  
	        byte[] data = new byte[1024];  
	        int count = -1;  
	        while ((count = is.read(data)) != -1)
	        	os.write(data, 0, count);  
	          
	        r = new String(os.toByteArray());  
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (is != null)
					is.close();
				if (os != null)
					os.close();
			}
			catch (IOException e)
			{
			}
		}
		
		return r;
	}
}
