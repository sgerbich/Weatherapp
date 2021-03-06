package com.tts.demo.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.tts.demo.model.Response;
import com.tts.demo.model.ZipCode;
import com.tts.demo.repository.RequestRepository;
import com.tts.demo.repository.ZipRepository;

@Service
public class WeatherService {

    @Value("${api_key}")
    private String apiKey;

	@Autowired
	private RequestRepository rRepo;

    @Autowired
	private ZipRepository zipRepository;
	
	public List<ZipCode> getRecentSearches() {
        return zipRepository.findAll();
    }


    public Response getForecast(String zipCode) {

		ZipCode zip = new ZipCode(zipCode);
        // code to check if zip is already in database
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" +
                zipCode + "&units=imperial&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        try {
            if (zipRepository.findByZip(zipCode) == null){
                zipRepository.save(zip);
            }
            return restTemplate.getForObject(url, Response.class);
        } catch (HttpClientErrorException|ConstraintViolationException ex){
            Response response = new Response();
            response.setName("error");
            return response;


	}
	
	
	}
}