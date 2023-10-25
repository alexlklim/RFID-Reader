package com.alex.tagservice.services.impl;

import com.alex.tagservice.services.ITagService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TagService implements ITagService {
    private final RestTemplate restTemplate;
    @Value("${my.url.send-tag-to-product-service}")
    private String URL_sendTagToProductServiceUrl;

    private List<String> tagList = new ArrayList<>();

    @Override
    public List<String> getAllTags() {
        return this.tagList;
    }

    @Override
    public void addIdHexToList(String json) {
        List<String> idHexList = getIdHexOfTags(json);
        for (String idHex : idHexList){
            if (!tagList.contains(idHex)){
                this.tagList.add(idHex);
                System.out.println("tag_ADDED: "+idHex);
                sendNewTagToProductService(idHex);
            }
        }
        System.out.println(tagList);
    }

    @Override
    public void sendNewTagToProductService(String idHex) {
        String url =URL_sendTagToProductServiceUrl + idHex;
        restTemplate.getForObject(url, String.class);


    }


    @Override
    public Boolean deleteIdHexFromList(String idHex) {
        if (this.tagList.contains(idHex)){
            this.tagList.remove(idHex);
            System.out.println(this.tagList);
            return true;
        }
        System.out.println(this.tagList);
        return false;

    }

    @Override
    public void clearAllTags() {
        this.tagList.clear();
    }

    @Override
    public List<String> getIdHexOfTags(String jsonString){
        List<String> idHexesList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            if (jsonNode.isArray()) {
                for (JsonNode element : jsonNode) {
                    JsonNode dataNode = element.path("data");
                    String idHex = dataNode.path("idHex").asText();
                    idHexesList.add(idHex);
                    System.out.println("idHex: " + idHex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idHexesList;
    }

}
