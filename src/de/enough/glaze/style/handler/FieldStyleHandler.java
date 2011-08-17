package de.enough.glaze.style.handler;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Margin;
import de.enough.glaze.style.Padding;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.font.GzFont;

public class FieldStyleHandler {

	private Field field;

	private Style style;

	private int visualState;

	public FieldStyleHandler(Field field) {
		this.field = field;
		updateVisualState();
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public void updateStyle(int visualState) {
		this.style = this.style.getStyle(visualState);
	}

	public void applyMargin(int availableWidth) {
		Margin margin = this.style.getMargin();
		XYEdges marginEdges = margin.toXYEdges(availableWidth);
		this.field.setMargin(marginEdges);
	}

	public void applyPadding(int availableWidth) {
		Padding padding = this.style.getPadding();
		XYEdges paddingEdges = padding.toXYEdges(availableWidth);
		this.field.setPadding(paddingEdges);
	}

	public void applyBackgrounds() {
		Style normalStyle = this.style.getStyle(Field.VISUAL_STATE_NORMAL);
		this.field.setBackground(Field.VISUAL_STATE_NORMAL,
				normalStyle.getBackground());

		Style focusStyle = this.style.getStyle(Field.VISUAL_STATE_FOCUS);
		this.field.setBackground(Field.VISUAL_STATE_FOCUS,
				focusStyle.getBackground());

		Style activeStyle = this.style.getStyle(Field.VISUAL_STATE_ACTIVE);
		this.field.setBackground(Field.VISUAL_STATE_ACTIVE,
				activeStyle.getBackground());

		Style disabledStyle = this.style.getStyle(Field.VISUAL_STATE_DISABLED);
		this.field.setBackground(Field.VISUAL_STATE_DISABLED,
				disabledStyle.getBackground());

		Style disabledFocusStyle = this.style
				.getStyle(Field.VISUAL_STATE_DISABLED_FOCUS);
		this.field.setBackground(Field.VISUAL_STATE_DISABLED,
				disabledFocusStyle.getBackground());
	}

	public void applyBorders() {
		Style normalStyle = this.style.getStyle(Field.VISUAL_STATE_NORMAL);
		this.field.setBorder(Field.VISUAL_STATE_NORMAL,
				normalStyle.getBorder());

		Style focusStyle = this.style.getStyle(Field.VISUAL_STATE_FOCUS);
		this.field.setBorder(Field.VISUAL_STATE_FOCUS,
				focusStyle.getBorder());

		Style activeStyle = this.style.getStyle(Field.VISUAL_STATE_ACTIVE);
		this.field.setBorder(Field.VISUAL_STATE_ACTIVE,
				activeStyle.getBorder());

		Style disabledStyle = this.style.getStyle(Field.VISUAL_STATE_DISABLED);
		this.field.setBorder(Field.VISUAL_STATE_DISABLED,
				disabledStyle.getBorder());

		Style disabledFocusStyle = this.style
				.getStyle(Field.VISUAL_STATE_DISABLED_FOCUS);
		this.field.setBorder(Field.VISUAL_STATE_DISABLED,
				disabledFocusStyle.getBorder());
	}

	public void applyFont() {
		GzFont font = this.style.getFont();
		if (font != null) {
			this.field.setFont(font.getFont());
		} else {
			this.field.setFont(Font.getDefault());
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
}
