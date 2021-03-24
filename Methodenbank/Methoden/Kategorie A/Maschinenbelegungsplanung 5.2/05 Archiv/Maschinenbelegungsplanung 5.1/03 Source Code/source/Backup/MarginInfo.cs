using System;
using System.Runtime.InteropServices;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for MarginInfo.
	/// </summary>
	public class MarginInfo 
	{
		[DllImport("gdi32.dll")]
		private static extern Int16 GetDeviceCaps([In] [MarshalAs (UnmanagedType.U4)] int hDc, [In] [MarshalAs (UnmanagedType.U2)] Int16 funct);

		private float _leftMargin = 0;
		private float _topMargin = 0;
		private float _rightMargin = 0;
		private float _bottomMargin = 0;

		const short HORZSIZE      = 4;     /* Horizontal size in millimeters */
		const short VERTSIZE      = 6;     /* Vertical size in millimeters */
		const short HORZRES       = 8;     /* Horizontal width in pixels */
		const short VERTRES       = 10;    /* Vertical height in pixels*/
		const short PHYSICALOFFSETX = 112; /* Physical Printable Area x margin */
		const short PHYSICALOFFSETY = 113; /* Physical Printable Area y margin */

		public MarginInfo(int deviceHandle) 
		{
			// Non printable margin in pixels
			float offsetX = Convert.ToSingle(GetDeviceCaps(deviceHandle, PHYSICALOFFSETX));
			float offsetY = Convert.ToSingle(GetDeviceCaps(deviceHandle, PHYSICALOFFSETY));
			// printable page size in pixels
			float resolutionX = Convert.ToSingle(GetDeviceCaps(deviceHandle, HORZRES));
			float resolutionY = Convert.ToSingle(GetDeviceCaps(deviceHandle, VERTRES));
			// printable page size in current unit (in this case, converted to inches)
			float horizontalSize = Convert.ToSingle(GetDeviceCaps(deviceHandle, HORZSIZE))/25.4f;
			float verticalSize = Convert.ToSingle(GetDeviceCaps(deviceHandle, VERTSIZE))/25.4f;

			float pixelsPerInchX = resolutionX/horizontalSize;
			float pixelsPerInchY = resolutionY/verticalSize;

			_leftMargin  = (offsetX/pixelsPerInchX) * 100.0f;
			_topMargin   = (offsetY/pixelsPerInchX) * 100.0f;
			_bottomMargin  = _topMargin + (verticalSize * 100.0f);
			_rightMargin  = _leftMargin + (horizontalSize * 100.0f);
		}

		public float Left 
		{
			get {return _leftMargin;}
		}

		public float Right 
		{
			get {return _rightMargin;}
		}

		public float Top 
		{
			get {return _topMargin;}
		}

		public float Bottom 
		{
			get {return _bottomMargin;}
		}

		public override string ToString() 
		{
			return "left=" + _leftMargin.ToString() + ", top=" + _topMargin.ToString() + ", right=" + _rightMargin.ToString() + ", bottom=" + _bottomMargin.ToString();
		}
	}
}