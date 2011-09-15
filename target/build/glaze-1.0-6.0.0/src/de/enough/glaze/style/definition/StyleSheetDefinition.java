package de.enough.glaze.style.definition;

/**
 * A directory of definition collections describing a stylesheet
 * 
 * @author Andre
 * 
 */
public class StyleSheetDefinition {

	/**
	 * the background definitions
	 */
	private DefinitionCollection backgroundDefinitions;

	/**
	 * the border definitions
	 */
	private DefinitionCollection borderDefinitions;

	/**
	 * the font definitions
	 */
	private DefinitionCollection fontDefinitions;

	/**
	 * the style definitions
	 */
	private DefinitionCollection styleDefinitions;

	/**
	 * Creates a new {@link StyleSheetDefinition} instance
	 */
	public StyleSheetDefinition() {
		this.backgroundDefinitions = new DefinitionCollection();
		this.borderDefinitions = new DefinitionCollection();
		this.fontDefinitions = new DefinitionCollection();
		this.styleDefinitions = new DefinitionCollection();
	}

	/**
	 * Returns the background definitions
	 * 
	 * @return the background definitions
	 */
	public DefinitionCollection getBackgroundDefinitions() {
		return this.backgroundDefinitions;
	}

	public Definition getBackgroundDefinition(String id) {
		return this.backgroundDefinitions.getDefinition(id);
	}

	/**
	 * Returns the border definitions
	 * 
	 * @return the border definitions
	 */
	public DefinitionCollection getBorderDefinitions() {
		return this.borderDefinitions;
	}

	public Definition getBorderDefinition(String id) {
		return this.borderDefinitions.getDefinition(id);
	}

	/**
	 * Returns the font definitions
	 * 
	 * @return the font definitions
	 */
	public DefinitionCollection getFontDefinitions() {
		return this.fontDefinitions;
	}

	public Definition getFontDefinition(String id) {
		return this.fontDefinitions.getDefinition(id);
	}

	/**
	 * Returns the style definitions
	 * 
	 * @return the style definitions
	 */
	public DefinitionCollection getStyleDefinitions() {
		return this.styleDefinitions;
	}

	public Definition getStyleDefinition(String id) {
		return this.styleDefinitions.getDefinition(id);
	}

	/**
	 * Clear all definitions.
	 */
	public void clear() {
		this.backgroundDefinitions.clear();
		this.borderDefinitions.clear();
		this.fontDefinitions.clear();
		this.styleDefinitions.clear();
	}
}
