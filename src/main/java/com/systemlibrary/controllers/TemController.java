package com.systemlibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systemlibrary.models.Temperature;
import com.systemlibrary.models.Weather;

@Controller
public class TemController {
	@RequestMapping(value = "/getTemparature", method = RequestMethod.GET)
	@ResponseBody()
	public Temperature getTemperature(@RequestParam(value = "zip") Long zip) {
		Temperature temperature = null;
		if (zip == 98007) {
			temperature = new Temperature("sunny", 56l, "D");
		} else if (zip == 98008) {
			temperature = new Temperature("Cloudy", 50l, "f");
		} else {
			temperature = new Temperature();
		}

		return temperature;

	}

	@RequestMapping(value = "/getHumidity", method = RequestMethod.GET)
	@ResponseBody()
	public Weather getHumidity(@RequestParam(value = "zip") Long zip) {
		Weather wa = null;
		if (zip == 98007) {
			wa = new Weather(50l, "56%", new Temperature("Cloudy", 50l, "f"));
		} else if (zip == 98008) {
			Temperature temperatur = new Temperature("Sunny", 80l, "f");
			wa = new Weather(70l, "70%", temperatur);
			wa.setHumidity("70%");

		} else {
			wa = new Weather();
		}

		return wa;

	}
	
}
