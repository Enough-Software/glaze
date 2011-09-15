package de.enough.glaze.style.definition.converter.background;

import net.rim.device.api.system.Bitmap;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.StyleResources;
import de.enough.glaze.style.Url;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.UrlPropertyParser;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.background.GzBackgroundFactory;
import de.enough.glaze.style.property.background.ImageBackground;

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
		int imagePosition = ImageBackground.POSITION_CENTER;
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
				result = ValuePropertyParser.getInstance().parse(
						backgroundImagePositionProp);
				if (result instanceof String) {
					String positionValue = (String) result;
					imagePosition = getPosition(positionValue,
							backgroundImagePositionProp);
				} else if (result instanceof String[]) {
					String[] positionValues = (String[]) result;
					imagePosition = 0;
					for (int index = 0; index < positionValues.length; index++) {
						String positionValue = positionValues[index];
						int tempImagePosition = getPosition(positionValue,
								backgroundImagePositionProp);
						if ( index == 0 && tempImagePosition == ImageBackground.POSITION_CENTER ) {
							imagePosition |= ImageBackground.POSITION_V_CENTER;
						} else if ( index == 1 && tempImagePosition == ImageBackground.POSITION_CENTER ) {
							imagePosition |= ImageBackground.POSITION_H_CENTER;
						} else {
							imagePosition |= tempImagePosition;
						}
					}
				}
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

	private int getPosition(String position,
			Property backgroundImagePositionProp) throws CssSyntaxError {
		if ("top".equals(position)) {
			return ImageBackground.POSITION_TOP;
		} else if ("bottom".equals(position)) {
			return ImageBackground.POSITION_BOTTOM;
		} else if ("left".equals(position)) {
			return ImageBackground.POSITION_LEFT;
		} else if ("right".equals(position)) {
			return ImageBackground.POSITION_RIGHT;
		} else if ("center".equals(position)) {
			return ImageBackground.POSITION_CENTER;
		} else {
			throw new CssSyntaxError("invalid background position",
					backgroundImagePositionProp);
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
