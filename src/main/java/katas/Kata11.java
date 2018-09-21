package katas;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Bookmark;
import model.BoxArt;
import model.Movie;
import model.MovieList;
import util.DataUtil;

/*
    Goal: Create a datastructure from the given data:

    This time we have 4 seperate arrays each containing lists, videos, boxarts, and bookmarks respectively.
    Each object has a parent id, indicating its parent.
    We want to build an array of list objects, each with a name and a videos array.
    The videos array will contain the video's id, title, bookmark time, and smallest boxart url.
    In other words we want to build the following structure:

    [
        {
            "name": "New Releases",
            "videos": [
                {
                    "id": 65432445,
                    "title": "The Chamber",
                    "time": 32432,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"
                },
                {
                    "id": 675465,
                    "title": "Fracture",
                    "time": 3534543,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"
                }
            ]
        },
        {
            "name": "Thrillers",
            "videos": [
                {
                    "id": 70111470,
                    "title": "Die Hard",
                    "time": 645243,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"
                },
                {
                    "id": 654356453,
                    "title": "Bad Boys",
                    "time": 984934,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg"
                }
            ]
        }
    ]

    DataSource: DataUtil.getLists(), DataUtil.getVideos(), DataUtil.getBoxArts(), DataUtil.getBookmarkList()
    Output: the given datastructure
*/
public class Kata11 {
	public static List<Map> execute() {
		List<Map> lists = DataUtil.getLists();
		List<Map> videos = DataUtil.getVideos();
		List<Map> boxArts = DataUtil.getBoxArts();
		List<Map> bookmarkList = DataUtil.getBookmarkList();

		// Bookmark by video id
		Map<Object, Bookmark> bookmarkByVideoId = getBookmarkByVideoId(bookmarkList);

		// Box arts by video id
		Map<Object, Optional<Map>> boxByVideoId = getBoxartsByVideoId(boxArts);

		// Videos by list id
		Map<Object, List<Movie>> moviesByListId = videos.stream()
				.collect(groupingBy(video -> video.get("listId"), mapping(video -> {
					Movie movie = new Movie();
					movie.setId((Integer) video.get("id"));
					movie.setTitle((String) video.get("title"));

					// Box art
					BoxArt boxAlert = new BoxArt();
					boxAlert.setUrl((String) boxByVideoId.get(movie.getId()).get().get("url"));
					movie.setBoxarts(Arrays.asList(boxAlert));

					// Bookmark
					movie.setBookmark(Arrays.asList(bookmarkByVideoId.get(movie.getId())));

					return movie;
				}, toList())));

		// Create movie list and fill with the videos
		Map<Object, MovieList> listById = lists.stream().collect(toMap(l -> l.get("id"), l -> {
			MovieList movieList = new MovieList();
			movieList.setName((String) l.get("name"));
			movieList.setVideos(moviesByListId.get(l.get("id")));
			return movieList;
		}));

		return Arrays.asList(listById);
	}

	private static Map<Object, Bookmark> getBookmarkByVideoId(List<Map> bookmarkList) {
		return bookmarkList.stream().collect(toMap(l -> l.get("videoId"), b -> {
			Bookmark bookmark = new Bookmark();
			bookmark.setTime(new Date((Integer) b.get("time")));
			return bookmark;
		}));
	}

	private static Map<Object, Optional<Map>> getBoxartsByVideoId(List<Map> boxArts) {
		return boxArts.stream().collect(groupingBy(b -> b.get("videoId"), reducing((b1, b2) -> {
			Integer w1 = (Integer) b1.get("width");
			Integer w2 = (Integer) b2.get("width");
			Integer h1 = (Integer) b1.get("height");
			Integer h2 = (Integer) b2.get("height");

			if ((w1 * h1) < (w2 * h2)) {
				return b1;
			} else {
				return b2;
			}
		})));
	}
}
