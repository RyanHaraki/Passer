package persistence;

import org.json.JSONObject;

// CITATION: Parts of this code were adapted from JsonSerializerDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

// Represents a class that is writable ot a JSON file
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
