public class HeroColor {
    final String img;
    final String colour;

    public HeroColor(String mycolour){
        this.colour=mycolour;
        this.img="C:\\Users\\ARS\\Documents\\2A\\INFO\\Java\\RunnerOldARS\\src\\img\\"+mycolour+".png";
    }

    public String getImg(){
        return img;}

    public String getColour(){
        return colour;
    }

}
