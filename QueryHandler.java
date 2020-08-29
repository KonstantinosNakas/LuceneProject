import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.swing.text.BadLocationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortedNumericSortField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class QueryHandler{
	
	private ResultsPanel results = new ResultsPanel();
	private Sort sorter;
	private static Analyzer my_analyzer = new StandardAnalyzer();;
	private static Path path_restaurant = Paths.get("D:/Downloads/ProjectLucene/RestaurantIndex");
	private static Path path_reviews = Paths.get("D:/Downloads/ProjectLucene/ReviewsIndex");
	int RESULTS_SIZE = 300;
	private static HashMap<String, Document> repres_restaurants;
	private static HashMap<String, Document> repres_reviews;
	private String repres_button;
	
	
	public void RestaurantsQueryFull(String my_Query, String type) throws ParseException, BadLocationException{
		try (Directory my_directory = FSDirectory.open(path_restaurant)){
			int m = 0;
			String text;
			DirectoryReader ireader = DirectoryReader.open(my_directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);
			QueryParser parser = null;
			MultiFieldQueryParser parser1 = null;
			ScoreDoc[] hits;
			if (type.equals("location")) {
				parser = new QueryParser("location", my_analyzer);
				Query query = parser.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE, sorter).scoreDocs;
			}else if (type.equals("reviews")) {
				parser = new QueryParser("reviews", my_analyzer);
				Query query = parser.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE, sorter).scoreDocs;	
			}else{
				String [] fields = {"location","reviews"};
				parser1 = new MultiFieldQueryParser(fields, my_analyzer) ;
				Query query = parser1.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE, sorter).scoreDocs;
			}	
			results.clearText();
			for (int i= 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				m++;
				String num = String.valueOf(m);
				text = num + ") The restaurant: " + hitDoc.get("name") + "		is in location: " + hitDoc.get("location") + "		with " + hitDoc.get("stars") + " stars" + "		and " + hitDoc.get("reviews_count") + " reviews" + "\n";
				results.addText(text);
			}
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		} 
	}


	
	public void ReviewsQueryFull(String my_Query, String type) throws ParseException, BadLocationException {
		try (Directory my_directory = FSDirectory.open(path_reviews)){
			int m = 0;
			String text;
			DirectoryReader ireader = DirectoryReader.open(my_directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);
			QueryParser parser = null;
			MultiFieldQueryParser parser1 = null;
			ScoreDoc[] hits;
			if (type.equals("name")) {
				parser = new QueryParser("name", my_analyzer);
				Query query = parser.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE, sorter).scoreDocs;	
			}else if (type.equals("review")) {
				parser = new QueryParser("review", my_analyzer);
				Query query = parser.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE, sorter).scoreDocs;	
			}else{
				String [] fields = {"name","review"};
				parser1 = new MultiFieldQueryParser(fields, my_analyzer) ;
				Query query = parser1.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE, sorter).scoreDocs;
			}			
			results.clearText();
			for (int i= 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				m++;
				String num = String.valueOf(m);
				text = num + ") The review: " + hitDoc.get("review") +  "		of the restaurant: " + hitDoc.get("name") + "		has usefull metric: " + hitDoc.get("useful") + "		and has written in: " +  hitDoc.get("Date") + "\n";
				results.addText(text);
			}
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}  
	}
	
	
	
	public void RestaurantsQueryRepresentative(String my_Query, String type) throws BadLocationException, ParseException {
		try (Directory my_directory = FSDirectory.open(path_restaurant)){
			repres_restaurants = new HashMap<String, Document>();
			int m = 0;
			String text;
			DirectoryReader ireader= DirectoryReader.open(my_directory);
			IndexSearcher isearcher= new IndexSearcher(ireader);
			QueryParser parser = null;
			MultiFieldQueryParser parser1 = null;
			ScoreDoc[] hits;
			if (type.equals("location")) {
				parser = new QueryParser("location", my_analyzer);
				Query query = parser.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE).scoreDocs;
			}else if (type.equals("reviews")) {
				parser = new QueryParser("reviews", my_analyzer);
				Query query = parser.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE).scoreDocs;
			}else{
				String [] fields = {"location","reviews"};
				parser1 = new MultiFieldQueryParser(fields, my_analyzer) ;
				Query query = parser1.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE).scoreDocs;
			}	
			for (int i= 0; i < hits.length; i++) {
				if (repres_button.equals("RestaurantsByStars")) {
					repres_restaurants.put(isearcher.doc(hits[i].doc).get("stars"), isearcher.doc(hits[i].doc));
				}else if(repres_button.equals("RestaurantsByReviews")) {
					repres_restaurants.put(isearcher.doc(hits[i].doc).get("reviews_count"), isearcher.doc(hits[i].doc));
				}else {
					System.out.println("Epelekse ena radio button.");
				}
			}	
			results.clearText();
			for (String key : repres_restaurants.keySet()) {
				Document hitDoc = repres_restaurants.get(key);
				m++;
				String num = String.valueOf(m);
				text = num + ") The restaurant: " + hitDoc.get("name") + "		is in location: " + hitDoc.get("location") + "		with " + hitDoc.get("stars") + " stars" + "		and " + hitDoc.get("reviews_count") + " reviews" + "\n";
				results.addText(text);	
			}	
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	
	public void ReviewsQueryRepresentative(String my_Query,String type) throws BadLocationException, ParseException  {
		try (Directory my_directory = FSDirectory.open(path_reviews)){
			repres_reviews = new HashMap<String, Document>();
			int m = 0;
			String text;
			DirectoryReader ireader= DirectoryReader.open(my_directory);
			IndexSearcher isearcher= new IndexSearcher(ireader);
			QueryParser parser = null;
			MultiFieldQueryParser parser1 = null;
			ScoreDoc[] hits;
			if (type.equals("name")) {
				parser = new QueryParser("name", my_analyzer);
				Query query = parser.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE).scoreDocs;
			}else if (type.equals("review")) {
				parser = new QueryParser("review", my_analyzer);
				Query query = parser.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE).scoreDocs;
			}else{
				String [] fields = {"name","review"};
				parser1 = new MultiFieldQueryParser(fields, my_analyzer) ;
				Query query = parser1.parse(my_Query);
				hits = isearcher.search(query, RESULTS_SIZE).scoreDocs;
			}	
			for (int i= 0; i < hits.length; i++) {
				if (repres_button.equals("ReviewsByUsefull")) {
					repres_reviews.put(isearcher.doc(hits[i].doc).get("useful"), isearcher.doc(hits[i].doc));
				}else if(repres_button.equals("ReviewsByDate")) {
					repres_reviews.put(isearcher.doc(hits[i].doc).get("Date"), isearcher.doc(hits[i].doc));
				}else {
					System.out.println("Epelekse ena radio button.");
				}
			}	
			results.clearText();
			for (String key : repres_reviews.keySet()) {
				Document hitDoc = repres_reviews.get(key);
				m++;
				String num = String.valueOf(m);
				text = num + ") The review: " + hitDoc.get("review") +  "		of the restaurant: " + hitDoc.get("name") + "		has usefull metric: " + hitDoc.get("useful") + "		and has written in: " +  hitDoc.get("Date") + "\n";
				results.addText(text);	
			}	
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}  
	}
	
	
	public void DetermineTheSortType(String sortType) {
		sorter = new Sort();	
		if(sortType.equals("RestaurantsByStars")) {
			SortField sortField = new SortedNumericSortField("int_stars",SortField.Type.LONG,true);
			sorter.setSort(sortField, SortField.FIELD_SCORE);
		}else if(sortType.equals("RestaurantsByReviews")) {
			SortField sortField = new SortedNumericSortField("int_reviews_count",SortField.Type.LONG,true);
			sorter.setSort(sortField, SortField.FIELD_SCORE);
		}else if(sortType.equals("ReviewsByUsefull")) {
			SortField sortField = new SortedNumericSortField("int_useful",SortField.Type.LONG,true);
			sorter.setSort(sortField, SortField.FIELD_SCORE);
		}else if(sortType.equals("ReviewsByDate")) {
			SortField sortField = new SortedNumericSortField("int_date",SortField.Type.LONG,true);
			sorter.setSort(sortField, SortField.FIELD_SCORE);
		}
	
	}
	
	public void DetermineTheReprType(String sortType) {
		repres_button = sortType;
	}
	
}
