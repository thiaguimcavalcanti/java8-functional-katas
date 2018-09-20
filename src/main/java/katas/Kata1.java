package katas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Movie;
import util.DataUtil;

/*
    Goal: use map() to project an array of videos into an array of {id, title}-pairs
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys")
*/
public class Kata1 {
	public static List<Map> execute() {
		List<Movie> movies = DataUtil.getMovies();

		Map<Integer, String> map = movies.stream().collect(Collectors.toMap(Movie::getId, Movie::getTitle));

		return movies.stream().map(m -> {
			Map<String, Object> movieMap = new HashMap<>();
			movieMap.put("id", m.getId());
			movieMap.put("title", m.getTitle());
			return movieMap;
		}).collect(Collectors.toList());
	}
}
