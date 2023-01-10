import java.util.Arrays;

public class tasks6 {
    // 1
    public static int bell(int n){
        int[][] b = new int[n+1][n+1];
        b[0][0] = 1;

        for (int i = 1; i <= n; i++) {
            b[i][0] = b[i-1][i-1];
            for (int j = 1; j <= i; j++) {
                b[i][j] = b[i-1][j-1] + b[i][j-1];
            }
        }
        return b[n][0];
    }

    // 2.1
    public static String translateWord(String word){
        if(word.length() == 0)return "\"\"";
        String v = "aeiouyAEIOUY";
        String c = "bcdfghjklmnpqrstvwxzBCDFGHJKLMNPQRSTVWXZ";
        String allowedSymbols = c + v;
        int endIdx = word.length();
        while(allowedSymbols.indexOf(word.charAt(endIdx - 1))== -1)endIdx--;
        if(v.indexOf(word.charAt(0)) == -1){
            return word.substring(1, endIdx) + word.charAt(0) + "ay" + word.substring(endIdx);
        } else {
            return word.substring(0, endIdx) + "yay" + word.substring(endIdx);
        }
    }

    // 2.2
    public static String translateSentence(String s){
        String[] words = s.split(" ");
        for(int i = 0; i < words.length; i++){
            words[i] = translateWord(words[i]);
        }
        words[0] = words[0].substring(0, 1).toUpperCase() + words[0].substring(1).toLowerCase();
        return String.join(" ", words);
    }

    // 3
    public static boolean validColor(String r){
        if(!r.contains("rgb"))return false;
        boolean isRgba = false;
        if(r.contains("rgba")){
            isRgba = true;
            r = r.substring(4);
        } else {
            r = r.substring(3);
        }
        if(r.charAt(0) != '(' || r.charAt(r.length() - 1) != ')') return false;
        r = r.substring(1, r.length() - 1);
        String[] numbers = r.split(",");
        if(!isRgba && numbers.length != 3) return false;
        for(int i = 0; i < 3; i++){
            try{
                int num = Integer.parseInt(numbers[i]);
                if(num < 0 || num > 255) return false;
            } catch (NumberFormatException e){
                return false;
            }
        }
        if(numbers.length == 4){
            try{
                double num = Double.parseDouble(numbers[3]);
                if(num < 0 || num > 1) return false;
            } catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }

    // 4
    public static String stripUrlParams(String url, String[] ...subParamsToStrip){
        int askIdx = url.indexOf('?');
        String exc = "";
        if(askIdx == -1)return url;
        String cleanUrl = url.substring(0, askIdx);
        String[] params = url.substring(askIdx + 1).split("&");
        int excludedLength = params.length;
        int excludedPtr = 0;
        String[] excluded;
        if(subParamsToStrip.length != 0){
            excludedLength += subParamsToStrip[0].length;
            if(subParamsToStrip.length > 1) return "Error";
            excluded = new String[excludedLength];
            for(int i = 0; i < subParamsToStrip[0].length; i++){
                excluded[excludedPtr] = subParamsToStrip[0][i];
                excludedPtr++;
            }
        } else {
            excluded = new String[excludedLength];
        }
        StringBuilder answer = new StringBuilder();
        String paramWord;
        String paramVal;
        for(int i = 0; i < params.length; i++){
            paramWord = params[i].split("=")[0];
            if(Arrays.asList(excluded).contains(paramWord))continue;
            String toFind = paramWord + "=";
            int ptr = params.length - 1;
            int paramIdx = params[ptr].indexOf(toFind);
            while(ptr > -1 && paramIdx != 0){
                paramIdx = params[ptr].indexOf(toFind);
                ptr--;
            }
            paramVal = params[ptr].substring(paramIdx + toFind.length());
            if(i == 0)answer.append("?");
            else answer.append("&");
            answer.append(paramWord).append("=").append(paramVal);
            excluded[excludedPtr] = paramWord;
            excludedPtr++;
        }
        answer.insert(0, cleanUrl);
        return answer.toString();
    }

    // 5.1
    public static String findWord(String word){
        String allowed = "qwertyuiopasdfghjklzxcvbnm";
        int wordLength = word.length();
        String lowerWord = word.toLowerCase();
        int lIdx = 0; int rIdx = wordLength - 1;
        while(allowed.indexOf(lowerWord.charAt(rIdx))== -1 && rIdx > -1)rIdx--;
        while(allowed.indexOf(lowerWord.charAt(lIdx))== -1 && lIdx < wordLength)lIdx++;
        //Возвращаем слово.
        return word.substring(lIdx, rIdx + 1);
    }

    // 5.2
    public static String[] getHashTags(String seq){
        int MAX_WORDS_COUNT = 3;
        String[] words = seq.split(" ");
        int len = words.length;
        if(len == 0)return new String[0];
        if(len == 1)return new String[]{"#" + words[0].toLowerCase()};
        if(len == 2)return new String[]{"#" + words[0].toLowerCase(), "#" + words[1].toLowerCase()};
        String[] maxWords = new String[MAX_WORDS_COUNT];
        for(int i = 0; i < MAX_WORDS_COUNT; i++){
            maxWords[i] = "";
        }
        for (String word : words) {
            word = findWord(word).toLowerCase();
            if (word.length() > (maxWords[0].length() - 1)) {
                maxWords[2] = maxWords[1];
                maxWords[1] = maxWords[0];
                maxWords[0] = "#" + word;
            } else if (word.length() > (maxWords[1].length() - 1)) {
                maxWords[2] = maxWords[1];
                maxWords[1] = "#" + word;
            } else if (word.length() > (maxWords[2].length() - 1)) {
                maxWords[2] = "#" + word;
            }
        }
        return maxWords;
    }

    // 6
    public static int ulam(int n){
        if(n < 1)return 0;
        if(n == 1)return 1;
        if(n == 2)return 2;
        int[] ulamSeq = new int[n];
        ulamSeq[0] = 1;
        ulamSeq[1] = 2;
        int ulamSeqIdx = 2;
        int ulamNum = 3;
        while(ulamSeqIdx < n){
            int counter = 0;
            for(int i = 0; i < ulamSeqIdx; i++){
                for(int j = i + 1; j < ulamSeqIdx; j++){
                    if(ulamSeq[i] + ulamSeq[j] == ulamNum)counter++;
                }
            }
            if(counter == 1){
                ulamSeq[ulamSeqIdx] = ulamNum;
                ulamSeqIdx++;
            }
            ulamNum++;
        }
        return ulamSeq[n - 1];
    }

    // 7
    public static String longestNonrepeatingSubstring(String seq){
        int seqLength = seq.length();
        if(seqLength == 0)return "";
        if(seqLength == 1)return seq;
        int maxLen = 1;
        int maxIdx = 0;
        int currLen = 1;
        int currIdx = 0;
        StringBuilder usedChars = new StringBuilder();
        usedChars.append(seq.charAt(0));
        for(int i = 1; i < seqLength; i++){
            int charIdx = usedChars.indexOf(String.valueOf(seq.charAt(i)));
            if(charIdx == -1){
                currLen++;
                usedChars.append(seq.charAt(i));
            } else {
                if(currLen > maxLen){
                    maxLen = currLen;
                    maxIdx = currIdx;
                }
                currLen = 1;
                currIdx = i;
                usedChars = new StringBuilder();
                usedChars.append(seq.charAt(i));
            }
        }
        if(currLen > maxLen){
            maxLen = currLen;
            maxIdx = currIdx;
        }
        return seq.substring(maxIdx, maxIdx + maxLen);
    }

    // 8
    public static String convertToRoman(int num){
        if(num < 1 || num > 3999)return "error";
        StringBuilder answer = new StringBuilder();
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int i = 0;
        while(num > 0){
            if(num >= nums[i]){
                answer.append(romans[i]);
                num -= nums[i];
            } else i++;
        }
        return answer.toString();
    }

    // 9
    public static int solvePart(String part){
        String operations = "+-*/";
        int idx = 0;
        while(idx < part.length() && operations.indexOf(part.charAt(idx)) == -1)idx++;
        if(idx == part.length())return Integer.parseInt(part.strip());
        char operation = part.charAt(idx);
        return switch (operation) {
            case '+' -> solvePart(part.substring(0, idx)) + solvePart(part.substring(idx + 1));
            case '-' -> solvePart(part.substring(0, idx)) - solvePart(part.substring(idx + 1));
            case '*' -> solvePart(part.substring(0, idx)) * solvePart(part.substring(idx + 1));
            case '/' -> solvePart(part.substring(0, idx)) / solvePart(part.substring(idx + 1));
            default -> 0;
        };
    }

    // 10.1
    public static boolean formula(String form){
        int equalsIdx = form.indexOf("=");
        if(equalsIdx == -1)return false;
        if(form.indexOf("=", equalsIdx + 1) != -1)return false;
        String[] parts = form.split("=");
        if(parts.length != 2)return false;
        int lPart = solvePart(parts[0]);
        int rPart = solvePart(parts[1]);
        return lPart == rPart;
    }

    // 10.2
    public static boolean isPalindrome(String palindrome){
        if(palindrome.length() == 0)return false;
        if(palindrome.length() % 2 != 0)return false;
        return palindrome.substring(0, palindrome.length() / 2).equals(new StringBuilder(palindrome.substring(palindrome.length() / 2)).reverse().toString());
    }

    // 10.3
    public static boolean palindromedescendant(int digit){
        String num = String.valueOf(digit);
        if(num.length() % 2 != 0)return false;
        StringBuilder w = new StringBuilder();
        while(num.length() > 1){
            if(isPalindrome(num))return true;
            for(int i = 0; i < num.length() / 2; i++){
                int first = Character.getNumericValue(num.charAt(i * 2));
                int second = Character.getNumericValue(num.charAt(i * 2 + 1));
                w.append(first + second);
            }
            num = w.toString();
            w = new StringBuilder();
        }
        return false;
    }
    public static void main(String[] args){
        System.out.println("Задача 1");
        System.out.println("bell(1) = " + bell(1));
        System.out.println("bell(2) = " + bell(2));
        System.out.println("bell(3) = " + bell(3));
        System.out.println("Задача 2");
        System.out.println("translateWord(\"flag\") = " + translateWord("flag"));
        System.out.println("translateWord(\"Apple\") = " + translateWord("apple"));
        System.out.println("translateWord(\"button\") = " + translateWord("button"));
        System.out.println("translateWord(\"\"); = " + translateWord(""));
        System.out.println("translateSentence(\"I like to eat honey waffles.\") = " + translateSentence("I like to eat honey waffles."));
        System.out.println("Задача 3");
        System.out.println("validColor(\"rgb(0,0,0)\") = " + validColor("rgb(0,0,0)"));
        System.out.println("validColor(\"rgb(0,,0)\") = " + validColor("rgb(0,,0)"));
        System.out.println("validColor(\"rgb(255,256,255)\") = " + validColor("rgb(255,256,255)"));
        System.out.println("validColor(\"rgba(0,0,0,0.123456789)\") = " + validColor("rgba(0,0,0,0.123456789)"));
        System.out.println("Задача 4");
        System.out.println("stripUrlParams(\"https://edabit.com?a=1&b=2&a=2\") = " + stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
        System.out.println("stripUrlParams(\"https://edabit.com?a=1&b=2&a=2\" ,[\"b\"]) = " + stripUrlParams("https://edabit.com?a=1&b=2&a=2", new String[]{"b"}));
        System.out.println("stripUrlParams(\"https://edabit.com\", [\"b\"]) = " + stripUrlParams("https://edabit.com", new String[]{"b"}));
        System.out.println("Задача 5");
        System.out.println("getHashTags(\"How the Avocado Became the Fruit of the Global Trade\") = " + Arrays.toString(getHashTags("How the Avocado Became the Fruit of the Global Trade")));
        System.out.println("getHashTags(\"Why You Will Probably Pay More for Your Christmas Tree This Year\") = " + Arrays.toString(getHashTags("Why You Will Probably Pay More for Your Christmas Tree This Year")));
        System.out.println("getHashTags(\"Hey Parents, Surprise, Fruit Juice Is Not Fruit\") = " + Arrays.toString(getHashTags("Hey Parents, Surprise, Fruit Juice Is Not Fruit")));
        System.out.println("getHashTags(\"Visualizing Science\") = " + Arrays.toString(getHashTags("Visualizing Science")));
        System.out.println("Задача 6");
        System.out.println("ulam(4) = " + ulam(4));
        System.out.println("ulam(9) = " + ulam(9));
        System.out.println("ulam(206) = " + ulam(206));
        System.out.println("Задача 7");
        System.out.println("longestNonrepeatingSubstring(\"abcabcbb\") = " + longestNonrepeatingSubstring("abcabcbb"));
        System.out.println("longestNonrepeatingSubstring(\"aaaaaa\") = " + longestNonrepeatingSubstring("aaaaaa"));
        System.out.println("longestNonrepeatingSubstring(\"abcde\") = " + longestNonrepeatingSubstring("abcde"));
        System.out.println("longestNonrepeatingSubstring(\"abcda\") = " + longestNonrepeatingSubstring("abcda"));
        System.out.println("Задача 8");
        System.out.println("convertToRoman(2) = " + convertToRoman(2));
        System.out.println("convertToRoman(12) = " + convertToRoman(12));
        System.out.println("convertToRoman(16) = " + convertToRoman(16));
        System.out.println("Задача 9");
        System.out.println("formula(\"6 * 4 = 24\") = " + formula("6 * 4 = 24"));
        System.out.println("formula(\"18 / 17 = 2\") = " + formula("18 / 17 = 2"));
        System.out.println("formula(\"16 * 10 = 160 = 14 + 120\") = " + formula("16 * 10 = 160 = 14 + 120"));
        System.out.println("Задача 10");
        System.out.println("palindromedescendant(11211230) = " + palindromedescendant(11211230));
        System.out.println("palindromedescendant(13001120) = " + palindromedescendant(13001120));
        System.out.println("palindromedescendant(23336014) = " + palindromedescendant(23336014));
        System.out.println("palindromedescendant(11) = " + palindromedescendant(1211));
    }
}
