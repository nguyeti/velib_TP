package com.example.tpvelib;

import java.io.InputStream;
import java.net.URL;

import velib.model.InfoStation;
import velib.model.ListeDesStationsVelib;
import velib.model.StationVelib;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListeStationVelibActivity extends ListActivity {

	private ListeDesStationsVelib stations;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.list); // par défaut cf. le cours
		try {
			URL url = new URL("http://www.velib.paris.fr/service/carto");
			// Paramètre donné à titre indicatif
			new ChargeurListeDesStations().execute(url);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				InputStream is = ListeStationVelibActivity.this.getAssets()
						.open("stations.xml");
				// Paramètre donné à titre indicatif
				new ChargeurListeDesStationsStream().execute(is);
			} catch (Exception e1) {
				Toast.makeText(
						this,
						"execptions : " + e.getMessage() + ", "
								+ e1.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}

		}

	}

	//
	// ChargeurListeDesStations est une classe interne et hérite de AsyncTask
	//
	// onPreExecute() --> ProgressDialog.show ...
	// doInBackground --> Lecture depuis XML
	// onPostExecute --> Un toast et affectation de la liste
	//
	private class ChargeurListeDesStations extends
			AsyncTask<URL, Void, Boolean> {
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(ListeStationVelibActivity.this,
					"Veuillez attendre...",
					"Nous chargeons les informations...");
		}

		@Override
		protected Boolean doInBackground(URL... params) {

			try {
				stations = new ListeDesStationsVelib();
				stations.chargerDepuisXML(params[0].openStream());

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// super.onPostExecute(result);
			Toast.makeText(ListeStationVelibActivity.this,
					"Chargement terminé", Toast.LENGTH_SHORT).show();
			// setListAdapter(new
			// ArrayAdapter<String>(ListeStationVelibActivity.this,
			// android.R.layout.simple_list_item_1,
			// stations.lesNomsDesStations()));
			setListAdapter(new StationAdapter(ListeStationVelibActivity.this,
					stations));
			dialog.dismiss();
		}

	}

	private class ChargeurListeDesStationsStream extends
			AsyncTask<InputStream, Void, Boolean> {
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(ListeStationVelibActivity.this,
					"Veuillez attendre...",
					"Nous chargeons les informations...");
		}

		@Override
		protected Boolean doInBackground(InputStream... params) {

			try {
				stations = new ListeDesStationsVelib();
				stations.chargerDepuisXML(params[0]);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// super.onPostExecute(result);
			Toast.makeText(ListeStationVelibActivity.this,
					"Chargement terminé", Toast.LENGTH_SHORT).show();
			// setListAdapter(new
			// ArrayAdapter<String>(ListeStationVelibActivity.this,
			// android.R.layout.simple_list_item_1,
			// stations.lesNomsDesStations()));
			setListAdapter(new StationAdapter(ListeStationVelibActivity.this,
					stations));
			dialog.dismiss();
		}

	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// à compléter en question 1-2
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		new ChargeurInfoStation().execute(stations.lireStation(l
				.getItemAtPosition(position).toString()));
	}

	private class ChargeurInfoStation extends
			AsyncTask<StationVelib, Void, Void> {
		private ProgressDialog dialog;
		private Double longitude, latitude;
		private int available, free;
		private InfoStation infoStation;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(ListeStationVelibActivity.this,
					"chargement", "Veuillez attendre..");
		}

		@Override
		protected Void doInBackground(StationVelib... params) {
			try {
				infoStation = new InfoStation(params[0]);
				longitude = params[0].getLongitude();
				latitude = params[0].getLatitude();
				available = infoStation.getAvailable();
				free = infoStation.getFree();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Intent myIntent = new Intent(ListeStationVelibActivity.this,
					InfoStationActivity.class);
			myIntent.putExtra("longitude", longitude);
			myIntent.putExtra("latitude", latitude);
			myIntent.putExtra("available", available);
			myIntent.putExtra("free", free);
			startActivity(myIntent);
			dialog.dismiss();
		}
	}

}