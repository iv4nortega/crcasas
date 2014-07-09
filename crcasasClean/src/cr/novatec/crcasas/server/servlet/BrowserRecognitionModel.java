package cr.novatec.crcasas.server.servlet;

public class BrowserRecognitionModel {
	private String userAgent,browserName,browserVer;
    public BrowserRecognitionModel(String userAgent){
        this.userAgent=userAgent;
        process();
    }
    private void process(){
        browserName="unknown";
        browserVer="unknown";
        if(userAgent.contains("Chrome")){ //checking if Chrome
            String substring=userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0];
            browserName=substring.split("/")[0];
            browserVer=substring.split("/")[1];
        }
        else if(userAgent.contains("Firefox")){  //Checking if Firefox
            String substring=userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0];
            browserName=substring.split("/")[0];
            browserVer=substring.split("/")[1];
        }
        else if(userAgent.contains("MSIE")){ //Checking if Internet Explorer
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browserName=substring.split(" ")[0];
            browserVer=substring.split(" ")[1];
        }
    }
    public String getName(){
        return browserName; //returning browser name
    }
    public String getVersion(){
        return browserVer;  //returning browser version
    }
}
