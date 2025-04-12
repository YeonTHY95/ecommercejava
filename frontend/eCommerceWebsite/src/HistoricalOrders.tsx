import {useState, useEffect, useContext} from 'react';
import { AuthenticationContext } from './RootLayout';
import { useNavigate, NavLink } from 'react-router-dom';
import axios from 'axios';
import axiosWithCredentials,{requestInterceptor, responseInterceptor} from './axiosWithCredentials';
import { OrderInterface } from './typeDefinition';
import HistoricalOrderCard from './HistoricalOrderCard';
import PaginationComponent from './PaginationComponent';

const HistoricalOrders = () => {

    const { userInfo } = useContext(AuthenticationContext);
    const navigate = useNavigate();
    const [historicalOrderList, setHistoricalOrderList] = useState<OrderInterface[]>([]);

    useEffect(() => {

        const controller = new AbortController();
        async function fetchHistoricalCart() {
            try {
                const getMyOrderResponse = await axiosWithCredentials.get(`http://localhost:8000/api/viewHistoricalOrder?userId=${userInfo.userId}&role=${userInfo.role}`, { 
                    signal: controller.signal 
                });

                if ( getMyOrderResponse.status === 200) {
                    console.log("My Cart Response is ", getMyOrderResponse.data);
                    // const sortedByDateHistoricalOrderList = getMyOrderResponse.data.sort((a:OrderInterface,b:OrderInterface)=> {return b.orderDate.getTime - Number(a.orderDate)});
                    // console.log("sortedByDateHistoricalOrderList is ", sortedByDateHistoricalOrderList);
                    setHistoricalOrderList(getMyOrderResponse.data);
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
        fetchHistoricalCart();

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
            <span className='text-5xl font-bold'>Historical Order</span>
            <span className='absolute left-[10px] ml-[5px] text-2xl'><NavLink to="/myorder">Back</NavLink></span>
        </div>
        <div className='w-full flex flex-col justify-center items-center'>
          {  historicalOrderList.length > 0 ? 
          <PaginationComponent array={historicalOrderList.map((order:OrderInterface) => {
                return <HistoricalOrderCard key={order.orderId} 
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
                selectedColor = {order.selectedColor}
                orderDate= {order.orderDate}
                orderQuantity= {order.quantity}
                role= {userInfo.role} />
                })} 
            itemsPerPage={2} />
          
           : <div className='text-3xl flex justify-center items-center font-bold mt-[10px]'>No Historical Order</div>}
        
        </div>
        
    </div>
  )
}

export default HistoricalOrders