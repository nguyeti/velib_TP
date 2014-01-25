package com.example.tpvelib;
//nouveau display
import velib.model.ListeDesStationsVelib;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StationAdapter extends BaseAdapter {
	private Context ctxt;
	private ListeDesStationsVelib station;
	public StationAdapter(Context ctxt, ListeDesStationsVelib station){
		this.ctxt = ctxt;
		this.station = station;
	}
	@Override
	public int getCount() {
		return station.lesNomsDesStations().size();
	}

	@Override
	public Object getItem(int position) {
		return station.lesNomsDesStations().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	private static class CacheView{
		  public TextView nom;
		  public TextView lieu;
		  public ImageView image;
	  }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		if(convertView == null){
		LayoutInflater inflater = null;
		inflater = (LayoutInflater) ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		itemView = inflater.inflate(R.layout.liste_station, parent, false);
		CacheView cache = new CacheView();
		cache.nom = (TextView) itemView.findViewById(R.id.nomStation);
		cache.lieu = (TextView) itemView.findViewById(R.id.adresseStation);
		cache.image = (ImageView) itemView.findViewById(R.id.imageId);
		itemView.setTag(cache);
		}
		CacheView cache = (CacheView) itemView.getTag();
		cache.nom.setText(station.lireStation(getItem(position).toString()).getName());
		cache.lieu.setText(station.lireStation(getItem(position).toString()).getFullAddress());
		Boolean open = station.lireStation(getItem(position).toString()).getOpen();
		if(open){
			cache.image.setImageResource(R.drawable.icone_verte);
		}else{
			cache.image.setImageResource(R.drawable.icone_rouge);
		}
		
		return itemView;
	}
	 
	  
}
