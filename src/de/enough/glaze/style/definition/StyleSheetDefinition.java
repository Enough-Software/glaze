package de.enough.glaze.style.definition;

public class StyleSheetDefinition {
	
	private DefinitionCollection backgroundDefinitions;

	private DefinitionCollection borderDefinitions;

	private DefinitionCollection fontDefinitions;

	private DefinitionCollection styleDefinitions;

	public StyleSheetDefinition() {
		this.backgroundDefinitions = new DefinitionCollection();
		this.borderDefinitions = new DefinitionCollection();
		this.fontDefinitions = new DefinitionCollection();
		this.styleDefinitions = new DefinitionCollection();
	}
	
	public DefinitionCollection getBackgroundDefinitions() {
		return this.backgroundDefinitions;
	}
	
	public DefinitionCollection getBorderDefinitions() {
		return this.borderDefinitions;
	}
	
	public DefinitionCollection getFontDefinitions() {
		return this.fontDefinitions;
	}
	
	public DefinitionCollection getStyleDefinitions() {
		return this.styleDefinitions;
	}
}
