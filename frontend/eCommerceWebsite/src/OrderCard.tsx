import { NavLink, useNavigate, useLocation } from 'react-router-dom';
import { useContext,useState } from 'react';
// import { AuthenticationContext } from './App';
import { AuthenticationContext } from './RootLayout';
import axios from 'axios';
import { OrderInterface } from './typeDefinition';

const OrderCard = ({orderId,inventoryId,title, name, rating, price,imageUrl,category,orderQuantity,buyer,seller,status,orderDate,role, selectedColor,orderList, setOrderList}:{
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
        orderDate:string,
        orderQuantity:number,
        role:string | null,
        selectedColor:string,
        orderList : OrderInterface[],
        setOrderList : React.Dispatch<React.SetStateAction<OrderInterface[]>>
    }) => {

    const navigate = useNavigate();
    const location = useLocation();
    const { userInfo } = useContext(AuthenticationContext);
    const [ sellerUpdateOrderStatus, setSellerUpdateOrderStatus ] = useState<string>("");
  
    const cancelOrder = async () => {
        console.log("Inside cancelOrder Action");
        if (userInfo.isAuthenticated === false) {
            navigate('/login',{state:{from:location.pathname}});
        }
        else {
            console.log("Cancel Order Clicked for orderID ", orderId," ", title);
            try {
                const cancelOrderResponse = await axios.delete('http://localhost:8000/api/cancelOrder', {
                    data: {
                        orderId,
                        role,
//                         updatedStatus: "Cancel Order"
                    }
                });
                if (cancelOrderResponse.status === 200) {
                    console.log("Cancel Order Response is ", cancelOrderResponse.data);       
                    setOrderList( orderList.filter( (order: OrderInterface) => {
                        return order.orderId !== orderId
                    }))
                }
                else {
                    console.log("Status Code not 200, Cancel Order Response is ", cancelOrderResponse.data);       
                    
                }
            }
            catch(error) {
                if (axios.isAxiosError(error)) {
                    console.log("Error response is ", error.response);
                } 
                else {
                    console.log("Error is ", error);
                }
            }
        }
    }

    const updateOrderAction = async (action:string) => {
        try {
            const confirmOrderResponse = await axios.patch('http://localhost:8000/api/updateOrderStatus', {
//                 data: {
                    orderId,
                    role,
                    updatedStatus: action
//                 }
            });
            if (confirmOrderResponse.status === 200) {
                console.log("Update Order Status Response is ", confirmOrderResponse.data);       
                setOrderList( orderList.map( (order: OrderInterface) => {
                    if (order.orderId === orderId ) {
                        return {
                            orderId,
                            inventory:{
                                title,
                                id:inventoryId,
                                name,
                                price,
                                imageUrl,
                                rating,
                                category
                            },
                            buyer,
                            seller,
                            status:action,
                            selectedColor,
                            orderDate,
                            quantity: orderQuantity
                        }
                    }
                    else {
                       return order

                    }
                }))
            }
            else {
                console.log("Status Code not 200, Update Order StatusResponse is ", confirmOrderResponse.data);       
                navigate(0);
            }
        }
        catch(error) {
            if (axios.isAxiosError(error)) {
                console.log("Error response is ", error.response);
            } 
            else {
                console.log("Error is ", error);
            }
        }
    }



    //["Pending Seller's Confirmation","Cancelled by Buyer","Cancelled by Seller","Confirmed by Seller","Shipped","Delivered by Seller","Received by Buyer"]
    
    return (
      <div key={inventoryId}className='flex flex-col justify-center items-center border-3 p-[10px] rounded-sm m-[10px] w-[90%]'>
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
            {
            role === "Seller" 
                ?   (<div>
                        <span>Status : </span> 
                        <span className='text-xl mr-[10px] font-bold'>{status}</span>
                        {   
                            (status === "Pending Seller's Confirmation") ? 
                            (<button onClick={ ()=> {updateOrderAction("Confirmed by Seller")}}>Confirm</button>) 
                            : status === "Delivered by Seller" ?  <></> :
                            (<div>
                                <select className="outline-rose-500 border-lime-500" id="updateOrderStatus" name="updateOrderStatus" value={sellerUpdateOrderStatus} onChange={e => setSellerUpdateOrderStatus(e.target.value)}>
                                    <option value="">Update Order Status</option>
                                    <option value="Shipped" >Shipped</option>
                                    <option value="Delivered by Seller" >Delivered by Seller</option>
                                </select>
                                <button onClick={ ()=> {updateOrderAction(sellerUpdateOrderStatus)}} disabled={sellerUpdateOrderStatus === "" ? true : false}>Update</button>
                            </div>)
                        }
                         
                    </div>)  
                :   ( role === "Buyer" && 
                        (<div>
                            <span>Status : <span>{status}</span></span>
                            {   
                            (status === "Delivered by Seller") ? 
                            (<button onClick={ ()=> {updateOrderAction("Received by Buyer");}}>Confirm</button>) 
                            : <></>
                            }
                        </div>) )
            }
          
          <div><button onClick={cancelOrder}>Cancel Order</button></div>
      </div>
    )

}

export default OrderCard