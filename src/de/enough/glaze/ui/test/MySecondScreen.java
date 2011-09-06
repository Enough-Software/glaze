package de.enough.glaze.ui.test;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.style.Style;
import de.enough.glaze.ui.component.LabelField;
import de.enough.glaze.ui.container.FlowFieldManager;
import de.enough.glaze.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MySecondScreen extends MainScreen implements
		FieldChangeListener {
	/**
	 * Creates a new MyScreen object
	 */
	public MySecondScreen() {
		super(Style.id("screen"));
		// Set the displayed title of the screen
		setTitle("My Second Screen");

		/*CheckboxField checkbox = new CheckboxField("test", true);
		add(checkbox, Style.id("test"), Style.id("solid_test"));

		DateField date = new DateField(null, System.currentTimeMillis(),
				DateFormat.getInstance(DateFormat.DATE_FULL));
		add(date, Style.id("test"), Style.id("solid_test"));

		try {
			Bitmap bitmap = StyleResources.getInstance().loadBitmap(
					"/img/icon.png");
			BitmapField bitmapField = new BitmapField(bitmap);
			add(bitmapField, Style.id("test"), Style.id("solid_test"));
		} catch (ContentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicEditField basicEditField = new BasicEditField();
		add(basicEditField, Style.id("test"), Style.id("solid_test"));

		GaugeField gaugeField = new GaugeField(null, 0, 100, 50, 0);
		add(gaugeField, Style.id("test"), Style.id("solid_test"));

		RadioButtonGroup group = new RadioButtonGroup();

		RadioButtonField myField = new RadioButtonField("first choice", group,
				true);
		add(myField, Style.id("test"), Style.id("solid_test"));
		myField = new RadioButtonField("first choice", group, false);
		add(myField, Style.id("test"), Style.id("solid_test"));*/
		
		//FlowFieldManager flow = new FlowFieldManager();
		//flow.
		add(new LabelField("test"), Style.id("test"));	
		
		//add(flow);
	}

	public void fieldChanged(Field field, int context) {
		UiApplication.getUiApplication().popScreen();
	}
}
