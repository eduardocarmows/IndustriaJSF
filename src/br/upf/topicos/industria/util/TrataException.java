package br.upf.topicos.industria.util;

public class TrataException {

	public static String getErrorMessage(Exception e) {
		Throwable t = e;
		while (t.getCause() != null){
			t = t.getCause();
		}
		return t.getMessage();
	}

}
