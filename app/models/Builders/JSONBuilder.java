package models.Builders;

public class JSONBuilder implements Builder {
    protected StringBuilder product;
    protected String type;
    protected String fields;
    protected String pawns;
    protected boolean firstPawn;
    protected boolean firstField;

    public JSONBuilder(){
        this.product = new StringBuilder();
        this.type = "{ \"type\" : ";
        this.fields = " \"fields\" : [ ";
        this.pawns = "\"pawns\" : [ ";
        this.firstField = true;
        this.firstPawn = true;
    }

    @Override
    public void setType(String type) {
        this.type += "\"" + type + "\", ";
    }

    @Override
    public void addPawn(int x, int y, String color) {
        if(this.firstPawn){
            this.firstPawn = false;
        }
        else{
            this.pawns += ",";
        }

        this.pawns += "{ \"x\" : \"" + x + "\", " + "\"y\" : \"" + y +"\", \"color\" : \" " + color + "\" }";
    }

    @Override
    public void addField(int x, int y, String type) {
        if(this.firstField){
            this.firstField = false;
        }
        else{
            this.fields += ",";
        }

        this.fields += "{ \"x\" : \"" + x + "\", " + "\"y\" : \"" + y +"\", \"type\" : \" " + type + "\" }";
    }

    @Override
    public String getResult() {
        this.fields += "],";
        this.pawns += "]";

        this.product.append(this.type);
        this.product.append(this.fields);
        this.product.append(this.pawns);
        this.product.append("}");

        return this.product.toString();
    }
}
