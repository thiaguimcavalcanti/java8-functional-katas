package katas;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Assert;
import org.junit.Test;

public class Kata8Test {

	@Test
	public void testExecute() {
		Assert.assertThat(Kata8.execute().size(), equalTo(4));
	}
}
