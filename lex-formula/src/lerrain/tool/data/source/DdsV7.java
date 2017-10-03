package lerrain.tool.data.source;

/**
 * 二进制费率文件(.dat)的读取器
 * 
 * 数据文件格式：
 * 
 * 【文件头部分】
 * - 标题 3字节
 * - 版本 1字节
 * - 数据集数量 1字节
 * # 循环(数据集数量)次
 *   - 数据集名称 32字节
 *   - 数据集相对文件头的偏移坐标 4字节
 * # 循环结束
 * 
 * 【数据体部分】
 * # 循环(数据集数量)次
 * # 循环结束
 * 
 * @author 李新豪
 * 
 */

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.data.DataNotFoundException;
import lerrain.tool.data.DataRecord;
import lerrain.tool.data.DataSource;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.formula.Value;

public class DdsV7 implements DataSource
{
	private static final long serialVersionUID = 1L;
	
	private static boolean cache				= true;
	
	File file;
	
	boolean readed = false;
	
	String[] groupName;
	
	Map seekerMap = new HashMap();
	
	public class SourceBinaryVar implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		private String keyStr;
		private String[] seekerKey;
		private Formula windagePos;
		private Formula pickLine;
		private int byteNumber;
		private int numberPerLine;
		private int scale;
		private int headerLen;
		private Map headerSeekerMap;
		
		public String getKeyStr()
		{
			return keyStr;
		}
		public void setKeyStr(String keyStr)
		{
			this.keyStr = keyStr;
		}
		public String[] getSeekerKey()
		{
			return seekerKey;
		}
		public void setSeekerKey(String[] seekerKey)
		{
			this.seekerKey = seekerKey;
		}
		public Formula getWindagePos()
		{
			return windagePos;
		}
		public void setWindagePos(Formula windagePos)
		{
			this.windagePos = windagePos;
		}
		public Formula getPickLine()
		{
			return pickLine;
		}
		public void setPickLine(Formula pickLine)
		{
			this.pickLine = pickLine;
		}
		public int getByteNumber()
		{
			return byteNumber;
		}
		public void setByteNumber(int byteNumber)
		{
			this.byteNumber = byteNumber;
		}
		public int getNumberPerLine()
		{
			return numberPerLine;
		}
		public void setNumberPerLine(int numberPerLine)
		{
			this.numberPerLine = numberPerLine;
		}
		public int getScale()
		{
			return scale;
		}
		public void setScale(int scale)
		{
			this.scale = scale;
		}
		public int getHeaderLen()
		{
			return headerLen;
		}
		public void setHeaderLen(int headerLen)
		{
			this.headerLen = headerLen;
		}
		public Map getHeaderSeekerMap()
		{
			return headerSeekerMap;
		}
		public void setHeaderSeekerMap(Map headerSeekerMap)
		{
			this.headerSeekerMap = headerSeekerMap;
		}
	}
	
	/**
	 * 数据源
	 * @param path
	 * @param value
	 */
	public DdsV7(File file)
	{
		this.file = file;
	}

	public DataRecord search(Factors param, String groupName) throws DataNotFoundException
	{
		if (file == null)
			return null;
		
		DataRecord data = null;
		
		try
		{
//			long s = System.currentTimeMillis();
			
			data = loadFromFile(param, groupName);
			
//			if (data == null)
//				System.out.println(groupName + " is null?");
			
//			System.out.println("readed " + groupName + " in " + (System.currentTimeMillis() - s) + "ms");
		} 
		catch (Exception e)
		{
			throw new DataNotFoundException("未能查询到数据", e);
		}
		
		return data;
	}
	
	public String[] getGroupsName()
	{
		if (groupName != null)
			return groupName;
		
		DataInputStream dis = null;
		try
		{
			dis = new DataInputStream (DdsAuto.getFileReader().open(file));
			
			dis.readByte();
			dis.readByte();
			dis.readByte();
			
			int version = dis.readByte();
			if (version != 7)
				throw new Exception("version incorrect! this version is " + version + "; this parser is DDS-V7");
			
			byte[] str = new byte[512];
			
			int dataNum = dis.readByte();
			groupName = new String[dataNum];

			for (int i = 0; i < dataNum; i++)
			{
				dis.read(str, 0, 32);
				String collectName = getString(str).trim();
				groupName[i] = collectName;

				dis.readInt();
			}
		}
		catch (Exception e)
		{
			groupName = new String[0];
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
			}
		}
		
		return groupName;
	}
		
	private static String translateFormula(byte[] bFormula)
	{
		for(int i=0;i<bFormula.length;i++)
		{
			int byteValue = bFormula[i] - 128;
			bFormula[i] = (byte)byteValue;
		}
		return new String(bFormula);
	}
	
	private static String getString(byte[] str)
	{
		int i;
		for(i=0;i<str.length;i++)
		{
			if (str[i] == 0) break;
		}
		return new String(str).substring(0, i);
	}
	
	private DataRecord loadFromFile(Factors param, String name) throws Exception
	{
		DataRecord result = null;

		DataInputStream dis = null;
		try
		{
			dis = new DataInputStream(DdsAuto.getFileReader().open(file));
				
			//标题
			dis.readByte();
			dis.readByte();
			dis.readByte();
			
			//版本
			int version = dis.readByte();
			if (version != 7)
				throw new Exception("version incorrect!");
			
			byte[] str = new byte[512];
			
			//数据集位置信息
			int dataNum = dis.readByte();
			int posWindage = 0;
			for(int i=0;i<dataNum;i++)
			{
				dis.read(str, 0, 32);
				String collectName = getString(str).trim();
				
				int posWindageTemp = dis.readInt();
				
				if (name.equals(collectName))
					posWindage = posWindageTemp;
			}
			//跳转到对应数据集
			dis.skipBytes(posWindage);
				
			SourceBinaryVar var = (SourceBinaryVar)seekerMap.get(name);
			if (var == null)
			{
				var = new SourceBinaryVar();
				
				//所有定位变量
				int seekerKeyNum = dis.readInt();
				byte[] str1 = new byte[seekerKeyNum];
				dis.read(str1, 0, seekerKeyNum);
				var.setKeyStr(getString(str1).trim());
				var.setSeekerKey(((String)var.getKeyStr()).split(","));
				
				var.setHeaderLen(var.getHeaderLen() + 4 + seekerKeyNum);
				
				//偏移公式
				int seekerWindageLen = dis.readInt();
				byte[] seekerWindageByte = new byte[seekerWindageLen];
				dis.read(seekerWindageByte, 0, seekerWindageLen);
				String seekerWindage = translateFormula(seekerWindageByte);
				var.setWindagePos(FormulaUtil.formulaOf(seekerWindage));
				
				var.setHeaderLen(var.getHeaderLen() + 4 + seekerWindageLen);
				
				//行数量公式
				int pickLineNumberLen = dis.readInt();
				byte[] pickLineNumberByte = new byte[pickLineNumberLen];
				dis.read(pickLineNumberByte, 0, pickLineNumberLen);
				String pickLineNumber = translateFormula(pickLineNumberByte);
				var.setPickLine(FormulaUtil.formulaOf(pickLineNumber));
				
				var.setHeaderLen(var.getHeaderLen() + 4 + pickLineNumberLen);
		
				//单个数据所占字节
				var.setByteNumber(dis.readByte());
				//每行数据数量
				var.setNumberPerLine(dis.readInt());
				//缩放比例
				var.setScale(dis.readByte());
				
				var.setHeaderLen(var.getHeaderLen() + 3);
				
				//定位表
				Map seekerMap = new HashMap();
				int seekerTypeNum = dis.readInt();
				for(int i=0;i<seekerTypeNum;i++)
				{
					String key="";
					for(int j=0;j<((String[])var.getSeekerKey()).length;j++)
					{
						int value = dis.readByte();
						key = key + value + " ";
					}
					int pos = dis.readInt();
					seekerMap.put(key.trim(), new Integer(pos));
				}
				var.setHeaderSeekerMap(seekerMap);
				var.setHeaderLen(var.getHeaderLen() + 4 + (((String[])var.getSeekerKey()).length + 4) * seekerTypeNum);
				
				if (cache)
					seekerMap.put(name, var);
			}
			else
			{
				dis.skipBytes(var.getHeaderLen());
			}
	
			//计算定位键
			String seekerValue = "";
			for(int i=0;i<var.getSeekerKey().length;i++)
			{
				Object obj = param.get(var.getSeekerKey()[i]);
				String key = null;
				if (obj instanceof Integer) 
					key = ((Integer)obj).intValue() + "";
				else if (obj instanceof Double) 
					key = ((Double)obj).intValue() + "";
				else if (obj instanceof BigDecimal) 
					key = ((BigDecimal)obj).intValue() + "";
				else if (obj instanceof String)
					key = (String)obj;
				
				seekerValue = seekerValue + (key + " ");
			}
			
			//定位
			Integer posInt = (Integer)var.getHeaderSeekerMap().get(seekerValue.trim());
			if (posInt == null) throw new Exception(file.getAbsolutePath() + "/" + name + "无法找到数据，详细信息如下：\n关键字：" + var.getKeyStr() + "\n关键值：" + seekerValue.trim() + "\n参数表：" + param);
			
			int pos = posInt.intValue();
			int windage = Value.valueOf(var.getWindagePos(), param).intValue();
	
			int posActual = (pos + windage) * var.getByteNumber() * var.getNumberPerLine()+ 2;
			
			dis.skipBytes(posActual);
			
			//计算读取行数量
			int readNumber = Value.valueOf(var.getPickLine(), param).intValue();
			
			//取值
			result = new DataRecord(readNumber, var.getNumberPerLine());
			byte[] dataLine;
			int[] dataCompute = new int[var.getByteNumber()];
			for(int k=0;k<readNumber;k++)
			{
				for(int y=0;y<var.getNumberPerLine();y++)
				{
					dataLine = new byte[var.getByteNumber()];
					dis.readFully(dataLine);
					
					double d = 0;
					for(int x=var.getByteNumber()-1;x>=0;x--)
					{
						dataCompute[x] = dataLine[x] < 0 ? dataLine[x] + 256 : dataLine[x];
						d = d * 256 + dataCompute[x];
					}
					d = d / Math.pow(10, var.getScale());
					result.set(k, y, d);
					
//					BigDecimal d = new BigDecimal(0);
//					for(int x=var.getByteNumber()-1;x>=0;x--)
//					{
//						dataCompute[x] = dataLine[x] < 0 ? dataLine[x] + 256 : dataLine[x];
//						d = d.multiply(new BigDecimal((int)256)).add(new BigDecimal((int)dataCompute[x]));
//					}
//					d = d.divide(new BigDecimal(Math.round(Math.pow(10, var.getScale()))), 10, BigDecimal.ROUND_HALF_UP);
//					result.set(k, y, (BigDecimal)d);
				}
			}	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			System.out.println("数据查询异常");
			throw e;
		}
		finally
		{
			if (dis != null)
				dis.close();
		}
		
		return result;
	}
	
//	public static int getScaleV3(int scale)
//	{
//		if (scale == 0)
//			return 1;
//		else if (scale == 1)
//			return 10;
//		else if (scale == 2)
//			return 100;
//		else if (scale == 3)
//			return 1000;
//		else if (scale == 4)
//			return 10000;
//		else if (scale == 5)
//			return 100000;
//		else if (scale == 6)
//			return 1000000;
//		else
//			return 1;
//	}

	public static void setCache(boolean cache)
	{
		DdsV7.cache = cache;
	}
}

