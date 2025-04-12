import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';
import {useState, createContext, SetStateAction, useEffect} from 'react';
import axios from 'axios';

export const AuthenticationContext = createContext<{userInfo: {userId: null, role:null, isAuthenticated: boolean}, setUserInfo: React.Dispatch<SetStateAction<{userId: null, role:null,  isAuthenticated: boolean}>>}>({
  userInfo:{userId: null, role:null, isAuthenticated: false} ,
  setUserInfo: () => {},
});

const RootLayout = () => {

  // const inventoryCategory = useLoaderData() ;

  const [userInfo, setUserInfo] = useState({userId: null, role:null, isAuthenticated: false});
  const [inventoryCategory, setInventoryCategory] = useState<{category:string}[]>([]);

  useEffect(()=> {
    async function fetchInventoryCategory () {
      const inventoryCategoryResponse = await axios.get('http://localhost:8000/api/getInventoryCategory');
          const inventoryCategory = inventoryCategoryResponse.data;
          console.log("Fetched Inventory Category is ", inventoryCategory);
          setInventoryCategory(inventoryCategory);
  }
  fetchInventoryCategory();
  },[])

  return (
    <AuthenticationContext.Provider value={{userInfo, setUserInfo}}>
    <div className='w-screen h-screen'>
      <div style={{ 
        width: '100%', 
        backgroundColor: '#fff',
        padding: 0,
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
      }}>
        <Navbar categoryList = {inventoryCategory}/>
      </div>
      
      <div>
        <Outlet />
      </div>
      
    </div>
    </AuthenticationContext.Provider>
  );
};

export default RootLayout;