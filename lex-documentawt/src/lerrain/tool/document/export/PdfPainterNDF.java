package lerrain.tool.document.export;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lerrain.tool.document.DocumentExportException;
import lerrain.tool.document.LexColor;
import lerrain.tool.document.LexDocument;
import lerrain.tool.document.LexFont;
import lerrain.tool.document.LexPage;
import lerrain.tool.document.element.DocumentImage;
import lerrain.tool.document.element.DocumentLine;
import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.DocumentRect;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.size.Paper;

import lerrain.tool.document.typeset.TypesetUtil;

public class PdfPainterNDF extends PdfPainterBase implements Painter
{
	public void paint(LexDocument doc, Object canvas, int canvasType)
	{
		if (canvasType == Painter.AUTO)
		{
			if (canvas instanceof File)
				canvasType = Painter.FILE;
			else if (canvas instanceof String)
				canvasType = Painter.FILEPATH;
			else if (canvas instanceof OutputStream)
				canvasType = Painter.STREAM;
		}
		
		if (canvasType == Painter.STREAM)
		{
			draw(doc, (OutputStream)canvas);
		}
		else if (canvasType == Painter.FILE || canvasType == Painter.FILEPATH)
		{
			File file = canvasType == Painter.FILE ? (File)canvas : new File((String)canvas);
			FileOutputStream fos = null;
			try
			{
				fos = new FileOutputStream(file);
				draw(doc, fos);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (fos != null)
						fos.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		else
		{
			throw new DocumentExportException("不支持的导出格式");
		}
	}
}
