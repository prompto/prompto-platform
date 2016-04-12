package prompto.type;

import java.lang.reflect.Type;

import prompto.runtime.Context;
import prompto.value.Image;

public class ImageType extends BinaryType { 

	static ImageType instance = new ImageType();
	
	public static ImageType instance() {
		return instance;
	}
	
	private ImageType() {
		super(Family.IMAGE);
	}

	@Override
	public Type getJavaType(Context context) {
		return Image.class;
	}
	
	@Override
	public boolean isAssignableTo(Context context, IType other) {
		return (other instanceof ImageType);
	}
	
}
