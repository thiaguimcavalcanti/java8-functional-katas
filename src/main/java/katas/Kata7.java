package katas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.BoxArt;
import model.Movie;
import model.MovieList;
import util.DataUtil;

/*
    Goal: Retrieve the id, title, and smallest box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": "url)
*/
public class Kata7 {
	public static List<Map> execute() {
		List<MovieList> movieLists = DataUtil.getMovieLists();

		return movieLists.stream().flatMap(ml -> ml.getVideos().stream()).map(movie -> {
			Map<String, Object> movieMap = new HashMap<>();
			movieMap.put("id", movie.getId());
			movieMap.put("title", movie.getTitle());
			movieMap.put("boxart", getBoxArtsUrl(movie));

			return movieMap;
		}).collect(Collectors.toList());
	}

	private static String getBoxArtsUrl(Movie m) {
		return m.getBoxarts().parallelStream().reduce((b1, b2) -> {
			if ((b1.getWidth() * b1.getHeight()) > (b2.getWidth() * b2.getHeight())) {
				return b1;
			} else {
				return b2;
			}
		}).map(BoxArt::getUrl).orElse(null);
	}
}
