package com.lunatech.airports.web;
import com.lunatech.airports.dao.CountryDaoService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * Rest Controller for Suggestions
 */

@RestController
public class FuzzySearchController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CountryDaoService countryService;
    @RequestMapping("/fuzzySearch")
    public String suggestion() {
        log.info("fuzzySearch");
        return "provide parameter in this /country/* format";
    }
    /**
     * Provide suggestions for Country Name
     * @param countryParam Country Name prefix
     * @return List of countries with given prefix
     */
    @RequestMapping("/fuzzySearch/country/{countryParam}")
    public List<String> countrySuggestions(@PathVariable String countryParam, @NonNull Model model) {
        log.debug("countrySuggestions for {}", countryParam);
        List<String> suggestions = countryService.countryNamesWithPrefix(countryParam);
        log.info("countrySuggestions for {} => {}", countryParam, suggestions);
        model.addAttribute("results", suggestions);
        log.info("Returning {}", suggestions);
        return suggestions;
    }

}
