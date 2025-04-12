import React,{useState, useEffect, useContext} from 'react'
import { NavLink , useNavigate} from 'react-router-dom';
import axios from 'axios';
import clsx from 'clsx';
import { AuthenticationContext } from './RootLayout';

const SearchInventoryComponent = ({categoryList}:{categoryList:{category:string}[]}) => {
  
    const [search, setSearch] = useState<string>("");
    const [category, setCategory] = useState<string>("");
    const [isFocus, setIsFocus] = useState(false);
    const [matchResult, setMatchResult] = useState<{id:number, title:string}[]>([]);
    const [inventoryInfoList, setInventoryInfoList] = useState<{id:number, title:string}[]>([]);
    const {userInfo} = useContext(AuthenticationContext)

    const navigate = useNavigate();

    const handleFocus = () => {
        setIsFocus(true);
    };
    const handleBlur = () => {
        setTimeout( ()=> setIsFocus(false), 1000);
    };

    useEffect(()=> {
        console.log("Inside useEffect of SearchInventoryComponent, userInfo is ", JSON.stringify(userInfo));
        async function fetchInventoryList() {
            const fetchInventoryResponse = await axios.get('http://localhost:8000/api/getInventoryTitleList');
            const inventoryInfoList = fetchInventoryResponse.data;
            console.log("Fetched Inventory Info List is ", inventoryInfoList);
            setInventoryInfoList(inventoryInfoList);
        }
        fetchInventoryList();
    },[]);

    useEffect(()=> {
        const matchResultFilter = inventoryInfoList.filter( inventory => {
            console.log("Search inside is ", search);
            console.log("fetchNameList's length is ", inventoryInfoList.length);
            if (search === "") {
                return false;
            }
            return inventory.title.toLowerCase().includes(search);
        });
        //console.log(`fetchNameList is ${fetchNameList}`);
        console.log(`matchResultFilter is ${matchResultFilter}`);
        setMatchResult(matchResultFilter);
    },[search]);

//action={`/inventorysearchpage`}

    const formSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        setSearch("");
        // setCategory("");
        navigate(`/inventorysearchpage?title=${search}&category=${category}`);
    }

  return (
    <div className='relative w-full max-w-[500px] m-auto'>
        <form onSubmit={formSubmit} className='w-full h-min flex border-3 rounded-md justify-between gap-[2px]'>
            <input onFocus={handleFocus} onBlur={handleBlur} name="title" className='min-h-auto w-full m-[10px] outline-none caret-cyan-900' placeholder='Search by Name' value={ search || "" } onChange={(event:React.FormEvent<HTMLInputElement>)=> {
                    setSearch(event.currentTarget.value);
                }
            }/>
            <select className="outline-rose-500 border-lime-500" id="category" name="category" value={category} onChange={e => setCategory(e.target.value)}>
                <option value="" >All Categories</option>
                {categoryList && categoryList.map( c => (
                    <option key={c.category} value={c.category}>
                    {c.category}
                    </option>
                ))}
            </select>
            <button type="submit" className={clsx('p-2 disabled:opacity-20')} disabled={ search === "" ? true : false }><img src="/magnifier.png" width={32} height={32} alt="Find" /></button>
        </form>
        {isFocus && (<div className='absolute top-[45px] m-[5px] z-15 bg-white'>
            <ul>
            { (matchResult.length > 0) && matchResult.slice(0,5).map( MatchedName => <li key={MatchedName.id} className='border-2 p-[5px]'><NavLink to={`/inventoryDetail/${MatchedName.id}`} >{MatchedName.title}</NavLink></li> ) }
            </ul>
        </div>)
        }
    </div>
  )

}

export default SearchInventoryComponent