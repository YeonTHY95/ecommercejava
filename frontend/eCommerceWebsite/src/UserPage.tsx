import React,{use, useContext, useEffect} from 'react'
import { AuthenticationContext } from './RootLayout'
import { useNavigate } from 'react-router-dom';

const UserPage = () => {

  const navigate = useNavigate();
  const { userInfo} = useContext(AuthenticationContext);

  useEffect(() => {
    if (userInfo.isAuthenticated === false) {
      navigate('/login');
    }
  }, [userInfo.isAuthenticated]);

  return (
    <div className='text-5xl font-bold mt-[10px]'>{ userInfo.role === "Buyer" ? <span>Buyer Page</span> : userInfo.role === "Seller" && <span>Seller Page</span>}</div>
  )
}

export default UserPage