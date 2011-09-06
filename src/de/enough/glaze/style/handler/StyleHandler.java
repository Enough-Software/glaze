package de.enough.glaze.style.handler;

import java.util.Enumeration;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Margin;
import de.enough.glaze.style.Padding;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.extension.Extension;
import de.enough.glaze.style.extension.Processor;
import de.enough.glaze.style.property.Visibility;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.background.ZeroBackground;
import de.enough.glaze.style.property.border.GzBorder;
import de.enough.glaze.style.property.border.ZeroBorder;
import de.enough.glaze.style.property.font.GzFont;

/**
 * A class handling the style for a given field. Instances are used in
 * {@link StyleManager}.
 * 
 * @author Andre
 * 
 */
public class StyleHandler {

	/**
	 * The field.
	 */
	private final Field field;

	/**
	 * The current style.
	 */
	private Style style;

	/**
	 * The current margin edges.
	 */
	private final XYEdges marginEdges;

	/**
	 * The current padding edges.
	 */
	private final XYEdges paddingEdges;

	/**
	 * The current visual state of the field.
	 */
	private int visualState;

	public StyleHandler(Field field, Style style) {
		this.field = field;
		this.marginEdges = new XYEdges(0, 0, 0, 0);
		this.paddingEdges = new XYEdges(0, 0, 0, 0);
		this.visualState = Integer.MIN_VALUE;
		setStyle(style);
	}

	/**
	 * Returns true if the current visual state in this handler differs from the
	 * visual state of the field.
	 * 
	 * @return true if the visual state differs otherwise false
	 */
	public boolean isVisualStateChanged() {
		return this.field.getVisualState() != this.visualState;
	}

	/**
	 * Updates the current visual state to the visual state of the field.
	 */
	public void updateVisualState() {
		this.visualState = this.field.getVisualState();
	}

	/**
	 * Returns true if the current style requires a layout update.
	 * 
	 * @return true if the current style requires a layout update otherwise
	 *         false
	 */
	public boolean layoutUpdate() {
		if (this.style != null) {
			return this.style.layoutUpdate();
		} else {
			return false;
		}
	}

	/**
	 * Returns the field.
	 * 
	 * @return the field
	 */
	public Field getField() {
		return this.field;
	}

	/**
	 * Returns the current style.
	 * 
	 * @return the current style
	 */
	public Style getStyle() {
		return this.style;
	}

	/**
	 * Sets the style to be applied to the field of this handler.
	 * 
	 * @param style
	 *            the style
	 */
	public void setStyle(Style style) {
		this.style = style;
		if (this.style != null) {
			applyBackgrounds();
			applyBorders();
			applyFont();
			applyExtensions();
			this.visualState = Integer.MIN_VALUE;
		}
	}

	/**
	 * Updates the style the style to the current visual state of the field
	 */
	public void updateStyle() {
		int visualState = this.field.getVisualState();
		if (this.style != null) {
			this.style = this.style.getStyle(visualState);
		}
	}

	/**
	 * Applies the current style to the field
	 * 
	 * @param availableWidth
	 *            the available width to use
	 */
	public void applyStyle(int availableWidth) {
		if (this.style != null) {
			applyMargin(availableWidth);
			applyPadding(availableWidth);
			applyFont();
			applyExtensions();
		}
	}

	/**
	 * Applies the margin of the current style to the field
	 * 
	 * @param availableWidth
	 *            the available width
	 */
	public void applyMargin(int availableWidth) {
		Margin margin;
		int visibility = this.style.getVisibility();
		if (visibility == Visibility.COLLAPSE) {
			margin = Margin.ZERO;
		} else {
			margin = this.style.getMargin();
		}
		margin.setEdges(this.marginEdges, availableWidth);
		this.field.setMargin(this.marginEdges);
	}

	/**
	 * Applies the padding of the current style to the field
	 * 
	 * @param availableWidth
	 *            the available width
	 */
	public void applyPadding(int availableWidth) {
		Padding padding;
		int visibility = this.style.getVisibility();
		if (visibility == Visibility.COLLAPSE) {
			padding = Padding.ZERO;
		} else if (visibility == Visibility.HIDDEN) {
			padding = this.style.getPadding();
			padding.setEdges(this.paddingEdges, availableWidth);
			GzBorder border = this.style.getBorder();
			// compensate the zero border in the padding
			this.paddingEdges.set(this.paddingEdges.top + border.getTop(),
					this.paddingEdges.right + border.getRight(),
					this.paddingEdges.bottom + border.getBottom(),
					this.paddingEdges.left + border.getLeft());
		} else {
			padding = this.style.getPadding();
			padding.setEdges(this.paddingEdges, availableWidth);
		}
		this.field.setPadding(this.paddingEdges);
	}

	/**
	 * Applies the backgrounds for each visual state
	 */
	public void applyBackgrounds() {
		applyBackground(Field.VISUAL_STATE_NORMAL);
		applyBackground(Field.VISUAL_STATE_FOCUS);
		applyBackground(Field.VISUAL_STATE_ACTIVE);
		applyBackground(Field.VISUAL_STATE_DISABLED);
		applyBackground(Field.VISUAL_STATE_DISABLED_FOCUS);
	}

	/**
	 * Applies the background for the given visual state
	 * 
	 * @param visualState
	 *            the visual state
	 */
	public void applyBackground(int visualState) {
		GzBackground background;
		Style style = this.style.getStyle(visualState);
		// if the visibility is visible ...
		if (style.getVisibility() == Visibility.VISIBLE) {
			// use the style background
			background = style.getBackground();
			// otherwise ...
		} else {
			// use the zero (none) background
			background = ZeroBackground.getInstance();
		}
		this.field.setBackground(visualState, background);
	}

	/**
	 * Applies the borders for each visual state
	 */
	public void applyBorders() {
		applyBorder(Field.VISUAL_STATE_NORMAL);
		applyBorder(Field.VISUAL_STATE_FOCUS);
		applyBorder(Field.VISUAL_STATE_ACTIVE);
		applyBorder(Field.VISUAL_STATE_DISABLED);
		applyBorder(Field.VISUAL_STATE_DISABLED_FOCUS);
	}

	/**
	 * Applies the border for the given visual state
	 * 
	 * @param visualState
	 *            the visual state
	 */
	public void applyBorder(int visualState) {
		GzBorder border;
		Style style = this.style.getStyle(visualState);
		// if the visibility is visible ...
		if (style.getVisibility() == Visibility.VISIBLE) {
			// use the style border
			border = style.getBorder();
			// otherwise ...
		} else {
			// use the zero (none) border
			border = ZeroBorder.getInstance();
		}
		this.field.setBorder(visualState, border, true);
	}

	/**
	 * Applie the font of the current style to the field
	 */
	public void applyFont() {
		if (this.style != null) {
			GzFont font = this.style.getFont();
			if (font != null) {
				this.field.setFont(font.getFont());
			} else {
				this.field.setFont(Font.getDefault());
			}
		}
	}

	/**
	 * Applies all extensions to the field
	 */
	public void applyExtensions() {
		Enumeration extensions = this.style.getExtensions();
		while (extensions.hasMoreElements()) {
			Extension extension = (Extension) extensions.nextElement();
			Object extensionData = this.style.getExtensionData(extension);
			Processor processor = extension.getProcessor();
			processor.apply(this.field, extensionData);
		}
	}

	/**
	 * Releases the style
	 */
	public void release() {
		Style style = getStyle();
		if (style != null) {
			style.release();
		}
	}
}
