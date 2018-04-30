package lerrain.tool.document.typeset.element.sheet;

import lerrain.tool.document.typeset.element.TypesetElement;

/**
 * Created by lerrain on 2017/5/1.
 */
public class SheetBlank
{
    String text;

    String font;
    int fontSize;

    int align							= TypesetElement.ALIGN_LEFT | TypesetElement.ALIGN_MIDDLE;

    int col = 1;
    int row = 1;

    int x = -1, y = -1;

    int[] margin;

    public SheetBlank()
    {
    }

    public boolean isVirtual() //跨行跨列的格子，在右下角虚拟一个sheetblank标识左上角的位置
    {
        return x >= 0 || y >= 0;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getAlign()
    {
        return align & 0x0F;
    }

    public int getVerticalAlign()
    {
        return align & 0xF0;
    }

    public int[] getMargin()
    {
        return margin;
    }

    public void setMargin(int[] margin)
    {
        this.margin = margin;
    }

    public void setAlign(int align)
    {
        this.align = align;
    }

    public String getFont()
    {
        return font;
    }

    public void setFont(String font)
    {
        this.font = font;
    }

    public int getFontSize()
    {
        return fontSize;
    }

    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }
}
