import java.util.Date;

public class Review {
	public String review_id;
	public String user_id;
	public String business_id;
	public int stars;
	public Date date;
	public String text;
	public int useful;
	public int funny;
	public int cool;
}

// Example review:
//
//{"review_id":"v0i_UHJMo_hPBq9bxWvW4w","user_id":"bv2nCi5Qv5vroFiqKGopiw","business_id":"0W4lkclzZThpx3V65bVgig",
// "stars":5,"date":"2016-05-28","text":"Love the staff, love the meat, love the place.","useful":0,"funny":0,"cool":0}