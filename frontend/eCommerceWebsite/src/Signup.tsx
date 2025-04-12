import { Form, useNavigate } from 'react-router-dom';
import { useState, useRef } from 'react';
import axios, { isAxiosError } from 'axios';
import './App.css';


const SignUp = () => {
  const [ username, setUsername] = useState<string>("") ;
  const [ password, setPassword] = useState<string>("") ;
  const [ role, setRole] = useState<string | "Buyer" | "Seller">("Buyer") ;
  const [ sex, setSex] = useState<"Male" | "Female" | string>("");
  const [ age, setAge] = useState<number | string>("");
  const [ phoneNumber, setPhoneNumber] = useState<string>("");

  const [ errorMessage , setErrorMessage] = useState('') ;

  const signupForm = useRef<HTMLFormElement>(null);
  const loaderRef = useRef<HTMLDivElement>(null);
  const navigate = useNavigate();

  const signupAction = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    loaderRef.current?.classList.add("loader");

    try {
      const axiosSignUpResponse = await axios.post('http://localhost:8000/api/signup', {
        username: username,
        password: password,
        role,
        sex,
        age,
        phoneNumber,
      });

      console.log(axiosSignUpResponse);

      if (axiosSignUpResponse.status === 201) {
        loaderRef.current?.classList.remove("loader");
        navigate('/login');
      }
    
    } catch (error) {

      if( isAxiosError(error) ) {
        console.log("SignUp Response Error is ", error);
        const errorMessage = Object.entries(error.response?.data);
        console.log(errorMessage[0]);
        setErrorMessage(`${errorMessage[0][0]} : ${errorMessage[0][1]}`);
        signupForm.current?.classList.add("formerror");
        loaderRef.current?.classList.remove("loader");
        console.log("Should have remove loader");
      } 
      else {
        console.log(error);
      }
    }

  };

  return (
    <div>
      <div className='flex justify-center items-center'><div ref={loaderRef}></div></div>
      
      <Form onSubmit={signupAction} ref={signupForm} id="signUpForm" className='w-full h-full flex justify-center items-center gap-[5px]'>
              <fieldset className='flex justify-center items-center'>
                  <legend><p className='text-[30px]'>Create Account</p></legend>
                  <div className="flex justify-center items-center" >
                      <div className="flex flex-col justify-center items-center gap-[5px]">
                          <div>
                              <label htmlFor="username" className='text-[20px]'>
                                  <span>Username : </span>
                              </label>
                              <input className="p-[3px] border-[2px] rounded-md w-full" id="username" type="text" placeholder="Please key in your user name" name="username" value={ username } onChange = {(event) => { setUsername(event.target.value) ; signupForm.current?.classList.remove("formerror"); setErrorMessage(""); }}  required />
                              { errorMessage && <p style={{ color:'red', fontSize:"30px"}}>{errorMessage}</p> }
                          </div>
                          <div>
                              <label htmlFor="password" className='text-[20px]'>
                              <span>Password : </span>
                              </label>
                              <input className="p-[3px] border-[2px] rounded-md w-full" id="password" type="password" placeholder="Password" name="password" value={ password } onChange = {(event) => { setPassword(event.target.value) ;  signupForm.current?.classList.remove("formerror");setErrorMessage("");}}  required />
                          </div>
                          <div>
                              <label htmlFor="age" className='text-[20px]'>
                              <span>Age : </span>
                              </label>
                              <input className="p-[3px] border-[2px] rounded-md w-full" id="age" type="number" placeholder="Age" name="age" value={ age } onChange = {(event) => { setAge(event.target.value) ;  signupForm.current?.classList.remove("formerror");setErrorMessage(""); }}  required />
                          </div>
                          <div>
                              <label htmlFor="phonenumber" className='text-[20px]'>
                              <span>Phone Number : </span>
                              </label>
                              <input className="p-[3px] border-[2px] rounded-md w-full" id="phonenumber" type="text" placeholder="Phone Number" name="phoneNumber" value={ phoneNumber } onChange = {(event) => { setPhoneNumber(event.target.value) ;  signupForm.current?.classList.remove("formerror");setErrorMessage("");}}  required />
                          </div>
                          <div>
                              <label htmlFor="male" >
                                  <input id="male" type="radio" name="sex" value="Male"  checked={ sex === "Male" } onChange={ () => {setSex && setSex("Male"); setErrorMessage(""); }}/>
                                  <span className='text-[20px]'> Male </span>
                              </label>
                              <label htmlFor="female" >
                                  <input id="female" type="radio" name="sex" value="Female" checked={ sex === "Female" } onChange={ () => {setSex && setSex("Female"); setErrorMessage("");}}/>
                                  <span className='text-[20px]'> Female </span>
                              </label>
                          </div>
                          <div>
                              <label htmlFor="buyer" >
                                  <input id="buyer" type="radio" name="role" value="Buyer" defaultChecked= {true}  onChange={ () => { setRole && setRole("Buyer"); setErrorMessage("");}}/>
                                  <span className='text-[20px]'> Buyer </span>
                              </label>
                              <label htmlFor="seller" >
                                  <input id="seller" type="radio" name="role" value="Seller" checked={ role === "Seller" } onChange={ () => {setRole && setRole("Seller"); setErrorMessage("");}}/>
                                  <span className='text-[20px]'> Seller </span>
                              </label>
                          </div> 
                          <div>
                              <button className='p-[10px] bg-sky-500 rounded-xl hover:bg-sky-700 disabled:opacity-30' disabled={ (username && password && age && sex && phoneNumber && role ) ? false : true }>{"Submit"}</button>
                          </div>
                      </div>
                      
                  </div>
                  
              </fieldset>
      </Form>
    </div>
  );
};

export default SignUp;