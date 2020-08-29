import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedNumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.NumericUtils;

import com.google.gson.Gson;

public class IndexesCreation {
	private static Analyzer my_analyzer;
	private static IndexWriterConfig config_Restaurant;
	private static IndexWriter iwriter_Restaurant;
	private static IndexWriterConfig config_Reviews;
	private static IndexWriter iwriter_Reviews;
	private static HashMap<String, Restaurant> restaurants;
	private static HashMap<String, Review> reviews;
	
	public static void main(String[] args){
		Gson gson = new Gson();
		restaurants = new HashMap<String, Restaurant>();
		reviews = new HashMap<String, Review>();
		my_analyzer = new StandardAnalyzer();
		Path path_restaurant = Paths.get("D:/Downloads/ProjectLucene/RestaurantIndex");
		Path path_reviews = Paths.get("D:/Downloads/ProjectLucene/ReviewsIndex");
		
		try (BufferedReader reader_res = new BufferedReader(new FileReader("Restaurants.json"))) {
			String line;
			Restaurant restaurant;
			while ((line = reader_res.readLine()) != null) {
				restaurant = gson.fromJson(line, Restaurant.class);
				restaurants.put(restaurant.business_id, restaurant);
			}	
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}	
		
		try (BufferedReader reader_rev = new BufferedReader(new FileReader("Restaurant_reviews.json"))) {
			String line;
			Review review;
			while ((line = reader_rev.readLine()) != null) {
				review = gson.fromJson(line, Review.class);
				reviews.put(review.review_id, review);
			}	
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
		try (Directory my_directory = FSDirectory.open(path_restaurant)){
			config_Restaurant = new IndexWriterConfig(my_analyzer);
			iwriter_Restaurant = new IndexWriter(my_directory, config_Restaurant);
			createRestaurantIndex();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}	
		
		try (Directory my_directory = FSDirectory.open(path_reviews)){
			config_Reviews = new IndexWriterConfig(my_analyzer);
			iwriter_Reviews = new IndexWriter(my_directory, config_Reviews);
			createReviewsIndex();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}  	
	}
	
	private static void createRestaurantIndex() throws IOException {
		for (String restaurant_id : restaurants.keySet()) {
			Document doc = new Document();
			Restaurant restaurant = restaurants.get(restaurant_id);
			
			String location = restaurant.address + " " + restaurant.city;
			String stars = String.valueOf(restaurant.stars);
			Long l_stars = NumericUtils.doubleToSortableLong(restaurant.stars);
			String review_count = String.valueOf(restaurant.review_count);
			Long l_reviews_count = NumericUtils.doubleToSortableLong(restaurant.review_count);
			
			StringBuilder builder = new StringBuilder();
			for (String review_id_r : reviews.keySet()) {
				Review review = reviews.get(review_id_r);
				if (review.business_id.equals(restaurant.business_id)) {
					builder.append(review.text);
				}
			}
					
			String all_reviews = builder.toString();	
			
			doc.add(new TextField("name", restaurant.name, Field.Store.YES));
			doc.add(new TextField("location", location, Field.Store.YES));
			doc.add(new SortedNumericDocValuesField("int_stars", l_stars));
			doc.add(new TextField("stars", stars, Field.Store.YES));
			doc.add(new SortedNumericDocValuesField("int_reviews_count", l_reviews_count));
			doc.add(new TextField("reviews_count", review_count, Field.Store.YES));
			doc.add(new TextField("reviews", all_reviews, Field.Store.NO));	
			iwriter_Restaurant.addDocument(doc);	
		}	
		iwriter_Restaurant.close();
	}
	
	
	
	private static void createReviewsIndex() throws IOException {
		for (String review_id_r : reviews.keySet()) {
			Document doc = new Document();
			Review review = reviews.get(review_id_r);
			
			String useful = String.valueOf(review.useful);
			Long l_useful = NumericUtils.doubleToSortableLong(review.useful);
			long time = review.date.getTime();
			String S_date = review.date.toString();
				
			String name = restaurants.get(review.business_id).name;	
			
			doc.add(new TextField("name", name, Field.Store.YES));
			doc.add(new SortedNumericDocValuesField("int_useful", l_useful));
			doc.add(new TextField("useful", useful, Field.Store.YES));
			doc.add(new SortedNumericDocValuesField("int_date", time));
			doc.add(new TextField("review", review.text, Field.Store.YES));
			doc.add(new TextField("Date", S_date, Field.Store.YES));
				
			iwriter_Reviews.addDocument(doc);	
		}	
		iwriter_Reviews.close();
	}
}


