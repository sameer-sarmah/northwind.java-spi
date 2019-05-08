package api;

import java.util.Map;


import exceptions.CoreException;
import util.HttpMethod;

public interface IHttpClient {
	String request(final String url,final HttpMethod method,Map<String,String> headers ,
            Map<String,String> queryParams,final String jsonString) throws CoreException;
}
