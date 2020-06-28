# AwtozamenApp
Custom keyboard auto correct with custom database

You can download APK file from app/ folder

![Screens](https://github.com/polatechno/AwtozamenApp/blob/master/app/screens.png)

 - Used Realm library for database operations.
 - Initially, I prelaoded these items to my Dictionary DB:
 So when you are trying the app, start writing with any of the below items.
 
 
 final List<DictionaryItem> programmingTerminList = Arrays.asList(
 
                new DictionaryItem("java"),                
                new DictionaryItem("kotlin"),                
                new DictionaryItem("php"),                
                new DictionaryItem("swift"),                
                new DictionaryItem("android"),                
                new DictionaryItem("ios"),                
                new DictionaryItem("css"),                
                new DictionaryItem("html"),                
                new DictionaryItem("groovy"),                
                new DictionaryItem("grails"),                
                new DictionaryItem("mysql"),                
                new DictionaryItem("python"),                
                new DictionaryItem("django"),                
                new DictionaryItem("c++")
                
        );
        
Firstly enable custom Keyboard name KeyboardPolatov. And set it as a default keyboard. You can do it from settings or directly from the app.

- If this is an auto-complete text view, then our predictions will not be shown and instead we will allow the editor to supply their own.  We only show the editor's candidates when in fullscreen mode, otherwise relying own it displaying its own UI.
- Do not display predictions / what the user is typing when they are entering a password.
- Predictions are not showing for e-mail addresses or URIs.

If you want to embed images, this is how you do it:





