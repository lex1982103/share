package lerrain.project.insurance.product.load;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlNode
{
	private static DocumentBuilder docBuilder;
	
	Element e;
	
	String text;
	
	List children = new ArrayList();

	public XmlNode(Element e)
	{
		this.e = e;

		NodeList list = e.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
		{
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				children.add(new XmlNode((Element)node));
			}
		}

		if (children.isEmpty())
			text = e.getTextContent();
	}
	
	public List<XmlNode> getChildren()
	{
		return children;
	}
	
	public List<XmlNode> getChildren(String name)
	{
		List<XmlNode> r = new ArrayList<>();
		
		for (Iterator iter = children.iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if (name.equals(n1.getName()))
				r.add(n1);
		}
		
		return r;
	}
	
	public boolean hasChildren()
	{
		return !children.isEmpty();
	}
	
	public String getName()
	{
		return e.getTagName();
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getAttribute(String name)
	{
		if (hasAttribute(name))
			return e.getAttribute(name);
		
		return null;
	}
	
	public String getAttribute(String[] name)
	{
		for (int i = 0; i < name.length; i++)
		{
			if (hasAttribute(name[i]))
				return e.getAttribute(name[i]);
		}
		
		return null;
	}

	public String getAttributeInOrder(String name)
	{
		return getAttribute(name.split(","));
	}

	public boolean hasAttribute(String name)
	{
		return e.hasAttribute(name);
	}

	public static XmlNode nodeOf(File f)
	{
		try
		{
			return new XmlNode(docBuilder().parse(f).getDocumentElement());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static XmlNode nodeOf(InputStream is)
	{
		try
		{
			return new XmlNode(docBuilder().parse(is).getDocumentElement());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static DocumentBuilder docBuilder()
	{
		if (docBuilder == null)
		{
			try 
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setValidating(false);
				docBuilder = dbf.newDocumentBuilder();
			}
			catch (ParserConfigurationException e) 
			{
				e.printStackTrace();
			}
		}

		return docBuilder;
	}
}
