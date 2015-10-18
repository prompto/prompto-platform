package prompto.value;

import java.io.IOException;

import prompto.declaration.NativeResourceDeclaration;
import prompto.error.SyntaxError;
import prompto.runtime.Context;

public class NativeResource extends NativeInstance implements IResource {

	public NativeResource(Context context, NativeResourceDeclaration declaration) throws SyntaxError {
		super(context, declaration);
	}
	
	@Override
	public boolean isReadable() {
		return ((IResource)instance).isReadable();
	}
	
	@Override
	public boolean isWritable() {
		return ((IResource)instance).isWritable();
	}
	
	@Override
	public String readFully() throws IOException {
		return ((IResource)instance).readFully();
	}
	
	@Override
	public void writeFully(String data) throws IOException {
		((IResource)instance).writeFully(data);
	}
	
	@Override
	public void close() {
		((IResource)instance).close();
	}

}
