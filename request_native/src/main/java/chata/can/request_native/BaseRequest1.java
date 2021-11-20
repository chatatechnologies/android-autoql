package chata.can.request_native;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseRequest1
{
	public void getBaseUrl() {

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Handler handler = new Handler(Looper.getMainLooper());

		executor.execute(() -> {
			try {

				String url = "https://carlos-buruel-ortiz.000webhostapp.com/mensaje.json";
				URL oUrl = new URL(url);

				HttpURLConnection connection = (HttpURLConnection) oUrl.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoOutput(true);

				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.flush();

				String line;
				BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

				while ((line = reader.readLine()) != null) {
					System.out.println("line ->" + line);
				}
				writer.close();
				reader.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		});
	}
}
