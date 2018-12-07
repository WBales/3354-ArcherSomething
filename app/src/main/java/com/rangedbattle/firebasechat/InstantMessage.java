package com.rangedbattle.firebasechat;



class InstantMessage {

    private String message;
    private String author;
    static String[] badWordDictionary = new String[] {"fuck", "bitch", "shit", "cunt", "pussy", " ass ", "asshole"};

    InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public static  String badWordCheck(String message ) {

        for (int i = 0; i < badWordDictionary.length; i++) {
            String badWord = badWordDictionary[i];
            if(message.toLowerCase().contains(badWord)){
                message = message.toLowerCase().replace(badWord, "*@#$*");
            }
        }
        return message;
    }
    public InstantMessage() {
    }

    String getMessage() {return badWordCheck( message); }

    String getAuthor() {
        return author;
    }
}
