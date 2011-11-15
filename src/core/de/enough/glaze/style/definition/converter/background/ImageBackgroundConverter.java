package de.enough.glaze.style.definition.converter.background;

import net.rim.device.api.system.Bitmap;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Url;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.UrlPropertyParser;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.background.GzBackgroundFactory;
import de.enough.glaze.style.property.background.ImageBackground;
import de.enough.glaze.style.property.background.image.ImageBackgroundPosition;
import de.enough.glaze.style.resources.StyleResources;

/**
 * A {@link Converter} implementation to convert a definition to an image
 * background
 * 
 * @author Andre
 * 
 */
public class ImageBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static ImageBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static ImageBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ImageBackgroundConverter();
		}

		return INSTANCE;
	}

	public String[] getIds() {
		return new String[] { "background-image", "background-image-position",
				"background-position", "background-image-repeat",
				"background-repeat" };
	}

	public Object convert(Definition definition) throws CssSyntaxError {
		if (!definition.hasProperties(this)) {
			return null;
		}

		Property backgroundTypeProp = definition.getProperty("background-type");
		Property backgroundImageProp = definition
				.getProperty("background-image");
		Property backgroundImagePositionProp = definition
				.getProperty(new String[] { "background-image-position",
						"background-position" });
		Property backgroundImageRepeatProp = definition
				.getProperty(new String[] { "background-image-repeat",
						"background-repeat" });

		Bitmap imageBitmap = null;
		ImageBackgroundPosition imagePosition = new ImageBackgroundPosition();
		int imageRepeat = ImageBackground.REPEAT_NONE;

		if (backgroundImageProp != null) {
			Object result = UrlPropertyParser.getInstance().parse(
					backgroundImageProp);
			if (result instanceof Url) {
				Url url = (Url) result;
				try {
					imageBitmap = StyleResources.getInstance().loadBitmap(url);
				} catch (ContentException e) {
					throw new CssSyntaxError("unable to load image",
							backgroundImageProp);
				}
			} else {
				throw new CssSyntaxError("must be a single url",
						backgroundImageProp);
			}

			if (backgroundImagePositionProp != null) {
				imagePosition = getPosition(backgroundImagePositionProp);
			}

			if (backgroundImageRepeatProp != null) {
				result = ValuePropertyParser.getInstance().parse(
						backgroundImageRepeatProp);
				if (result instanceof String) {
					String repeatValue = (String) result;
					imageRepeat = getRepeat(repeatValue,
							backgroundImageRepeatProp);
				} else if (result instanceof String[]) {
					throw new CssSyntaxError("must be a single id",
							backgroundImageRepeatProp);
				}
			}

			if (imageBitmap != null) {
				return GzBackgroundFactory.createImageBackground(imageBitmap,
						imagePosition, imageRepeat);
			} else {
				throw new CssSyntaxError(
						"unable to create image background, properties are missing",
						backgroundTypeProp);
			}
		}

		return null;
	}

	/**
	 * Returns the image background position for the given property
	 * 
	 * @param backgroundImagePositionProp
	 *            the image background position property
	 * @return the created {@link ImageBackgroundPosition} instance
	 * @throws CssSyntaxError
	 *             if the CSS syntax is wrong
	 */
	private ImageBackgroundPosition getPosition(
			Property backgroundImagePositionProp) throws CssSyntaxError {
		Object result = ValuePropertyParser.getInstance().parse(
				backgroundImagePositionProp);
		// if a single value is given ...
		if (result instanceof String) {
			String imageBackgroundPositionValue = (String) result;
			// get the relative position from the value
			Dimension imageBackgroundPosition = getRelativePosition(imageBackgroundPositionValue);
			// if the value is a relative horizontal position ...
			if (imageBackgroundPosition != null
					&& ImageBackgroundPosition
							.isValidHorizontalPosition(imageBackgroundPosition)) {
				// return the image background position
				return new ImageBackgroundPosition(imageBackgroundPosition);
				// otherwise ...
			} else {
				// parse the dimension
				imageBackgroundPosition = (Dimension) DimensionPropertyParser
						.getInstance().parse(backgroundImagePositionProp);
				// return the image background position
				return new ImageBackgroundPosition(imageBackgroundPosition);
			}
			// if multiple values are given ...
		} else if (result instanceof String[]) {
			Dimension horizontalPosition = ImageBackgroundPosition.CENTER;
			Dimension verticalPosition = ImageBackgroundPosition.CENTER;

			String[] imageBackgroundPositionValues = (String[]) result;
			// if 2 values are given ...
			if (imageBackgroundPositionValues.length == 2) {
				// for each value ...
				for (int index = 0; index < imageBackgroundPositionValues.length; index++) {
					String imageBackgroundPositionValue = imageBackgroundPositionValues[index];
					// get the relative position for the value
					Dimension imageBackgroundPosition = getRelativePosition(imageBackgroundPositionValue);
					// if the value is a relative position ...
					if (imageBackgroundPosition != null) {
						// if the relative position is the first value and a
						// valid horizontal position ...
						if (index == 0
								&& ImageBackgroundPosition
										.isValidHorizontalPosition(imageBackgroundPosition)) {
							horizontalPosition = imageBackgroundPosition;
							// if the relative position is the second value and
							// a
							// valid vertical position ...
						} else if (index == 1
								&& ImageBackgroundPosition
										.isValidVerticalPosition(imageBackgroundPosition)) {
							verticalPosition = imageBackgroundPosition;
						} else {
							throw new CssSyntaxError(
									"invalid image background position",
									backgroundImagePositionProp);
						}
						// otherwise ...
					} else {
						// parse the dimension
						imageBackgroundPosition = (Dimension) DimensionPropertyParser
								.getInstance().parse(
										imageBackgroundPositionValue,
										backgroundImagePositionProp);
						if (index == 0) {
							horizontalPosition = imageBackgroundPosition;
						} else if (index == 1) {
							verticalPosition = imageBackgroundPosition;
						}
					}
				}

				// return the image background position
				return new ImageBackgroundPosition(horizontalPosition,
						verticalPosition);
			} else {
				throw new CssSyntaxError(
						"must be 1 or two dimensions or values",
						backgroundImagePositionProp);
			}
		} else {
			// return an default image background position
			return new ImageBackgroundPosition();
		}
	}

	/**
	 * Returns the dimension for the given position value
	 * 
	 * @param position
	 *            the position value
	 * @return the dimension for the given position value
	 */
	private Dimension getRelativePosition(String position) {
		if ("top".equals(position)) {
			return ImageBackgroundPosition.TOP;
		} else if ("bottom".equals(position)) {
			return ImageBackgroundPosition.BOTTOM;
		} else if ("left".equals(position)) {
			return ImageBackgroundPosition.LEFT;
		} else if ("right".equals(position)) {
			return ImageBackgroundPosition.RIGHT;
		} else if ("center".equals(position)) {
			return ImageBackgroundPosition.CENTER;
		} else {
			return null;
		}
	}

	/**
	 * Returns the repeat flag for the given repeat value
	 * 
	 * @param repeat
	 *            the repeat value
	 * @param backgroundImageRepeatProp
	 *            the repeat property
	 * @return the repeat flag
	 * @throws CssSyntaxError
	 */
	private int getRepeat(String repeat, Property backgroundImageRepeatProp)
			throws CssSyntaxError {
		if ("repeat".equals(repeat)) {
			return ImageBackground.REPEAT_X | ImageBackground.REPEAT_Y;
		} else if ("repeat-x".equals(repeat)) {
			return ImageBackground.REPEAT_X;
		} else if ("repeat-y".equals(repeat)) {
			return ImageBackground.REPEAT_Y;
		} else {
			throw new CssSyntaxError("invalid background repeat",
					backgroundImageRepeatProp);
		}
	}
}
