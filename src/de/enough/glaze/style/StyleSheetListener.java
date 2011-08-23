package de.enough.glaze.style;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

public interface StyleSheetListener {
	public void onLoaded(String url);
	
	public void onSyntaxError(CssSyntaxError syntaxError);
	
	public void onError(Exception e);
}
