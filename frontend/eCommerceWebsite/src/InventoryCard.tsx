import { NavLink, useNavigate, useLocation } from 'react-router-dom';
import { useContext, useState } from 'react';
// import { AuthenticationContext } from './App';
import { AuthenticationContext } from './RootLayout';
import axios from 'axios';
import { InventoryInterface } from './typeDefinition';
import clsx from 'clsx';
import toast, { Toaster } from 'react-hot-toast';
import axiosWithCredentials from './axiosWithCredentials';


const InventoryCard = ({id,title, name, rating, price,imageUrl,category,stockQuantity,color}:InventoryInterface) => {

  const navigate = useNavigate();
  const location = useLocation();
  const { userInfo } = useContext(AuthenticationContext);
  const [ selectedColor, setSelectedColor] = useState<string>("")


  const addToCart = async () => {
    console.log("Inside addToCart Action");
    if (userInfo.isAuthenticated === false) {
      navigate('/login',{state:{from:location.pathname}});
    }
    else {
      console.log("Add to Cart Clicked for ", title);
      try {
        const addToCartAction = await axiosWithCredentials.post('http://localhost:8000/api/addToCart', {
          user: userInfo.userId,
          inventory: id,
          quantity: 1,
          selectedColor
        });
        
        if (addToCartAction.status === 201) {
          console.log("Add to Cart Response is ", addToCartAction.data);
          setSelectedColor("");
          toast.success('Successfully Add to Cart!');
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
    <div key={id}className='border-3 p-[10px] rounded-sm m-[10px] flex flex-col justify-center items-center w-[90%] min-h-[500px] h-[600px]'>
        <Toaster />
        <div className='border-none rounded-sm overflow-hidden'><img src={`/${imageUrl}.jpg`} width={300} height={300} alt={category} /></div>
        <div className='text-3xl font-bold'><NavLink to={`/inventoryDetail/${id}`}>{title}</NavLink></div>
        <div>{name}</div>
        <div><span>Category : </span>{category}</div>
        <div><span>Color : </span>{color.map(c=> <button key={c} className={clsx(selectedColor === c ? "text-red-500" : "text-black")} onClick={()=> setSelectedColor(c)}>{c}</button>)}</div>
        <div><span>Rating : </span>{rating}</div>
        <div><span>Unit Price : $ </span>{price}</div>
        <div><button className="disabled:opacity-20" onClick={addToCart} disabled={ selectedColor=== "" ? true : false}>Add to Cart</button></div>
    </div>
  )
}

export default InventoryCard