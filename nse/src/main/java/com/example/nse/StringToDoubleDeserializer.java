package com.example.nse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class StringToDoubleDeserializer implements com.google.gson.JsonDeserializer<Double> {

	//@Override
	public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Double value = null;
		ObjectCodec codec = p.getCodec();
		TreeNode readTree = codec.readTree(p);
		System.out.println(readTree.toString());
		try {
			value = DecimalFormat.getNumberInstance().parse(readTree.toString()).doubleValue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Double value = null;
		System.out.println(json.getAsString());
		try {
			value = DecimalFormat.getNumberInstance().parse(json.getAsString()).doubleValue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

}
