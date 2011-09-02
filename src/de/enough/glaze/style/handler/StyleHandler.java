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

public class StyleHandler {

	private final Field field;

	private Style style;

	private final XYEdges marginXYEdges;

	private final XYEdges paddingXYEdges;

	private int visualState = Integer.MIN_VALUE;

	public StyleHandler(Field field, Style style) {
		this.field = field;
		this.marginXYEdges = new XYEdges(0, 0, 0, 0);
		this.paddingXYEdges = new XYEdges(0, 0, 0, 0);
		setStyle(style);
	}

	public boolean isVisualStateChanged() {
		return field.getVisualState() != this.visualState;
	}

	public void updateVisualState() {
		this.visualState = field.getVisualState();
	}

	public Field getField() {
		return this.field;
	}

	public Style getStyle() {
		return this.style;
	}
	
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

	public void updateStyle(int availableWidth) {
		int visualState = this.field.getVisualState();
		if (this.style != null) {
			this.style = this.style.getStyle(visualState);
			applyMargin(availableWidth);
			applyPadding(availableWidth);
			applyFont();
			applyExtensions();
		}
	}

	public void applyMargin(int availableWidth) {
		Margin margin;
		int visibility = this.style.getVisibility();
		if (visibility == Visibility.COLLAPSE) {
			margin = Margin.ZERO;
		} else {
			margin = this.style.getMargin();
		}
		margin.setXYEdges(this.marginXYEdges, availableWidth);
		this.field.setMargin(this.marginXYEdges);
	}

	public void applyPadding(int availableWidth) {
		Padding padding;
		int visibility = this.style.getVisibility();
		if (visibility == Visibility.COLLAPSE) {
			padding = Padding.ZERO;
		} else if (visibility == Visibility.HIDDEN) {
			padding = this.style.getPadding();
			padding.setXYEdges(this.paddingXYEdges, availableWidth);
			GzBorder border = this.style.getBorder();
			// compensate the zero border in the padding
			this.paddingXYEdges.set(this.paddingXYEdges.top + border.getTop(),
					this.paddingXYEdges.right + border.getRight(),
					this.paddingXYEdges.bottom + border.getBottom(),
					this.paddingXYEdges.left + border.getLeft());
		} else {
			padding = this.style.getPadding();
			padding.setXYEdges(this.paddingXYEdges, availableWidth);
		}
		this.field.setPadding(this.paddingXYEdges);
	}

	public void applyBackgrounds() {
		applyBackground(Field.VISUAL_STATE_NORMAL);
		applyBackground(Field.VISUAL_STATE_FOCUS);
		applyBackground(Field.VISUAL_STATE_ACTIVE);
		applyBackground(Field.VISUAL_STATE_DISABLED);
		applyBackground(Field.VISUAL_STATE_DISABLED_FOCUS);
	}

	public void applyBackground(int visualState) {
		GzBackground background;
		Style style = this.style.getStyle(visualState);
		if (style.getVisibility() == Visibility.VISIBLE) {
			background = style.getBackground();
		} else {
			background = ZeroBackground.getInstance();
		}
		this.field.setBackground(visualState, background);
	}

	public void applyBorders() {
		applyBorder(Field.VISUAL_STATE_NORMAL);
		applyBorder(Field.VISUAL_STATE_FOCUS);
		applyBorder(Field.VISUAL_STATE_ACTIVE);
		applyBorder(Field.VISUAL_STATE_DISABLED);
		applyBorder(Field.VISUAL_STATE_DISABLED_FOCUS);
	}

	public void applyBorder(int visualState) {
		GzBorder border;
		Style style = this.style.getStyle(visualState);
		if (style.getVisibility() == Visibility.VISIBLE) {
			border = style.getBorder();
		} else {
			border = ZeroBorder.getInstance();
		}
		this.field.setBorder(visualState, border, true);
	}

	public void applyFont() {
		GzFont font = this.style.getFont();
		if (font != null) {
			this.field.setFont(font.getFont());
		} else {
			this.field.setFont(Font.getDefault());
		}
	}

	public void applyExtensions() {
		Enumeration extensions = this.style.getExtensions();
		while (extensions.hasMoreElements()) {
			Extension extension = (Extension) extensions.nextElement();
			Object extensionData = this.style.getExtensionData(extension);
			Processor processor = extension.getProcessor();
			processor.process(this.field, extensionData);
		}
	}

	public void release() {
		Style style = getStyle();
		if (style != null) {
			style.release();
		}
	}
}
