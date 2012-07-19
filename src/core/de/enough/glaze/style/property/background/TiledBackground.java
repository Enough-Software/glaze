/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style.property.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.property.background.clipping.ClippingTest;
import de.enough.glaze.style.property.background.patch.Tile;

public class TiledBackground extends GzBackground {

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
	 * the clipping test
	 */
	private ClippingTest clippingTest;

	/**
	 * Constructs a new {@link TiledBackground} instance
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param tiling
	 *            the tiling
	 */
	public TiledBackground(Bitmap bitmap, Dimension[] tiling) {
		int argbWidth = bitmap.getWidth();
		int argbHeight = bitmap.getHeight();
		int[] argb = new int[argbWidth * argbHeight];

		// get the argb array
		bitmap.getARGB(argb, 0, argbWidth, 0, 0, argbWidth, argbHeight);

		// create the tiling edges
		this.tiling = new XYEdges();
		this.tiling.top = tiling[0].getValue();
		this.tiling.right = tiling[1].getValue();
		this.tiling.bottom = tiling[2].getValue();
		this.tiling.left = tiling[3].getValue();

		// create the corner tiles
		this.topLeftTile = createCornerTile(0, 0, this.tiling.left,
				this.tiling.top, argb, argbWidth, argbHeight);
		this.topRightTile = createCornerTile(argbWidth - this.tiling.right, 0,
				this.tiling.right, this.tiling.top, argb, argbWidth, argbHeight);
		this.bottomRightTile = createCornerTile(argbWidth - this.tiling.right,
				argbHeight - this.tiling.bottom, this.tiling.right,
				this.tiling.bottom, argb, argbWidth, argbHeight);
		this.bottomLeftTile = createCornerTile(0, argbHeight
				- this.tiling.bottom, this.tiling.left, this.tiling.bottom,
				argb, argbWidth, argbHeight);

		// create the top, right, bottom, left and center tile
		this.topPatchTile = createTopTile(argb, argbWidth, argbHeight);
		this.rightPatchTile = createRightTile(argb, argbWidth, argbHeight);
		this.bottomPatchTile = createBottomTile(argb, argbWidth, argbHeight);
		this.leftPatchTile = createLeftTile(argb, argbWidth, argbHeight);
		this.centerPatchTile = createCenterTile(argb, argbWidth, argbHeight);

		this.clippingTest = new ClippingTest();
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
			int tileHeight, int[] argb, int argbWidth, int argbHeight) {
		return new Tile(Tile.TILING_SINGLE, argb, argbWidth, argbHeight, tileX,
				tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the top tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createTopTile(int[] argb, int argbWidth, int argbHeight) {
		int tileX = this.tiling.left;
		int tileY = 0;

		int tileWidth = Math.max(1, argbWidth
				- (this.tiling.left + this.tiling.right));
		int tileHeight = this.tiling.top;

		return new Tile(Tile.TILING_HORIZONTAL, argb, argbWidth,
				argbHeight, tileX, tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the right tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createRightTile(int[] argb, int argbWidth, int argbHeight) {
		int tileX = argbWidth - this.tiling.right;
		int tileY = this.tiling.top;

		int tileWidth = this.tiling.right;
		int tileHeight = Math.max(1, argbHeight
				- (this.tiling.top + this.tiling.bottom));

		return new Tile(Tile.TILING_VERTICAL, argb, argbWidth, argbHeight,
				tileX, tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the bottom tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createBottomTile(int[] argb, int argbWidth, int argbHeight) {
		int tileX = this.tiling.left;
		int tileY = argbHeight - this.tiling.bottom;

		int tileWidth = Math.max(1, argbWidth
				- (this.tiling.left + this.tiling.right));
		int tileHeight = this.tiling.bottom;

		return new Tile(Tile.TILING_HORIZONTAL, argb, argbWidth,
				argbHeight, tileX, tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the left tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createLeftTile(int[] argb, int argbWidth, int argbHeight) {
		int tileX = 0;
		int tileY = this.tiling.top;

		int tileWidth = this.tiling.left;
		int tileHeight = Math.max(1, argbHeight
				- (this.tiling.top + this.tiling.bottom));

		return new Tile(Tile.TILING_VERTICAL, argb, argbWidth, argbHeight,
				tileX, tileY, tileWidth, tileHeight);
	}

	/**
	 * Creates the center tile.
	 * 
	 * @return the created {@link Tile}
	 */
	private Tile createCenterTile(int[] argb, int argbWidth, int argbHeight) {
		int tileX = this.tiling.left;
		int tileY = this.tiling.top;

		int tileWidth = Math.max(1, argbWidth
				- (this.tiling.left + this.tiling.right));
		int tileHeight = Math.max(1, argbHeight
				- (this.tiling.top + this.tiling.bottom));

		return new Tile(Tile.TILING_FILL, argb, argbWidth, argbHeight,
				tileX, tileY, tileWidth, tileHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		this.clippingTest.setProperties(graphics);
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

		Bitmap topPatchTileBitmap = this.topPatchTile.getBitmap();
		int tileWidth = this.topPatchTile.getWidth();
		int tileHeight = this.topPatchTile.getHeight();

		if (this.clippingTest.isInClippingArea(fillX, fillY, fillWidth, tileHeight)) {
			graphics.pushContext(fillX, fillY, fillWidth, tileHeight, 0, 0);
			for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset
					+ tileWidth) {
				if (this.clippingTest.isInClippingArea(fillX + xOffset, fillY,
						tileWidth, tileHeight)) {
					graphics.drawBitmap(fillX + xOffset, fillY, tileWidth,
							tileHeight, topPatchTileBitmap, 0, 0);
				}
			}
			graphics.popContext();
		}

		// draw the bottom tiles
		fillY = y + (height - this.tiling.bottom);

		Bitmap bottomPatchTileBitmap = this.bottomPatchTile.getBitmap();
		tileWidth = this.bottomPatchTile.getWidth();
		tileHeight = this.bottomPatchTile.getHeight();

		if (this.clippingTest.isInClippingArea(fillX, fillY, fillWidth, tileHeight)) {
			graphics.pushContext(fillX, fillY, fillWidth, tileHeight, 0, 0);
			for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset
					+ tileWidth) {
				if (this.clippingTest.isInClippingArea(fillX + xOffset, fillY,
						tileWidth, tileHeight)) {
					graphics.drawBitmap(fillX + xOffset, fillY, tileWidth,
							tileHeight, bottomPatchTileBitmap, 0, 0);
				}
			}
			graphics.popContext();
		}
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

		Bitmap leftPatchTileBitmap = this.leftPatchTile.getBitmap();
		int tileWidth = this.leftPatchTile.getWidth();
		int tileHeight = this.leftPatchTile.getHeight();

		if (this.clippingTest.isInClippingArea(fillX, fillY, tileWidth, fillHeight)) {
			graphics.pushContext(fillX, fillY, tileWidth, fillHeight, 0, 0);
			for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset
					+ tileHeight) {
				if (this.clippingTest.isInClippingArea(fillX, fillY + yOffset,
						tileWidth, tileHeight)) {
					graphics.drawBitmap(fillX, fillY + yOffset, tileWidth,
							tileHeight, leftPatchTileBitmap, 0, 0);
				}
			}
			graphics.popContext();
		}

		// draw the right tiles
		fillX = x + width - this.tiling.right;

		Bitmap rightPatchTileBitmap = this.rightPatchTile.getBitmap();
		tileWidth = this.rightPatchTile.getWidth();
		tileHeight = this.rightPatchTile.getHeight();

		if (this.clippingTest.isInClippingArea(fillX, fillY, tileWidth, fillHeight)) {
			graphics.pushContext(fillX, fillY, tileWidth, fillHeight, 0, 0);
			for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset
					+ tileHeight) {
				graphics.drawBitmap(fillX, fillY + yOffset, tileWidth,
						tileHeight, rightPatchTileBitmap, 0, 0);
			}
			graphics.popContext();
		}
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

		int fillWidth = Math.max(1, width
				- (this.tiling.right + this.tiling.left));
		int fillHeight = Math.max(1, height
				- (this.tiling.top + this.tiling.bottom));

		Bitmap centerPatchTileBitmap = this.centerPatchTile.getBitmap();
		int tileWidth = this.centerPatchTile.getWidth();
		int tileHeight = this.centerPatchTile.getHeight();

		if (this.clippingTest.isInClippingArea(fillX, fillY, fillWidth, fillHeight)) {
			graphics.pushContext(fillX, fillY, fillWidth, fillHeight, 0, 0);
			for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset
					+ tileWidth) {
				for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset
						+ tileHeight) {
					if (this.clippingTest.isInClippingArea(fillX + xOffset, fillY
							+ yOffset, tileWidth, tileHeight)) {
						graphics.drawBitmap(fillX + xOffset, fillY + yOffset,
								tileWidth, tileHeight, centerPatchTileBitmap,
								0, 0);
					}
				}
			}
			graphics.popContext();
		}
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
	private void drawCorner(Tile cornerTile, int x, int y, Graphics graphics) {
		Bitmap bitmap = cornerTile.getBitmap();
		int width = cornerTile.getWidth();
		int height = cornerTile.getHeight();
		if (this.clippingTest.isInClippingArea(x, y, width, height)) {
			graphics.drawBitmap(x, y, width, height, bitmap, 0, 0);
		}
	}
}
