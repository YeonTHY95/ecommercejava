import axios from "axios";

export default async function fetchInventoryCategory () {
    const inventoryCategoryResponse = await axios.get('http://localhost:8000/api/getInventoryCategory');
        const inventoryCategory = inventoryCategoryResponse.data;
        console.log("Fetched Inventory Category is ", inventoryCategory);
        return inventoryCategory
}