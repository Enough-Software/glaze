package de.enough.glaze.style.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.decor.Background;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;

public class GzBackgroundFactory {

	/**
	 * Creates a solid background with the given color
	 * 
	 * @param color
	 *            the color
	 * @return the created background
	 */
	public static GzBackground createSolidBackground(Color color) {
		// a simple solid background with a color
		return new SolidBackground(color);
	}

	/**
	 * Creates a solid background with the given color
	 * 
	 * @param color
	 *            the color
	 * @return the created background
	 */
	public static GzBackground createRoundrectBackground(Color color,
			Dimension[] arcs) {
		// a background with round edges, specified by arcs
		// - color : the color for the background
		// - arcs : must be 4 values, corresponding to the arc size for the upper-left, upper-right, lower-right
		// and lower-left corners of the background. If an arc is null, no round edges is drawn
		// for the given corner.
		return new RoundrectBackground(color, arcs);
	}

	/**
	 * Creates a bitmap background with the given bitmap, position and repeat
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param position
	 *            the position
	 * @param repeat
	 *            the repeat
	 * @return the created background
	 */
	public static GzBackground createImageBackground(Bitmap bitmap,
			String[] position, String repeat) {
		// a background displaying an image
		// - bitmap : the image to draw
		// - position : must be 1 or 2 values of the following :
		// top, left, bottom, right, center. The first value is the vertical 
		// alignment, the second is the horizontal alignment. The default values are
		// center center. Single values are set to their corresponding position 
		// (top, bottom, center: first value, left, right: second value)
		// - repeat : must be 1 value of the following : no-repeat, repeat-x, repeat-y, repeat.
		// no repeat obviously doesn't repeat the image, 
		// repeat-x repeats the image across the horizontal space, the vertical position is still respected
		// repeat-y repeats the image accorss the vertical space, the horizontal position is still respected
		
		int positionFlag =0;		
		int repeatFlag = 0;
		
		for (int i=0;i<position.length;i++) {
			if ( "top".equals(position[i])) {
				positionFlag |= ImageBackground.POSITION_TOP;
			} else if ( "bottom".equals(position[i]) ) {
				positionFlag |= ImageBackground.POSITION_BOTTOM;
			} else if ( "left".equals(position[i]) ) {
				positionFlag |= ImageBackground.POSITION_LEFT;
			} else if ( "right".equals(position[i]) ) {
				positionFlag |= ImageBackground.POSITION_RIGHT;
			} else if ( "center".equals(position[i]) ) {
				positionFlag |= ImageBackground.POSITION_CENTER;
			}
		}
		
		if ( "repeat".equals(repeat) ) {
			repeatFlag = ImageBackground.REPEAT_X | ImageBackground.REPEAT_Y;
		} else if ( "repeat-x".equals(repeat) ) {
			repeatFlag = ImageBackground.REPEAT_X;
		} else if ( "repeat-y".equals(repeat) ) {
			repeatFlag = ImageBackground.REPEAT_Y;
		}
				
		return new ImageBackground(bitmap, positionFlag, repeatFlag);
	}

	/**
	 * Creates a gradient background with the given orientation, colors and
	 * offsets
	 * 
	 * @param orientation
	 *            the orientation
	 * @param colors
	 *            the colors
	 * @param offsets
	 *            the offsets
	 * @return the created background
	 */
	public static GzBackground createGradientBackground(String orientation,
			Color[] colors, Dimension[] offsets) {
		// a gradient background
		// - orientation : a static value, either "vertical" or "horizontal"
		// - colors : must be 2 values and specifies the start and end color
		// - offsets : must be 2 values and specifies the start and end offset
		// e.g. the gradient color is drawn from 0% of the available width to
		// 50%,
		// the rest is filled with the end color
		if ( "vertical".equals(orientation)) {
			return new VerticalGradientBackground(colors, offsets);
		} else if ( "horizontal".equals(orientation) ) {
			return new HorizontalGradientBackground(colors, offsets);
		} else {
			return null;
		}
	}

	/**
	 * Creates a new patch background with the given bitmap, padding, tiling and
	 * color
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param padding
	 *            the padding
	 * @param tiling
	 *            the tiling
	 * @param color
	 *            the color
	 * @return the created background
	 */
	public static GzBackground createPatchBackground(Bitmap bitmap,
			Dimension[] margin, Dimension[] tiling) {
		// a patch background, comparable to a 9-patch on Android
		// (see de.enough.polish.ui.background.PatchBackground)
		// - bitmap : the bitmap to use
		// - margin : the margin, mapped like shorthand margins in W3C CSS (see
		// above).
		// the drawing area is enlarged by the given margin values.
		// - tiling : the tiling of the bitmap, e.g.:
		// 10px : the width and height of the edges are 10px
		// 10px 11px : the height is 10px, the width is 12px
		// 10px 11px 12px : the top height is 10px, the left and right width is
		// 11px and the bottom height is 12px
		// 10px 11px 12px 13px : the top height is 10px, the right width is
		// 11px, the bottom height is 12px and the left width is 13px
		return null;
	}

	/**
	 * Creates a new mask background with the given mask color, mask background
	 * and background
	 * 
	 * @param maskColor
	 *            the mask color
	 * @param maskBackground
	 *            the mask background
	 * @param background
	 *            the background
	 * @return the created background
	 */
	public static GzBackground createMaskBackground(Color maskColor,
			GzBackground maskBackground, GzBackground background) {
		// a mask background
		// (see de.enough.polish.ui.backgrounds.MaskBackground)
		// - maskColor : the color to fill with the given background
		// - maskBackground : the background to use as the mark
		// - background: the background to draw into the mask
		return null;
	}

	/**
	 * Creates a new layer background with the given backgrounds and margins
	 * 
	 * @param backgrounds
	 *            the backgrounds
	 * @param margins
	 *            the margins
	 * @return the created background
	 */
	public static GzBackground createLayerBackground(Background[] backgrounds,
			Dimension[] margins) {
		// a layer background which layers the given backgrounds onto each other
		// (see de.enough.polish.ui.backgrounds.LayerBackground)
		// - backgrounds : the backgrounds to layer, first is bottom
		// - margins: the margins for the single backgrounds, must be null or
		// the count
		// of backgrounds
		return null;
	}
}
