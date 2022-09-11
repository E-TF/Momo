import React from 'react';
import './App.css';
import Header from "./components/Header";
import {Route, Routes} from "react-router-dom";
import Login from "./components/Login";
import Signup from "./components/Signup";
import CallBack from "./components/CallBack";

function App() {

    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<Header/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/signup" element={<Signup/>}/>
                <Route path='/callBack/:accessToken/:refreshToken' element={<CallBack/>}/>
            </Routes>
        </div>
    );
};

export default App;
