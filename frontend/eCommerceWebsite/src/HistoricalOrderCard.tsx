import { NavLink, useNavigate, useLocation } from 'react-router-dom';
import { useContext,useState } from 'react';
import { AuthenticationContext } from './RootLayout';
import axios from 'axios';

const HistoricalOrderCard = ({orderId,inventoryId,title, name, rating, price,imageUrl,category,orderQuantity,buyer,seller,status,orderDate,role,selectedColor}:{
        orderId:number,
        title:string,
        inventoryId:number,
        name:string,
        price:number,
        imageUrl:string,
        rating:number,
        category:string
        buyer:string,
        seller:string,
        status:string,
        selectedColor:string,
        orderDate:string,
        orderQuantity:number,
        role:string | null
    }) => {


    //["Pending Seller's Confirmation","Cancelled by Buyer","Cancelled by Seller","Confirmed by Seller","Shipped","Delivered by Seller","Received by Buyer"]
    
    return (
      <div key={inventoryId} className='flex flex-col justify-center items-center border-3 p-[10px] rounded-sm m-[10px] w-[90%]'>
            <div className='border-none rounded-sm overflow-hidden'><img src={`/${imageUrl}.jpg`} width={300} height={300} alt={category}/></div>
            <div className='text-3xl font-bold'><NavLink to={`/inventoryDetail/${inventoryId}`}>{title}</NavLink></div>
            <div>{name}</div>
            <div><span>Category : </span>{category}</div>
            <div><span>Selected Color : </span>{selectedColor}</div>
            <div><span>Rating : </span>{rating}</div>
            <div><span>Unit Price : $ </span>{price}</div>
            <div><span>Order Quantity : </span>{orderQuantity}</div>
            <div><span>Order Date : </span><span>{orderDate.split("T")[0]} </span><span>{orderDate.split("T")[1].split(".")[0]}</span></div>
            { role === "Seller" && <div><span>Buyer : </span>{buyer}</div> }
            <div><span>Seller : </span>{seller}</div>
            <span>Status : </span><span className='text-xl mr-[10px] font-bold'>{status}</span>
      </div>
    )

}

export default HistoricalOrderCard