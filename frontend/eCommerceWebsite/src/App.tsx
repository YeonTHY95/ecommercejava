import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './RootLayout';
import MainPage from './MainPage';
import InventoryDetail from './InventoryDetail';
import Login from './Login';
import SignUp from './Signup';
import UserPage from './UserPage';
import hotSalesInventoryLoader from './hotSalesInventoryLoader';
import InventorySearchPage from './InventorySearchPage';
import MyCart from './MyCart';
import MyOrder from './MyOrder';
import HistoricalOrders from './HistoricalOrders';
import fetchInventoryCategory from './fetchInventoryCategory';



const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    // loader: fetchInventoryCategory,
    children: [
      {
        index: true,
        element: <MainPage />,
        // loader : hotSalesInventoryLoader,
      },
      {
        path: '/inventoryDetail/:id',
        element: <InventoryDetail />,
      },
      {
        path: '/login',
        element: <Login />,
      },
      {
        path: '/signup',
        element: <SignUp />,
      },
      {
        path: '/userpage',
        element: <UserPage />,
      },
      {
        path: '/inventorysearchpage',
        element: <InventorySearchPage />,
      },
      {
        path: '/mycart',
        element: <MyCart />,
      },
      {
        path: '/myorder',
        element: <MyOrder />,
      },
      {
        path: '/historicalOrders',
        element: <HistoricalOrders />,
      },
    ],
  },
]);


function App() {
  return <RouterProvider router={router} />
}

export default App;