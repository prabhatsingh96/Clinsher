package com.example.fluper.clinsher.appActivity.controller.utils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by fluper on 22/3/18.
 */

public class CountryList {

    private Country country;
      //  public static void main(String[] args) {
    public ArrayList<Country> gettingCountry(){
            // A collection to store our country object
            ArrayList<Country> countries = new ArrayList<Country> ();

            // Get ISO countries, create Country object and
            // store in the collection.
            String[] isoCountries = Locale.getISOCountries();
            for (String country : isoCountries) {
                Locale locale = new Locale("en", country);
                String iso = locale.getISO3Country();
                String code = locale.getCountry();
                String name = locale.getDisplayCountry();

                if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                    countries.add(new Country(iso, code, name));
                }
            }

            // Sort the country by their name and then display the content
            // of countries collection object.
            Collections.sort(countries, new CountryComparator());

            /*for (Country country : countries) {
                System.out.println(country);
            }*/
            return countries;
        }

        /**
         * Country pojo class.
         */
        public class Country {
            private String iso;
            private String code;
            private String name;

            public Country() {
            }

            Country(String iso, String code, String name) {
                this.iso = iso;
                this.code = code;
                this.name = name;
            }

            public String getIso() {
                return iso;
            }

            public void setIso(String iso) {
                this.iso = iso;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String toString() {
                return iso + " - " + code + " - " + name.toUpperCase();
            }
        }

        /**
         * CountryComparator class.
         */
        private static class CountryComparator implements Comparator<Country> {
            private Comparator<Object> comparator;

            CountryComparator() {
                comparator = Collator.getInstance();
            }

            public int compare(Country c1, Country c2) {
                return comparator.compare(c1.name, c2.name);
            }
        }
   }

