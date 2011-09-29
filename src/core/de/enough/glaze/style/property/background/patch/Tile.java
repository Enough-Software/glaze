package de.enough.glaze.style.property.background.patch;

import net.rim.device.api.system.Bitmap;
import de.enough.glaze.style.property.background.TiledBackground;

/**
 * A class representing a tile used in a {@link TiledBackground}. Takes a
 * specified tile in a ARGB buffer and repeats it horizontally, vertically or in
 * both directions until a maximum size defined by SIZE_POLICY is reached.
 * 
 * @author Andre
 * 
 */
public class Tile {

	/**
	 * The maximum dimension of a patch tile in pixels.
	 */
	private static int SIZE_POLICY = 64 * 64;

	/**
	 * The single tiling constant.
	 */
	public static final int TILING_SINGLE = 0x00;

	/**
	 * The horizontal tiling constant.
	 */
	public static final int TILING_HORIZONTAL = 0x01;

	/**
	 * The vertical tiling constant.
	 */
	public static final int TILING_VERTICAL = 0x02;

	/**
	 * The horizontal and vertical tiling constant.
	 */
	public static final int TILING_FILL = 0x03;

	/**
	 * The buffer.
	 */
	private final Bitmap bitmap;

	/**
	 * The width.
	 */
	private int width;

	/**
	 * The height.
	 */
	private int height;

	/**
	 * Sets the policy for the maximum size a patch tile may occupy in pixels.
	 * 
	 * @param sizePolicy
	 *            the maximum size for a patch tile
	 */
	public static void setSizePolicy(int sizePolicy) {
		SIZE_POLICY = sizePolicy;
	}

	/**
	 * Constructs a new {@link Tile} instance.
	 * 
	 * @param tiling
	 *            the tiling method
	 * @param argb
	 *            the bitmap as argb data
	 * @param argbWidth
	 *            the width of the bitmap represented as argb data
	 * @param argbHeight
	 *            the width of the bitmap represented as argb data
	 * @param tileX
	 *            the x offset of the tile
	 * @param tileY
	 *            the y offset of the tile
	 * @param tileWidth
	 *            the width of the tile
	 * @param tileHeight
	 *            the height of the tile
	 */
	public Tile(int tiling, int[] argb, int argbWidth, int argbHeight,
			int tileX, int tileY, int tileWidth, int tileHeight) {
		int tileSize = tileWidth * tileHeight;
		int[] buffer; 
		// if the tile size is larger than the policy size ...
		if (tileSize > SIZE_POLICY || tiling == TILING_SINGLE) {
			// set a single tile in the buffer
			this.width = tileWidth;
			this.height = tileHeight;
			buffer = new int[tileWidth * tileHeight];
			setTile(argb, argbWidth, argbHeight, tileX, tileY, tileWidth,
					tileHeight, buffer, 0, 0, tileWidth, tileHeight);
		} else {
			// if the tiling is horizontal ...
			if (tiling == TILING_HORIZONTAL) {
				// calculate and set the width and height for horizontal tiling
				setHorizontalDimension(tileWidth, tileHeight);
				// if the tiling is vertical ...
			} else if (tiling == TILING_VERTICAL) {
				// calculate and set the width and height for vertical tiling
				setVerticalDimension(tileWidth, tileHeight);
				// if the tiling is horizontal and vertical (FILL) ...
			} else {
				// calculate and set the width and height for horizontal and
				// vertical tiling
				setFillDimension(tileWidth, tileHeight);
			}

			// create and fill the buffer for the calculated width and height
			buffer = new int[this.width * this.height];
			if (tiling == TILING_HORIZONTAL) {
				for (int xOffset = 0; xOffset < this.width; xOffset = xOffset
						+ tileWidth) {
					setTile(argb, argbWidth, argbHeight, tileX, tileY,
							tileWidth, tileHeight, buffer, xOffset, 0,
							this.width, this.height);
				}
			} else if (tiling == TILING_VERTICAL) {
				for (int yOffset = 0; yOffset < this.height; yOffset = yOffset
						+ tileHeight) {
					setTile(argb, argbWidth, argbHeight, tileX, tileY,
							tileWidth, tileHeight, buffer, 0, yOffset,
							this.width, this.height);
				}
			} else {
				for (int xOffset = 0; xOffset < this.width; xOffset = xOffset
						+ tileWidth) {
					for (int yOffset = 0; yOffset < this.height; yOffset = yOffset
							+ tileHeight) {
						setTile(argb, argbWidth, argbHeight, tileX, tileY,
								tileWidth, tileHeight, buffer, xOffset,
								yOffset, this.width, this.height);
					}
				}
			}
			
		}
		
		this.bitmap = new Bitmap(this.width, this.height);
		this.bitmap.setARGB(buffer, 0, this.width, 0, 0, this.width, this.height);
	}

	/**
	 * Calculates and sets the buffer width and height for horizontal tiling.
	 * 
	 * @param tileWidth
	 *            the tile width
	 * @param tileHeight
	 *            the tile height
	 */
	private void setHorizontalDimension(int tileWidth, int tileHeight) {
		int maxWidth = (SIZE_POLICY / tileHeight);
		this.width = maxWidth - (maxWidth % tileWidth);
		this.height = tileHeight;
	}

	/**
	 * Calculates and sets the buffer width and height for vertical tiling.
	 * 
	 * @param tileWidth
	 *            the tile width
	 * @param tileHeight
	 *            the tile height
	 */
	private void setVerticalDimension(int tileWidth, int tileHeight) {
		this.width = tileWidth;
		int maxHeight = (SIZE_POLICY / tileWidth);
		this.height = maxHeight - (maxHeight % tileHeight);
	}

	/**
	 * Calculates and sets the buffer width and height for horizontal and
	 * vertical tiling.
	 * 
	 * @param tileWidth
	 *            the tile width
	 * @param tileHeight
	 *            the tile height
	 */
	private void setFillDimension(int tileWidth, int tileHeight) {
		// check the ratio between width and height and
		// set the tile width or height to approximate a rectangular
		// tile
		int rectangularTileWidth = tileWidth;
		int rectangularTileHeight = tileHeight;
		if (tileWidth < tileHeight) {
			rectangularTileWidth = tileWidth * (tileHeight / tileWidth);
		} else {
			rectangularTileHeight = tileHeight * (tileWidth / tileHeight);
		}

		// calculate the maximum repetition of the tile
		int totalPixels = rectangularTileWidth * rectangularTileHeight;
		int multiplier = 0;
		do {
			multiplier++;
			totalPixels = (rectangularTileWidth * (multiplier + 1))
					* (rectangularTileHeight * (multiplier + 1));
			// continue while the total pixels are below the maximum size
		} while (totalPixels < SIZE_POLICY);

		this.width = rectangularTileWidth * multiplier;
		this.height = rectangularTileHeight * multiplier;
	}

	/**
	 * Copies the tile specified by the given offsets and dimension to the given
	 * offsets of the target buffer.
	 * 
	 * @param argb
	 *            the argb data
	 * @param argbWidth
	 *            the width of the argb data
	 * @param argbHeight
	 *            the height of the argb data
	 * @param tileX
	 *            the x offset of the tile
	 * @param tileY
	 *            the y offset of the tile
	 * @param tileWidth
	 *            the width of the tile
	 * @param tileHeight
	 *            the height of the tile
	 * @param target
	 *            the target buffer
	 * @param targetX
	 *            the target x offset
	 * @param targetY
	 *            the target y offset
	 * @param targetWidth
	 *            the target width
	 * @param targetHeight
	 *            the target height
	 */
	private void setTile(int[] argb, int argbWidth, int argbHeight, int tileX,
			int tileY, int tileWidth, int tileHeight, int[] target,
			int targetX, int targetY, int targetWidth, int targetHeight) {
		for (int yOffset = 0; yOffset < tileHeight; yOffset++) {
			int sourceOffset = ((tileY + yOffset) * argbWidth) + tileX;
			int targetOffset = ((targetY + yOffset) * targetWidth) + targetX;
			System.arraycopy(argb, sourceOffset, target, targetOffset,
					tileWidth);
		}
	}

	/**
	 * Returns the width.
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns the height.
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Returns the buffer.
	 * 
	 * @return the buffer
	 */
	public Bitmap getBitmap() {
		return this.bitmap;
	}
}
