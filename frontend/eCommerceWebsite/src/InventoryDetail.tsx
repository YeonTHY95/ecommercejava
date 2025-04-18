import { NavLink, useNavigate , useParams} from 'react-router-dom';
import { useContext,useState, useEffect } from 'react';
import { AuthenticationContext } from './RootLayout';
import axios from 'axios';
import type {InventoryDetailInterface} from './typeDefinition'
import clsx from 'clsx';
import axiosWithCredentials from './axiosWithCredentials';


const InventoryDetail = () => {

  let {id} = useParams();
  const navigate = useNavigate();
  const { userInfo } = useContext(AuthenticationContext);
  const [ inventoryDetail, setInventoryDetail] = useState<InventoryDetailInterface | null>(null)
  const [ addToCartQuantity, setAddToCartQuantity] = useState<number>(1)
  const [ selectedColor, setSelectedColor] = useState<string>("")
  
  useEffect(()=> {
    async function fetchInventoryDetail() {
      try {
        const fetchInventoryDetailResponse = await axios.get(`http://localhost:8000/api/getInventoryDetail?id=${id}`);
        if (fetchInventoryDetailResponse.status === 200) {
          console.log("fetchInventoryDetailResponse Data is ",fetchInventoryDetailResponse.data);
          if (fetchInventoryDetailResponse.data === null) {
            throw new Error("fetchInventoryDetailResponse is null");
          }
          else {
            setInventoryDetail(fetchInventoryDetailResponse.data);
          }
        }
        else {
          console.log("Status Code not 200, fetchInventoryDetailResponse Data is ",fetchInventoryDetailResponse.data);
        }
      }
      catch(error) {
        if (axios.isAxiosError(error)) {
          console.log(error.response)
        }
        else {
          console.log(error);
        }
      }

    }
    console.log("Inside useEffect, User Info is ", JSON.stringify(userInfo));
    fetchInventoryDetail()
  },[id]);

  const addToCart = async () => {
    console.log("Inside addToCart Action");
    console.log("User Info is ", JSON.stringify(userInfo));
    if (userInfo.isAuthenticated === false) {
      navigate('/login',{state:{from:location.pathname}});
    }
    else {
      console.log("Add to Cart Clicked for ", inventoryDetail?.title);
      try {
        const addToCartAction = await axiosWithCredentials.post('http://localhost:8000/api/addToCart', {
          user: userInfo.userId,
          inventory: inventoryDetail?.id,
          quantity: addToCartQuantity,
          selectedColor
        });
        
        if (addToCartAction.status === 201) {
          console.log("Add to Cart Response is ", addToCartAction.data);
          navigate('/mycart', {state:{addToast:true}});

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
    return (
      inventoryDetail && 
      <div className='flex flex-col justify-center items-center border-3 p-[10px] rounded-sm m-[10px] w-[90%]'>
        <div className='relative w-full flex-col justify-center items-center m-[10px] mb-[10px]'>
          <span className="text-5xl font-bold "><NavLink to={`/inventoryDetail/${id}`}>{inventoryDetail.title}</NavLink></span>
          {/* <span className="absolute left-[10px] text-[16px] font-bold "><NavLink to="/inventorysearchpage"><span>Search Page</span></NavLink></span> */}
        </div>
          <div className='border-none rounded-sm overflow-hidden'><img src={`/${inventoryDetail.imageUrl}.jpg`} width={500} height={500} alt={inventoryDetail.category}/></div>
          <div><span>Name : </span>{inventoryDetail.name}</div>
          <div><span>Description : </span>{inventoryDetail.description}</div>
          <div><span>Brand : </span>{inventoryDetail.brand}</div>
          <div><span>Category : </span>{inventoryDetail.category}</div>
          <div><span>Color : </span>{inventoryDetail.color.map(color=> <button key={color}className={clsx(selectedColor === color ? "text-red-500" : "text-black")} onClick={()=> setSelectedColor(color)}>{color}</button>)}</div>
          <div><span>Material : </span>{inventoryDetail.material.join(", ")}</div>
          <div><span>Dimension(Width x Height x Length)(cm): </span><span>{inventoryDetail.dimensions.width} x {inventoryDetail.dimensions.height} x {inventoryDetail.dimensions.length}</span></div>
          <div><span>Weight : </span>{inventoryDetail.weight} kg</div>
          <div><span>Rating : </span>{inventoryDetail.rating}</div>
          <div><span>Unit Price : $ </span>{inventoryDetail.price}</div>
          <div><span>In Stock Quantity : </span>{inventoryDetail.quantity}</div>
          <div><span>Status : </span>{inventoryDetail.status}</div>
          <div><span>Seller : </span>{inventoryDetail.seller}</div>
          <div>
            <div>Add to Cart Quantity : <span className='m-[5px] text-xl font-bold'>{addToCartQuantity}</span><button className="m-[5px] font-bold" onClick={()=> setAddToCartQuantity(number=> {
              if (number === 1) return 1
              else return number -1
            })}>-</button><button className="m-[5px] font-bold" onClick={()=> setAddToCartQuantity(number => number + 1)}>+</button></div>
            <button className="disabled:opacity-20" disabled={ selectedColor === "" ? true : false} onClick={addToCart}>Add to Cart</button>
          </div>
      </div>
    )
};

export default InventoryDetail;