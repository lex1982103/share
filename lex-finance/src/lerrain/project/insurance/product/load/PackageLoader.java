package lerrain.project.insurance.product.load;

import lerrain.project.insurance.product.Company;
import lerrain.project.insurance.product.Insurance;
import lerrain.project.insurance.product.PackageIns;
import lerrain.tool.formula.FormulaUtil;

import java.io.File;
import java.util.Iterator;

/**
 * Created by lerrain on 2017/4/25.
 * @deprecated
 */
public class PackageLoader
{
    Company company;

    PackageIns product;

    Loader loader;

//	String path;

    public PackageLoader(Company company, Loader loader)
    {
        this.company = company;
        this.loader = loader;
    }

    public PackageIns loadProduct(XmlNode root)
    {
        this.product = new PackageIns();

        parseAttribution(root);
        parseRoot(root);

        return product;
    }

    public void parseAttribution(XmlNode n1)
    {
        String seq = n1.getAttribute("sequence");
        if (seq == null || "".equals(seq))
            product.setSequence(1000);
        else
            product.setSequence(Integer.parseInt(seq));

        product.setId(n1.getAttribute("id"));
        product.setName(n1.getAttribute("name"));
        product.setAbbrName(n1.getAttribute("name_abbr"));
        product.setCode(n1.getAttribute("code"));
        product.setType(n1.getAttribute("type"));

        String vendor = n1.getAttributeInOrder("vendor,company_id,corporation_id");
        product.setVendor(vendor);
    }

    private void parseRoot(XmlNode e)
    {
        for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
        {
            XmlNode n1 = (XmlNode)iter.next();

            if ("input".equals(n1.getName()))
            {
                parseInput(n1);
            }
            else if ("product".equals(n1.getName()))
            {
                parseProduct(n1);
            }
            else
            {
            }
        }
    }

    private void parseInput(XmlNode e)
    {
        for (Iterator iter = e.getChildren("item").iterator(); iter.hasNext(); )
        {
            XmlNode n1 = (XmlNode)iter.next();

            PackageIns.InputItem pi = new PackageIns.InputItem();
            pi.setName(n1.getAttribute("name"));
        }
    }

    private void parseProduct(XmlNode e)
    {

    }
}
