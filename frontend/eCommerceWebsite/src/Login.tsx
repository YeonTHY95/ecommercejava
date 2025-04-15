import { Form, useNavigate, NavLink, useLocation } from 'react-router-dom';
import { useState, useRef, useContext } from 'react';
import axios from 'axios';
import { AuthenticationContext } from './RootLayout';


const Login = () => {

  const navigate = useNavigate();
  const location = useLocation();

  const [invalidMessage, setInvalidMessage] = useState<string | null>(null);

  const [ signinName, setsigninName] = useState<string>('') ;
  const [ signinPassword, setsigninPassword] = useState<string>('') ;

  const signinFormRef = useRef<HTMLFormElement>(null);

  const {  userInfo, setUserInfo } = useContext(AuthenticationContext);

  const loginAction = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!signinName || !signinPassword) {
        setInvalidMessage("Please key in your username and password");
        signinFormRef.current?.classList.add("formerror");
        return;
    }

    try {
        const axiosSignInResponse = await axios.post('http://localhost:8000/api/login', {
            username: signinName,
            password: signinPassword
        });
    
        if (axiosSignInResponse.status === 200) {
            console.log("axiosSignInResponse data is ",axiosSignInResponse.data);
            const getRoleResponse = await axios.get(`http://localhost:8000/api/userinfo?id=${axiosSignInResponse.data?.user?.pk}`);

            if (getRoleResponse.status === 200) {
                console.log("getRoleResponse data is ", getRoleResponse.data);
                console.log("Role is ", getRoleResponse.data.role);
                const role = getRoleResponse.data.role;
                if (role !== "Buyer" && role !== "Seller") {
                    setInvalidMessage("Invalid role, please log in again");
                    signinFormRef.current?.classList.add("formerror");
                    return;
                }
                setUserInfo({...userInfo, isAuthenticated: true, userId: axiosSignInResponse.data?.user?.pk, role: role});
                console.log("location.state?.from is ", location.state?.from);
                navigate( location.state?.from || '/UserPage', {state: {userId: axiosSignInResponse.data?.user?.pk}});
            }
            else {  
                console.log("Unable to get role, please log in again");
                setInvalidMessage("Unable to get role, please log in again");
            }
            
        } 
    }

    catch(error) {
        if (axios.isAxiosError(error)) {
            console.log("SignIn Error response is ", error.response);
        }
        else {
            console.log("SignIn Error is ", error);
            setInvalidMessage("Invalid username or password");
            signinFormRef.current?.classList.add("formerror");
        }
        
    }

  };


  return (
    <Form onSubmit={loginAction}  id="signinForm" ref={signinFormRef} className='w-full h-[50vh] flex justify-center items-center gap-[5px] m-[10px]'>
            <fieldset className='p-[10px] flex flex-col justify-center items-center' >
                <div className='text-[30px] flex justify-center items-center'><p className='text-center'>Sign In</p></div>
                {
                    invalidMessage && <h3 className='text-red-300'> {invalidMessage}</h3>
                }
                <div className="relative border-[2px] rounded-md p-[20px]">
                    <div className='flex flex-col justify-center gap-[5px]'>
                        <div>
                            <label htmlFor="username" className='text-[20px]'>
                                Username :
                            </label>
                            <input className="p-[3px] border-[2px] rounded-md w-full" id="username" type="text" placeholder="Please key in your user name" name="signinUsername" value= {signinName} onChange = { event => {setsigninName(event.target.value ) ; setInvalidMessage(""); signinFormRef.current?.classList.remove("formerror"); }}  />
                        </div>
                        <div>
                            <label htmlFor="password" className='text-[20px]'>
                                Password :
                            </label>
                            <input className="p-[3px] border-[2px] rounded-md w-full" id="password" type="password" placeholder="Password" name="signinPassword" value= {signinPassword} onChange = { event => {setsigninPassword(event.target.value ); setInvalidMessage(""); signinFormRef.current?.classList.remove("formerror");}} />    
                        </div>
                        
                    </div>
                    <div className='flex justify-center items-center'>
                        <button className='p-[10px] bg-sky-500 rounded-xl hover:bg-sky-700 disabled:opacity-30 m-[10px]' disabled={(signinName && signinPassword) ? false : true }>{"Submit" }</button>
                    </div>
                    <div className='relative bottom-[-15px] text-center w-full flex justify-center items-center'>
                        <NavLink to="/signup"><p className='text-[13px]'>Create New Account</p></NavLink>
                    </div>
                </div>
                
            </fieldset>
        </Form>
  );
};

export default Login;