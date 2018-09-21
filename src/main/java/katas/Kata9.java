package katas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import model.BoxArt;
import model.InterestingMoment;
import model.MovieList;
import util.DataUtil;

/*
    Goal: Retrieve each video's id, title, middle interesting moment time, and smallest box art url
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", 5, "title", "some title", "time", new Date(), "url", "someUrl")
*/
public class Kata9 {

	private static BinaryOperator<BoxArt> smallestBoxartOperator = (b1, b2) -> {
		if ((b1.getWidth() * b1.getHeight()) < (b2.getWidth() * b2.getHeight())) {
			return b1;
		} else {
			return b2;
		}
	};

	public static List<Map> execute() {
		List<MovieList> movieLists = DataUtil.getMovieLists();

		return movieLists.stream().flatMap(ml -> ml.getVideos().stream()).map(m -> {
			Map<String, Object> movieMap = new HashMap<>();
			movieMap.put("id", m.getId());
			movieMap.put("title", m.getTitle());

			// time
			InterestingMoment interestingMoment = m.getInterestingMoments().stream()
					.filter(i -> "Middle".equals(i.getType())).findFirst().orElse(null);
			movieMap.put("time", interestingMoment.getTime());

			// url
			String urlBoxArt = m.getBoxarts().parallelStream().reduce(smallestBoxartOperator).map(BoxArt::getUrl)
					.orElse(null);
			movieMap.put("url", urlBoxArt);

			return movieMap;
		}).collect(Collectors.toList());
	}
}
