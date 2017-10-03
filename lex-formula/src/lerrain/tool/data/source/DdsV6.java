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

public class DdsV6 implements DataSource
{
	private static final long serialVersionUID = 1L;

	File file;
	
	boolean readed = false;
	
	String[] groupName;
	String[] collectName = new String[] {"FEE","DIVIDEND","CASH_VALUE","REDUCED_PAIDUP"};
	
	/**
	 * 数据源
	 * @param path
	 * @param value
	 */
	public DdsV6(File file)
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
				double[][] d = loadFromFile(file, new OldVar(param), type, collectName[type - 1]);

				result = new DataRecord(d);
//				result = new DataRecord(d.length, d[0].length);
//				for (int i = 0; i < d.length; i++)
//					for (int j = 0; j < d[0].length; j++)
//					{
//						result.set(i, j, new BigDecimal(d[i][j]));
//					}
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
			if (version != 6)
				throw new Exception("version incorrect! this version is " + version + "; this parser is DDS-V6");
				
			byte[] str = new byte[512];
			
			int dataNum = dis.readByte();
			groupName = new String[dataNum];

			for (int i = 0; i < dataNum; i++)
			{
				int len = getValue(dis, 2);
				dis.read(str, 0, len);
				String collectName = getString(str);
				dis.read(str, 0, 1);
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
		/*int v = 0;
		for(int i=0;i<byteNum;i++)
		{
			v = v * 256 + dis.readByte();
		}*/
		int v = 0;
		int[] d = new int[byteNum];
		for (int i = 0; i < byteNum; i++)
		{
			d[i] = dis.readByte();
			if (d[i] < 0)
			{
				d[i] = d[i] + 256;
			}
		}
		for (int i = byteNum - 1; i >= 0; i--)
		{
			v = v * 256 + d[i];
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

//	public double[][] loadFromFileData(File file, Factors vs, int type) throws Exception
//	{
//		int fIndex = fileName.lastIndexOf("/") > fileName.lastIndexOf("\\") ? fileName.lastIndexOf("/") : fileName.lastIndexOf("\\");
//		int eIndex = fileName.lastIndexOf(".");
//		path = fileName.substring(0, fIndex+1);
//		id = fileName.substring(fIndex+1, eIndex);
//		getGroupNameList();
//		
//		return loadFromFile(fileName, vs, type);
//	}
	
	public static double[][] loadFromFile(File file, Factors vs, int type, String collectName) throws Exception
	{
		double[][] result = null;

		DataInputStream dis = null;
		try
		{
		dis = new DataInputStream (new FileInputStream(file));
		
		dis.readByte();
		dis.readByte();
		dis.readByte();
		
		int version = dis.readByte(); //version
		if (version != 6)
			throw new Exception("version incorrect! this version is :" + version + "; this parser is SourceBinarySigV6");

		byte[] str = new byte[512];
		int dataNum = dis.readByte();
		int posWindage = 0;
		for(int i=0;i<dataNum;i++)
		{
			/*int dataType = dis.readByte();
			int posWindageTemp = dis.readInt();
			
			if (dataType==type)
				posWindage = posWindageTemp;*/
			int len = (int) getValue(dis, 2);
			dis.read(str, 0, len);
			String collectName1 = getString(str);
			dis.read(str, 0, 1);
			int posWindageTemp = (int) getValue(dis, 4);
			if (collectName1.equals(collectName))
			{
				posWindage = posWindageTemp;
			}
		}
		dis.skipBytes(posWindage);
		

		int seekerKeyNum = dis.readByte();
		String[] seekerKey = new String[seekerKeyNum];//seekerKey是指要保费的主键，例如保费需要PAY_PERIOD_VALUE、PAY_MODE_VALUE、GENDER、AGE去标识（seekerKey内存的就是AGE。。。的字符串）
		Map seekerMap = getSeekerKeyMap();
		String keyName = "";
		for(int i=0;i<seekerKeyNum;i++)
		{
			int key = dis.readByte();
			seekerKey[i]=(String)seekerMap.get(key+"");
			
			if (seekerKey[i] == null) throw new Exception("inexistent seeker key:" + key);
			keyName = keyName + seekerKey[i] + " ";
		}
		

//		int seekerWindageLen = dis.readByte();
		int seekerWindageLen = getValue(dis, 2);
		byte[] seekerWindageByte = new byte[seekerWindageLen];
		dis.read(seekerWindageByte, 0, seekerWindageLen);
//		String seekerWindage = translateFormula(seekerWindageByte);
		String seekerWindage = getString(seekerWindageByte);


//		int pickLineNumberLen = dis.readByte();
		int pickLineNumberLen = getValue(dis, 2);
		byte[] pickLineNumberByte = new byte[pickLineNumberLen];
		dis.read(pickLineNumberByte);
//		String pickLineNumber = translateFormula(pickLineNumberByte);
		String pickLineNumber = getString(pickLineNumberByte);
		

//		int numberPerLine = dis.readByte();
		int numberPerLine = getValue(dis, 2);
		int byteNumber = dis.readByte();
		

	    int scale = dis.readByte();
		
		
		Map headerSeekerMap = new HashMap();
		
		int seekerTypeNum = getValue(dis, 3);
		/*if (version == 1)
		{
			int t = dis.readByte();
			seekerTypeNum = t < 0 ? t + 256 : t;
		}
		else if (version == 2 && (fileName.indexOf("0046") >= 0 || fileName.indexOf("0047") >= 0)) //0046特殊处理
		{
			seekerTypeNum = 161252;
			int t1 = dis.readByte();
			int t2 = dis.readByte();
		}
		else if (version == 2)
		{
			int t1 = dis.readByte();
			int t2 = dis.readByte();
			t1 = t1 < 0 ? t1 + 256 : t1;
			t2 = t2 < 0 ? t2 + 256 : t2;
			seekerTypeNum = t1 + t2 * 256;
		}*/

		//System.out.println(seekerTypeNum + ":" + type);

//		for(int i=0;i<seekerTypeNum;i++)
//		{
//			String key="";
//			for(int j=0;j<seekerKeyNum;j++)
//			{
//				int value = dis.readByte();
//				key = key + value + " ";
//			}
//			int pos = dis.readInt();
//			headerSeekerMap.put(key.trim(), new Integer(pos));
//		}
		
		if (seekerTypeNum > 100000) //0046特殊处理
		{
			if ((type == 1 && headerSeekerMap0046f == null) || (type == 2 && headerSeekerMap0046d == null))
				for(int i=0;i<seekerTypeNum;i++)
				{
					String key="";
					for(int j=0;j<seekerKeyNum;j++)
					{
						int value = dis.readByte();
						key = key + value + " ";
					}
					int pos = dis.readInt();
					//if(key.trim().equals("3 100 1 0")){
						//System.out.println(key.trim() + ":" + dis.readInt());
					//}
					headerSeekerMap.put(key.trim(), new Integer(pos));
				}
			else
			{
				if (type == 1)
					headerSeekerMap = headerSeekerMap0046f;
				else if (type == 2)
					headerSeekerMap = headerSeekerMap0046d;
				
				dis.skipBytes(seekerTypeNum * (seekerKeyNum + 4));
			}
			
			if (type == 1)
				headerSeekerMap0046f = headerSeekerMap;
			else if (type == 2)
				//System.out.println(headerSeekerMap.size());
				headerSeekerMap0046d = headerSeekerMap;
		}
		else
		{
			for(int i=0;i<seekerTypeNum;i++)
			{
				String key="";
				for(int j=0;j<seekerKeyNum;j++)
				{
					int value = dis.readByte();
					key = key + value + " ";
				}
//				int pos = dis.readInt();
				int pos = getValue(dis, 4);
				headerSeekerMap.put(key.trim(), new Integer(pos));
			}
		}
		
		/*dis.readByte();
		dis.readByte();*/
		
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
		System.out.println(file.getAbsolutePath() + "," + seekerKey.length + "," + headerSeekerMap.size());
		if (posInt == null) throw new Exception("无法找到数据，详细信息如下：\n关键字：" + keyName.trim() + "\n关键值：" + seekerValue.trim() + "\n数据库：" + file.getAbsolutePath() + "\n参数表：" + vs);
		
		int pos = posInt.intValue();
		//int windage = (int)Formula.parseFormula(seekerWindage, parameter);
		int windage = Value.intOf(FormulaUtil.formulaOf(seekerWindage), vs);
		//System.out.println(seekerWindage+"="+windage);
		//System.out.println(parameter);

		int posActual = (pos + windage) * byteNumber * numberPerLine;
		
		dis.skipBytes(posActual);
		
		//int readNumber = (int)Formula.parseFormula(pickLineNumber, parameter);
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
				result[k][y] = result[k][y] / getScale(scale);
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@:"+result[k][y]);
			}
		}
		}
		finally
		{
			if (dis != null)
				dis.close();
		}
		
//		System.out.println("RESULT = "+ result);
//		System.out.println("PAR = " + parameter);
		
		return result;
	}
	
	private static int getScale(int byteNumber)
	{
		/*int scale = 1;
		
		if (byteNumber == 3)
			scale = 100;
		else if (byteNumber == 4)
			scale = 10000;
		else if (byteNumber == 5)
			scale = 10000;
		else if (byteNumber == 6)
			scale = 10000;*/
		
		if (byteNumber == 0)
			return 1;
		if (byteNumber == 1)
			return 10;
		if (byteNumber == 2)
			return 100;
		if (byteNumber == 3)
			return 1000;
		if (byteNumber == 4)
			return 10000;
		if (byteNumber == 5)
			return 100000;
		
		return byteNumber != 6 ? 1 : 1000000;
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
			
			return vs.get(name);
		}
		
	}
}
