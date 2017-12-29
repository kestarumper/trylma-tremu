package models.Builders;

public interface Builder {
    public void setType(String type);
    public void addPawn(int x, int y, String color);
    public void addField(int x, int y, String type);
    public String getResult();
}
