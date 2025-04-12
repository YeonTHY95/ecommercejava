import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import axios from 'axios';
import SearchInventoryComponent from './SearchInventoryComponent';
import InventoryCard from './InventoryCard';
import PaginationComponent from './PaginationComponent';

const InventorySearchPage = () => {

    const [searchParams] = useSearchParams();
    const searchCategory = searchParams.get("category");
    const searchTitle = searchParams.get("title");

    const [categoryList, setCategoryList] = useState<{category:string}[]>([]);
    const [inventoryList, setInventoryList] = useState<{id:number,title:string, name: string, price:number,imageUrl:string, rating:number, category:string, stockQuantity:number, color:string[]}[]>([]);

    async function searchInventory() {
        console.log("Searching Inventory");
        try {
            const searchInventoryResponse = await axios.get(`http://localhost:8000/api/searchInventory?category=${searchCategory}&title=${searchTitle}`);
            const searchInventoryData = searchInventoryResponse.data;
            console.log("Search Inventory Data is ", searchInventoryData);
            setInventoryList(searchInventoryData);
        }
        catch (error) {
            if (axios.isAxiosError(error)) {
                console.log("Error response is ", error.response);
            }
            else {
                console.log(error); 
            }
        }
    }

    useEffect(() => {
        //console.log("Category : ", searchCategory);
        
        async function fetchCategoryList() {
            const fetchCategoryResponse = await axios.get('http://localhost:8000/api/getInventoryCategory');
            const categoryList = fetchCategoryResponse.data;
            console.log("Fetched Category List is ", categoryList);
            setCategoryList(categoryList);
        }
        fetchCategoryList();

        searchInventory();
    }, []);

    useEffect(()=>{
        searchInventory();
    },[searchParams]);

  return (
    <div>
        {/* <div><SearchInventoryComponent categoryList={categoryList}/></div> */}
        {
            inventoryList && 
            <PaginationComponent 
                array={ inventoryList.map((product:{id:number,title:string, name: string, price:number,imageUrl:string, rating:number, category:string,stockQuantity:number,color:string[]}) => {
                    return <InventoryCard key={product.id} id={product.id} title={product.title} name={product.name} price={product.price} imageUrl={product.imageUrl} rating={product.rating} category={product.category} stockQuantity={product.stockQuantity} color={product.color}/>
                            }
                        )
                    }
                itemsPerPage={5} />
        } 
    </div>
  )
}

export default InventorySearchPage