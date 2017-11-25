package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.*;


public class Main {

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String output = (new Main()).readRawDataToString();

        ItemParser parser = new ItemParser();

        ArrayList<String> dataStringArray = parser.parseRawDataIntoStringArray(output);

        ArrayList<Item> items = new ArrayList<>();
        for (String item : dataStringArray) {
            items.add(parser.parseStringIntoItem(item));
        }

        int errorCounter = 0;
        Integer milkCounter = 0;


        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if ("N/A".equals(item.getName()) || item.getPrice() == 0.0) {
                errorCounter++;
                iterator.remove();
            }
        }

        HashMap<String, Integer> names = getNameMap(items);

        for (Map.Entry<String, Integer> name : names.entrySet()) {
            HashMap<String, HashMap<Double, Integer>> namePriceMap = getNamePriceMap(items, name.getKey());
            System.out.println("==============================");
            System.out.println("Name: " + name.getKey() + " Appears: " + name.getValue() + " times");
            System.out.println("------------------------------");
            for(Map.Entry<String, HashMap<Double, Integer>> namePrice : namePriceMap.entrySet()){
                String key = namePrice.getKey();
                for(Map.Entry<Double,Integer> priceMap : namePrice.getValue().entrySet()){
                    System.out.println("Price: " + priceMap.getKey() + " Appears: " + priceMap.getValue());
                }


            }
        }
        System.out.println("------------------------------");
        System.out.println("Errors: " + errorCounter);



    }


    public static HashMap<String, HashMap<Double, Integer>> getNamePriceMap(ArrayList<Item> items, String itemName) {
        HashMap<String, HashMap<Double, Integer>> namePriceMap = new HashMap<>();
        ArrayList <Item> nameList = new ArrayList<>();
        for (Item item : items) {
            if(item.getName().equals(itemName)){
                nameList.add(item);
            }
        }
        namePriceMap.put(itemName, getPriceMap(nameList));
        return namePriceMap;
    }

    public static HashMap<Double, Integer> getPriceMap(ArrayList<Item> items) {
        HashMap<Double, Integer> map = new HashMap<>();
        for (Item item : items) {
            if (map.containsKey(item.getPrice())) {
                Integer newItemCount = map.get(item.getPrice());
                newItemCount++;
                map.put(item.getPrice(), newItemCount);
            } else {
                map.put(item.getPrice(), 1);
            }
        }
        return map;
    }

    public static HashMap<String, Integer> getNameMap(ArrayList<Item> items) {
        HashMap<String, Integer> map = new HashMap<>();
        for (Item item : items) {
            if (map.containsKey(item.getName())) {
                Integer newItemCount = map.get(item.getName());
                newItemCount++;
                map.put(item.getName(), newItemCount);
            } else {
                map.put(item.getName(), 1);
            }
        }
        return map;
    }
}
