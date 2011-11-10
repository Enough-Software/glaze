package de.enough.glaze.style;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

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
	 * The interval to automatically update the stylesheet.
	 */
	private static final int AUTOUPDATE_DEFAULT_INTERVAL = 2 * 1000;

	/**
	 * the wait dialog
	 */
	private static Dialog waitDialog;

	static {
		waitDialog = new Dialog(DIALOG_WAIT, null, null, Dialog.GLOBAL_STATUS,
				Bitmap.getPredefinedBitmap(Bitmap.HOURGLASS));
	}

	/**
	 * the error dialog
	 */
	private Dialog errorDialog;

	/**
	 * the url
	 */
	private String url;

	/**
	 * The automatic update timer.
	 */
	private Timer autoUpdateTimer;

	/**
	 * Constructs a new {@link StyleSheetSandbox} instance
	 */
	public StyleSheetSandbox(String url) {
		this.url = url;
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
			waitDialog.show();
			StyleSheet.getInstance().update(this.url, this);
		}
	}

	/**
	 * Starts the automatic update of this sandbox.
	 */
	public synchronized void startAutoUpdate() {
		startAutoUpdate(AUTOUPDATE_DEFAULT_INTERVAL);
	}

	/**
	 * Starts the automatic update of this sandbox.
	 * 
	 * @param interval
	 *            the interval for the update
	 */
	public synchronized void startAutoUpdate(int interval) {
		synchronized (this) {
			final Url url = new Url(this.url); 
			TimerTask autoUpdateTask = new TimerTask() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see java.util.TimerTask#run()
				 */
				public void run() {
					try {
						StyleSheet.getInstance().addListener(
								StyleSheetSandbox.this);
						InputStream stream = url.openStream();
						// if the contents of the url have been modified ...
						if (url.isModified()) {
							synchronized (UiApplication.getEventLock()) {
								// show the wait dialog and update the
								// stylesheet
								StyleSheetSandbox.waitDialog.show();
								StyleSheet.getInstance().setUrl(url);
								StyleSheet.getInstance().getDefinition().clear();
								StyleSheet.getInstance().load(stream, true);
								StyleSheet.getInstance().notifyLoaded(
										url.toString());
							}
						}
					} catch (CssSyntaxError e) {
						StyleSheet.getInstance().notifySyntaxError(e);
					} catch (Exception e) {
						StyleSheet.getInstance().notifyError(e);
					}
				}
			};

			// schedule the automatic update with the given interval
			this.autoUpdateTimer = new Timer();
			this.autoUpdateTimer.schedule(autoUpdateTask, 0, interval);
		}
	}

	/**
	 * Ends the automatic update of this sandbox.
	 */
	public void endAutoUpdate() {
		synchronized (this) {
			if (this.autoUpdateTimer != null) {
				this.autoUpdateTimer.cancel();
			}
			StyleSheet.getInstance().removeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.StyleSheetListener#onLoaded(java.lang.String)
	 */
	public void onLoaded(String url) {
		synchronized (UiApplication.getEventLock()) {
			if (UiApplication.getUiApplication().getActiveScreen() == waitDialog) {
				UiApplication.getUiApplication().popScreen(waitDialog);
			}
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
			UiApplication.getUiApplication().popScreen(waitDialog);
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
			String error = e.toString() + ":" + e.getMessage();
			if (UiApplication.getUiApplication().getActiveScreen() == waitDialog) {
				UiApplication.getUiApplication().popScreen(waitDialog);
			}
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
