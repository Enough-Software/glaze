package de.enough.glaze.style.property.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.property.background.patch.Tile;

public class PatchBackground extends GzBackground {

	/**
	 * the argb array
	 */
	private final int[] argb;

	/**
	 * the argb width
	 */
	private final int argbWidth;

	/**
	 * the argb height
	 */
	private final int argbHeight;

	/**
	 * the tiling
	 */
	private XYEdges tiling;

	/**
	 * the top/left corner tile
	 */
	private Tile topLeftTile;

	/**
	 * the top/right corner tile
	 */
	private Tile topRightTile;

	/**
	 * the bottom/right corner tile
	 */
	private Tile bottomRightTile;
	/**
	 * the bottom/left corner tile
	 */
	private Tile bottomLeftTile;

	/**
	 * the top tile
	 */
	private Tile topPatchTile;

	/**
	 * the left tile
	 */
	private Tile leftPatchTile;

	/**
	 * the bottom tile
	 */
	private Tile bottomPatchTile;

	/**
	 * the right tile
	 */
	private Tile rightPatchTile;

	/**
	 * the center tile
	 */
	private Tile centerPatchTile;

	/**
	 * Constructs a new {@link PatchBackground} instance
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param tiling
	 *            the tiling
	 */
	public PatchBackground(Bitmap bitmap, Dimension[] tiling) {
		this.argbWidth = bitmap.getWidth();
		this.argbHeight = bitmap.getHeight();
		this.argb = new int[this.argbWidth * this.argbHeight];

		// get the argb array
		bitmap.getARGB(this.argb, 0, this.argbWidth, 0, 0, this.argbWidth,
				this.argbHeight);

		// create the tiling edges
		this.tiling = new XYEdges();
		this.tiling.top = tiling[0].getValue();
		this.tiling.right = tiling[1].getValue();
		this.tiling.bottom = tiling[2].getValue();
		this.tiling.left = tiling[3].getValue();

		// create the corner tiles
		this.topLeftTile = createCornerTile(0, 0, this.tiling.left,
				this.tiling.top);
		this.topRightTile = createCornerTile(
				this.argbWidth - this.tiling.right, 0, this.tiling.right,
				this.tiling.top);
		this.bottomRightTile = createCornerTile(this.argbWidth
				- this.tiling.right, this.argbHeight - this.tiling.bottom,
				this.tiling.right, this.tiling.bottom);
		this.bottomLeftTile = createCornerTile(0, this.argbHeight
				- this.tiling.bottom, this.tiling.left, this.tiling.bottom);

		// create the top, right, bottom, left and center tile
		this.topPatchTile = createTopTile();
		this.rightPatchTile = createRightTile();
		this.bottomPatchTile = createBottomTile();
		this.leftPatchTile = createLeftTile();
		this.centerPatchTile = createCenterTile();

	}

	/**
	 * Creates a corner (single) tile for the given tile offset and dimension.
	 * 
	 * @param tileX
	 *            the x offset
	 * @param tileY
	 *            the y offset
	 * @param tileWidth
	 *            the width
	 * @param tileHeight
	 *            the height
	 * @return the created {@link Tile}
	 */
	private Tile createCornerTile(int tileX, int tileY, int tileWidth,
			int tileHeight) {
		return new Tile(Tile.TILING_SINGLE, this.argb, this.argbWidth,
				this.argbHeight, tileX, tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the top tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createTopTile() {
		int tileX = this.tiling.left;
		int tileY = 0;

		int tileWidth = Math.max(1, this.argbWidth
				- (this.tiling.left + this.tiling.right));
		int tileHeight = this.tiling.top;

		return new Tile(Tile.TILING_HORIZONTAL, this.argb, this.argbWidth,
				this.argbHeight, tileX, tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the right tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createRightTile() {
		int tileX = this.argbWidth - this.tiling.right;
		int tileY = this.tiling.top;

		int tileWidth = this.tiling.right;
		int tileHeight = Math.max(1, this.argbHeight
				- (this.tiling.top + this.tiling.bottom));

		return new Tile(Tile.TILING_VERTICAL, this.argb, this.argbWidth,
				this.argbHeight, tileX, tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the bottom tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createBottomTile() {
		int tileX = this.tiling.left;
		int tileY = this.argbHeight - this.tiling.bottom;

		int tileWidth = Math.max(1, this.argbWidth
				- (this.tiling.left + this.tiling.right));
		int tileHeight = this.tiling.bottom;

		return new Tile(Tile.TILING_HORIZONTAL, this.argb, this.argbWidth,
				this.argbHeight, tileX, tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the left tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createLeftTile() {
		int tileX = 0;
		int tileY = this.tiling.top;

		int tileWidth = this.tiling.left;
		int tileHeight = Math.max(1, this.argbHeight
				- (this.tiling.top + this.tiling.bottom));

		return new Tile(Tile.TILING_VERTICAL, this.argb, this.argbWidth,
				this.argbHeight, tileX, tileY, tileWidth, tileHeight);
	}
	
	/**
	 * Creates the center tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createCenterTile() {
		int tileX = this.tiling.left;
		int tileY = this.tiling.top;

		int tileWidth = Math.max(1, this.argbWidth
				- (this.tiling.left + this.tiling.right));
		int tileHeight = Math.max(1, this.argbHeight
				- (this.tiling.top + this.tiling.bottom));

		return new Tile(Tile.TILING_FILL, this.argb, this.argbWidth,
				this.argbHeight, tileX, tileY, tileWidth, tileHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		drawHorizontalTiles(x, y, width, height, graphics);
		drawVerticalTiles(x, y, width, height, graphics);
		drawCorners(x, y, width, height, graphics);
		drawCenterTiles(x, y, width, height, graphics);
	}

	/**
	 * Paints the horizontal tiles of the background.
	 * 
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	private void drawHorizontalTiles(int x, int y, int width, int height,
			Graphics graphics) {
		// draw the top tiles
		int fillX = x + this.tiling.left;
		int fillY = y;
		int fillWidth = Math.max(1, width
				- (this.tiling.left + this.tiling.right));

		int[] topPatchTileBuffer = this.topPatchTile.getBuffer();
		int tileWidth = this.topPatchTile.getWidth();
		int tileHeight = this.topPatchTile.getHeight();

		graphics.pushContext(fillX, fillY, fillWidth, tileHeight, 0, 0);
		for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset + tileWidth) {
			graphics.drawARGB(topPatchTileBuffer, 0, tileWidth, fillX + xOffset,
					fillY, tileWidth, tileHeight);
		}
		graphics.popContext();

		// draw the bottom tiles
		fillY = y + (height - this.tiling.bottom);

		int[] bottomPatchTileBuffer = this.bottomPatchTile.getBuffer();
		tileWidth = this.bottomPatchTile.getWidth();
		tileHeight = this.bottomPatchTile.getHeight();

		graphics.pushContext(fillX, fillY, fillWidth, tileHeight, 0, 0);
		for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset + tileWidth) {
			graphics.drawARGB(bottomPatchTileBuffer, 0, tileWidth, fillX
					+ xOffset, fillY, tileWidth, tileHeight);
		}
		graphics.popContext();
	}

	/**
	 * Paints the vertical tiles of the background.
	 * 
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	private void drawVerticalTiles(int x, int y, int width, int height,
			Graphics graphics) {
		// draw the left tiles
		int fillX = x;
		int fillY = y + this.tiling.top;
		int fillHeight = Math.max(1, height
				- (this.tiling.top + this.tiling.bottom));

		int[] leftPatchTileBuffer = this.leftPatchTile.getBuffer();
		int tileWidth = this.leftPatchTile.getWidth();
		int tileHeight = this.leftPatchTile.getHeight();

		graphics.pushContext(fillX, fillY, tileWidth, fillHeight, 0, 0);
		for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset
				+ tileHeight) {
			graphics.drawARGB(leftPatchTileBuffer, 0, tileWidth, fillX, fillY
					+ yOffset, tileWidth, tileHeight);
		}
		graphics.popContext();

		// draw the right tiles
		fillX = x + width - this.tiling.right;

		int[] rightPatchTileBuffer = this.rightPatchTile.getBuffer();
		tileWidth = this.rightPatchTile.getWidth();
		tileHeight = this.rightPatchTile.getHeight();

		graphics.pushContext(fillX, fillY, tileWidth, fillHeight, 0, 0);
		for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset
				+ tileHeight) {
			graphics.drawARGB(rightPatchTileBuffer, 0, tileWidth, fillX, fillY
					+ yOffset, tileWidth, tileHeight);
		}
		graphics.popContext();
	}

	/**
	 * Paints the center tiles of the background.
	 * 
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	private void drawCenterTiles(int x, int y, int width, int height,
			Graphics graphics) {
		int fillX = x + this.tiling.left;
		int fillY = y + this.tiling.top;

		int fillWidth = Math.max(1, width - (this.tiling.right + this.tiling.left));
		int fillHeight = Math.max(1, height - (this.tiling.top + this.tiling.bottom));

		int[] centerPatchTileBuffer = this.centerPatchTile.getBuffer();
		int tileWidth = this.centerPatchTile.getWidth();
		int tileHeight = this.centerPatchTile.getHeight();

		graphics.pushContext(fillX, fillY, fillWidth, fillHeight, 0, 0);
		for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset
				+ tileWidth) {
			for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset
					+ tileHeight) {
				graphics.drawARGB(centerPatchTileBuffer, 0, tileWidth, fillX
						+ xOffset, fillY + yOffset, tileWidth, tileHeight);
			}
		}
		graphics.popContext();
	}

	/**
	 * Draws the 4 corners of the background.
	 * 
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	private void drawCorners(int x, int y, int width, int height,
			Graphics graphics) {
		drawCorner(this.topLeftTile, 0, 0, graphics);
		drawCorner(this.topRightTile, width - this.tiling.right, 0, graphics);
		drawCorner(this.bottomRightTile, width - this.tiling.right, height
				- this.tiling.bottom, graphics);
		drawCorner(this.bottomLeftTile, 0, height - this.tiling.bottom,
				graphics);
	}

	/**
	 * Draws a corner of the background.
	 * 
	 * @param cornerTile
	 *            the corner tile
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	private void drawCorner(Tile cornerTile, int x, int y,
			Graphics graphics) {
		int[] buffer = cornerTile.getBuffer();
		int width = cornerTile.getWidth();
		int height = cornerTile.getHeight();
		graphics.drawARGB(buffer, 0, width, x, y, width, height);
	}
}
