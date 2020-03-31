package chata.can.chata_ai.view.relinker;

import android.content.Context;

import java.io.File;

public class ReLinker {
	public interface LoadListener {
		void success();
		void failure(Throwable t);
	}

	public interface Logger {
		void log(String message);
	}

	public interface LibraryLoader {
		void loadLibrary(String libraryName);
		void loadPath(String libraryPath);
		String mapLibraryName(String libraryName);
		String unmapLibraryName(String mappedLibraryName);
		String[] supportedAbis();
	}

	public interface LibraryInstaller {
		void installLibrary(Context context, String[] abis, String mappedLibraryName,
		                    File destination, ReLinkerInstance logger);
	}

	public static void loadLibrary(final Context context, final String library) {
		loadLibrary(context, library, null, null);
	}

	public static void loadLibrary(final Context context,
	                               final String library,
	                               final String version) {
		loadLibrary(context, library, version, null);
	}

	public static void loadLibrary(final Context context,
	                               final String library,
	                               final LoadListener listener) {
		loadLibrary(context, library, null, listener);
	}

	public static void loadLibrary(final Context context,
	                               final String library,
	                               final String version,
	                               final ReLinker.LoadListener listener) {
		new ReLinkerInstance().loadLibrary(context, library, version, listener);
	}

	public static ReLinkerInstance force() {
		return new ReLinkerInstance().force();
	}

	public static ReLinkerInstance log(final Logger logger) {
		return new ReLinkerInstance().log(logger);
	}

	public static ReLinkerInstance recursively() {
		return new ReLinkerInstance().recursively();
	}

	private ReLinker() {}
}