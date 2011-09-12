package de.enough.glaze.style;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.DialogClosedListener;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public abstract class StyleSheetSandbox implements StyleSheetListener {

	/**
	 * the dialog wait message
	 */
	private static final String DIALOG_WAIT = "Please wait ...";

	/**
	 * the dialog wait message
	 */
	private static final String MENU_UPDATE = "Update styles";

	/**
	 * the wait dialog
	 */
	private Dialog waitDialog;

	/**
	 * the error dialog
	 */
	private Dialog errorDialog;

	/**
	 * the url
	 */
	private String url;

	/**
	 * Constructs a new {@link StyleSheetSandbox} instance
	 */
	public StyleSheetSandbox(String url) {
		this.url = url;
		this.waitDialog = new Dialog(DIALOG_WAIT, null, null,
				Dialog.GLOBAL_STATUS,
				Bitmap.getPredefinedBitmap(Bitmap.HOURGLASS));
	}

	/**
	 * Returns the update menu item text
	 * 
	 * @return the update menu item text
	 */
	protected String getUpdateMenuTextItem() {
		return MENU_UPDATE;
	}

	/**
	 * Creates an menu item to update the stylesheet
	 * 
	 * @return the created menu item
	 */
	public MenuItem createUpdateMenuItem() {
		MenuItem updateMenuItem = new MenuItem(getUpdateMenuTextItem(), 0, 0) {

			public void run() {
				StyleSheetSandbox.this.update();
			}

		};

		return updateMenuItem;
	}

	/**
	 * Creates the {@link Screen} used to test styles
	 * 
	 * @return the created {@link Screen}
	 */
	public abstract Screen createSandboxScreen();

	/**
	 * Loads the given url in the stylesheet
	 * 
	 * @param url
	 *            the url
	 */
	public void update() {
		synchronized (UiApplication.getEventLock()) {
			this.waitDialog.show();
			StyleSheet.getInstance().load(this.url, this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.StyleSheetListener#onLoaded(java.lang.String)
	 */
	public void onLoaded(String url) {
		synchronized (UiApplication.getEventLock()) {
			UiApplication.getUiApplication().popScreen(this.waitDialog);
			if (UiApplication.getUiApplication().getActiveScreen() == null) {
				Screen sandboxScreen = createSandboxScreen();
				UiApplication.getUiApplication().pushScreen(sandboxScreen);
			}
			StyleSheet.getInstance().removeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.StyleSheetListener#onSyntaxError(de.enough.glaze
	 * .style.parser.exception.CssSyntaxError)
	 */
	public void onSyntaxError(final CssSyntaxError syntaxError) {
		synchronized (UiApplication.getEventLock()) {
			String error = syntaxError.toString();
			String message = Log.TAG + "\n" + error;
			UiApplication.getUiApplication().popScreen(this.waitDialog);
			this.errorDialog = new Dialog(message, new String[] { "OK" }, null,
					Dialog.OK, Bitmap.getPredefinedBitmap(Bitmap.EXCLAMATION));
			this.errorDialog
					.setDialogClosedListener(new DialogClosedListener() {

						/*
						 * (non-Javadoc)
						 * 
						 * @see
						 * net.rim.device.api.ui.component.DialogClosedListener
						 * #dialogClosed(net.rim.device.api.ui.component.Dialog,
						 * int)
						 */
						public void dialogClosed(Dialog dialog, int choice) {
							if (UiApplication.getUiApplication()
									.getActiveScreen() == null) {
								System.exit(0);
							}
						}
					});
			this.errorDialog.show();
			StyleSheet.getInstance().removeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.StyleSheetListener#onError(java.lang.Exception)
	 */
	public void onError(Exception e) {
		synchronized (UiApplication.getEventLock()) {
			String error = e.getMessage();
			UiApplication.getUiApplication().popScreen(this.waitDialog);
			this.errorDialog = new Dialog(error, new String[] { "OK" }, null,
					Dialog.OK, Bitmap.getPredefinedBitmap(Bitmap.EXCLAMATION));
			this.errorDialog
					.setDialogClosedListener(new DialogClosedListener() {

						/*
						 * (non-Javadoc)
						 * 
						 * @see
						 * net.rim.device.api.ui.component.DialogClosedListener
						 * #dialogClosed(net.rim.device.api.ui.component.Dialog,
						 * int)
						 */
						public void dialogClosed(Dialog dialog, int choice) {
							if (UiApplication.getUiApplication()
									.getActiveScreen() == null) {
								System.exit(0);
							}
						}
					});
			this.errorDialog.show();
			StyleSheet.getInstance().removeListener(this);
		}
	}
}
