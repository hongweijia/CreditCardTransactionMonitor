package com.hortonworks.iot.financial.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.hortonworks.iot.financial.events.IncomingTransaction;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class TransactionEventJSONScheme implements Scheme {
		private static final long serialVersionUID = 1L;
		private static final Charset UTF8 = Charset.forName("UTF-8");

	    public List<Object> deserialize(final byte[] bytes) {
	        String eventJSONString = new String(bytes, UTF8);
	        IncomingTransaction incomingTransaction = null;
	        ObjectMapper mapper = new ObjectMapper();
	        
	        try {
				incomingTransaction = mapper.readValue(eventJSONString, IncomingTransaction.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return new Values(incomingTransaction);
	    }

	    public Fields getOutputFields() {
	        return new Fields("IncomingTransaction");
	    }
}