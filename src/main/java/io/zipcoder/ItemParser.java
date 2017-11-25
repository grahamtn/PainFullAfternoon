package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ItemParser {

    public int errorCount = 0;

    public ArrayList<String> parseRawDataIntoStringArray(String rawData) {
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException {

        ArrayList<String> items = findKeyValuePairsInRawItemData(rawItem);


        String name = items.get(0);
        String price = items.get(1);
        String type = items.get(2);
        String exp = items.get(3);



        String nameRegex = "[n][a][m][e]:";
        name = name.replaceAll(nameRegex, "");
        name = fixString(name);


        String priceRegex = "[p][r][i][c][e]:";
        price = price.replaceAll(priceRegex, "");
        double priceAsDouble = fixPrice(price);


        String typeRegex = "[t][y][p][e]:";
        type = type.replaceAll(typeRegex, "");
        type = fixString(type);

        String expRegex = "[e][x][p][i][r][a][t][i][o][n]:";
        exp = exp.replaceAll(expRegex, "");
        exp = fixString(exp);

        return new Item(name, priceAsDouble, type, exp);
    }


    public String fixString(String item) {
        if (item.equals("")) {
            errorCount++;
            return "N/A";
        }
        if (item.startsWith("c")) {
            item = item.replace("0", "o");
        }
        return item.substring(0, 1).toUpperCase() + item.substring(1);

    }

    public double fixPrice(String price) {
        if (price.equals("")) {
            return 0.0;
        }
        return Double.parseDouble(price);
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem) {
        String stringPattern = "[^a-z0-9/.:]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem.toLowerCase());
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString) {
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }


}
