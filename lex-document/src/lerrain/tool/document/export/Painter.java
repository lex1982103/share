package lerrain.tool.document.export;

import lerrain.tool.document.LexDocument;

public interface Painter
{
	public static final int AUTO		= 0;
	public static final int DIRECTORY	= 1;
	public static final int FILE		= 2;
	public static final int STREAM		= 3;
	public static final int OBJECT		= 4;
	public static final int FILEPATH	= 5;
	
	public void paint(LexDocument doc, Object canvas, int canvasType);
}
