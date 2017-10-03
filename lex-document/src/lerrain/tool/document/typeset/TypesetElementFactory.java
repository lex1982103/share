package lerrain.tool.document.typeset;

import lerrain.tool.document.typeset.element.TypesetElement;
import org.w3c.dom.Element;

/**
 * Created by lerrain on 2017/8/18.
 */
public interface TypesetElementFactory
{
    public TypesetElement parse(Typeset typeset, Object node);
}
