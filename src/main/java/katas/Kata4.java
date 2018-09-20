package katas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.BoxArt;
import model.MovieList;
import util.DataUtil;

/*
    Goal: Retrieve id, title, and a 150x200 box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": BoxArt)
*/
public class Kata4 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        return movieLists.stream().flatMap(ml -> ml.getVideos().stream()).map(m -> {
        	Map<String, Object> movieMap = new HashMap<>();
			movieMap.put("id", m.getId());
			movieMap.put("title", m.getTitle());
			
			BoxArt boxArt = m.getBoxarts().parallelStream().filter(b -> "150x200".equals(b.getDimension())).findAny().orElse(null);
			movieMap.put("boxart", boxArt);
			
			return movieMap;
        }).collect(Collectors.toList());
    }
}
