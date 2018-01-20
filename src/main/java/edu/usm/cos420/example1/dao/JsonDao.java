package edu.usm.cos420.example1.dao;

/* 
 *    Data Access Object that uses GSON library (from Google) for 
 *    converting objects to/from json representation.
 *    GSON performance on small datasets is good (https://blog.takipi.com/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/) 
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDao<IDType, T extends Serializable> implements GenericDao<IDType, T> {

	// collection class to read entities in & out of persistent storage
	private Map<IDType, T> entityMap = new HashMap<>();
	// filename associated with the ObjectStream
	private final String fileName;
	private final Type type;

	/**
	 * Constructor to create new JsonDao
	 *
	 * @param fileName
	 *            name of the file to be written to, filename is written to
	 *            project's current directory
	 * @param type
	 *            Type of entity that is stored in the Dao, unfortunately Gson
	 *            cannot unmarshall generics back into the correct type without this
	 *            field
	 */
	public JsonDao(String fileName, Type type) {
		this.fileName = fileName;
		this.type = type;
		readFileIntoMap();
	}

	/**
	 * Add an entity to the JsonDao
	 *
	 * @param id
	 *            id of the entity to be added with Type IDType
	 * @param entity
	 *            object of Type T to be added to the collection
	 */
	@Override
	public void add(IDType id, T entity) {
		readFileIntoMap();
		entityMap.put(id, entity);
		writeMapToFile();
	}

	/**
	 * Update an entity to the JsonDao
	 *
	 * @param id
	 *            id of the entity to be update with Type IDType
	 * @param entity
	 *            object of Type T to be updated to the collection
	 */
	@Override
	public void update(IDType id, T entity) {
		readFileIntoMap();
		entityMap.put(id, entity);
		writeMapToFile();
	}

	/**
	 * Remove an entity to the JsonDao
	 *
	 * @param id
	 *            id of the entity to be removed with Type IDType
	 */
	@Override
	public void remove(IDType id) {
		readFileIntoMap();
		entityMap.remove(id);
		writeMapToFile();
	}

	/**
	 * Find an entity in the JsonDao
	 *
	 * @param key
	 *            id of the entity to find
	 */
	@Override
	public T find(IDType key) {
		readFileIntoMap();
		return entityMap.get(key);
	}

	/**
	 * List all entities in the persisted collection
	 *
	 * @return List of all entity objects of Type T in the collection
	 */
	@Override
	public List<T> list() {
		readFileIntoMap();
		return new ArrayList<>(entityMap.values());
	}

	/**
	 * Calculates the number of Objects currently in the Object Stream
	 * 
	 * @return number of Objects currently in the Object Stream persistent storage
	 */
	public int determineNumberOfObjectsInStream() {
		Map<IDType, T> tempEntityMap = new HashMap<>();
		Gson gson = new Gson();
		try {
			JsonReader jsonReader = new JsonReader(new FileReader(new File(fileName)));
			tempEntityMap = gson.fromJson(jsonReader, type);
			if (tempEntityMap == null) {
				tempEntityMap = new HashMap<>();
			}
		} catch (FileNotFoundException e) {
			tempEntityMap = new HashMap<>();
		}
		return tempEntityMap.size();
	}

	private void readFileIntoMap() {
		Gson gson = new Gson();
		try {
			JsonReader jsonReader = new JsonReader(new FileReader(new File(fileName)));
			entityMap = gson.fromJson(jsonReader, type);
			if (entityMap == null) {
				entityMap = new HashMap<>();
			}
		} catch (FileNotFoundException e) {
			entityMap = new HashMap<>();
		}
	}

	private void writeMapToFile() {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonOutput = gson.toJson(entityMap);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));
			bufferedOutputStream.write(jsonOutput.getBytes());
			bufferedOutputStream.close();
		} catch (IOException e) {
			System.out.println("An error occurred writing back to file.");
		}
	}
}
