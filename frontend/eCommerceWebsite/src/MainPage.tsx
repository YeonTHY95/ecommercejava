import InventoryCard from './InventoryCard';
import { InventoryInterface } from './typeDefinition';
import { useEffect, useState } from 'react';
import axios from 'axios';

const MainPage = () => {
  // const {hotSalesInventoryList} = useLoaderData() ;

  const [hotSalesInventoryList, setHotSalesInventoryList] = useState<InventoryInterface[]>([]);

  useEffect(()=>{
    async function hotSalesInventoryLoader() {

      try {
          const hotSalesInventory = await axios.get('http://localhost:8000/api/getHotSalesInventory');
          console.log("Fetched Hot Sales Inventory is ", hotSalesInventory);
          const hotSalesInventoryListData = hotSalesInventory.data;
          console.log("hotSalesInventoryList type is ", typeof(hotSalesInventoryListData));
          console.log("hotSalesInventoryList is ", hotSalesInventoryListData);
          // const inventoryCategoryResponse = await axios.get('http://localhost:8000/api/getInventoryCategory');
          // const inventoryCategory = inventoryCategoryResponse.data;
          // console.log("Fetched Inventory Category is ", inventoryCategory);
          setHotSalesInventoryList(hotSalesInventoryListData);
      }
      catch (error) {
          if (axios.isAxiosError(error)) {
              console.log("Error response is ", error.response);
          }
  
      }
    }
    hotSalesInventoryLoader();


    
  },[]);

  return (
      <div className='flex flex-col justify-center items-center w-screen gap-[10px]'>
        <div className='mt-[20px]'><span className='text-5xl font-bold'>Top 5 🔥 Hot Sales Inventory : </span></div>
        <div className='flex justify-center items-center'>
          {hotSalesInventoryList.map((product:InventoryInterface) => {
            return <InventoryCard key={product.id} id={product.id} title={product.title} name={product.name} price={product.price} imageUrl={product.imageUrl} rating={product.rating}  category={product.category} stockQuantity={product.stockQuantity} color={product.color} / >
            }
          )}
        
        </div>
      </div>
  );
} ;

export default MainPage;