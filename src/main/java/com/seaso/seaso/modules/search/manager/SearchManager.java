package com.seaso.seaso.modules.search.manager;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaso.seaso.common.exception.ManagerException;
import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.common.utils.FileUtils;
import com.seaso.seaso.modules.search.entity.TextResponse;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class SearchManager {

    private static final Logger logger = LoggerFactory.getLogger(SearchManager.class);
    private String hostUrl;

    public SearchManager(@Value("${model-server.address}") String searchServerAddress,
                         @Value("${model-server.port}") int searchServerPort) {
        // default value
        if (searchServerAddress == null)
            searchServerAddress = "localhost";
        if (searchServerPort == 0)
            searchServerPort = 5000;

        try {
            URL url = new URL("http://" + searchServerAddress + ":" + searchServerPort);
            hostUrl = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort();
            logger.info("Connect to model server at: " + hostUrl);
        } catch (MalformedURLException e) {
            logger.error("Connect to model server fail", e);
        }
    }


    public String getTextFromImage(MultipartFile image) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(hostUrl + "/predict/");

        FileBody file = new FileBody(FileUtils.multipartFileToFile(image));

        HttpEntity requestBody = MultipartEntityBuilder.create().addPart("image", file).build();
        post.setHeader("Accept", "application/json");
        post.setEntity(requestBody);

        try {
            HttpResponse response = client.execute(post);
            return parseGetTextFromImageEntity(response).getDescription();
        } catch (IOException e) {
            throw new ResourceNotFoundException("Cannot post the request", e);
        }
    }

    private TextResponse parseGetTextFromImageEntity(HttpResponse response) {
        ObjectMapper mapper = new ObjectMapper();

        String jsonResponse;
        try {
            jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
            StringReader reader = new StringReader(jsonResponse);
            int status = response.getStatusLine().getStatusCode();

            if (status < HttpStatus.OK.value() || status > HttpStatus.MULTIPLE_CHOICES.value()) {
                JsonResponseBody<?> jsonResp = mapper.readValue(reader, JsonResponseBody.getJsonResponseBodyType());
                throw new ManagerException("" + HttpStatus.valueOf(status) +
                        " - Error during parsing text from image: " + jsonResp.getMessage());
            } else {
                JsonResponseBody<TextResponse> jsonResp = mapper.readValue(reader, TextResponse.getTextResponseType());
                return jsonResp.getData();
            }
        } catch (JsonParseException e) {
            throw new ManagerException("Error when parsing JSON.", e);
        } catch (IOException e) {
            throw new ManagerException("Error when stringify HTTP entity.", e);
        }
    }
}
