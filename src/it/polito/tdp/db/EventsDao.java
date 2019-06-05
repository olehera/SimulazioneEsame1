package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.model.Event;

public class EventsDao {
	
	public List<Event> listEvents(LocalDate data){
		
		String sql = "SELECT * FROM events WHERE date(reported_date) = ? ORDER BY reported_date" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setDate(1, Date.valueOf(data));
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listAllDistricts(){
		
		String sql = "SELECT DISTINCT district_id FROM events" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getInt("district_id"));
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listAllYears(){
		
		String sql = "SELECT DISTINCT year(reported_date) as anno FROM events" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getInt("anno") );
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	public LatLng centroDistretto(int anno, int id) {
		
		String sql = "SELECT AVG(geo_lon) as lon, AVG(geo_lat) as lat " + 
				     "FROM events " + 
				     "WHERE year(reported_date) = ? AND district_id = ? " + 
				     "GROUP BY district_id" ;
		
		LatLng centro = null;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			st.setInt(2, id);
			
			ResultSet res = st.executeQuery() ;
			
			if (res.next()) {
				centro = new LatLng(res.getDouble("lat"), res.getDouble("lon"));
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return centro;
	}
	
	public int centralePolizia(int anno){
		
		String sql = "SELECT district_id, COUNT(*) AS cnt FROM events " + 
				     "WHERE year(reported_date) = ? GROUP BY district_id";
		
		int centrale = 0;
		int cnt = 0;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {
				if (res.getInt("cnt") < cnt || cnt == 0) {
					centrale = res.getInt("district_id");
					cnt = res.getInt("cnt");
				}	
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return centrale;
	}
	

}
