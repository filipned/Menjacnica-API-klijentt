package rs.fon.manipulation;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rs.fon.domain.Valuta;
import rs.fon.util.JsonRatesCommunication;

public class AzuriranjeKursneListe {

	public static final String putanjaDoFajlaKursnaLista = "data/kursnaLista.json";

	public static LinkedList<Valuta> ucitajValute() {

		LinkedList<Valuta> valute = new LinkedList<Valuta>();

		FileReader reader;

		try {
			reader = new FileReader(putanjaDoFajlaKursnaLista);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject mJson = gson.fromJson(reader, JsonObject.class);
			JsonArray valuteJson = (JsonArray) mJson.get("valute");

			for (int i = 0; i < valuteJson.size(); i++) {
				JsonObject valutaJson = (JsonObject) valuteJson.get(i);

				Valuta v = new Valuta();
				v.setKurs(valutaJson.get("kurs").getAsDouble());
				v.setNaziv(valutaJson.get("naziv").getAsString());
				valute.add(v);
			}
			return valute;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static void upisiValute(LinkedList<Valuta> valute,
			GregorianCalendar datum) throws IOException {

		datum = new GregorianCalendar();
		String azuriranDatum = datum.get(
				GregorianCalendar.DAY_OF_MONTH)
				+ "."
				+ datum.get(
						GregorianCalendar.MONTH)
				+ "."
				+ datum.get(
						GregorianCalendar.YEAR) + ".";

		JsonArray valuteJson = new JsonArray();
		
		JsonObject azuriraneValute = new JsonObject();

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				putanjaDoFajlaKursnaLista)));
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		for (int i = 0; i < valute.size(); i++) {
			Valuta v = valute.get(i);

			JsonObject valutaJson = new JsonObject();
			valutaJson.addProperty("naziv", v.getNaziv());
			valutaJson.addProperty("kurs", v.getKurs());

			valuteJson.add(valutaJson);
			
		}
		azuriraneValute.addProperty("datum", azuriranDatum);
		azuriraneValute.add("valute", valuteJson);
		

		String azuriraneValuteString = gson.toJson(azuriraneValute);

		out.write(azuriraneValuteString);
		out.close();

	}

	public static void azurirajValute() {
		LinkedList<Valuta> ucitaneValute = ucitajValute();
		String[] valuteZaAzuriranje = new String[ucitaneValute.size()];
		GregorianCalendar datum = new GregorianCalendar();
		for (int i = 0; i < ucitaneValute.size(); i++) {
			valuteZaAzuriranje[i] = ucitaneValute.get(i).getNaziv();
			

		}
		
		LinkedList<Valuta> azuriraneValute = JsonRatesCommunication
				.vratiIzonsKurseva(valuteZaAzuriranje);
		
		try {
			
			upisiValute(azuriraneValute, datum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
