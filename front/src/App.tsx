import React, {ChangeEvent, useState} from 'react';
import './App.css';
import InputBox from "./components/inputBox";
import {Route, Routes} from "react-router-dom";
import SignUp from "./views/Authentication/SignUp";
import SignIn from "./views/Authentication/SignIn";
import OAuth from "./views/OAuth";

function App() {

    return (
    <>
        <Routes>
            <Route path='/auth'>
                <Route path='sign-up' element={<SignUp />} />
                <Route path='sign-in' element={<SignIn />} />
                <Route path='oauth-response/:token/:expirationTime' element={<OAuth />} />
            </Route>
        </Routes>
    </>
  );
}

export default App;
