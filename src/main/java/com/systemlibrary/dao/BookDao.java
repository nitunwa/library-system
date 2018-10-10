package com.systemlibrary.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.systemlibrary.models.Book;

public class BookDao {
	public List<Book> getBookList() {

		List<Book> bookList1 = Arrays.asList(new Book(1l, null, "Math"), new Book(2l, "Math G1", "Math"),
				new Book(3l, "Math G2", "Math"), new Book(4l, "Math G3", "Math"), new Book(5l, "skoopy", "English"),
				new Book(6l, "skoopy", "English"), new Book(7l, "Cinderla", null));
		return bookList1;
	}

	// variable argument example
	private static void printString(String... strList) {
		for (String str : strList) {
			System.out.println(str);
		}

	}

	public static void main(String[] arg) {
		BookDao bDao = new BookDao();
		List<Book> bList = bDao.getBookList();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Book book : bList) {
			if (map.containsKey(book.getCategory())) {
				int count = map.get(book.getCategory()) + 1;
				map.put(book.getCategory(), count);
				
			}
			else{
			map.put(book.getCategory(), 1);
			}
		}
		
		Set<Map.Entry<String ,Integer>> entrySet = map.entrySet();
		for(Map.Entry<String ,Integer> row :entrySet){
			String st = row.getKey();
			int count = row.getValue();
			System.out.println(st + " :"+ count);
		}
		
		/*Set<String> set = map.keySet();
		for(String catergory : set){
			int count = map.get(catergory) ;
			System.out.println(catergory + ":" + count );
		}*/

	}
}
