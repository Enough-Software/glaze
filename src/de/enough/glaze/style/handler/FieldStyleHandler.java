package de.enough.glaze.style.handler;

import java.util.Enumeration;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Margin;
import de.enough.glaze.style.Padding;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.border.GzBorder;
import de.enough.glaze.style.extension.Extension;
import de.enough.glaze.style.extension.Processor;
import de.enough.glaze.style.font.GzFont;

public class FieldStyleHandler {

	private final Field field;

	private Style style;
	
	private final XYEdges marginXYEdges;
	
	private final XYEdges paddingXYEdges;

	private int visualState = Integer.MIN_VALUE;

	public FieldStyleHandler(Field field, Style style) {
		this.field = field;
		this.marginXYEdges = new XYEdges(0,0,0,0);
		this.paddingXYEdges = new XYEdges(0,0,0,0);
		setStyle(style);
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

	protected void applyMargin(int availableWidth) {
		Margin margin = this.style.getMargin();
		margin.setXYEdges(this.marginXYEdges, availableWidth);
		this.field.setMargin(this.marginXYEdges);
	}

	protected void applyPadding(int availableWidth) {
		Padding padding = this.style.getPadding();
		padding.setXYEdges(this.paddingXYEdges, availableWidth);
		this.field.setPadding(this.paddingXYEdges);
	}

	protected void applyBackgrounds() {
		Style style;
		GzBackground background;

		style = this.style.getStyle(Field.VISUAL_STATE_NORMAL);
		background = style.getBackground();
		if (background != null) {
			this.field.setBackground(Field.VISUAL_STATE_NORMAL, background);
		}

		style = this.style.getStyle(Field.VISUAL_STATE_FOCUS);
		background = style.getBackground();
		if (background != null) {
			this.field.setBackground(Field.VISUAL_STATE_FOCUS, background);
		}

		style = this.style.getStyle(Field.VISUAL_STATE_ACTIVE);
		background = style.getBackground();
		if (background != null) {
			this.field.setBackground(Field.VISUAL_STATE_ACTIVE, background);
		}

		style = this.style.getStyle(Field.VISUAL_STATE_DISABLED);
		background = style.getBackground();
		if (background != null) {
			this.field.setBackground(Field.VISUAL_STATE_DISABLED, background);
		}

		style = this.style.getStyle(Field.VISUAL_STATE_DISABLED_FOCUS);
		background = style.getBackground();
		if (background != null) {
			this.field.setBackground(Field.VISUAL_STATE_DISABLED_FOCUS,
					background);
		}
	}

	protected void applyBorders() {
		Style style;
		GzBorder border;

		style = this.style.getStyle(Field.VISUAL_STATE_NORMAL);
		border = style.getBorder();
		if (border != null) {
			this.field.setBorder(Field.VISUAL_STATE_NORMAL, border);
		}

		style = this.style.getStyle(Field.VISUAL_STATE_FOCUS);
		border = style.getBorder();
		if (border != null) {
			this.field.setBorder(Field.VISUAL_STATE_FOCUS, border);
		}

		style = this.style.getStyle(Field.VISUAL_STATE_ACTIVE);
		border = style.getBorder();
		if (border != null) {
			this.field.setBorder(Field.VISUAL_STATE_ACTIVE, border);
		}

		style = this.style.getStyle(Field.VISUAL_STATE_DISABLED);
		border = style.getBorder();
		if (border != null) {
			this.field.setBorder(Field.VISUAL_STATE_DISABLED, border);
		}

		style = this.style.getStyle(Field.VISUAL_STATE_DISABLED_FOCUS);
		border = style.getBorder();
		if (border != null) {
			this.field.setBorder(Field.VISUAL_STATE_DISABLED_FOCUS, border);
		}
	}

	protected void applyFont() {
		GzFont font = this.style.getFont();
		if (font != null) {
			this.field.setFont(font.getFont());
		} else {
			this.field.setFont(Font.getDefault());
		}
	}

	protected void applyExtensions() {
		Enumeration extensions = this.style.getExtensions();
		while (extensions.hasMoreElements()) {
			Extension extension = (Extension) extensions.nextElement();
			Object extensionData = this.style.getExtensionData(extension);
			Processor processor = extension.getProcessor();
			processor.process(this.field, extensionData);
		}
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
}
