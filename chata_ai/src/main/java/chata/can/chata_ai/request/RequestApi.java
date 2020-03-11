package chata.can.chata_ai.request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestApi
{
	public RequestApi()
	{
		//callRequest("https://carlos-buruel.000webhostapp.com/test_login.php");
		callRequest("https://backend.chata.ai/oauth/token");
	}

	void callRequest(String urlPath)
	{
		try
		{
			URL url = new URL(urlPath);
			final HttpURLConnection newConnection = openConnection(url);
			setRequestProperty(newConnection);
			newConnection.setRequestMethod("POST");
			new Thread(new Runnable() {
				@Override
				public void run()
				{
					try
					{
						addBody(newConnection);
						int responseCode = newConnection.getResponseCode();

						InputStreamReader inputStreamReader = new InputStreamReader(newConnection.getInputStream());
						BufferedReader reader = new BufferedReader(inputStreamReader);
						StringBuilder out = new StringBuilder();

						for (String line; (line = reader.readLine() )!= null;)
						{
							out.append(line);
						}
						System.out.println(out.toString());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						newConnection.disconnect();
					}
				}
			}).start();
		}
		catch(MalformedURLException ex)
		{
			ex.printStackTrace();
		}
		catch(ProtocolException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void setRequestProperty(HttpURLConnection connection)
	{
		Map<String, String> map = new HashMap<>();
		map.put("Authorization", "Basic Y2hhdGE6Y2hhdGE=");

		for (String headerName: map.keySet())
		{
			connection.setRequestProperty(headerName, map.get(headerName));
		}
	}

	private String DEFAULT_PARAMS_ENCODING = "UTF-8";
	private byte[] buildParams()
	{
		Map<String, String> params = new HashMap<>();
		params.put("scope", "read");
		params.put("grant_type", "password");
		params.put("username", "carlos@rinro.com.mx");
		params.put("password", "$Chata124");

		//method encodeParameters
		StringBuilder encodedParams = new StringBuilder();
		try
		{
			for (Map.Entry<String, String> entry: params.entrySet())
			{
				if (entry.getKey() == null || entry.getValue() == null)
				{
					throw new IllegalArgumentException(
						String.format(
							"Request#getParams() or Request#getPostParams() returned a map "
								+ "containing a null key or value: (%s, %s). All keys "
								+ "and values must be non-null.",
							entry.getKey(), entry.getValue()));
				}
				encodedParams.append(URLEncoder.encode(entry.getKey(), DEFAULT_PARAMS_ENCODING));
				encodedParams.append('=');
				encodedParams.append(URLEncoder.encode(entry.getValue(), DEFAULT_PARAMS_ENCODING));
				encodedParams.append('&');
			}
			return encodedParams.toString().getBytes(DEFAULT_PARAMS_ENCODING);
		}
		catch (UnsupportedEncodingException uee)
		{
			throw new RuntimeException("Encoding not supported: " + DEFAULT_PARAMS_ENCODING, uee);
		}
	}

	String HEADER_CONTENT_TYPE = "Content-Type";
	private void addBody(HttpURLConnection connection) throws  IOException
	{
		byte[] body = buildParams();
		connection.setDoOutput(true);

		if (!connection.getRequestProperties().containsKey(HEADER_CONTENT_TYPE))
		{
			connection.setRequestProperty(
				HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
		}
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.write(body);
		out.close();
	}

	private HttpURLConnection openConnection(URL url) throws IOException
	{
		HttpURLConnection connection = createConnection(url);
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		return connection;
	}

	private HttpURLConnection createConnection(URL url) throws IOException
	{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setInstanceFollowRedirects(true);
		return connection;
	}
}