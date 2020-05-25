package com.lz.xml;

public class BookBean {
    private String id;
    private String author;
    private String name;
    private String price;
    private String year;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }
    
    @Override
    public String toString() {
        return "BookBean{" +
                       "id='" + id + '\'' +
                       ", author='" + author + '\'' +
                       ", name='" + name + '\'' +
                       ", price='" + price + '\'' +
                       ", year='" + year + '\'' +
                       '}';
    }
}
