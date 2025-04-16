import { Link, NavLink, useNavigate  } from 'react-router-dom';
import { useState, useContext } from 'react';
import { AuthenticationContext } from './RootLayout';
import axios from 'axios';
import SearchInventoryComponent from './SearchInventoryComponent';


const Navbar = ({categoryList}:{categoryList:{category:string}[]}) => {
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState<boolean>(false);
  const navigate = useNavigate();
  const { userInfo, setUserInfo } = useContext(AuthenticationContext);

  return (
    <nav style={{
      padding: '0 24px',
      height: '64px',
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
      backgroundColor: '#fff'
    }}>
      <div>
        <Link to="/">
          <p style={{ margin: 0 }}>
            E-Commerce
          </p>
        </Link>
      </div>
      <div>
          <SearchInventoryComponent categoryList={categoryList}/>
        </div>
      <div> {
        !userInfo.isAuthenticated ? (
        <>
        <button onClick={()=> navigate('/login')}>Login</button>
        <button onClick={()=> navigate('/signup')}>Sign Up</button>
        </>) : (
          <>
            {
              userInfo.role === 'Buyer' && (
                <button><NavLink to={`/mycart`}>MyCart</NavLink></button>
              )
            }
            <button onClick={()=> {navigate('/myorder');}} >MyOrder</button>
            <button onClick={async ()=> {

              try{
                await axios.post('http://localhost:8000/api/logout');
              }
              catch(error) {
                if (axios.isAxiosError(error)) {
                console.log("Error response is ", error.response);
                }
                console.log("Error is ", error);
              }
              finally {
                setUserInfo({...userInfo, isAuthenticated: false, role: null, userId: null});
                navigate('/login');
              }
            }}>Logout</button>
          </>
        )
        
        }
        <button className="md:hidden" onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
        >Open</button>
      </div>

      {/* Mobile Menu */}
      {isMobileMenuOpen && (
        <div className="absolute top-[64px] left-0 right-0 p-[16px] shadow-lg shadow-grey-300 md:hidden">
            <div> {
        !userInfo.isAuthenticated ? (
        <>
        <button onClick={()=> navigate('/login')}>Login</button>
        <button onClick={()=> navigate('/signup')}>Sign Up</button>
        </>) : (
          <>
            {
              userInfo.role === 'Buyer' && (
                <button><NavLink to={`/mycart`}>MyCart</NavLink></button>
              )
            }
            <button onClick={()=> {navigate('/myorder');}} >MyOrder</button>
            <button onClick={async ()=> {

              try{
                await axios.post('http://localhost:8000/dj-rest-auth/logout');
              }
              catch(error) {
                if (axios.isAxiosError(error)) {
                console.log("Error response is ", error.response);
                }
                console.log("Error is ", error);
              }
              finally {
                setUserInfo({...userInfo, isAuthenticated: false, role: null, userId: null});
                navigate('/login');
              }
            }}>Logout</button>
          </>
        )
        
        }
        <button className="md:hidden" onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
        >Open</button>
      </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;