import {useState, useEffect, useContext, useRef} from 'react';
import { AuthenticationContext } from './RootLayout';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import axiosWithCredentials,{requestInterceptor, responseInterceptor} from './axiosWithCredentials';
import { CartInterface,InventoryInterface } from './typeDefinition';
import AddToCartCard from './AddToCartCard';
import './App.css';
import toast, { Toaster } from 'react-hot-toast';


const MyCart = () => {

    const { userInfo } = useContext(AuthenticationContext);
    const navigate = useNavigate();
    const [addToCartList, setAddToCartList] = useState<CartInterface[]>([]);
    const [totalPrice, setTotalPrice] = useState<number>(0);
    const loaderRef = useRef<HTMLDivElement>(null);
    const locationHook = useLocation();

    useEffect(() => {

        const controller = new AbortController();
        async function fetchMyCart() {
            try {
                const getMyCartResponse = await axiosWithCredentials.get(`http://localhost:8000/api/myCart?id=${userInfo.userId}`, { 
                    signal: controller.signal 
                });

                if ( getMyCartResponse.status === 200) {
                    console.log("My Cart Response is ", getMyCartResponse.data);
                    const inventoryList = getMyCartResponse.data.map((i: {inventory:InventoryInterface[],quantity:number,selectedColor:string}) => {
                        return {
                            id: i.inventory[0].id,
                            title:i.inventory[0].title, 
                            name: i.inventory[0].name, 
                            price:i.inventory[0].price,
                            imageUrl:i.inventory[0].imageUrl, 
                            rating:i.inventory[0].rating,
                            category:i.inventory[0].category,
                            quantity:i.quantity,
                            colorList : i.inventory[0].color,
                            selectedColor:i.selectedColor
                        }
                    });
                    console.log("Inventory List is ", inventoryList);
                    setAddToCartList(inventoryList);
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

        if ( locationHook.state?.addToast) {
            toast.success('Successfully Add to Cart!', { removeDelay: 3000 });
        }

        return () => {
            controller.abort(); // Request will cancel and abort when unmounts
            axios.interceptors.request.eject(requestInterceptor);
            axios.interceptors.response.eject(responseInterceptor);
        }
    }, []);

    useEffect(() => {
        
        setTotalPrice(addToCartList.reduce((acc, product) => acc + product.price*product.quantity
        , 0));
    }, [addToCartList]);

    useEffect(() => {
        if (userInfo.isAuthenticated === false) {
            navigate('/login');
        }
    }, [userInfo.isAuthenticated]);

    const makeOrder = async () => {
        // Make Order
        loaderRef.current?.classList.add('loader');
        console.log("Inside Make Order");
        console.log("Total Price is ", totalPrice);
        console.log("Add to Cart List is ", addToCartList);
        try {
            const makeOrderResponse = await axios.post('http://localhost:8000/api/makeOrder', {
                user: userInfo.userId,
                totalPrice: totalPrice,
                orderList: addToCartList
            });

            if (makeOrderResponse.status === 201) {
                console.log("Make Order Response is ", makeOrderResponse.data);
                setAddToCartList([]);
                setTotalPrice(0);
                navigate('/myorder');
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
        finally {
            loaderRef.current?.classList.remove('loader');
        }  

    }

  return (
    <div className='relative flex flex-col justify-center items-center'>
        <Toaster />
        <div className='text-5xl font-bold'>MyCart</div>
        <div className='w-full'>
          { addToCartList.length > 0 ? addToCartList.map((product:CartInterface) => {
            return <AddToCartCard key={product.id+product.selectedColor} id={product.id} title={product.title} name={product.name} price={product.price} imageUrl={product.imageUrl} rating={product.rating}  category={product.category} quantity={product.quantity} colorList={product.colorList} currentSelectedColor={product.selectedColor} cartList={addToCartList} setCartList={setAddToCartList}/>
            } 
            
            ): <div className='text-3xl flex justify-center items-center font-bold mt-[10px]'>No Cart</div> 
          }
        
        </div>
        { addToCartList.length > 0 && <div className='sticky bottom-0 h-[100px] w-full bg-cyan-300 border-5 rounded-sm flex justify-around items-center'>
            <div><span className='text-5xl'>Total Price : $</span></div> 
            <div>
                <span className='text-6xl font-bold'>{
                // (Math.floor((totalPrice) * 100) / 100).toFixed(2)}
                totalPrice.toFixed(2)
                }
                </span>
            </div>
            <button onClick={makeOrder}>Make Order</button>
            <div ref={loaderRef} ></div>
        </div>
        }
    </div>
  )
}

export default MyCart