package de.enough.glaze.style.definition.converter.background;

import net.rim.device.api.system.Bitmap;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.StyleResources;
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

	private ImageBackgroundPosition getPosition(
			Property backgroundImagePositionProp) throws CssSyntaxError {
		Object result = ValuePropertyParser.getInstance().parse(
				backgroundImagePositionProp);

		if (result instanceof String) {
			String imageBackgroundPositionValue = (String)result;
			Dimension imageBackgroundPosition = getRelativePosition(imageBackgroundPositionValue);
			if( imageBackgroundPosition != null && ImageBackgroundPosition.isValidHorizontalPosition(imageBackgroundPosition)) {
				return new ImageBackgroundPosition(imageBackgroundPosition);
			} else {
				imageBackgroundPosition = (Dimension)DimensionPropertyParser.getInstance().parse(backgroundImagePositionProp);
				return new ImageBackgroundPosition(imageBackgroundPosition);
			}
		} else if (result instanceof String[]) {
			Dimension horizontalPosition = ImageBackgroundPosition.CENTER;
			Dimension verticalPosition = ImageBackgroundPosition.CENTER;
			
			String[] imageBackgroundPositionValues = (String[]) result;
			if (imageBackgroundPositionValues.length == 2) {
				for (int index = 0; index < imageBackgroundPositionValues.length; index++) {
					String imageBackgroundPositionValue = imageBackgroundPositionValues[index];
					Dimension imageBackgroundPosition = getRelativePosition(imageBackgroundPositionValue);
					if (imageBackgroundPosition != null) {
						if (index == 0
								&& ImageBackgroundPosition
										.isValidHorizontalPosition(imageBackgroundPosition)) {
							horizontalPosition = imageBackgroundPosition;
						} else if (index == 1
								&& ImageBackgroundPosition
										.isValidVerticalPosition(imageBackgroundPosition)) {
							verticalPosition = imageBackgroundPosition;
						}
					} else {
						imageBackgroundPosition = (Dimension)DimensionPropertyParser.getInstance().parse(backgroundImagePositionProp);
						if (index == 0) {
							horizontalPosition = imageBackgroundPosition;
						} else if (index == 1) {
							verticalPosition = imageBackgroundPosition;
						}
					}
				}
				
				return new ImageBackgroundPosition(horizontalPosition, verticalPosition);
			} else {
				throw new CssSyntaxError(
						"must be 1 or two dimensions or values",
						backgroundImagePositionProp);
			}
		} else {
			return new ImageBackgroundPosition();
		}
	}

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
