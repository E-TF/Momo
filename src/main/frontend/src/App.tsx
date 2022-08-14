import React from 'react';
import './App.css';
import Header from "./components/Header";
import {Route, Routes} from "react-router-dom";
import Login from "./components/Login";
import Signup from "./components/Signup";
import Callback from "./components/Callback";
import NewWindow from "./components/NewWindow";

interface User {
    id: number,
    imageUrl: string,
    name: string
}

function App() {

    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<Header/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/signup" element={<Signup/>}/>
                <Route path='/callback' element={<Callback/>}/>
                <Route path='/newWindow/:accessToken/:refreshToken' element={<NewWindow/>}/>
            </Routes>
        </div>
    );
}

export default App;
