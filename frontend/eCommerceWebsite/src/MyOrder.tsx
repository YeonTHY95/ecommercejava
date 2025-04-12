import {useState, useEffect, useContext} from 'react';
import { AuthenticationContext } from './RootLayout';
import { NavLink, useNavigate } from 'react-router-dom';
import axios from 'axios';
import axiosWithCredentials,{requestInterceptor, responseInterceptor} from './axiosWithCredentials';
import { OrderInterface } from './typeDefinition';
import OrderCard from './OrderCard';

const MyOrder = () => {

    const { userInfo } = useContext(AuthenticationContext);
    const navigate = useNavigate();
    const [orderList, setOrderList] = useState<OrderInterface[]>([]);

    useEffect(() => {

        const controller = new AbortController();
        async function fetchMyCart() {
            try {
                const getMyOrderResponse = await axiosWithCredentials.get(`http://localhost:8000/api/viewOrder?userId=${userInfo.userId}&role=${userInfo.role}`, { 
                    signal: controller.signal 
                });

                if ( getMyOrderResponse.status === 200) {
                    console.log("My Cart Response is ", getMyOrderResponse.data);
                    setOrderList(getMyOrderResponse.data);
                }
                    
            }
            catch(error) {
                if(axios.isAxiosError(error)) {
                    console.log("Error response is ", error.response);
                }
                else {
                    console.log("Error is ", error);
                }
            }
        }
        fetchMyCart();

        return () => {
            controller.abort(); // Request will cancel and abort when unmounts
            axios.interceptors.request.eject(requestInterceptor);
            axios.interceptors.response.eject(responseInterceptor);
        }
    }, []);


    useEffect(() => {
        if (userInfo.isAuthenticated === false || userInfo.role === null) {
            navigate('/login');
        }
    }, [userInfo]);



  return (
    <div className='relative flex flex-col justify-center items-center border-2 border-grey-300 rounded-sm m-[10px] p-[10px] min-height-[1000px]'>
        <div className='relative w-full flex justify-center items-center'>
            <span className='text-5xl font-bold'>Pending Order</span>
            <span className='absolute right-[10px] mr-[5px] text-3xl'><NavLink to="/historicalOrders">Historical Orders</NavLink></span>
            
        </div>
        
        <div className='w-full flex flex-col justify-center items-center'>
          { orderList.length > 0 ? orderList.map((order:OrderInterface) => {
            return <OrderCard key={order.orderId} 
                        orderId={order.orderId}
            title = {order.inventory.title}
            inventoryId = {order.inventory.id}
            name= {order.inventory.name}
            price= {order.inventory.price}
            imageUrl= {order.inventory.imageUrl}
            rating= {order.inventory.rating}
            category= {order.inventory.category}
            buyer= {order.buyer}
            seller= {order.seller}
            status= {order.status}
            orderDate= {order.orderDate}
            orderQuantity= {order.quantity}
            role= {userInfo.role} 
            orderList = {orderList}
            setOrderList = {setOrderList }
            selectedColor = {order.selectedColor}
            />
            }
          ): <div className='text-3xl flex justify-center items-center font-bold mt-[10px]'>No Pending Order</div>}
        
        </div>
        
    </div>
  )
}

export default MyOrder