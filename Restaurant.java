import java.util.List;
import java.util.Map;

public class Restaurant {
	public String business_id;
	public String name;
	public String neighborhood;
	public String address;
	public String city;
	public String state;
	public String postal_code;
	public double latitude;
	public double longitude;
	public double stars;
	public int review_count;
	public int is_open;
	public Map<String, Object> attributes;
	public List<String> categories;
	public Map<String, String> hours;
}

// Example business:
//
//{"business_id": "FYWN1wneV18bWNgQjJ2GNg", "name": "Dental by Design", "neighborhood": "",
//	"address": "4855 E Warner Rd, Ste B9", "city": "Ahwatukee", "state": "AZ", "postal_code": "85044",
//	"latitude": 33.3306902, "longitude": -111.9785992, "stars": 4.0, "review_count": 22, "is_open": 1,
//	"attributes": {"AcceptsInsurance": true, "ByAppointmentOnly": true, "BusinessAcceptsCreditCards": true},
//	"categories": ["Dentists", "General Dentistry", "Health & Medical", "Oral Surgeons", "Cosmetic Dentists", "Orthodontists"],
//	"hours": {"Friday": "7:30-17:00", "Tuesday": "7:30-17:00", "Thursday": "7:30-17:00", "Monday": "7:30-17:00"}
//}