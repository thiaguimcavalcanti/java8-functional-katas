package katas;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Movie;
import model.MovieList;
import util.DataUtil;

/*
    Goal: Create a datastructure from the given data:

    We have 2 arrays each containing lists, and videos respectively.
    Each video has a listId field indicating its parent list.
    We want to build an array of list objects, each with a name and a videos array.
    The videos array will contain the video's id and title.
    In other words we want to build the following structure:

    [
        {
            "name": "New Releases",
            "videos": [
                {
                    "id": 65432445,
                    "title": "The Chamber"
                },
                {
                    "id": 675465,
                    "title": "Fracture"
                }
            ]
        },
        {
            "name": "Thrillers",
            "videos": [
                {
                    "id": 70111470,
                    "title": "Die Hard"
                },
                {
                    "id": 654356453,
                    "title": "Bad Boys"
                }
            ]
        }
    ]

    DataSource: DataUtil.getLists(), DataUtil.getVideos()
    Output: the given datastructure
*/
public class Kata10 {
	public static List<Map> execute() {
		List<Map> lists = DataUtil.getLists();
		List<Map> videos = DataUtil.getVideos();

		// Videos by list id
		final Map<Object, List<Movie>> videosByListId = videos.stream()
				.collect(groupingBy(v -> v.get("listId"), mapping(v -> {
					Movie m = new Movie();
					m.setId((Integer) v.get("id"));
					m.setTitle((String) v.get("title"));
					return m;
				}, toList())));

		// Create movie list and fill with the videos
		Map<Object, MovieList> listById = lists.stream().collect(toMap(v -> v.get("id"), v -> {
			MovieList m = new MovieList();
			m.setName((String) v.get("name"));
			m.setVideos(videosByListId.get(v.get("id")));
			return m;
		}));

		List<Map> result = new ArrayList<>();
		result.add(listById);

		return result;
	}
}
