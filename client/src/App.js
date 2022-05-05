import './App.css';
import MyNav from "./features/MyNav";
import 'bootstrap/dist/css/bootstrap.min.css'
import {BrowserRouter} from "react-router-dom";
import {useState} from "react";
import Login from "./features/Login";


function App() {

    const [isLoggedIn,setIsLoggedIn]=useState( !!sessionStorage.getItem("token"))
    return (
        <BrowserRouter>
            {isLoggedIn && <MyNav setIsLoggedIn={setIsLoggedIn}/>}
            {!isLoggedIn && <Login setIsLoggedIn={setIsLoggedIn}/>}
        </BrowserRouter>

    );
}

export default App;
