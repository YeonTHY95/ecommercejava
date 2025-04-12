import axios from "axios"

export default async function hotSalesInventoryLoader() {

    try {
        const hotSalesInventory = await axios.get('http://localhost:8000/api/getHotSalesInventory');
        console.log("Fetched Hot Sales Inventory is ", hotSalesInventory);
        const hotSalesInventoryList = hotSalesInventory.data;
        console.log("hotSalesInventoryList type is ", typeof(hotSalesInventoryList));
        console.log("hotSalesInventoryList is ", hotSalesInventoryList);
        // const inventoryCategoryResponse = await axios.get('http://localhost:8000/api/getInventoryCategory');
        // const inventoryCategory = inventoryCategoryResponse.data;
        // console.log("Fetched Inventory Category is ", inventoryCategory);
        return {hotSalesInventoryList};
    }
    catch (error) {
        if (axios.isAxiosError(error)) {
            console.log("Error response is ", error.response);
        }

    }
    

    
}