export class Book {
    public id: number;
    public name: string;
    public author: string;
    public price: number;
    public picByte: string;   
    
    constructor( id: number,name: string,author: string,price: number,picByte: string){
        this.id=id;
        this.name=name;
        this.author=author;
        this.price=price;
        this.picByte=picByte;
    }
    
 }