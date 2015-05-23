package rs.fon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.fon.domain.Valuta;

public class JsonRatesCommunication {

	private static final String appkey = "jr-ba8999934fc5a7ab64a4872fb4ed9af7";
	private static final String jsonRatesURL = "http://jsonrates.com/get/";

	public static LinkedList<Valuta> vratiIzonsKurseva(String[] valute) {
		LinkedList<Valuta> kurseviValuta = new LinkedList<Valuta>();

		for (int i = 0; i <3; i++) {
			
			String url = jsonRatesURL + "?" + "from="+valute[i] + "&to=RSD"
					+ "&apikey=" + appkey;

			String result;

			try {
				result = sendGet(url);
				Gson gson = new GsonBuilder().create();
				JsonObject valutaJson = gson.fromJson(result, JsonObject.class);

				Valuta v = new Valuta();
				v.setNaziv(valute[i]);
				v.setKurs(Double.parseDouble(valutaJson.get("rate").getAsString()));
				kurseviValuta.add(v);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return kurseviValuta;

		
	}

	private static String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		conn.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));

		boolean endReading = false;
		String response = "";

		while (!endReading) {
			String s = in.readLine();

			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}

		}
		in.close();
		
		return response.toString();
	}
}
