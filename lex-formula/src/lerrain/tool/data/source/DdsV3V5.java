package lerrain.tool.data.source;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.data.DataRecord;
import lerrain.tool.data.DataSource;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.formula.Value;

public class DdsV3V5 implements DataSource
{
	private static final long serialVersionUID = 1L;

	String id;
	File file;
	
	boolean readed = false;
	
	String[] groupName;
	String[] collectName = new String[] {"FEE","DIVIDEND","CASH_VALUE","REDUCED_PAIDUP"};
	
	public DdsV3V5(File file)
	{
		this.file = file;
	}
	
	public DataRecord search(Factors param, String groupName)
	{
		if ("RATE".equals(groupName))
			groupName = "FEE";
		else if ("DATA".equals(groupName))
			groupName = "DIVIDEND";
		
		int type = -1;
		for (int i=0;i<collectName.length;i++)
		{
			if (collectName[i].equals(groupName))
			{
				type = i + 1;
				break;
			}
		}
		
		DataRecord result = null;
		
		if (type > 0)
		{
			try
			{
				System.out.println(file.getAbsolutePath() + "/" + groupName);
				double[][] d = loadFromFile(file, new OldVar(param), type);
				
				result = new DataRecord(d);
//				result = new DataRecord(d.length, d[0].length);
//				for (int i=0;i<d.length;i++)
//					for (int j=0;j<d[0].length;j++)
//					{
//						result.set(i, j, new BigDecimal(d[i][j]));
//						//result.set(i, j, BigDecimal.valueOf(d[i][j]));
//					}
				//System.out.println(result);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public String[] getGroupsName()
	{
		if (groupName != null)
			return groupName;
		
		DataInputStream dis = null;
		try
		{
			dis = new DataInputStream (new FileInputStream(file));
			
			dis.readByte();
			dis.readByte();
			dis.readByte();
			
			int version = dis.readByte();
			if (version != 3 && version != 5)
				throw new Exception("version incorrect! this version is " + version + "; this parser is DDS-V3&V5");

			byte[] str = new byte[512];
			
			int dataNum = dis.readByte();
			groupName = new String[dataNum];
			
			for (int i = 0; i < dataNum; i++)
			{
				int len = getValue(dis, 2);
				dis.read(str, 0, len);
				String collectName = getString(str);

				getValue(dis, 4); // posWindageTemp

				if ("FEE".equals(collectName))
					collectName = "RATE";
				else if ("DIVIDEND".equals(collectName))
					collectName = "DATA";

				groupName[i] = collectName;
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

	static Map headerSeekerMap0046f, headerSeekerMap0046d = null;
	
	public static String translateFormula(byte[] bFormula)
	{
		for(int i=0;i<bFormula.length;i++)
		{
			int byteValue = bFormula[i] - 128;
			bFormula[i] = (byte)byteValue;
		}
		return new String(bFormula);
	}
	
	public static String getString(byte[] str)
	{
		int i;
		for(i=0;i<str.length;i++)
		{
			if (str[i] == 0) break;
		}
		return new String(str).substring(0, i);
	}
	
	public static int getValue(DataInputStream dis, int byteNum) throws Exception
	{
		int v = 0;
		for (int i = 0; i < byteNum; i++)
		{
			v = v * 256 + dis.readByte();
		}
		return v;
	}
	
	static Map seekerKeyMap = null;
	public static Map getSeekerKeyMap()
	{
		if (seekerKeyMap == null)
		{
			seekerKeyMap = new HashMap();
			seekerKeyMap.put("10", "ENSURE_PERIOD_VALUE");
			seekerKeyMap.put("11", "ENSURE_PERIOD_TYPE");
			seekerKeyMap.put("12", "ENSURE_PERIOD_TERM_VALUE");
			seekerKeyMap.put("13", "ENSURE_PERIOD_COMMON_VALUE");
			seekerKeyMap.put("20", "PAY_MODE_VALUE");
			seekerKeyMap.put("21", "PAY_MODE");
			seekerKeyMap.put("30", "PAY_PERIOD_VALUE");
			seekerKeyMap.put("31", "PAY_PERIOD_TYPE");
			seekerKeyMap.put("32", "PAY_PERIOD_TERM_VALUE");
			seekerKeyMap.put("33", "PAY_PERIOD_COMMON_VALUE");
			seekerKeyMap.put("34", "PAY_PERIOD_DEFINE_VALUE");
			seekerKeyMap.put("40", "GENDER");
			seekerKeyMap.put("50", "DRAW_AGE");
			seekerKeyMap.put("55", "DRAW_MODE_VALUE");
			seekerKeyMap.put("56", "DRAW_PERIOD_VALUE");
			seekerKeyMap.put("60", "SMOKE_FLAG");
			seekerKeyMap.put("70", "RANK");
			seekerKeyMap.put("80", "OCCUPATION_LEVEL");
			seekerKeyMap.put("100", "AGE");
			seekerKeyMap.put("120", "REDUCE_TYPE");
			seekerKeyMap.put("125", "REMAIN_ENSURE_PERIOD");
		}
		return seekerKeyMap;
	}
	
	static Map importerKeyMap = null;
	public static Map getImporterSeekerKeyMap()
	{
		if (importerKeyMap == null)
		{
			importerKeyMap = new HashMap();
			importerKeyMap.put("ENSURE_PERIOD_VALUE", "10");
			importerKeyMap.put("ENSURE_PERIOD_TYPE", "11");
			importerKeyMap.put("ENSURE_PERIOD_TERM_VALUE", "12");
			importerKeyMap.put("ENSURE_PERIOD_COMMON_VALUE", "13");
			importerKeyMap.put("PAY_MODE_VALUE", "20");
			importerKeyMap.put("PAY_MODE", "21");
			importerKeyMap.put("PAY_PERIOD_VALUE", "30");
			importerKeyMap.put("PAY_PERIOD_TYPE", "31");
			importerKeyMap.put("PAY_PERIOD_TERM_VALUE", "32");
			importerKeyMap.put("PAY_PERIOD_COMMON_VALUE", "33");
			importerKeyMap.put("PAY_PERIOD_DEFINE_VALUE", "34");
			importerKeyMap.put("GENDER", "40");
			importerKeyMap.put("DRAW_AGE", "50");
			importerKeyMap.put("DRAW_MODE_VALUE", "55");
			importerKeyMap.put("DRAW_PERIOD_VALUE", "56");
			importerKeyMap.put("SMOKE_FLAG", "60");
			importerKeyMap.put("RANK", "70");
			importerKeyMap.put("OCCUPATION_LEVEL", "80");
			importerKeyMap.put("AGE", "100");
			importerKeyMap.put("REDUCE_TYPE", "120");
			importerKeyMap.put("REMAIN_ENSURE_PERIOD", "125");
		}
		
		return importerKeyMap;
	}

	private static double[][] loadFromFile(DataInputStream dis, Factors vs, String name) throws Exception
	{
		double[][] result = null;
		byte[] str = new byte[512];
		
		int dataNum = dis.readByte();
		int posWindage = 0;
		for(int i=0;i<dataNum;i++)
		{
			int len = getValue(dis, 2);
			dis.read(str, 0, len);
			String collectName = getString(str);
			
			int posWindageTemp = getValue(dis, 4);
			
			if (name.equals(collectName))
				posWindage = posWindageTemp;
		}
		dis.skipBytes(posWindage);
		
		int byteNumber = dis.readByte();
		int numberPerLine = dis.readByte();
		
		int seekerWindageLen = dis.readByte();
		int pickLineNumberLen = dis.readByte();
		
		byte[] seekerWindageByte = new byte[seekerWindageLen];
		dis.read(seekerWindageByte);
		String seekerWindage = getString(seekerWindageByte);

		byte[] pickLineNumberByte = new byte[pickLineNumberLen];
		dis.read(pickLineNumberByte);
		String pickLineNumber = getString(pickLineNumberByte);
		
		int seekerKeyNum = dis.readByte();
		String[] seekerKey = new String[seekerKeyNum];
		Map seekerMap = getSeekerKeyMap();
		String keyName = "";
		for(int i=0;i<seekerKeyNum;i++)
		{
			int key = dis.readByte();
			seekerKey[i]=(String)seekerMap.get(key+"");
			keyName = keyName + seekerKey[i] + " ";
			if (seekerKey[i] == null) throw new Exception("inexistent seeker key:" + key);
		}
		
		Map headerSeekerMap = new HashMap();
		
		int seekerTypeNum = 0;
		int t1 = dis.readByte();
		int t2 = dis.readByte();
		t1 = t1 < 0 ? t1 + 256 : t1;
		t2 = t2 < 0 ? t2 + 256 : t2;
		seekerTypeNum = t1 + t2 * 256;

			for(int i=0;i<seekerTypeNum;i++)
			{
				String key="";
				for(int j=0;j<seekerKeyNum;j++)
				{
					int value = dis.readByte();
					key = key + value + " ";
				}
				int pos = dis.readInt();
				headerSeekerMap.put(key.trim(), new Integer(pos));
			}
		
		dis.readByte();
		dis.readByte();
		
		//String seekerKeyStr = "";
		String seekerValue = "";
		for(int i=0;i<seekerKey.length;i++)
		{
			//seekerKeyStr += seekerKey[i] + " ";
			Object obj = vs.get(seekerKey[i]);
			//System.out.println(seekerKey[i]+":"+obj);
			String key = null;
			if (obj instanceof Integer) 
				key = ((Integer)obj).intValue() + "";
			else if (obj instanceof Double) 
				key = (int)((Double)obj).doubleValue() + "";
			else if (obj instanceof String)
				key = (String)obj;
			else if (obj instanceof BigDecimal)
				key = ((BigDecimal)obj).toString();
			
			seekerValue = seekerValue + key + " ";
		}
		
		Integer posInt = (Integer)headerSeekerMap.get(seekerValue.trim());
		if (posInt == null) throw new Exception("无法找到数据，详细信息如下：\n关键字：" + keyName + "\n关键值：" + seekerValue.trim() + "\n参数表：" + vs);
		
		int pos = posInt.intValue();
		int windage = Value.intOf(FormulaUtil.formulaOf(seekerWindage), vs);

		int posActual = (pos + windage) * byteNumber * numberPerLine;
		
		dis.skipBytes(posActual);
		
		int readNumber = Value.intOf(FormulaUtil.formulaOf(pickLineNumber), vs);
		
		result = new double[readNumber][numberPerLine];
		byte[] dataLine;
		int[] dataCompute = new int[byteNumber];
		for(int k=0;k<readNumber;k++)
		{
			for(int y=0;y<numberPerLine;y++)
			{
				dataLine = new byte[byteNumber];
				dis.readFully(dataLine);
				result[k][y] = 0;
				for(int x=byteNumber-1;x>=0;x--)
				{
					dataCompute[x] = dataLine[x] < 0 ? dataLine[x] + 256 : dataLine[x];
					result[k][y] = result[k][y] * 256 + dataCompute[x];
				}
//				if (result[k][y] > 256L * 256 * 256 * 256)
//					System.out.println(result[k][y]);
				result[k][y] = result[k][y] / getScale(byteNumber);
			}
		}	

		return result;
	}
	
	public double[][] loadFromFile(File file, Factors vs, int type) throws Exception
	{
		DataInputStream dis = null;
		
		try
		{
			dis = new DataInputStream (new FileInputStream(file));
			
			dis.readByte();
			dis.readByte();
			dis.readByte();
			
			int version = dis.readByte();
			if (version == 3 || version == 5)
				return loadFromFile(dis, vs, collectName[type-1]);
			
			throw new Exception("version incorrect! this version is :" + version + "; this parser is SourceBinarySigV3V5");
		}
		finally
		{
			if (dis != null)
				dis.close();
		}
	}
	
	private static int getScale(int byteNumber)
	{
		int scale = 1;
		
		if (byteNumber == 3)
			scale = 100;
		else if (byteNumber == 4)
			scale = 10000;
		else if (byteNumber == 5)
			scale = 10000;
		else if (byteNumber == 6)
			scale = 10000;
		
		return scale;
	}

	public class OldVar implements Factors, Serializable
	{
		private static final long serialVersionUID = 1L;
		
		Factors vs;
		
		public OldVar(Factors vs)
		{
			this.vs = vs;
		}

		public Object get(String name)
		{
			if ("GENDER".equals(name))
			{
				return vs.get("GENDER_CODE");
			}
			else if ("PAY_PERIOD_VALUE".equals(name))
			{
				return vs.get("PAY_VALUE");
			}
			else if ("PAY_PERIOD_TERM_VALUE".equals(name))
			{
				return vs.get("PAY_PERIOD");
			}
			else if ("PAY_PERIOD_COMMON_VALUE".equals(name))
			{
				return vs.get("PAY_PERIOD");
			}
			else if ("ENSURE_PERIOD_VALUE".equals(name))
			{
				return vs.get("INSURE_VALUE");
			}
			else if ("ENSURE_PERIOD_TERM_VALUE".equals(name))
			{
				return vs.get("INSURE_PERIOD");
			}
			else if ("RANK".equals(name))
			{
				return vs.get("RANK_VALUE");
			}
			
			return vs.get(name);
		}
		
	}
}
