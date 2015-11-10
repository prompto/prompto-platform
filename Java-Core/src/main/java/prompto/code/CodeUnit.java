package prompto.code;

import prompto.grammar.Identifier;
import prompto.runtime.Context;
import prompto.store.IStorable;
import prompto.value.Image;
import prompto.value.Text;

public class CodeUnit {
	
	private Text name;
	private Text version;
	private Text text;
	private Image image;
	
	public Text getName() {
		return name;
	}
	
	public void setName(Text name) {
		this.name = name;
	}
	
	public Text getVersion() {
		return version;
	}
	
	public void setVersion(Text version) {
		this.version = version;
	}
	
	public Text getText() {
		return text;
	}
	
	public void setText(Text text) {
		this.text = text;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

	public void populate(Context context, IStorable storable) {
		storable.setValue(context, new Identifier("name"), name);
		storable.setValue(context, new Identifier("version"), version);
		if(text!=null)
			storable.setValue(context, new Identifier("text"), text);
		if(image!=null)
			storable.setValue(context, new Identifier("image"), image);
	}
}
