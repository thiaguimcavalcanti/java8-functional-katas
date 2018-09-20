package katas;

import model.BoxArt;
import model.Movie;
import util.DataUtil;

import java.util.List;
import java.util.function.BinaryOperator;

/*
    Goal: Retrieve the url of the largest boxart using map() and reduce()
    DataSource: DataUtil.getMovieLists()
    Output: String
*/
public class Kata6 {
    public static String execute() {
        List<Movie> movies = DataUtil.getMovies();

        BinaryOperator<BoxArt> binaryOperator = (b1, b2) -> {
        	if ((b1.getWidth() * b1.getHeight()) > (b2.getWidth() * b2.getHeight())) {
        		return b1;
        	} else {
        		return b2;
        	}
        };
        
		return movies.stream().flatMap(m -> m.getBoxarts().stream()).reduce(binaryOperator).map(BoxArt::getUrl).orElse(null);
    }
}
