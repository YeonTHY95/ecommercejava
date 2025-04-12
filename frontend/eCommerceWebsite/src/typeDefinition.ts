export interface Product {
    id: number;
    name: string;
    price: number;
    image: string;
    description?: string;
  }
  
  export interface User {
    id: number;
    name: string;
    email: string;
  }
  
  export interface LoginCredentials {
    email: string;
    password: string;
  }
  
  export interface SignUpData extends LoginCredentials {
    name: string;
    confirmPassword: string;
  }

  export interface InventoryInterface {
    id:number,
    title:string, 
    name: string, 
    price:number,
    imageUrl:string, 
    rating:number,
    category:string,
    stockQuantity:number,
    color:string[]
  }

  export interface CartInterface {
    id:number,
    title:string, 
    name: string, 
    price:number,
    imageUrl:string, 
    rating:number,
    category:string,
    quantity:number,
    colorList: string[],
    selectedColor : string
  }
  export interface OrderInterface {
    orderId:number,
    inventory:{
      title:string,
      id:number,
      name:string,
      price:number,
      imageUrl:string,
      rating:number,
      category:string
      },
    buyer:string,
    seller:string,
    status:string,
    orderDate:string,
    quantity:number,
    selectedColor : string
  }

  export interface InventoryDetailInterface {
    id : number,
    inventoryId : string,
    title : string,
    name : string,
    description : string,
    brand : string,
    category : string,
    color: string[],
    material : string[],
    hotSalesScore : number,
    weight : number,
    rating : number,
    price : number,
    quantity : number,
    dimensions : Record<string,number>
    discount : number,
    imageUrl : string,
    status : string,
    seller : number
  }