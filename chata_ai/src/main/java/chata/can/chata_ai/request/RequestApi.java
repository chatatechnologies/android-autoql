package chata.can.chata_ai.request;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestApi
{
	public RequestApi()
	{
		callRequest("https://carlos-buruel.000webhostapp.com/test_login.php");
		//callRequest("http://backend.chata.ai/oauth/token");
	}

	void callRequest(String urlPath)
	{
		try
		{
			URL url = new URL(urlPath);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			//connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			final JSONObject json = new JSONObject();
			json.put("scope", "read");
			json.put("grant_type", "password");
			json.put("username", "carlos@rinro.com.mx");
			json.put("password", "$Chata124");

			Thread thread = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try {
						OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
						outputStreamWriter.write(json.toString());
						outputStreamWriter.close();

						InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
						int codeResponse = connection.getResponseCode();

						if (codeResponse == 200)
						{
							BufferedReader reader = new BufferedReader(inputStreamReader);
							StringBuilder out = new StringBuilder();

							for (String line; (line = reader.readLine()) != null;)
							{
								out.append(line);
							}
							System.out.println("Code " + codeResponse);
							System.out.println("Response " + out);
						}
						else
						{
							System.out.println("Code " + codeResponse);
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			});
			thread.start();
		}
		catch (Exception ex)
		{

		}
	}
}