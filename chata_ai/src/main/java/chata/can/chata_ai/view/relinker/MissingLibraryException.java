package chata.can.chata_ai.view.relinker;

import java.util.Arrays;

public class MissingLibraryException extends RuntimeException {
	public MissingLibraryException(final String library, final String[] wantedABIs, final String[] supportedABIs) {
		super("Could not find '" + library + "'. " +
			"Looked for: " + Arrays.toString(wantedABIs) + ", " +
			"but only found: " + Arrays.toString(supportedABIs) + ".");
	}
}